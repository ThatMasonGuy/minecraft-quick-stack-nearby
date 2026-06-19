param(
    [switch]$DryRun,
    [switch]$ReplaceGallery
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Net.Http

$repoRoot = Split-Path -Parent $PSScriptRoot
$apiBase = 'https://api.modrinth.com/v2'
$userAgent = 'ThatMasonGuy/quick-stack-nearby-gallery-sync'

$projects = @(
    @{
        Section = 'Quick Stack Nearby'
        GalleryDir = 'QuickStackNearby'
        ProjectIdProperty = 'modrinth_project_id'
    }
)

function ConvertTo-QueryValue([string]$Value) {
    return [System.Uri]::EscapeDataString($Value)
}

function Get-ModrinthToken {
    if (-not [string]::IsNullOrWhiteSpace($env:MODRINTH_TOKEN)) {
        return $env:MODRINTH_TOKEN.Trim()
    }

    $envPath = Join-Path $repoRoot '.env'
    if (-not (Test-Path -LiteralPath $envPath)) {
        return $null
    }

    foreach ($line in Get-Content -LiteralPath $envPath) {
        if ($line -match '^\s*MODRINTH_TOKEN\s*=\s*(.+?)\s*$') {
            return $Matches[1].Trim().Trim('"').Trim("'")
        }
    }

    return $null
}

function Read-GradleProperties {
    $path = Join-Path $repoRoot 'gradle.properties'
    $properties = @{}
    foreach ($line in Get-Content -LiteralPath $path) {
        if ($line -match '^\s*([^#][^=]+?)\s*=\s*(.*?)\s*$') {
            $properties[$Matches[1].Trim()] = $Matches[2].Trim()
        }
    }
    return $properties
}

function Get-SectionField([string]$Section, [string]$Label) {
    $pattern = '(?m)^\s*-\s*' + [regex]::Escape($Label) + ':\s*(?:`(?<tick>[^`\r\n]+)`|(?<plain>[^\r\n]+))\s*$'
    $match = [regex]::Match($Section, $pattern)
    if (-not $match.Success) {
        return $null
    }

    $value = if ($match.Groups['tick'].Success) {
        $match.Groups['tick'].Value
    } else {
        $match.Groups['plain'].Value
    }

    return $value.Trim()
}

function Read-ProjectMetadata([string]$Section) {
    $metadata = @{}
    $fields = @(
        @{ Label = 'Client side'; Key = 'client_side' },
        @{ Label = 'Server side'; Key = 'server_side' },
        @{ Label = 'License ID'; Key = 'license_id' },
        @{ Label = 'Source URL'; Key = 'source_url' },
        @{ Label = 'Issues URL'; Key = 'issues_url' },
        @{ Label = 'Icon'; Key = 'Icon' }
    )

    foreach ($field in $fields) {
        $value = Get-SectionField -Section $Section -Label $field.Label
        if (-not [string]::IsNullOrWhiteSpace($value)) {
            $metadata[$field.Key] = $value
        }
    }

    $validSideValues = @('required', 'optional', 'unsupported', 'unknown')
    foreach ($sideKey in @('client_side', 'server_side')) {
        if ($metadata.ContainsKey($sideKey) -and ($validSideValues -notcontains $metadata[$sideKey])) {
            throw "$sideKey must be one of: $($validSideValues -join ', ')."
        }
    }

    return $metadata
}

function Read-PageSections {
    $path = Join-Path $repoRoot 'gradle/modrinth-project-pages.md'
    $markdown = Get-Content -LiteralPath $path -Raw
    $sections = @{}

    for ($i = 0; $i -lt $projects.Count; $i++) {
        $project = $projects[$i]
        $nextHeading = if ($i -lt ($projects.Count - 1)) { [regex]::Escape($projects[$i + 1].Section) } else { $null }
        $pattern = if ($nextHeading) {
            '(?s)^## ' + [regex]::Escape($project.Section) + '\r?\n(?<section>.*?)(?=^## ' + $nextHeading + '\r?\n)'
        } else {
            '(?s)^## ' + [regex]::Escape($project.Section) + '\r?\n(?<section>.*)\z'
        }

        $match = [regex]::Match($markdown, $pattern, [System.Text.RegularExpressions.RegexOptions]::Multiline)
        if (-not $match.Success) {
            throw "Could not find project page section '$($project.Section)'."
        }

        $section = $match.Groups['section'].Value
        $summaryMatch = [regex]::Match($section, '(?s)- Summary:\s*\r?\n\r?\n(?<summary>.*?)(?=\r?\n\r?\n### Description Markdown)')
        if (-not $summaryMatch.Success) {
            throw "Could not find summary in '$($project.Section)'."
        }

        $bodyMatch = [regex]::Match($section, '(?s)```markdown\r?\n(?<body>.*?)\r?\n```')
        if (-not $bodyMatch.Success) {
            throw "Could not find description markdown fence in '$($project.Section)'."
        }

        $sections[$project.GalleryDir] = @{
            Summary = $summaryMatch.Groups['summary'].Value.Trim()
            Body = $bodyMatch.Groups['body'].Value.Trim()
            Metadata = Read-ProjectMetadata -Section $section
        }
    }

    return $sections
}

function Read-GalleryMetadata {
    $path = Join-Path $repoRoot 'gallery/metadata.json'
    return Get-Content -LiteralPath $path -Raw | ConvertFrom-Json
}

function Resolve-RepoPath([string]$Path) {
    if ([string]::IsNullOrWhiteSpace($Path)) {
        return $null
    }

    $resolved = if ([System.IO.Path]::IsPathRooted($Path)) {
        [System.IO.Path]::GetFullPath($Path)
    } else {
        [System.IO.Path]::GetFullPath((Join-Path $repoRoot $Path))
    }

    if (-not (Test-Path -LiteralPath $resolved -PathType Leaf)) {
        throw "Could not find file '$Path'."
    }

    return $resolved
}

function Get-OrderFromFileName([string]$FileName) {
    if ($FileName -notmatch '^(\d+)_') {
        throw "Gallery image '$FileName' must start with a numeric order prefix."
    }
    return [int]$Matches[1]
}

function Get-LocalGalleryPlan($Project, $Metadata) {
    $galleryDir = Join-Path $repoRoot ('gallery/' + $Project.GalleryDir)
    $bannerDir = Join-Path $galleryDir 'banner'
    $descriptionDir = Join-Path $galleryDir 'description_images'

    $rootImages = @(Get-ChildItem -LiteralPath $galleryDir -File -Filter '*.png' |
        Sort-Object @{ Expression = { Get-OrderFromFileName $_.Name } }, Name)
    $bannerImages = @(Get-ChildItem -LiteralPath $bannerDir -File -Filter '*.png')
    $descriptionImages = @(Get-ChildItem -LiteralPath $descriptionDir -File -Filter '*.png' |
        Sort-Object @{ Expression = { Get-OrderFromFileName $_.Name } }, Name)

    if ($bannerImages.Count -ne 1) {
        throw "$($Project.GalleryDir) must have exactly one banner selector image."
    }

    $metadataProject = $Metadata.($Project.GalleryDir)
    if ($null -eq $metadataProject) {
        throw "Missing metadata for $($Project.GalleryDir)."
    }

    $metadataByFile = @{}
    foreach ($image in @($metadataProject.images)) {
        $metadataByFile[$image.file] = $image
    }

    $rootNames = @($rootImages | ForEach-Object Name)
    $metadataNames = @(@($metadataProject.images) | ForEach-Object file)
    foreach ($name in $rootNames) {
        if (-not $metadataByFile.ContainsKey($name)) {
            throw "Missing metadata entry for $($Project.GalleryDir)/$name."
        }
    }
    foreach ($name in $metadataNames) {
        if ($rootNames -notcontains $name) {
            throw "Metadata references missing gallery image $($Project.GalleryDir)/$name."
        }
    }

    $bannerName = $bannerImages[0].Name
    if ($rootNames -notcontains $bannerName) {
        throw "$($Project.GalleryDir) banner selector '$bannerName' is not one of the root gallery images."
    }

    $descriptionNames = @()
    foreach ($descriptionImage in $descriptionImages) {
        if ($rootNames -notcontains $descriptionImage.Name) {
            throw "$($Project.GalleryDir) description selector '$($descriptionImage.Name)' is not one of the root gallery images."
        }
        $descriptionNames += $descriptionImage.Name
    }

    $items = @()
    foreach ($rootImage in $rootImages) {
        $imageMetadata = $metadataByFile[$rootImage.Name]
        $items += [PSCustomObject]@{
            File = $rootImage.Name
            Path = $rootImage.FullName
            Order = Get-OrderFromFileName $rootImage.Name
            Title = [string]$imageMetadata.title
            Description = [string]$imageMetadata.description
            Featured = ($rootImage.Name -eq $bannerName)
            InDescription = ($descriptionNames -contains $rootImage.Name)
        }
    }

    return $items
}

function New-ModrinthHeaders([string]$Token, [switch]$Json) {
    $headers = @{
        'User-Agent' = $userAgent
        'Accept' = 'application/json'
    }

    if (-not [string]::IsNullOrWhiteSpace($Token)) {
        $headers['Authorization'] = $Token
    }

    if ($Json) {
        $headers['Content-Type'] = 'application/json'
    }

    return $headers
}

function Invoke-ModrinthWrite([string]$Method, [string]$Uri, [string]$Token, [string]$Body = $null) {
    $client = [System.Net.Http.HttpClient]::new()
    $request = $null
    $content = $null
    try {
        $client.DefaultRequestHeaders.UserAgent.ParseAdd($userAgent)
        $client.DefaultRequestHeaders.Accept.ParseAdd('application/json')
        $client.DefaultRequestHeaders.TryAddWithoutValidation('Authorization', $Token) | Out-Null

        $request = [System.Net.Http.HttpRequestMessage]::new([System.Net.Http.HttpMethod]::new($Method), $Uri)
        if ($null -ne $Body) {
            $content = [System.Net.Http.StringContent]::new($Body, [System.Text.Encoding]::UTF8, 'application/json')
            $request.Content = $content
        }

        $response = $client.SendAsync($request).GetAwaiter().GetResult()
        if (-not $response.IsSuccessStatusCode) {
            $responseBody = $response.Content.ReadAsStringAsync().GetAwaiter().GetResult()
            throw "Modrinth $Method request failed with HTTP $([int]$response.StatusCode): $responseBody"
        }
    } finally {
        if ($null -ne $content) {
            $content.Dispose()
        }
        if ($null -ne $request) {
            $request.Dispose()
        }
        $client.Dispose()
    }
}

function Get-ModrinthProject([string]$ProjectId, [string]$Token) {
    $headers = New-ModrinthHeaders -Token $Token
    return Invoke-RestMethod -Method Get -Uri "$apiBase/project/$ProjectId" -Headers $headers
}

function Remove-GalleryImage([string]$ProjectId, [string]$Token, [string]$Url) {
    $encodedUrl = ConvertTo-QueryValue $Url
    Invoke-ModrinthWrite -Method 'DELETE' -Uri "$apiBase/project/$ProjectId/gallery?url=$encodedUrl" -Token $Token
}

function Send-GalleryImage([string]$ProjectId, [string]$Token, $Item) {
    $query = @(
        'ext=png'
        'featured=' + ([string]$Item.Featured).ToLowerInvariant()
        'title=' + (ConvertTo-QueryValue $Item.Title)
        'description=' + (ConvertTo-QueryValue $Item.Description)
        'ordering=' + $Item.Order
    ) -join '&'

    $client = [System.Net.Http.HttpClient]::new()
    $content = $null
    try {
        $client.DefaultRequestHeaders.UserAgent.ParseAdd($userAgent)
        $client.DefaultRequestHeaders.Accept.ParseAdd('application/json')
        $client.DefaultRequestHeaders.TryAddWithoutValidation('Authorization', $Token) | Out-Null

        $bytes = [System.IO.File]::ReadAllBytes($Item.Path)
        $content = [System.Net.Http.ByteArrayContent]::new($bytes)
        $content.Headers.ContentType = [System.Net.Http.Headers.MediaTypeHeaderValue]::Parse('image/png')

        $response = $client.PostAsync("$apiBase/project/$ProjectId/gallery?$query", $content).GetAwaiter().GetResult()
        if (-not $response.IsSuccessStatusCode) {
            $body = $response.Content.ReadAsStringAsync().GetAwaiter().GetResult()
            throw "Gallery upload failed for '$($Item.File)' with HTTP $([int]$response.StatusCode): $body"
        }
    } finally {
        if ($null -ne $content) {
            $content.Dispose()
        }
        $client.Dispose()
    }
}

function Update-GalleryImageMetadata([string]$ProjectId, [string]$Token, [string]$Url, $Item) {
    $query = @(
        'url=' + (ConvertTo-QueryValue $Url)
        'featured=' + ([string]$Item.Featured).ToLowerInvariant()
        'title=' + (ConvertTo-QueryValue $Item.Title)
        'description=' + (ConvertTo-QueryValue $Item.Description)
        'ordering=' + $Item.Order
    ) -join '&'

    Invoke-ModrinthWrite -Method 'PATCH' -Uri "$apiBase/project/$ProjectId/gallery?$query" -Token $Token
}

function Get-IconContentType([string]$Extension) {
    switch ($Extension) {
        'png' { return 'image/png' }
        'jpg' { return 'image/jpeg' }
        'jpeg' { return 'image/jpeg' }
        'webp' { return 'image/webp' }
        default { throw "Unsupported Modrinth project icon extension '$Extension'." }
    }
}

function Send-ModrinthProjectIcon([string]$ProjectId, [string]$Token, [string]$IconPath) {
    $file = Get-Item -LiteralPath $IconPath
    if ($file.Length -gt (256 * 1024)) {
        throw "Modrinth project icon '$IconPath' is $($file.Length) bytes; the maximum is 262144 bytes."
    }

    $extension = [System.IO.Path]::GetExtension($IconPath).TrimStart('.').ToLowerInvariant()
    if ($extension -eq 'jpeg') {
        $extension = 'jpg'
    }

    $contentType = Get-IconContentType -Extension $extension
    $client = [System.Net.Http.HttpClient]::new()
    $request = $null
    $content = $null
    try {
        $client.DefaultRequestHeaders.UserAgent.ParseAdd($userAgent)
        $client.DefaultRequestHeaders.Accept.ParseAdd('application/json')
        $client.DefaultRequestHeaders.TryAddWithoutValidation('Authorization', $Token) | Out-Null

        $bytes = [System.IO.File]::ReadAllBytes($IconPath)
        $content = [System.Net.Http.ByteArrayContent]::new($bytes)
        $content.Headers.ContentType = [System.Net.Http.Headers.MediaTypeHeaderValue]::Parse($contentType)

        $request = [System.Net.Http.HttpRequestMessage]::new([System.Net.Http.HttpMethod]::new('PATCH'), "$apiBase/project/$ProjectId/icon?ext=$extension")
        $request.Content = $content

        $response = $client.SendAsync($request).GetAwaiter().GetResult()
        if (-not $response.IsSuccessStatusCode) {
            $body = $response.Content.ReadAsStringAsync().GetAwaiter().GetResult()
            throw "Project icon upload failed with HTTP $([int]$response.StatusCode): $body"
        }
    } finally {
        if ($null -ne $content) {
            $content.Dispose()
        }
        if ($null -ne $request) {
            $request.Dispose()
        }
        $client.Dispose()
    }
}

function Resolve-BodyGalleryUrls([string]$GalleryDir, [string]$Body, $Items, $LiveProject) {
    $galleryByTitle = @{}
    foreach ($galleryImage in $LiveProject.gallery) {
        if (-not [string]::IsNullOrWhiteSpace($galleryImage.title)) {
            $galleryByTitle[$galleryImage.title] = $galleryImage
        }
    }

    $rendered = $Body
    foreach ($item in $Items) {
        $liveImage = $galleryByTitle[$item.Title]
        if ($null -eq $liveImage) {
            throw "Could not find uploaded gallery image '$($item.Title)' on $GalleryDir."
        }

        if ($item.InDescription) {
            $placeholder = "modrinth-gallery://$GalleryDir/$($item.File)"
            if (-not $rendered.Contains($placeholder)) {
                throw "Description for $GalleryDir does not contain expected placeholder '$placeholder'."
            }
            $rendered = $rendered.Replace($placeholder, [string]$liveImage.url)
        }
    }

    if ($rendered -match 'modrinth-gallery://') {
        throw "Unresolved gallery placeholder remains in $GalleryDir description."
    }

    return $rendered
}

function Update-ModrinthProjectPage([string]$ProjectId, [string]$Token, [string]$Summary, [string]$Body, $ProjectMetadata) {
    $payloadObject = @{
        description = $Summary
        body = $Body
    }

    foreach ($key in @('client_side', 'server_side', 'license_id', 'source_url', 'issues_url')) {
        if ($ProjectMetadata.ContainsKey($key)) {
            $payloadObject[$key] = $ProjectMetadata[$key]
        }
    }

    $payload = $payloadObject | ConvertTo-Json -Depth 4

    Invoke-ModrinthWrite -Method 'PATCH' -Uri "$apiBase/project/$ProjectId" -Token $Token -Body $payload
}

function Assert-ProjectMetadataReadback($Project, $ProjectMetadata, $Readback) {
    $statusProperty = $Readback.PSObject.Properties['status']
    $requestedStatusProperty = $Readback.PSObject.Properties['requested_status']
    $status = if ($null -eq $statusProperty) { $null } else { [string]$statusProperty.Value }
    $requestedStatus = if ($null -eq $requestedStatusProperty) { $null } else { [string]$requestedStatusProperty.Value }
    $sideReadbackMayLag = ($status -in @('draft', 'processing')) -and ($requestedStatus -eq 'approved')

    foreach ($key in @('client_side', 'server_side', 'license_id', 'source_url', 'issues_url')) {
        if (-not $ProjectMetadata.ContainsKey($key)) {
            continue
        }

        if ($key -eq 'license_id') {
            $licenseProperty = $Readback.PSObject.Properties['license']
            $actual = if ($null -eq $licenseProperty -or $null -eq $licenseProperty.Value) {
                $null
            } else {
                [string]$licenseProperty.Value.id
            }
        } else {
            $property = $Readback.PSObject.Properties[$key]
            $actual = if ($null -eq $property) { $null } else { [string]$property.Value }
        }
        $expected = [string]$ProjectMetadata[$key]
        if ($actual -ne $expected) {
            if (($key -in @('client_side', 'server_side')) -and $sideReadbackMayLag -and $actual -eq 'unknown') {
                Write-Warning "Readback $key is still 'unknown' while $($Project.GalleryDir) is $status with requested_status=$requestedStatus; keeping expected '$expected' in source metadata."
                continue
            }

            throw "Readback $key mismatch for $($Project.GalleryDir): expected '$expected', got '$actual'."
        }
    }

    if ($ProjectMetadata.ContainsKey('Icon')) {
        $iconUrlProperty = $Readback.PSObject.Properties['icon_url']
        if ($null -eq $iconUrlProperty -or [string]::IsNullOrWhiteSpace([string]$iconUrlProperty.Value)) {
            throw "Readback icon_url is empty for $($Project.GalleryDir)."
        }
    }
}

$gradleProperties = Read-GradleProperties
$pageSections = Read-PageSections
$metadata = Read-GalleryMetadata
$token = Get-ModrinthToken

if (-not $DryRun -and [string]::IsNullOrWhiteSpace($token)) {
    throw 'MODRINTH_TOKEN is required for live sync. Set it in the environment or .env.'
}

foreach ($project in $projects) {
    $projectId = $gradleProperties[$project.ProjectIdProperty]
    if ([string]::IsNullOrWhiteSpace($projectId)) {
        throw "Missing Gradle property $($project.ProjectIdProperty)."
    }

    $items = @(Get-LocalGalleryPlan -Project $project -Metadata $metadata)
    $page = $pageSections[$project.GalleryDir]
    $iconPath = if ($page.Metadata.ContainsKey('Icon')) { Resolve-RepoPath -Path $page.Metadata['Icon'] } else { $null }

    Write-Host "Project $($project.GalleryDir): $($items.Count) gallery image(s), $(@($items | Where-Object InDescription).Count) description image(s)."

    foreach ($item in $items) {
        $featured = if ($item.Featured) { 'featured' } else { 'gallery' }
        Write-Host "  [$($item.Order)] $($item.Title) ($featured)"
    }

    foreach ($key in @('client_side', 'server_side', 'license_id', 'source_url', 'issues_url')) {
        if ($page.Metadata.ContainsKey($key)) {
            Write-Host "  $key=$($page.Metadata[$key])"
        }
    }
    if ($null -ne $iconPath) {
        $iconFile = Get-Item -LiteralPath $iconPath
        Write-Host "  icon=$($page.Metadata['Icon']) ($($iconFile.Length) bytes)"
    }

    foreach ($item in @($items | Where-Object InDescription)) {
        $placeholder = "modrinth-gallery://$($project.GalleryDir)/$($item.File)"
        if (-not ([string]$page.Body).Contains($placeholder)) {
            throw "Description for $($project.GalleryDir) does not reference selected image '$placeholder'."
        }
    }

    if ($DryRun) {
        continue
    }

    $liveProject = Get-ModrinthProject -ProjectId $projectId -Token $token
    if ($ReplaceGallery) {
        foreach ($galleryImage in @($liveProject.gallery)) {
            Remove-GalleryImage -ProjectId $projectId -Token $token -Url $galleryImage.url
        }
        $liveProject = Get-ModrinthProject -ProjectId $projectId -Token $token
    }

    $liveByTitle = @{}
    foreach ($galleryImage in @($liveProject.gallery)) {
        if (-not [string]::IsNullOrWhiteSpace($galleryImage.title)) {
            $liveByTitle[$galleryImage.title] = $galleryImage
        }
    }

    foreach ($item in $items) {
        $existing = $liveByTitle[$item.Title]
        if ($null -eq $existing) {
            Send-GalleryImage -ProjectId $projectId -Token $token -Item $item
        } else {
            Update-GalleryImageMetadata -ProjectId $projectId -Token $token -Url $existing.url -Item $item
        }
    }

    $liveProject = Get-ModrinthProject -ProjectId $projectId -Token $token
    $renderedBody = Resolve-BodyGalleryUrls -GalleryDir $project.GalleryDir -Body $page.Body -Items $items -LiveProject $liveProject
    Update-ModrinthProjectPage -ProjectId $projectId -Token $token -Summary $page.Summary -Body $renderedBody -ProjectMetadata $page.Metadata

    if ($null -ne $iconPath) {
        Send-ModrinthProjectIcon -ProjectId $projectId -Token $token -IconPath $iconPath
    }

    $readback = Get-ModrinthProject -ProjectId $projectId -Token $token
    if ($readback.description -ne $page.Summary) {
        throw "Readback summary mismatch for $($project.GalleryDir)."
    }
    if ($readback.body -ne $renderedBody) {
        throw "Readback body mismatch for $($project.GalleryDir)."
    }
    if (@($readback.gallery).Count -lt $items.Count) {
        throw "Readback gallery count for $($project.GalleryDir) is lower than expected."
    }
    Assert-ProjectMetadataReadback -Project $project -ProjectMetadata $page.Metadata -Readback $readback

    Write-Host "  Synced $($readback.title) ($($readback.slug)): $(@($readback.gallery).Count) gallery image(s)."
}

if ($DryRun) {
    Write-Host 'Dry run complete. No Modrinth API writes were made.'
} else {
    Write-Host 'Modrinth project page sync complete.'
}

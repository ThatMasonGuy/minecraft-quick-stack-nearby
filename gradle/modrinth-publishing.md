# Modrinth Publishing

Modrinth publishing uses the profiles listed in
`supported_minecraft_version_profiles`. For `0.4.0`, the supported publish
profiles cover Minecraft `1.20` through `26.3-snapshot-1`.

## Tasks

```powershell
.\gradlew.bat publishValidation
.\gradlew.bat prepareModrinthUploads
.\gradlew.bat publishModrinthDryRun
.\gradlew.bat publishModrinth -Pmodrinth_confirm_publish=true
```

- `publishValidation` builds and smoke-tests supported profiles only.
- `prepareModrinthUploads` writes `build/modrinth/upload-plan.json` after the
  validation gate passes.
- `publishModrinthDryRun` performs the full validation path without calling the
  Modrinth API.
- `publishModrinth` performs the real upload and requires
  `-Pmodrinth_confirm_publish=true`.

## Current Project

- Project id: `5Hu4HCfZ`
- Slug read back with authentication on 2026-06-19: `quick-stack-nearby`
- Current status on 2026-06-26: `processing`
- Current requested status on 2026-06-26: `approved`
- Current public unauthenticated readback: `404 Not Found`, expected while the
  project remains draft/private or processing.

## Release Notes

Modrinth changelogs come from:

```text
gradle/release-notes/<mod_version>.md
```

The current release version is `0.4.0`, so the active notes file is
`gradle/release-notes/0.4.0.md`.

## Project Page Copy

Public page source copy lives in:

```text
gradle/modrinth-project-pages.md
gallery/
```

Dry-run the page sync before any live write:

```powershell
.\scripts\sync-modrinth-project-pages.ps1 -DryRun
```

Use the live mode only after the copy, license, side metadata, and icon are
ready to be written to Modrinth.

## GitHub Workflow Shape

The guarded `modrinth publish` workflow follows the Bigger Boats single-mod
pipeline: it installs Java 17, Java 21, and Java 25, runs Gradle on Java 21,
executes the supported-profile client/server smoke gate under `xvfb`, checks
for `MODRINTH_TOKEN` before any live upload, and captures upload plans, release
jars, smoke logs, smoke mod lists, and smoke run directories.

# Modrinth Publishing

Modrinth publishing is disabled until at least one Minecraft profile is promoted
to `supported_minecraft_version_profiles`.

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
- Current status on 2026-06-19: `draft`
- Current public unauthenticated readback: `404 Not Found`, expected while the
  project remains draft/private.

## Release Notes

Modrinth changelogs come from:

```text
gradle/release-notes/<mod_version>.md
```

The current scaffold version is `0.1.0`, so the active draft notes file is
`gradle/release-notes/0.1.0.md`.

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

# Changelog

## Unreleased

- Scaffolded Quick Stack Nearby as a Fabric single-mod project with the
  InventorySort checkpoint, CI, Modrinth, profile, and documentation workflow.
- Copied `AGENTS.md` word-for-word from InventorySort as requested.
- Added a Gradle build with active-profile `buildAllMods`, candidate profile
  validation, client/server smoke hooks, release metadata checks, and guarded
  Modrinth upload planning.
- Recorded InventorySort-derived candidate compatibility groups without
  promoting any supported publish target yet.
- Added Modrinth project id `5Hu4HCfZ`, source-controlled project-page copy,
  gallery metadata, and the page-sync workflow/script.
- Added scaffold-only Fabric main and client entrypoints; no quick-stack
  gameplay behavior has been implemented.
- Downscaled the supplied root icon source into the packaged mod icon while
  preserving the source image.
- Verified the scaffold with `git diff --check`, Modrinth page dry-run, and
  default-profile `buildAllMods`.

## Research Notes

- Fabric metadata supports a regular both-side mod through `environment="*"`
  plus separate main and client entrypoints.
- Fabric events provide the lifecycle hooks used by the scaffold smoke tests.
- Modrinth project metadata has separate client-side and server-side support
  fields that will need to be synced once the install contract is finalized.
- InventorySort's UI/button placement code is the donor pattern for the unload
  button and drives the candidate compatibility groups for this repo.

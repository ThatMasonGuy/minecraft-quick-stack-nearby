# Changelog

## Unreleased

- Started the `0.2.0` development line.
- Replaced the small quick-stack button glyph with a clearer chest-and-arrow
  icon.
- Added an InventorySort-style recipe-book render bridge so the quick-stack
  button refreshes position when vanilla shifts the inventory screen.
- Added shared TempestStudios app-data storage for server config and op-only
  commands to inspect, update, and reload the quick-stack scan range.
- Added an InventorySort-style quick-stack rules screen on right-click with
  scoped TempestStudios storage, slot locks, and per-slot keep counts.
- Quick-stack requests now send the active scoped slot rules so the server-side
  movement engine leaves locked or keep-counted source slots alone.
- Avoided rewriting the server config file during normal startup loads; it now
  writes on first creation, recovery, or command-driven changes.
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
- Added a first playable 1.21.11 quick-stack implementation: a survival
  inventory button, C2S request payload, server-side nearby container scan, and
  matching-stack movement from main inventory slots.
- Added optional Inv+ slot API reflection for button placement with a local
  InventorySort-style fallback when the API is unavailable.
- Added a packaged dedicated-server smoke self-test that exercises matching
  item movement.
- Downscaled the supplied root icon source into the packaged mod icon while
  preserving the source image.
- Verified the scaffold with `git diff --check`, Modrinth page dry-run, and
  default-profile `buildAllMods`.
- Corrected the GitHub testing pipeline toward the Bigger Boats single-mod
  shape: push builds use `buildAllVersions`, candidate smoke validation runs
  client/server launch matrices, and Modrinth publish captures smoke artifacts.
- Verified the 1.21.11 implementation with `buildRelease`,
  `smokeTestSelectedServers`, and `smokeTestSelectedClients`.
- Promoted `1.21.11` as the only supported `0.1.0` publish profile and kept the
  remaining compatibility groups as candidates.
- Updated Modrinth project-page copy, metadata, and gallery text for the
  playable `0.1.0` review release.

## Research Notes

- Fabric metadata supports a regular both-side mod through `environment="*"`
  plus separate main and client entrypoints.
- Fabric events provide the lifecycle hooks used by the scaffold smoke tests.
- Modrinth project metadata has separate client-side and server-side support
  fields that will need to be synced once the install contract is finalized.
- InventorySort's UI/button placement code is the donor pattern for the unload
  button and drives the candidate compatibility groups for this repo.

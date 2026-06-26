# Changelog

## Unreleased

- Started the `0.3.1` release line and fixed the quick-stack button's
  secondary-click rules-screen path by intercepting right-clicks before
  inventory slot handling can consume them.
- Fixed the split source-set build wiring so clean client compiles can resolve
  common Quick Stack Nearby classes from `sourceSets.main.output`.
- Updated the `26.x` release profile to compile from Minecraft
  `26.3-snapshot-1`, publish profile id `26.1-26.3-snapshot-1`, and claim
  `26.2` final plus `26.3-snapshot-1` after local selected client and
  dedicated-server smoke passed for every listed 26.x runtime.
- Retired `26.2-pre-3` from the current `0.3.1` Modrinth target set after
  Minecraft `26.2` became the supported final release target.
- Updated the 26.x runtime-only smoke profiles to launch with Fabric Loader
  `0.19.3`, matching the `0.3.1` release jar's loader dependency.
- Prepared the `0.3.0` release line and promoted all current compatibility
  groups from Minecraft `1.20` through `26.x` to supported publish profiles.
- Recorded full exact-runtime smoke evidence from GitHub Actions candidate
  smoke validation run `27922347858`, which passed every listed client and
  dedicated-server runtime and reported `selfTestItemsMoved=48` in all 23
  server self-tests.
- Made the smoke matrix gate explicit for every supported publish profile so
  future Modrinth uploads require pass records for all listed game versions.
- Published `0.3.0` through the guarded GitHub Actions Modrinth workflow,
  uploaded seven listed Modrinth versions, tagged `v0.3.0` on the exact release
  source commit, created the asset-free GitHub Release, and synced the
  Modrinth project page copy for the release.
- Updated Modrinth gallery metadata/source images and project-page copy to show
  the new screenshots and note that more UI improvements are planned.
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
- Polished the quick-stack button and rules screen: the button now uses a
  monochrome inventory-out icon, text buttons clamp labels to their bounds, and
  the rules modal gives selection state, legend, and keep-count controls their
  own space.
- Added the first cross-version compatibility slice with profile-specific
  adapters for identifiers, C2S payload registration/sending, player feedback,
  server-level access, item-stack identity, custom button rendering/click
  handling, window handles, and singleplayer server directory paths.
- Added legacy `1.20-1.20.4` adapters for Fabric's old
  `FabricPacket`/`PacketType` networking, NBT stack identity, container
  max-stack limits, and public widget rendering/bounds APIs.
- Added 26.x adapters for `serverboundPlay` networking, server feedback,
  extractor-based widget/screen rendering, screen navigation, and singleplayer
  state checks.
- Verified validation builds for every profile from `1.20-1.20.4` through
  `26.x` while keeping new profile promotion blocked on exact-runtime smoke
  coverage.
- Smoke-tested each compatibility group's anchor runtime on both client and
  dedicated server: `1.20`, `1.20.5`, `1.21`, `1.21.6`, `1.21.9`,
  `1.21.11`, and `26.2-pre-3`.
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
- Published `0.1.0` through the guarded GitHub Actions Modrinth workflow,
  uploaded listed Modrinth version `tO7OxI85`, tagged `v0.1.0` on the exact
  release source commit, and created the asset-free GitHub Release.
- Hardened the Modrinth project-page sync readback for current API behavior:
  license verification now reads `license.id`, and draft review projects can
  tolerate a temporary side-support readback lag while still keeping required
  client/server metadata in source.
- Ignored the workspace-local `.gradle-codex/` cache used by local verification
  commands.

## Research Notes

- Fabric metadata supports a regular both-side mod through `environment="*"`
  plus separate main and client entrypoints.
- Fabric events provide the lifecycle hooks used by the scaffold smoke tests.
- Modrinth project metadata has separate client-side and server-side support
  fields that will need to be synced once the install contract is finalized.
- InventorySort's UI/button placement code is the donor pattern for the unload
  button and drives the candidate compatibility groups for this repo.

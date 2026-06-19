# TODO

## Current Checkpoint

- Project scaffold follows the InventorySort workflow philosophy.
- `AGENTS.md` is copied word-for-word from
  `D:\Cloud\Development\MinecraftMods\InventorySort\inventory-sort`.
- The Gradle skeleton is adapted from the single-mod profile-driven pattern
  used by Bigger Boats, with InventorySort's button-oriented profile groups as
  candidates.
- The testing workflow now follows Bigger Boats more closely: push/PR runs
  `buildAllVersions`, manual candidate smoke runs client and dedicated-server
  launches, and the Modrinth workflow captures smoke artifacts.
- Modrinth project id `5Hu4HCfZ` is recorded in `gradle.properties`.
- Authenticated Modrinth readback on 2026-06-19 confirmed the project is
  `quick-stack-nearby`, titled `Quick Stack Nearby`, and currently draft with
  unknown client/server metadata and unknown license.
- The supplied `quick-stack-nearby.jpg` remains at the repo root as the source
  image.
- A downscaled `256x256` packaged icon is generated at
  `src/main/resources/assets/quick-stack-nearby/icon.jpg`.
- Default-profile `buildAllMods` passed on 2026-06-19 and produced
  `build/release/1.21.11/quick-stack-nearby-0.1.0.jar` at `48342` bytes.
- Modrinth project page dry-run passed on 2026-06-19 with one gallery image and
  one description image selector.
- `buildAllVersions` falls back to the active profile while no supported
  profiles exist, preventing the push build from silently passing without a jar.
- The first playable 1.21.11 implementation now has a survival-inventory quick
  stack button, an empty C2S request packet, server-side nearby-container item
  movement, optional Inv+ slot API reflection, and a local slot-placement
  fallback copied from the InventorySort pattern.
- The first implementation moves matching main-inventory stacks only; hotbar,
  armor, offhand, and carried shulker contents are intentionally untouched.
- Compatibility compile probes have been run for every candidate group from
  `1.20-1.20.4` through `26.x`; breakpoints and the shim plan are documented in
  `docs/compatibility-research.md`.

## Research Conclusions

- Quick Stack Nearby should run as a regular Fabric mod with
  `environment="*"` because multiplayer needs server authority and the client
  needs UI entrypoints.
- Remote multiplayer should require the server component for item movement.
  Client-only remote installs can later show disabled UI or omit the button.
- Offline singleplayer can work from a client install because the integrated
  server runs the common entrypoint in the client process.
- UI/container button drift should follow InventorySort's candidate profile
  groups until proven otherwise.
- No profile is publishable yet; every candidate in `gradle/smoke-tests.json`
  is intentionally pending.

## Next Implementation Tasks

1. Manually smoke the 1.21.11 button in a real singleplayer world with nearby
   chests/barrels before promoting any supported profile.
2. Add richer result feedback or sounds if the first playtest feels too quiet.
3. Decide whether the first release should include hotbar stacks, carried
   shulker-box contents, or a config toggle for those behaviors.
4. Implement the smallest compat wrapper set from
   `docs/compatibility-research.md`, starting with `1.21.9-1.21.10`.
5. Promote only the smoke-passed profile groups into
   `supported_minecraft_version_profiles`.
6. Update Modrinth project page metadata, license, and icon after page copy is
   reviewed.

## Verification Log

- Passed: `git diff --check`.
- Passed: `.\scripts\sync-modrinth-project-pages.ps1 -DryRun`.
- Passed: `.\gradlew.bat buildAllMods --no-daemon --console=plain`.
- Passed after pipeline correction:
  `.\gradlew.bat buildAllVersions --no-daemon --console=plain`.
- Passed: `.\gradlew.bat buildRelease --no-daemon --console=plain`.
- Passed: `.\gradlew.bat smokeTestSelectedServers '-Pquickstacknearby_smoke_profiles=1.21.11' '-Pquickstacknearby_smoke_install_sets=quick-stack-nearby-server-only' --no-daemon --console=plain`
  with `selfTestItemsMoved=48`.
- Passed: `.\gradlew.bat smokeTestSelectedClients '-Pquickstacknearby_smoke_profiles=1.21.11' '-Pquickstacknearby_smoke_install_sets=quick-stack-nearby-client-only' --no-daemon --console=plain`.
- Passed after final bridge hardening:
  `.\gradlew.bat smokeTestSelected '-Pquickstacknearby_smoke_profiles=1.21.11' --no-daemon --console=plain`.
- Research probe: every candidate profile was compiled with
  `.\gradlew.bat compileJava compileClientJava "-Pminecraft_version_profile=<profile>" --no-daemon --console=plain`;
  all non-1.21.11 candidate failures are documented in
  `docs/compatibility-research.md`.

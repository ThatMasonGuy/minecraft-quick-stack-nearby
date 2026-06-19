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
- The `1.21.11` profile is promoted as the only supported profile for the
  public `0.1.0` review release.
- Modrinth page copy now describes the implemented `0.1.0` behavior instead of
  scaffold-only plans, and project metadata includes required client/server
  sides, LGPL license id, source/issues URLs, and the root source icon.
- The `0.1.0` publish dry run writes one listed Fabric upload plan for Modrinth
  project `5Hu4HCfZ`, profile `1.21.11`, game version `1.21.11`, and release
  jar `quick-stack-nearby-0.1.0.jar`.
- Live v0.1.0 publishing remains prepared at commit
  `4d95b7a5e5da68f942381f54cf8fd42cc21afd05`, but this Codex environment still
  cannot reach GitHub or Modrinth over outbound HTTPS.
- The working tree is now on `0.2.0` development. The first client UI slice
  replaces the tiny quick-stack glyph with a chest-and-arrow icon and adds the
  InventorySort-style recipe-book render bridge for button repositioning.

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
- Only `1.21.11` is publishable for `0.1.0`; every remaining candidate in
  `gradle/smoke-tests.json` stays pending.

## Next Implementation Tasks

1. From a network-enabled shell, push the prepared `0.1.0` release commit,
   publish the `1.21.11` Modrinth version,
   sync the project page/icon metadata, tag `v0.1.0`, and create the GitHub
   release.
2. Add richer result feedback or sounds if the first playtest feels too quiet.
3. Build the `0.2.0` slot-lock UI: copy InventorySort's rule screen pattern,
   change the accent color, and add per-slot keep counts.
4. Copy InventorySort's shared TempestStudios data namespace/storage approach
   for client slot rules and server/player config.
5. Add op commands for configurable quick-stack range.
6. Decide whether a later release should include hotbar stacks, carried
   shulker-box contents, or a config toggle for those behaviors.
7. Implement the smallest compat wrapper set from
   `docs/compatibility-research.md`, starting with `1.21.9-1.21.10`.
8. Promote only future smoke-passed profile groups into
   `supported_minecraft_version_profiles`.

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
- Passed after release-profile promotion:
  `powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\sync-modrinth-project-pages.ps1 -DryRun`;
  parsed required client/server metadata, LGPL license id, source/issues URLs,
  and root icon `quick-stack-nearby.jpg` at `140258` bytes.
- Passed after release-profile promotion:
  `.\gradlew.bat publishModrinthDryRun --no-daemon --console=plain` with a
  workspace-local Gradle cache/temp path for sandbox compatibility; built the
  `1.21.11` release jar, ran supported client and dedicated-server smoke,
  reported `selfTestItemsMoved=48`, and wrote
  `build/modrinth/upload-plan.json`.
- Passed after starting `0.2.0`:
  `.\gradlew.bat buildAllMods --no-daemon --console=plain` with workspace-local
  Gradle cache/temp paths for sandbox compatibility; built and verified
  `build/release/1.21.11/quick-stack-nearby-0.2.0.jar`.

# TODO

## Current Checkpoint

- Project scaffold is being built from the InventorySort workflow philosophy.
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

1. Research and prototype the server-side quick-stack scan/move algorithm.
2. Define the client/server packet contract for an unload request and result.
3. Add optional Inv+ slot API detection and the fallback button placement layer.
4. Promote only the smoke-passed profile groups into
   `supported_minecraft_version_profiles`.
5. Update Modrinth project page metadata, license, and icon after page copy is
   reviewed.

## Verification Log

- Passed: `git diff --check`.
- Passed: `.\scripts\sync-modrinth-project-pages.ps1 -DryRun`.
- Passed: `.\gradlew.bat buildAllMods --no-daemon --console=plain`.
- Passed after pipeline correction:
  `.\gradlew.bat buildAllVersions --no-daemon --console=plain`.

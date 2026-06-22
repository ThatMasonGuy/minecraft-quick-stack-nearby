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
  `quick-stack-nearby`, titled `Quick Stack Nearby`, with required
  client/server metadata, LGPL-3.0-or-later licensing, source/issues URLs, the
  root source icon, and one gallery image synced from source control.
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
- Live `0.1.0` publishing succeeded from release branch `release/v0.1.0` at
  source commit `4d95b7a5e5da68f942381f54cf8fd42cc21afd05`.
- GitHub Actions run `27824441279` uploaded Modrinth version `tO7OxI85` for
  project `5Hu4HCfZ` as a listed Fabric `1.21.11` release.
- Annotated tag `v0.1.0` points to
  `4d95b7a5e5da68f942381f54cf8fd42cc21afd05`, and GitHub Release
  `https://github.com/ThatMasonGuy/minecraft-quick-stack-nearby/releases/tag/v0.1.0`
  is published without jar assets.
- The working tree is now on `0.3.0` release prep after the exact-runtime
  compatibility smoke pass. The first client UI slice
  replaces the tiny quick-stack glyph with a chest-and-arrow icon and adds the
  InventorySort-style recipe-book render bridge for button repositioning.
- The server config slice now creates
  `TempestStudios/QuickStackNearby/server_config.json` and exposes op-only
  `/quickstacknearby range`, `/quickstacknearby range <horizontal> <vertical>`,
  and `/quickstacknearby reload` commands, with `/qsn` aliases.
- The client rule slice now creates
  `TempestStudios/QuickStackNearby/quick_stack_rules.json`, scoped by
  singleplayer instance/world or multiplayer server/account, and opens an
  InventorySort-style slot rules screen from right-clicking the quick-stack
  button.
- Slot rules currently apply to the same main-inventory source slots that quick
  stack unloads today. Locked slots are skipped, and keep counts leave the
  configured number of items in that slot while still allowing the rest to move.
- The `0.2.0` UI pass replaced the colored chest/green-arrow quick-stack glyph
  with a monochrome inventory-out arrow, widened the rules modal, gave the side
  panel real vertical breathing room, shortened crowded labels, and clamps text
  button labels inside their own bounds.
- The first compatibility implementation slice now routes identifiers, C2S
  packets, player feedback, server-level access, item-stack identity, custom
  button rendering/click handling, window handles, and singleplayer server
  directory paths through version-specific adapters.
- Compatibility compile checks now pass for `1.20-1.20.4`,
  `1.20.5-1.20.6`, `1.21-1.21.5`, `1.21.6-1.21.8`,
  `1.21.9-1.21.10`, `1.21.11`, and `26.x`.
- The legacy `1.20-1.20.4` compatibility slice now compiles with Fabric's old
  `FabricPacket`/`PacketType` networking API, NBT-era item-stack identity, the
  no-argument container max-stack limit, and old widget bounds/render hooks.
- The `26.x` compatibility slice now compiles with `serverboundPlay`
  networking, `Identifier` resources, server system-message feedback, and a
  `GuiGraphicsExtractor` bridge that lets the existing rules/button UI render
  through the 26.x extractor pass.
- `buildValidationVersions` now builds release jars for every validation
  profile: `1.20-1.20.4`, `1.20.5-1.20.6`, `1.21-1.21.5`,
  `1.21.6-1.21.8`, `1.21.9-1.21.10`, `1.21.11`, and `26.x`.
- Selected anchor client and dedicated-server smoke passed for `1.20`,
  `1.20.5`, `1.21`, `1.21.6`, `1.21.9`, `1.21.11`, and `26.2-pre-3`; every
  server smoke reported `selfTestItemsMoved=48`.
- GitHub Actions candidate smoke validation run `27922347858` passed on
  2026-06-22 at source commit `e547e00c52ba67b7c859337dc551d6d97bbe95cc`.
  Artifact logs captured 23 client pass markers, 23 dedicated-server pass
  markers, and 23 dedicated-server self-tests reporting
  `selfTestItemsMoved=48`.
- `gradle/smoke-tests.json` now records pass evidence for every exact runtime
  from Minecraft `1.20` through `26.x`.
- `gradle.properties` now promotes `1.20-1.20.4`, `1.20.5-1.20.6`,
  `1.21-1.21.5`, `1.21.6-1.21.8`, `1.21.9-1.21.10`, `1.21.11`, and `26.x`
  to supported publish metadata for `0.3.0`.
- GitHub push CI run `27923577574` passed on the `0.3.0` promotion commit
  `0be077d08495b0aba4dfa3ec2a5d4b41964e53e2`.
- GitHub Actions `modrinth publish` dry-run workflow run `27923580593` passed
  on commit `0be077d08495b0aba4dfa3ec2a5d4b41964e53e2` and prepared seven
  listed Fabric upload plans.
- GitHub Actions `modrinth publish` live workflow run `27924627691` passed on
  commit `0be077d08495b0aba4dfa3ec2a5d4b41964e53e2` and uploaded listed
  Modrinth versions `7yQysNA6`, `DP3nAUdg`, `zgSJNefs`, `KmN9ax8w`,
  `FRX31oZm`, `uLPA9zwU`, and `lYiTZ1J6`.
- Authenticated Modrinth readback on 2026-06-22 confirmed the project remains
  `draft` with requested status `approved`, public unauthenticated readback
  still returns `404 Not Found`, and all seven `0.3.0` versions are `listed`.
- Annotated tag `v0.3.0` points to
  `0be077d08495b0aba4dfa3ec2a5d4b41964e53e2`, and GitHub Release
  `https://github.com/ThatMasonGuy/minecraft-quick-stack-nearby/releases/tag/v0.3.0`
  is published without jar assets.
- Modrinth project page dry-run and live sync completed on 2026-06-22 with the
  `0.3.0` project-page copy, required side metadata, LGPL license, source/issues
  URLs, root icon, and one gallery image.
- The local `0.2.0` package has been rebuilt and focused-smoked on 1.21.11
  client and dedicated-server launches. Smoke used a workspace-local `APPDATA`
  override so TempestStudios config creation was exercised without touching the
  real user app-data folder.

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
- `0.3.0` is publishable for the supported profile groups from Minecraft
  `1.20` through `26.x`; no active candidate profiles remain queued.

## Next Implementation Tasks

1. Watch Modrinth review for the draft project moving from requested
   `approved` to publicly approved/listed, then confirm the public project and
   version URLs resolve without authentication.
2. Add richer result feedback or sounds if the first playtest feels too quiet.
3. Decide whether a later release should include hotbar stacks, carried
   shulker-box contents, or a config toggle for those behaviors.
4. Decide whether a later release should add a server world-identity profile
   handshake for multiplayer servers that rotate worlds behind one address,
   matching the optional InventorySort profile refinement.

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
- Passed after adding server range config and commands:
  `.\gradlew.bat buildAllMods --no-daemon --console=plain` with workspace-local
  Gradle cache/temp paths for sandbox compatibility; built and verified
  `build/release/1.21.11/quick-stack-nearby-0.2.0.jar`.
- Passed after adding the slot-rule UI and keep-count packet path:
  `.\gradlew.bat compileJava compileClientJava --no-daemon --console=plain`
  with workspace-local Gradle cache/temp paths for sandbox compatibility.
- Passed after adding the slot-rule UI and keep-count packet path:
  `.\gradlew.bat buildAllMods --no-daemon --console=plain` with
  workspace-local Gradle cache/temp paths for sandbox compatibility; built and
  verified `build/release/1.21.11/quick-stack-nearby-0.2.0.jar`.
- Passed after final `0.2.0` packaging:
  `.\gradlew.bat smokeTestSelected "-Pquickstacknearby_smoke_profiles=1.21.11" --no-daemon --console=plain`
  with workspace-local Gradle cache/temp paths and `APPDATA` redirected to
  `.smoke-appdata`; emitted both `QUICKSTACKNEARBY_SMOKE_TEST_PASS` and
  `QUICKSTACKNEARBY_SERVER_SMOKE_TEST_PASS`, with
  `selfTestItemsMoved=48`.
- Passed after `0.2.0` UI polish:
  `.\gradlew.bat compileJava compileClientJava --no-daemon --console=plain`
  with workspace-local Gradle cache path; first attempt timed out while Gradle
  held the Fabric Loom cache lock, and the longer rerun completed successfully.
- Passed after `0.2.0` UI polish:
  `.\gradlew.bat buildAllMods --no-daemon --console=plain` with
  workspace-local Gradle cache path; rebuilt and verified
  `build/release/1.21.11/quick-stack-nearby-0.2.0.jar`.
- Passed during the first compatibility implementation slice:
  `.\gradlew.bat compileJava compileClientJava --no-daemon --console=plain`
  with workspace-local Gradle cache path for the active `1.21.11` profile.
- Passed during the first compatibility implementation slice:
  `.\gradlew.bat compileJava compileClientJava "-Pminecraft_version_profile=1.21.9-1.21.10" --no-daemon --console=plain`.
- Passed during the first compatibility implementation slice:
  `.\gradlew.bat compileJava compileClientJava "-Pminecraft_version_profile=1.21.6-1.21.8" --no-daemon --console=plain`.
- Passed during the first compatibility implementation slice:
  `.\gradlew.bat compileJava compileClientJava "-Pminecraft_version_profile=1.21-1.21.5" --no-daemon --console=plain`.
- Passed during the first compatibility implementation slice:
  `.\gradlew.bat compileJava compileClientJava "-Pminecraft_version_profile=1.20.5-1.20.6" --no-daemon --console=plain`.
- Passed during the first compatibility implementation slice:
  `.\gradlew.bat buildAllMods --no-daemon --console=plain` with
  workspace-local Gradle cache path; rebuilt and verified the supported
  `1.21.11` release jar while leaving candidate profiles non-publishable.
- Passed after adding legacy 1.20 compatibility adapters:
  `.\gradlew.bat compileJava compileClientJava "-Pminecraft_version_profile=1.20-1.20.4" --no-daemon --console=plain`.
- Passed after adding legacy 1.20 compatibility adapters:
  `.\gradlew.bat buildAllMods --no-daemon --console=plain` with
  workspace-local Gradle cache path; rebuilt and verified the supported
  `1.21.11` release jar while candidate profiles remained non-publishable.
- Passed after adding 26.x and UI extractor compatibility:
  `.\gradlew.bat compileJava compileClientJava "-Pminecraft_version_profile=26.x" --no-daemon --console=plain`.
- Passed after completing the compile/build compatibility pass:
  `.\gradlew.bat buildValidationVersions --no-daemon --console=plain`; built
  validation release jars for `1.20-1.20.4`, `1.20.5-1.20.6`,
  `1.21-1.21.5`, `1.21.6-1.21.8`, `1.21.9-1.21.10`, `1.21.11`, and `26.x`.
- Passed anchor runtime smoke after completing the compatibility pass:
  `.\gradlew.bat smokeTestSelected "-Pquickstacknearby_smoke_profiles=1.20-1.20.4,1.20.5-1.20.6,1.21-1.21.5,1.21.6-1.21.8,1.21.9-1.21.10,1.21.11,26.x" "-Pquickstacknearby_smoke_game_versions=1.20,1.20.5,1.21,1.21.6,1.21.9,1.21.11,26.2-pre-3" --no-daemon --console=plain`
  with `APPDATA` redirected to `.smoke-appdata`; each selected client emitted
  `QUICKSTACKNEARBY_SMOKE_TEST_PASS`, and each selected dedicated server
  emitted `QUICKSTACKNEARBY_SERVER_SMOKE_TEST_PASS` with
  `selfTestItemsMoved=48`.
- Passed exact-runtime GitHub candidate smoke validation: workflow run
  `27922347858` on `main` at commit
  `e547e00c52ba67b7c859337dc551d6d97bbe95cc` completed successfully in
  37m16s; artifacts contained 23 client pass markers, 23 dedicated-server pass
  markers, and 23 server self-tests reporting `selfTestItemsMoved=48`.
- Passed after `0.3.0` profile promotion:
  `.\gradlew.bat listVersionProfiles verifySmokeTestMatrix --no-daemon --console=plain`;
  Gradle listed seven supported profiles, no candidates, and verified pass
  records for every supported exact runtime in `gradle/smoke-tests.json`.
- Passed after `0.3.0` profile promotion:
  `.\gradlew.bat buildAllVersions --no-daemon --console=plain`; built and
  verified release jars for `1.20-1.20.4`, `1.20.5-1.20.6`,
  `1.21-1.21.5`, `1.21.6-1.21.8`, `1.21.9-1.21.10`, `1.21.11`, and
  `26.1-26.2-pre-3`.
- Passed on GitHub Actions: push build run `27923577574` on `main`, source
  commit `0be077d08495b0aba4dfa3ec2a5d4b41964e53e2`, completed successfully.
- Passed on GitHub Actions: manual `modrinth publish` dry-run workflow run
  `27923580593` on `main`, source commit
  `0be077d08495b0aba4dfa3ec2a5d4b41964e53e2`, completed successfully and
  prepared seven listed Fabric upload plans for Modrinth project `5Hu4HCfZ`.
- Passed on GitHub Actions: manual `modrinth publish` live workflow run
  `27924627691` on `main`, source commit
  `0be077d08495b0aba4dfa3ec2a5d4b41964e53e2`, completed successfully and
  uploaded Modrinth versions `7yQysNA6`, `DP3nAUdg`, `zgSJNefs`, `KmN9ax8w`,
  `FRX31oZm`, `uLPA9zwU`, and `lYiTZ1J6`.
- Live publish artifact readback: upload plan requested seven listed Fabric
  release entries for project `5Hu4HCfZ`, and captured 23 client smoke markers,
  23 dedicated-server smoke markers, and 23 server self-tests reporting
  `selfTestItemsMoved=48`.
- Passed after live page sync:
  `.\scripts\sync-modrinth-project-pages.ps1 -DryRun` and
  `.\scripts\sync-modrinth-project-pages.ps1` completed with the `0.3.0`
  project-page copy, required client/server metadata, LGPL-3.0-or-later
  license, source/issues URLs, root icon, and one gallery image.
- Authenticated release readback: project `5Hu4HCfZ` remains `draft` with
  requested status `approved`, public unauthenticated readback still returns
  `404 Not Found`, and all seven `0.3.0` versions are `listed`.
- Passed release readback: `v0.3.0` resolves to
  `0be077d08495b0aba4dfa3ec2a5d4b41964e53e2`, and GitHub Release
  `https://github.com/ThatMasonGuy/minecraft-quick-stack-nearby/releases/tag/v0.3.0`
  is published with no jar assets attached.
- Passed on GitHub Actions: manual `modrinth publish` workflow run
  `27824441279` on branch `release/v0.1.0`, source commit
  `4d95b7a5e5da68f942381f54cf8fd42cc21afd05`, completed successfully and
  uploaded Modrinth version `tO7OxI85`.
- Publish artifact readback: upload plan requested listed release
  `quick-stack-nearby-0.1.0.jar` for Fabric `1.21.11`; captured client smoke
  marker `QUICKSTACKNEARBY_SMOKE_TEST_PASS` and dedicated-server marker
  `QUICKSTACKNEARBY_SERVER_SMOKE_TEST_PASS` with `selfTestItemsMoved=48`.
- Passed after live page sync: `.\scripts\sync-modrinth-project-pages.ps1`
  completed and authenticated Modrinth readback showed required client/server
  metadata, LGPL-3.0-or-later license, source/issues URLs, icon, and one gallery
  image.
- Passed release readback: `v0.1.0` resolves to
  `4d95b7a5e5da68f942381f54cf8fd42cc21afd05`, and GitHub Release
  `https://github.com/ThatMasonGuy/minecraft-quick-stack-nearby/releases/tag/v0.1.0`
  is published with no jar assets attached.

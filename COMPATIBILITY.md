# Compatibility

Scope: Quick Stack Nearby `0.4.0` release compatibility. Version `0.4.0`
keeps the `0.3.1` Minecraft `1.20` through `26.3-snapshot-1`
compatibility matrix and rebuilds the supported compatibility-group jars with
the current quick-stack fixes and keybind feature.

## Candidate Profiles

No active candidate profiles are queued for `0.4.0`; every current
compatibility-group profile has been promoted after full exact-runtime smoke
coverage.

## Supported Profiles

| Profile | Compile anchor | Runtime versions | Release status |
| --- | --- | --- | --- |
| `1.20-1.20.4` | `1.20` | `1.20` through `1.20.4` | Supported for `0.4.0`. |
| `1.20.5-1.20.6` | `1.20.5` | `1.20.5` through `1.20.6` | Supported for `0.4.0`. |
| `1.21-1.21.5` | `1.21` | `1.21` through `1.21.5` | Supported for `0.4.0`. |
| `1.21.6-1.21.8` | `1.21.6` | `1.21.6` through `1.21.8` | Supported for `0.4.0`. |
| `1.21.9-1.21.10` | `1.21.9` | `1.21.9` through `1.21.10` | Supported for `0.4.0`. |
| `1.21.11` | `1.21.11` | `1.21.11` | Supported for `0.4.0`. |
| `26.x` | `26.3-snapshot-1` | `26.1`, `26.1.1`, `26.1.2`, `26.2`, `26.3-snapshot-1` | Supported for `0.4.0`. |

## Current Evidence

- Official Mojang and Fabric metadata checked on 2026-06-26 confirmed
  Minecraft `26.2` as a stable release, `26.3-snapshot-1` as the current
  snapshot target, Fabric Loader `0.19.3`, Fabric API `0.153.0+26.2` for
  `26.2`, and Fabric API `0.153.1+26.3` for `26.3-snapshot-1`.
- `buildAllVersions` passed locally on 2026-06-29 after bumping to `0.4.0`
  and rebuilt all seven supported release jars, including
  `26.1-26.3-snapshot-1/quick-stack-nearby-0.4.0.jar`.
- The `0.3.2` patch keeps the same supported game-version matrix and replaces
  the shared raw mouse-state right-click interception with profile-specific
  click-event shims for the old coordinate API and the newer
  `MouseButtonEvent` API.
- `buildAllVersions` passed locally on 2026-06-26 after the `0.3.2` patch and
  rebuilt the seven supported release jars, including
  `26.1-26.3-snapshot-1/quick-stack-nearby-0.3.2.jar`.
- Targeted client smoke passed locally on 2026-06-26 for `1.20`,
  `1.21.11`, and `26.3-snapshot-1` with the `0.3.2` release jars, covering
  both click-event shim families at runtime.
- The `26.x` release profile now compiles from `26.3-snapshot-1`, publishes
  profile id `26.1-26.3-snapshot-1`, and lists Modrinth game versions
  `26.1`, `26.1.1`, `26.1.2`, `26.2`, and `26.3-snapshot-1`. The
  `26.2-pre-3` runtime-only profile has been retired from the current target
  set.
- Selected 26.x client and dedicated-server smoke passed locally on 2026-06-26
  for every listed 26.x runtime. Each client emitted
  `QUICKSTACKNEARBY_SMOKE_TEST_PASS`, and each dedicated server emitted
  `QUICKSTACKNEARBY_SERVER_SMOKE_TEST_PASS` with `selfTestItemsMoved=48`.
- `buildValidationVersions` passed on 2026-06-22 and built release jars for
  every validation profile from `1.20-1.20.4` through `26.x`.
- Full GitHub Actions candidate smoke validation run `27922347858` passed on
  2026-06-22 at source commit `e547e00c52ba67b7c859337dc551d6d97bbe95cc`.
  Artifact logs captured 23 client pass markers, 23 dedicated-server pass
  markers, and 23 dedicated-server self-tests reporting
  `selfTestItemsMoved=48`.
- Every exact game version listed in `gradle/smoke-tests.json` now has a pass
  record, with the 26.x records refreshed locally for the `0.3.1`
  `26.1-26.3-snapshot-1` release profile.
- `1.21.11` compiles and passes selected packaged client and dedicated-server
  smoke tests. The dedicated-server smoke exercises the quick-stack move engine
  and reports `selfTestItemsMoved=48`.
- Candidate compile probes for `1.21.9-1.21.10`, `1.21.6-1.21.8`,
  `1.21-1.21.5`, `1.20.5-1.20.6`, `1.20-1.20.4`, and `26.x` were run on
  2026-06-19 from baseline commit `4bedcd1`.
- Detailed breakpoints and bridge recommendations are recorded in
  `docs/compatibility-research.md`.

## Drift Surfaces

- Client inventory UI and button widgets are the first likely split point.
  QuickStack should use InventorySort's `InventoryScreenButtonSlots` API when
  present and fall back to a local implementation copied from InventorySort.
- Container detection and item movement must run on the server and must not load
  client-only classes.
- Multiplayer requires a server-side mod for authoritative inventory changes.
  Client-only remote installs show an unavailable message when the server does
  not have Quick Stack Nearby.
- Shulker-box support needs separate validation because it touches nested item
  container data rather than only player inventory slots.
- Common API splits now proven by compile probes include resource identifiers,
  server-level access, item component/tag identity, Fabric networking payload
  APIs, action-bar feedback, and 26.x extractor-based client rendering.

## Research Evidence

- Fabric's current metadata documentation shows `fabric.mod.json` as the root
  loader metadata file, allows hyphenated mod ids, and uses `environment="*"`
  with main/client entrypoints for both-side mods.
- Fabric's event documentation describes API events as compatibility-friendly
  hooks and explicitly includes grouped client tick events, matching the smoke
  hook approach used here.
- Modrinth's project modify API documents `client_side`, `server_side`, body,
  license, and source URL fields that the project-page sync step should update
  once the install contract is final.
- InventorySort's `docs/button-slot-api.md` records the Inv+ right-side button
  reservation contract and first-party slot order.

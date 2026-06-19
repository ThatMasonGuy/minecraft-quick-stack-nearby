# Compatibility

Scope: Quick Stack Nearby source compatibility after the first playable
`1.21.11` implementation. No Minecraft version is supported for publishing yet.

## Candidate Profiles

| Profile | Compile anchor | Runtime versions | Reason to track |
| --- | --- | --- | --- |
| `1.20-1.20.4` | `1.20.4` | `1.20` through `1.20.4` | Older inventory screen and widget surface from the InventorySort donor. |
| `1.20.5-1.20.6` | `1.20.6` | `1.20.5` through `1.20.6` | First post-1.20.4 UI/API split tracked by InventorySort. |
| `1.21-1.21.5` | `1.21.5` | `1.21` through `1.21.5` | Stable early 1.21 group for container UI work. |
| `1.21.6-1.21.8` | `1.21.8` | `1.21.6` through `1.21.8` | Separate InventorySort button compatibility group. |
| `1.21.9-1.21.10` | `1.21.10` | `1.21.9` through `1.21.10` | Late 1.21 group before the 1.21.11 profile split. |
| `1.21.11` | `1.21.11` | `1.21.11` | Current default development anchor. |
| `26.x` | `26.2-pre-3` | `26.1` through `26.2-pre-3` | Experimental non-remap lane inherited from InventorySort. |

## Supported Profiles

None yet. `supported_minecraft_version_profiles` is intentionally blank until
manual gameplay smoke and exact-version launch smoke pass for every runtime
claimed on Modrinth.

## Current Evidence

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
  A client-only install is expected to be useful only for offline singleplayer
  or for UI-only detection before a server handshake disables the action.
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

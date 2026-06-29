# Quick Stack Nearby

A Fabric mod for Terraria-style "quick stack to nearby chests" in Minecraft.

The intended final shape is client and server together for multiplayer, and a
single client install for offline singleplayer where the integrated server runs
the server-side logic in the same process.

## Intent

- Add an unload button to the player inventory UI.
- Move matching items from the player inventory, and eventually supported
  carried storage such as shulker boxes, into nearby containers that already
  hold those item types.
- Keep the authoritative item movement on the server.
- Use Inventory Mods button-slot registration when an Inv+ suite mod is
  installed.
- Fall back to a local copy of the Inventory Mods button implementation when
  the slot API is unavailable.

## Current State

The `0.3.2` release supports Minecraft `1.20` through `26.3-snapshot-1`
using seven smoke-tested compatibility-group jars. The survival inventory
button sends a server-authoritative request that scans nearby containers and
moves matching main-inventory stacks into containers that already hold those
item types. The same quick-stack request can also be triggered from a
`B`-by-default keybind while no UI screen is open; holding the key repeats the
request.

Right-clicking the quick-stack button opens a player-inventory rule screen.
Main-inventory slots can be locked from unloading or assigned a keep count that
leaves that many items behind when quick stacking. Rules are saved under the
shared TempestStudios app-data folder and scoped by singleplayer instance/world
or multiplayer server/account.

Hotbar, armor, offhand, and carried shulker contents are intentionally left
alone for this release.

## Commands

Ops can inspect or change the nearby-container scan range:

```text
/quickstacknearby range
/quickstacknearby range <horizontal> <vertical>
/quickstacknearby reload
```

`/qsn` is available as a shorter alias. Range settings are persisted in the
shared TempestStudios app-data folder.

Supported Minecraft version profiles and exact runtime evidence are documented
in `COMPATIBILITY.md` and `gradle/version-profiles/README.md`.

The default local test build targets the supported `26.x` profile. Focused
launcher tests use that 26.x jar against exact runtime `26.1.2`.

## Build

```powershell
.\gradlew.bat buildAllMods --no-daemon --console=plain
.\gradlew.bat buildAllVersions --no-daemon --console=plain
.\gradlew.bat printVersionProfile --no-daemon --console=plain
```

Targeted local test build example:

```powershell
.\gradlew.bat buildAllMods "-Pminecraft_version_profile=26.x" --no-daemon --console=plain
```

Publishing targets only profiles whose exact runtime matrix has smoke evidence.

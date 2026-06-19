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

The `0.1.0` release supports Minecraft `1.21.11`. The survival inventory button
sends a server-authoritative request that scans nearby containers and moves
matching main-inventory stacks into containers that already hold those item
types.

Hotbar, armor, offhand, and carried shulker contents are intentionally left
alone for the first public review build.

## Commands

Ops can inspect or change the nearby-container scan range:

```text
/quickstacknearby range
/quickstacknearby range <horizontal> <vertical>
/quickstacknearby reload
```

`/qsn` is available as a shorter alias. Range settings are persisted in the
shared TempestStudios app-data folder.

Other Minecraft version profiles remain candidates in `COMPATIBILITY.md` and
`gradle/version-profiles/README.md` until compatibility work and smoke evidence
prove their release targets.

## Build

```powershell
.\gradlew.bat buildAllMods --no-daemon --console=plain
.\gradlew.bat buildAllVersions --no-daemon --console=plain
.\gradlew.bat printVersionProfile --no-daemon --console=plain
```

Supported profile build example:

```powershell
.\gradlew.bat buildAllMods "-Pminecraft_version_profile=1.21.11" --no-daemon --console=plain
```

Publishing currently targets only the smoke-tested `1.21.11` profile.

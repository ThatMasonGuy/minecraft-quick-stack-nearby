# Quick Stack Nearby

A Fabric mod scaffold for Terraria-style "quick stack to nearby chests" in
Minecraft.

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

This repository is scaffolding only. It contains the Fabric/Gradle build,
candidate compatibility profiles, CI and publishing workflows, smoke-test hooks,
project-page source copy, and the supplied icon assets. It does not yet
implement quick-stack gameplay.

No Minecraft version profile is supported for publishing yet. Candidate profiles
are tracked in `COMPATIBILITY.md` and `gradle/version-profiles/README.md` until
research and smoke evidence prove the real release targets.

## Build

```powershell
.\gradlew.bat buildAllMods --no-daemon --console=plain
.\gradlew.bat buildAllVersions --no-daemon --console=plain
.\gradlew.bat printVersionProfile --no-daemon --console=plain
```

Candidate profile build example:

```powershell
.\gradlew.bat buildAllMods "-Pminecraft_version_profile=1.21.11" --no-daemon --console=plain
```

Publishing stays disabled until `supported_minecraft_version_profiles` is
populated with smoke-tested profiles.

While no supported profiles are configured, `buildAllVersions` intentionally
falls back to the active profile build so the Bigger Boats-style push workflow
continues to compile a real jar instead of no-oping.

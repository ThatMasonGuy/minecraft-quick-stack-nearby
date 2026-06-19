# Minecraft Version Profiles

Build profiles keep one source tree while letting Gradle swap the Minecraft,
Fabric Loader, Fabric API, Loom, and Java target versions.

Quick Stack Nearby `0.1.0` supports Minecraft `1.21.11` only. The current
profile lists are:

```properties
minecraft_version_profile=1.21.11
supported_minecraft_version_profiles=1.21.11
candidate_minecraft_version_profiles=1.20-1.20.4,1.20.5-1.20.6,1.21-1.21.5,1.21.6-1.21.8,1.21.9-1.21.10,26.x
```

The candidate groups mirror InventorySort's UI/button compatibility surfaces
because QuickStack will either reserve a slot through the Inv+ API or fall back
to a copied InventorySort-style button implementation.

The first compile-probe pass for the implemented 1.21.11 feature is documented
in `docs/compatibility-research.md`.

## Candidate Profiles

| Profile | Compile anchor | Runtime versions | Compat group |
| --- | --- | --- | --- |
| `1.20-1.20.4` | `1.20.4` | `1.20` through `1.20.4` | `1.20-1.20.4` |
| `1.20.5-1.20.6` | `1.20.6` | `1.20.5` through `1.20.6` | `1.20.5-1.20.6` |
| `1.21-1.21.5` | `1.21.5` | `1.21` through `1.21.5` | `1.21-1.21.5` |
| `1.21.6-1.21.8` | `1.21.8` | `1.21.6` through `1.21.8` | `1.21.6-1.21.8` |
| `1.21.9-1.21.10` | `1.21.10` | `1.21.9` through `1.21.10` | `1.21.9-1.21.10` |
| `26.x` | `26.2-pre-3` | `26.1` through `26.2-pre-3` | `26.x` |

## Supported Profiles

| Profile | Compile anchor | Runtime versions | Compat group |
| --- | --- | --- | --- |
| `1.21.11` | `1.21.11` | `1.21.11` | `1.21.11` |

## Commands

```powershell
.\gradlew.bat printVersionProfile --no-daemon --console=plain
.\gradlew.bat buildAllMods --no-daemon --console=plain
.\gradlew.bat buildAllVersions --no-daemon --console=plain
.\gradlew.bat buildAllMods "-Pminecraft_version_profile=1.21.11" --no-daemon --console=plain
.\gradlew.bat buildValidationVersions --no-daemon --console=plain
.\gradlew.bat listVersionProfiles --no-daemon --console=plain
```

`buildAllVersions` builds every supported profile. For `0.1.0`, that means the
single `1.21.11` jar.

Focused smoke commands:

```powershell
.\gradlew.bat smokeTestSelectedClients "-Pquickstacknearby_smoke_profiles=1.21.11" "-Pquickstacknearby_smoke_game_versions=1.21.11" --no-daemon --console=plain
.\gradlew.bat smokeTestSelectedServers "-Pquickstacknearby_smoke_profiles=1.21.11" "-Pquickstacknearby_smoke_game_versions=1.21.11" --no-daemon --console=plain
```

## Promotion Rule

Only add a profile to `supported_minecraft_version_profiles` after the packaged
jar compiles and launches cleanly for every exact version listed in that
profile's `modrinth_game_versions`.

Profiles are compatibility groups, not guesses. Broaden a group only when the
same compiled jar really passes the exact runtime smoke checks; split a group
when source, dependency, metadata, or runtime validation proves a split is
needed.

## Java Toolchains

The `1.20-1.20.4` lane uses Java 17, `1.20.5+` and `1.21.x` lanes use Java 21,
and the experimental `26.x` lane uses Java 25. Local builds need those JDKs
installed or exposed through one of `JAVA_HOME`, `JAVA_HOME_17_X64`,
`JAVA_HOME_21_X64`, or `JAVA_HOME_25_X64`.

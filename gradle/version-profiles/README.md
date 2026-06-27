# Minecraft Version Profiles

Build profiles keep one source tree while letting Gradle swap the Minecraft,
Fabric Loader, Fabric API, Loom, and Java target versions.

Quick Stack Nearby `0.3.2` supports Minecraft `1.20` through
`26.3-snapshot-1`. The current profile lists are:

```properties
minecraft_version_profile=26.x
supported_minecraft_version_profiles=1.20-1.20.4,1.20.5-1.20.6,1.21-1.21.5,1.21.6-1.21.8,1.21.9-1.21.10,1.21.11,26.x
candidate_minecraft_version_profiles=
```

The active `26.x` profile builds the supported jar used for local 26.x testing.
Focused smoke can then launch that jar against exact runtime `26.1.2`. The
`26.1.2` profile file is runtime-only and should not be used as the normal
`buildAllMods` target.

The candidate groups mirror InventorySort's UI/button compatibility surfaces
because QuickStack will either reserve a slot through the Inv+ API or fall back
to a copied InventorySort-style button implementation.

The first compile-probe pass for the implemented 1.21.11 feature is documented
in `docs/compatibility-research.md`.

## Candidate Profiles

No active candidate profiles are queued for `0.3.2`.

## Supported Profiles

| Profile | Compile anchor | Runtime versions | Compat group |
| --- | --- | --- | --- |
| `1.20-1.20.4` | `1.20` | `1.20` through `1.20.4` | `1.20-1.20.4` |
| `1.20.5-1.20.6` | `1.20.5` | `1.20.5` through `1.20.6` | `1.20.5-1.20.6` |
| `1.21-1.21.5` | `1.21` | `1.21` through `1.21.5` | `1.21-1.21.5` |
| `1.21.6-1.21.8` | `1.21.6` | `1.21.6` through `1.21.8` | `1.21.6-1.21.8` |
| `1.21.9-1.21.10` | `1.21.9` | `1.21.9` through `1.21.10` | `1.21.9-1.21.10` |
| `1.21.11` | `1.21.11` | `1.21.11` | `1.21.11` |
| `26.x` | `26.3-snapshot-1` | `26.1`, `26.1.1`, `26.1.2`, `26.2`, `26.3-snapshot-1` | `26.x` |

Exact `26.x` runtime-only smoke profiles include `26.1`, `26.1.1`, `26.1.2`,
`26.2`, and `26.3-snapshot-1`. They are not release profiles; the public
release jar is built once from `26.x` and launched against each exact runtime
before its game version is claimed on Modrinth. `26.2-pre-3` is no longer a
current target now that `26.2` is final.

## Commands

```powershell
.\gradlew.bat printVersionProfile --no-daemon --console=plain
.\gradlew.bat buildAllMods --no-daemon --console=plain
.\gradlew.bat buildAllVersions --no-daemon --console=plain
.\gradlew.bat buildAllMods "-Pminecraft_version_profile=26.x" --no-daemon --console=plain
.\gradlew.bat buildValidationVersions --no-daemon --console=plain
.\gradlew.bat listVersionProfiles --no-daemon --console=plain
```

`buildAllVersions` builds every supported profile. For `0.3.2`, that means the
seven compatibility-group jars listed above.

Focused smoke commands:

```powershell
.\gradlew.bat smokeTestSelectedClients "-Pquickstacknearby_smoke_profiles=26.x" "-Pquickstacknearby_smoke_game_versions=26.1.2" --no-daemon --console=plain
.\gradlew.bat smokeTestSelectedServers "-Pquickstacknearby_smoke_profiles=26.x" "-Pquickstacknearby_smoke_game_versions=26.1.2" --no-daemon --console=plain
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

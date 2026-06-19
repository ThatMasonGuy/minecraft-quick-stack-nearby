# Smoke Tests

Quick Stack Nearby has scaffold smoke hooks for both client and dedicated-server
launches.

## Markers

- Client marker: `QUICKSTACKNEARBY_SMOKE_TEST_PASS`
- Server marker: `QUICKSTACKNEARBY_SERVER_SMOKE_TEST_PASS`

## Focused Commands

```powershell
.\gradlew.bat smokeTestSelectedClients "-Pquickstacknearby_smoke_profiles=1.21.11" "-Pquickstacknearby_smoke_game_versions=1.21.11" --no-daemon --console=plain
.\gradlew.bat smokeTestSelectedServers "-Pquickstacknearby_smoke_profiles=1.21.11" "-Pquickstacknearby_smoke_game_versions=1.21.11" --no-daemon --console=plain
```

## Matrix Policy

`gradle/smoke-tests.json` records candidate profiles as `pending`. A profile can
only move to `supported_minecraft_version_profiles` after every listed exact
game version has a `pass` record with a date, method, and evidence.

The publish workflow should be treated as the authoritative full-matrix gate
once the real implementation is ready.

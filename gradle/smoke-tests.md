# Smoke Tests

Quick Stack Nearby has scaffold smoke hooks for both client and dedicated-server
launches.

## Markers

- Client marker: `QUICKSTACKNEARBY_SMOKE_TEST_PASS`
- Server marker: `QUICKSTACKNEARBY_SERVER_SMOKE_TEST_PASS`

## Focused Commands

```powershell
.\gradlew.bat smokeTestSelected "-Pquickstacknearby_smoke_profiles=1.21.11" "-Pquickstacknearby_smoke_game_versions=1.21.11" --no-daemon --console=plain
.\gradlew.bat smokeTestSelectedClients "-Pquickstacknearby_smoke_profiles=1.21.11" "-Pquickstacknearby_smoke_game_versions=1.21.11" --no-daemon --console=plain
.\gradlew.bat smokeTestSelectedServers "-Pquickstacknearby_smoke_profiles=1.21.11" "-Pquickstacknearby_smoke_game_versions=1.21.11" --no-daemon --console=plain
```

## GitHub Workflow

The manual `candidate smoke validation` workflow mirrors the Bigger Boats
testing shape. With no filters it runs `smokeTestValidation` across supported
and candidate profiles. With filters it runs `smokeTestSelected`, preserving
both client and dedicated-server install sets unless `smoke_install_sets` narrows
the run.

## Matrix Policy

`gradle/smoke-tests.json` records pass evidence for every exact runtime claimed
by the supported compatibility-group profiles. The original full GitHub
Actions candidate smoke pass is recorded for Minecraft `1.20` through
`26.2-pre-3`; the `0.3.1` 26.x refresh records local selected client and
dedicated-server smoke for `26.1`, `26.1.1`, `26.1.2`, `26.2-pre-3`, `26.2`,
and `26.3-snapshot-1`.

A profile can only move to `supported_minecraft_version_profiles` after every
listed exact game version has a `pass` record with a date, method, and
evidence.

The publish workflow should be treated as the authoritative full-matrix gate
once the real implementation is ready.

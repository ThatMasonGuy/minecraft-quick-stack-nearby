# Compatibility Research

Research date: 2026-06-19.

Baseline: `4bedcd1 Implement quick stack for 1.21.11`.

## Commands Run

The 1.21.11 baseline was verified with:

```powershell
.\gradlew.bat buildRelease --no-daemon --console=plain
.\gradlew.bat smokeTestSelected '-Pquickstacknearby_smoke_profiles=1.21.11' --no-daemon --console=plain
```

Candidate compile probes were run with:

```powershell
.\gradlew.bat compileJava compileClientJava "-Pminecraft_version_profile=<profile>" --no-daemon --console=plain
```

Logs were written under `build/compat-research/`; that directory is generated
build evidence and is not committed.

## Results

| Profile | Compile result | First blockers | Bridge direction |
| --- | --- | --- | --- |
| `1.21.11` | Pass | None for the current implementation. | Original implementation anchor for the first compatibility pass. |
| `1.21.9-1.21.10` | Fails in `compileJava`. | `net.minecraft.resources.Identifier` does not exist; this lane still uses `ResourceLocation`. | Add a resource-id compat class or a version-specific request-payload source. Re-run compile to expose client blockers. |
| `1.21.6-1.21.8` | Fails in `compileJava`. | Same `Identifier` to `ResourceLocation` split as `1.21.9-1.21.10`. | Same resource-id compat bridge; expect the client button to use the pre-1.21.11 render method. |
| `1.21-1.21.5` | Fails in `compileJava`. | `Identifier` is still `ResourceLocation`; `ServerPlayer.level()` returns `Level` rather than `ServerLevel`. | Add resource-id and server-level compat. Re-run compile after common shims. |
| `1.20.5-1.20.6` | Fails in `compileJava`. | Same `ResourceLocation` and `ServerPlayer.level()` blockers as early 1.21. | Likely shares the early-1.21 common bridge, then needs a client button compat pass. |
| `1.20-1.20.4` | Fails in `compileJava`. | No data-component API, old Fabric networking, no `RegistryFriendlyByteBuf` or `StreamCodec`, no `CustomPacketPayload`, no `PayloadTypeRegistry`, no `ItemStack.isSameItemSameComponents`, no `ItemStack.getComponentsPatch`, and `Container.getMaxStackSize(ItemStack)` is unavailable. | This needs a separate old-networking payload, an NBT-era item-key bridge, and old container max-stack helpers. |
| `26.x` | Fails in `compileJava`. | `PayloadTypeRegistry.playC2S()` was renamed to `serverboundPlay()`; `ServerPlayer.displayClientMessage(Component, boolean)` is gone. | Add a 26.x networking registration bridge and message bridge. Client code also needs extractor-based rendering. |

## Common Shims

- `ResourceIdCompat`: expose `quickStackId(path)` and compile it per compat
  group. Use `Identifier.fromNamespaceAndPath` for `1.21.11` and 26.x; use
  `ResourceLocation.tryBuild` or constructor-based creation for older 1.x
  lanes.
- `ServerLevelCompat`: return a `ServerLevel` from `ServerPlayer`. `1.21.11`
  and 26.x can call `player.level()`; early 1.21 and 1.20 lanes should call
  `player.serverLevel()` or cast only inside a version-specific source.
- `PlayerFeedbackCompat`: show the action-bar result. `1.21.11` uses
  `displayClientMessage(component, true)`; 26.x exposes `sendOverlayMessage`.
- `ItemStackKeyCompat`: compare item identity and components/tags. Modern lanes
  can use `getComponentsPatch` and `isSameItemSameComponents`; `1.20-1.20.4`
  needs `Item + tag` identity, with `ItemStack.isSameItemSameTags` for
  comparison.
- `ContainerCompat`: expose `maxStackSize(container, stack)`. Modern lanes use
  `Container.getMaxStackSize(ItemStack)`; 1.20 uses `Container.getMaxStackSize()`
  plus `ItemStack.getMaxStackSize()`.

## Networking Bridges

- `1.21.11` through `1.20.5` can keep Fabric's object payload API once the
  `Identifier`/`ResourceLocation` naming is bridged.
- `1.20-1.20.4` must use the classic Fabric networking API:
  `ServerPlayNetworking.registerGlobalReceiver(ResourceLocation, handler)` and
  client send through a `FriendlyByteBuf` channel or the old `FabricPacket`
  shape.
- `26.x` keeps object payloads but uses `PayloadTypeRegistry.serverboundPlay()`
  and `clientboundPlay()` instead of `playC2S()` and `playS2C()`.

## Client UI Bridges

- `1.20` through `1.21.10` use `AbstractButton.renderWidget(GuiGraphics, ...)`
  for custom button drawing.
- `1.21.11` uses `AbstractButton.renderContents(GuiGraphics, ...)`.
- `26.x` removes the `GuiGraphics` render path for widgets in favor of
  `GuiGraphicsExtractor` and `extractContents`.
- Keep the mixin and button classes in compat-specific client source folders
  rather than trying to hide these method-name changes behind reflection.
- The Inv+ reflection bridge can stay shared after the screen/button classes
  compile, because it only reflects the external slot API and returns x/y
  placement.

## Recommended Implementation Order

1. Split the current 1.21.11 request payload, networking registration, player
   feedback, and button class behind small compat wrappers.
2. Bring up `1.21.9-1.21.10` first; it should validate the smallest
   `Identifier` to `ResourceLocation` bridge.
3. Bring up `1.21.6-1.21.8`, then `1.21-1.21.5` and `1.20.5-1.20.6` once the
   server-level bridge is in place.
4. Treat `1.20-1.20.4` as a separate old-networking and old-item-data port.
5. Treat `26.x` as a separate future-client-renderer port with Java 25 and the
   new Fabric networking registry names.

# Modrinth Project Page Copy

## Quick Stack Nearby

- Modrinth project name: `Quick Stack Nearby`
- Project ID: `5Hu4HCfZ`
- Summary:

Quickly unload matching inventory items into nearby containers.

### Description Markdown

```markdown
![Quick Stack Nearby](modrinth-gallery://QuickStackNearby/01_quick_stack_nearby.png)

Quick Stack Nearby brings Terraria-style quick stacking to Minecraft. The goal
is simple: press one inventory button and send matching items from your
inventory into nearby containers that already hold those items.

## What 0.1.0 Does

- Adds a quick-stack button to the player inventory.
- Moves matching main-inventory stacks into nearby containers that already hold
  those item types.
- Keeps item movement server-authoritative for multiplayer.
- Uses the Inv+ button-slot API when an Inventory Mods companion is installed.
- Falls back to a local InventorySort-style button placement when Inv+ is not
  installed.

## Install Note

Multiplayer quick stacking requires Quick Stack Nearby on both the server and
client. If the client is installed without the server mod, the button shows a
clear unavailable message instead of moving items.

Offline singleplayer works from a client install because the integrated server
runs the common mod logic in the same process.

Version 0.1.0 supports Minecraft 1.21.11 only. Hotbar, armor, offhand, and
carried shulker contents are intentionally left alone in this first review
build. The nearby-container range is currently fixed at 8 blocks horizontally
and 4 blocks vertically; configurable range is planned for the next feature
pass.

Fabric API is required.
```

### Project Metadata

- Client side: `required`
- Server side: `required`
- License ID: `LGPL-3.0-or-later`
- Source URL: `https://github.com/ThatMasonGuy/minecraft-quick-stack-nearby`
- Issues URL: `https://github.com/ThatMasonGuy/minecraft-quick-stack-nearby/issues`
- Icon: `quick-stack-nearby.jpg`

## Live Update Record

- 2026-06-19: Authenticated Modrinth readback confirmed project id `5Hu4HCfZ`,
  slug `quick-stack-nearby`, title `Quick Stack Nearby`, status `draft`,
  client/server metadata `unknown`, and license `LicenseRef-Unknown`.

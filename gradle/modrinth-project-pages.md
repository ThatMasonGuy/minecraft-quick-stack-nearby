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

## What 0.3.0 Does

- Adds a quick-stack button to the player inventory.
- Moves matching main-inventory stacks into nearby containers that already hold
  those item types.
- Lets players right-click the button to lock main-inventory slots or keep a
  chosen count behind while quick stacking.
- Adds server-operator commands for inspecting, changing, and reloading the
  nearby-container scan range.
- Keeps item movement server-authoritative for multiplayer.
- Uses the Inv+ button-slot API when an Inventory Mods companion is installed.
- Falls back to a local InventorySort-style button placement when Inv+ is not
  installed.

![Quick Stack Nearby rules](modrinth-gallery://QuickStackNearby/02_quick_stack_nearby.png)

## Install Note

Multiplayer quick stacking requires Quick Stack Nearby on both the server and
client. If the client is installed without the server mod, the button shows a
clear unavailable message instead of moving items.

Offline singleplayer works from a client install because the integrated server
runs the common mod logic in the same process.

Version 0.3.0 supports Minecraft 1.20 through 26.x. Hotbar, armor, offhand,
and carried shulker contents are intentionally left alone in this release.
More UI improvements are planned as the quick-stack button and rules screen
continue to mature.

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
- 2026-06-22: Live page sync completed after the `0.3.0` publish; authenticated
  readback confirmed project status `draft`, requested status `approved`,
  required client/server metadata, LGPL-3.0-or-later license, source/issues
  URLs, and seven listed `0.3.0` Modrinth versions.
- 2026-06-22: Live gallery refresh replaced the Modrinth gallery from local
  source with four images, kept `Quick Stack Result` featured, added the rules
  screenshot to the description, and authenticated readback confirmed the UI
  improvements note in the body.

# Quick Stack Design

Quick Stack Nearby should feel like Terraria's "quick stack to nearby chests":
one player action moves matching items out of the player's carried inventory
into nearby containers that already contain those item types.

## Runtime Contract

- The client owns the button and request UX.
- The server owns all item movement.
- In singleplayer, a client install is enough because the integrated server runs
  the common mod entrypoint.
- On remote multiplayer, the button must only perform item movement when the
  server side is installed and has acknowledged support.
- If the server is missing, the client should not attempt local-only container
  mutation.

## First Gameplay Slice

1. Client shows an unload button on appropriate player inventory screens.
2. Client sends one unload request packet.
3. Server scans a conservative nearby container radius.
4. Server builds a set of item types already present in those containers.
5. Server moves matching player inventory stacks into existing compatible
   container stacks and empty container slots.
6. Server shows a concise action-bar result.

## Implemented In 1.21.11

- The button appears on the vanilla survival inventory screen.
- The request packet is empty; all trust-sensitive work is server-side.
- The server scans an 8-block horizontal and 4-block vertical radius.
- Accessible block-entity containers with existing item stacks are eligible.
- Main inventory slots are moved, excluding hotbar, armor, and offhand.
- Containers accept only item/component matches that were already present in
  that container before the move began.
- The dedicated-server smoke test runs a deterministic mover self-test and
  asserts 48 matching items move while non-matching items stay put.

## Current Non-Goals

- No config file or Mod Menu screen is defined yet.
- No supported Minecraft profile has been promoted for publishing.
- No carried shulker-box contents are unpacked.
- No hotbar, armor, or offhand items are moved.

## Open Questions

- Whether "nearby chests" includes barrels, shulker boxes placed in-world,
  hoppers, droppers, dispensers, or modded inventories in the first release.
- Whether carried shulker boxes should be unpacked for matching items in the
  first release or staged behind a later setting.
- Whether the button should also appear on container screens.
- Whether the 8x4x8 scan radius feels right in play.

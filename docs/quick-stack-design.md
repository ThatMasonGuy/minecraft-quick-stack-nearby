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
6. Server returns a concise result for future feedback and smoke assertions.

## Explicit Non-Goals For The Scaffold

- No gameplay item movement is implemented yet.
- No networking packet is defined yet.
- No config file or Mod Menu screen is defined yet.
- No supported Minecraft profile has been promoted for publishing.

## Open Questions

- Exact scan radius and whether vertical range should differ from horizontal
  range.
- Whether "nearby chests" includes barrels, shulker boxes placed in-world,
  hoppers, droppers, dispensers, or modded inventories in the first release.
- Whether carried shulker boxes should be unpacked for matching items in the
  first release or staged behind a later setting.
- Whether the first UI button should appear only in the player inventory or also
  on container screens.

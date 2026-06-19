# Inventory Plus Integration

Quick Stack Nearby should cooperate with the Inv+ suite when it is installed
and use a local fallback when it is not.

## Decision

- If an Inv+ suite mod exposes `InventoryScreenButtonSlots`, reserve a
  right-side player inventory slot through that API.
- If the API is unavailable, use a local custom button implementation copied
  from InventorySort's button renderer and compatibility overlays.
- Keep the feature installable without requiring InventorySort, Inventory
  Search, or Inventory Catalogue.

## Current Implementation

- `QuickStackButtonSlotBridge` reflects
  `tempeststudios.inventorysort.api.InventoryScreenButtonSlots` when an Inv+
  suite mod exposes it.
- If reflection fails, `QuickStackScreenButtonSlots` provides the local
  InventorySort-style player-inventory slot fallback.
- The current fallback is intentionally narrow: it only reserves the one
  QuickStack survival-inventory button. Broader InventorySort widget classes can
  be transplanted later if older profile UI ports prove they need them.

## Slot API Plan

Use a stable owner and slot id:

```text
owner: quick-stack-nearby
slot: quick_stack_nearby
group: PLAYER_INVENTORY
priority: THIRD_PARTY_DEFAULT_PRIORITY
```

The button should reserve again when refreshing render position so recipe-book
screen shifts and other mods' reservations are respected. If the button is
hidden for a screen state, release the reservation.

## Fallback Plan

The current fallback is copied from InventorySort's slot-placement shape. Older
client profiles may still need version-specific button overlays from
InventorySort:

- `InventorySortHitboxButton`
- `InventorySortIconButton`
- `InventorySortModalIconButton`
- `InventorySortTextButton`
- shared renderer utilities
- the version-specific compat overlays that make those widgets compile

Only transplant the overlays that compile-probe evidence proves are needed.

## Build Target Implication

The UI fallback is why this repo tracks InventorySort's profile groups instead
of a broader server-only matrix. If future research proves the unload button can
share one compiled jar across more versions, profiles can be merged before any
supported publish target is declared.

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

The fallback should be transplanted from InventorySort instead of rewritten:

- `InventorySortHitboxButton`
- `InventorySortIconButton`
- `InventorySortModalIconButton`
- `InventorySortTextButton`
- shared renderer utilities
- the version-specific compat overlays that make those widgets compile

The fallback can be renamed into the QuickStack package after the candidate
profile matrix proves which overlays are still needed.

## Build Target Implication

The UI fallback is why this repo tracks InventorySort's profile groups instead
of a broader server-only matrix. If future research proves the unload button can
share one compiled jar across more versions, profiles can be merged before any
supported publish target is declared.

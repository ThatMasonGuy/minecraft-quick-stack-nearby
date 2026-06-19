package tempeststudios.quickstacknearby;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public final class QuickStackService {
    private QuickStackService() {
    }

    public static QuickStackMoveEngine.Result quickStack(ServerPlayer player) {
        return quickStack(player, QuickStackMoveEngine.SourceRules.EMPTY);
    }

    public static QuickStackMoveEngine.Result quickStack(
            ServerPlayer player,
            QuickStackMoveEngine.SourceRules sourceRules
    ) {
        List<QuickStackMoveEngine.Target> targets = nearbyTargets(player);
        QuickStackMoveEngine.Result result = QuickStackMoveEngine.moveMatchingItems(
                player.getInventory(),
                Inventory.getSelectionSize(),
                Inventory.INVENTORY_SIZE,
                targets,
                sourceRules
        );

        if (result.itemsMoved() > 0) {
            player.containerMenu.broadcastChanges();
        }
        player.displayClientMessage(messageFor(result, targets.size()), true);
        return result;
    }

    private static List<QuickStackMoveEngine.Target> nearbyTargets(ServerPlayer player) {
        ServerLevel level = player.level();
        BlockPos center = player.blockPosition();
        List<ScannedContainer> scannedContainers = new ArrayList<>();
        QuickStackServerConfig config = QuickStackServerConfig.getInstance();
        int horizontalRadius = config.horizontalRadius();
        int verticalRadius = config.verticalRadius();

        BlockPos min = center.offset(-horizontalRadius, -verticalRadius, -horizontalRadius);
        BlockPos max = center.offset(horizontalRadius, verticalRadius, horizontalRadius);
        for (BlockPos candidate : BlockPos.betweenClosed(min, max)) {
            BlockPos position = candidate.immutable();
            if (!level.isLoaded(position)) {
                continue;
            }

            BlockEntity blockEntity = level.getBlockEntity(position);
            if (!(blockEntity instanceof Container container) || !canUseContainer(container, blockEntity, player)) {
                continue;
            }

            Set<QuickStackMoveEngine.StackKey> acceptedTypes = QuickStackMoveEngine.acceptedTypes(container);
            if (!acceptedTypes.isEmpty()) {
                scannedContainers.add(new ScannedContainer(
                        container,
                        acceptedTypes,
                        center.distSqr(position)
                ));
            }
        }

        scannedContainers.sort(Comparator.comparingDouble(ScannedContainer::distance));

        List<QuickStackMoveEngine.Target> targets = new ArrayList<>(scannedContainers.size());
        for (ScannedContainer scannedContainer : scannedContainers) {
            targets.add(new QuickStackMoveEngine.Target(scannedContainer.container(), scannedContainer.acceptedTypes()));
        }
        return targets;
    }

    private static boolean canUseContainer(Container container, BlockEntity blockEntity, ServerPlayer player) {
        if (container instanceof BaseContainerBlockEntity baseContainer && !baseContainer.canOpen(player)) {
            return false;
        }
        return container.stillValid(player) && player.mayInteract(player.level(), blockEntity.getBlockPos());
    }

    private static Component messageFor(QuickStackMoveEngine.Result result, int targetCount) {
        if (targetCount == 0) {
            return Component.literal("No nearby containers with matching item stacks.");
        }
        if (result.itemsMoved() == 0) {
            return Component.literal("No matching inventory items to quick stack.");
        }

        String itemLabel = result.itemsMoved() == 1 ? "item" : "items";
        String containerLabel = result.targetContainersTouched() == 1 ? "container" : "containers";
        return Component.literal("Quick stacked "
                + result.itemsMoved()
                + " "
                + itemLabel
                + " into "
                + result.targetContainersTouched()
                + " "
                + containerLabel
                + ".");
    }

    private record ScannedContainer(
            Container container,
            Set<QuickStackMoveEngine.StackKey> acceptedTypes,
            double distance
    ) {
    }
}

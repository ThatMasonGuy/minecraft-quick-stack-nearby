package tempeststudios.quickstacknearby;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

final class QuickStackNetworkingCompat {
    private QuickStackNetworkingCompat() {
    }

    static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
                QuickStackLegacyPayloadCompat.TYPE,
                (payload, player, responseSender) -> QuickStackService.quickStack(
                        player,
                        QuickStackMoveEngine.SourceRules.fromPayloadRules(payload.request().slotRules())
                )
        );
    }
}

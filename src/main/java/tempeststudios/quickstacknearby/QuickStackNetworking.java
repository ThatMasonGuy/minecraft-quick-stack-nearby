package tempeststudios.quickstacknearby;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class QuickStackNetworking {
    private QuickStackNetworking() {
    }

    public static void register() {
        PayloadTypeRegistry.playC2S().register(QuickStackRequestPayload.TYPE, QuickStackRequestPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(
                QuickStackRequestPayload.TYPE,
                (payload, context) -> QuickStackService.quickStack(context.player())
        );
    }
}

package tempeststudios.quickstacknearby;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

final class QuickStackClientNetworkingCompat {
    private QuickStackClientNetworkingCompat() {
    }

    static boolean canSendQuickStack() {
        return ClientPlayNetworking.canSend(QuickStackLegacyPayloadCompat.TYPE);
    }

    static void sendQuickStackRequest(QuickStackRequestPayload request) {
        ClientPlayNetworking.send(new QuickStackLegacyPayloadCompat.Packet(request));
    }
}

package tempeststudios.quickstacknearby;

import net.minecraft.client.Minecraft;

final class ClientStateCompat {
    private ClientStateCompat() {
    }

    static boolean hasSingleplayerServer(Minecraft client) {
        return client.isSingleplayer();
    }

    static boolean isScreenOpen(Minecraft client) {
        return client.screen != null;
    }
}

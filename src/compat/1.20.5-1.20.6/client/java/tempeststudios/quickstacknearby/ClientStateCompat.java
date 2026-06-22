package tempeststudios.quickstacknearby;

import net.minecraft.client.Minecraft;

final class ClientStateCompat {
    private ClientStateCompat() {
    }

    static boolean hasSingleplayerServer(Minecraft client) {
        return client.isSingleplayer();
    }
}

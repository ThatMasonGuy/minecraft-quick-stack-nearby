package tempeststudios.quickstacknearby;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public final class ClientScreenCompat {
    private ClientScreenCompat() {
    }

    public static void setScreen(Minecraft client, Screen screen) {
        client.gui.setScreen(screen);
    }
}

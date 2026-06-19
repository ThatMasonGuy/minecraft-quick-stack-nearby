package tempeststudios.quickstacknearby;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

public final class QuickStackNearbySmokeTest {
    private static final String SMOKE_TEST_PROPERTY = "quickstacknearby.smokeTest";
    private static final int PASS_AFTER_TICKS = 20;

    private static int ticks;
    private static boolean complete;

    private QuickStackNearbySmokeTest() {
    }

    public static void registerIfEnabled() {
        if (!Boolean.getBoolean(SMOKE_TEST_PROPERTY)) {
            return;
        }

        System.out.println("[QuickStackNearby] Automated client smoke test armed.");
        ClientTickEvents.END_CLIENT_TICK.register(QuickStackNearbySmokeTest::tick);
    }

    private static void tick(Minecraft client) {
        if (complete) {
            return;
        }

        ticks++;
        if (ticks < PASS_AFTER_TICKS) {
            return;
        }

        complete = true;
        System.out.println(
                "QUICKSTACKNEARBY_SMOKE_TEST_PASS minecraftProfile="
                        + System.getProperty("quickstacknearby.smokeMinecraftProfile", "unknown")
                        + " gameVersion="
                        + System.getProperty("quickstacknearby.smokeGameVersion", "unknown")
                        + " releaseProfile="
                        + System.getProperty("quickstacknearby.smokeReleaseProfile", "unknown")
                        + " installSet="
                        + System.getProperty("quickstacknearby.smokeInstallSet", "unknown")
                        + " injectedMods="
                        + System.getProperty("fabric.addMods", "unknown")
        );
        client.stop();
    }
}

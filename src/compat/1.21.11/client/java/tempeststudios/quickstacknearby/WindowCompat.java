package tempeststudios.quickstacknearby;

import com.mojang.blaze3d.platform.Window;

final class WindowCompat {
    private WindowCompat() {
    }

    static long handle(Window window) {
        return window.handle();
    }
}

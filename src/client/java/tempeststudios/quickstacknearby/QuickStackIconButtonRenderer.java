package tempeststudios.quickstacknearby;

import net.minecraft.client.gui.GuiGraphics;

final class QuickStackIconButtonRenderer {
    static final int SIZE = 12;

    private static final int ICON_COLOR = 0xFF1C1C1C;
    private static final int ICON_HOVER_COLOR = 0xFF000000;

    private QuickStackIconButtonRenderer() {
    }

    static void render(GuiGraphics guiGraphics, int x, int y, boolean hovered) {
        drawBeveledBackground(guiGraphics, x, y, SIZE, SIZE, hovered);
        drawQuickStackIcon(guiGraphics, x, y, hovered ? ICON_HOVER_COLOR : ICON_COLOR);
    }

    private static void drawBeveledBackground(GuiGraphics guiGraphics, int x, int y, int width, int height, boolean hovered) {
        fill(guiGraphics, x + 2, y, x + width - 2, y + 1, 0xFF000000);
        fill(guiGraphics, x + 2, y + height - 1, x + width - 2, y + height, 0xFF000000);
        fill(guiGraphics, x, y + 2, x + 1, y + height - 2, 0xFF000000);
        fill(guiGraphics, x + width - 1, y + 2, x + width, y + height - 2, 0xFF000000);
        fill(guiGraphics, x + 1, y + 1, x + 2, y + 2, 0xFF000000);
        fill(guiGraphics, x + width - 2, y + 1, x + width - 1, y + 2, 0xFF000000);
        fill(guiGraphics, x + 1, y + height - 2, x + 2, y + height - 1, 0xFF000000);
        fill(guiGraphics, x + width - 2, y + height - 2, x + width - 1, y + height - 1, 0xFF000000);

        int centerColor = hovered ? 0xFFE0E0E0 : 0xFFC6C6C6;
        fill(guiGraphics, x + 2, y + 2, x + width - 2, y + height - 2, centerColor);

        fill(guiGraphics, x + 2, y + 1, x + width - 2, y + 2, 0xFFFFFFFF);
        fill(guiGraphics, x + 1, y + 2, x + 2, y + height - 2, 0xFFFFFFFF);
        fill(guiGraphics, x + 2, y + height - 2, x + width - 2, y + height - 1, 0xFF555555);
        fill(guiGraphics, x + width - 2, y + 2, x + width - 1, y + height - 2, 0xFF555555);
    }

    private static void drawQuickStackIcon(GuiGraphics guiGraphics, int x, int y, int color) {
        fill(guiGraphics, x + 3, y + 3, x + 6, y + 4, color);
        fill(guiGraphics, x + 4, y + 4, x + 7, y + 5, color);
        fill(guiGraphics, x + 5, y + 5, x + 8, y + 6, color);
        fill(guiGraphics, x + 6, y + 4, x + 7, y + 8, color);
        fill(guiGraphics, x + 5, y + 7, x + 8, y + 8, color);

        fill(guiGraphics, x + 3, y + 8, x + 9, y + 9, color);
        fill(guiGraphics, x + 3, y + 9, x + 4, y + 10, color);
        fill(guiGraphics, x + 8, y + 9, x + 9, y + 10, color);
        fill(guiGraphics, x + 4, y + 10, x + 8, y + 11, color);
    }

    private static void fill(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int color) {
        guiGraphics.fill(left, top, right, bottom, color);
    }
}

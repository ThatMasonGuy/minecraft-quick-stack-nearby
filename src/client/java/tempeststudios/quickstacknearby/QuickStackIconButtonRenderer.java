package tempeststudios.quickstacknearby;

import net.minecraft.client.gui.GuiGraphics;

final class QuickStackIconButtonRenderer {
    static final int SIZE = 12;

    private static final int ICON_COLOR = 0xFF1C1C1C;
    private static final int ICON_HOVER_COLOR = 0xFF000000;
    private static final int CHEST_COLOR = 0xFF6F451B;
    private static final int CHEST_HOVER_COLOR = 0xFF8A5A24;
    private static final int CHEST_DARK_COLOR = 0xFF4D3015;
    private static final int LATCH_COLOR = 0xFFFFD84D;
    private static final int ARROW_COLOR = 0xFF147A34;
    private static final int ARROW_HOVER_COLOR = 0xFF0C9639;

    private QuickStackIconButtonRenderer() {
    }

    static void render(GuiGraphics guiGraphics, int x, int y, boolean hovered) {
        drawBeveledBackground(guiGraphics, x, y, SIZE, SIZE, hovered);
        drawQuickStackIcon(guiGraphics, x, y, hovered);
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

    private static void drawQuickStackIcon(GuiGraphics guiGraphics, int x, int y, boolean hovered) {
        int outline = hovered ? ICON_HOVER_COLOR : ICON_COLOR;
        int chest = hovered ? CHEST_HOVER_COLOR : CHEST_COLOR;
        int arrow = hovered ? ARROW_HOVER_COLOR : ARROW_COLOR;

        fill(guiGraphics, x + 6, y + 2, x + 7, y + 6, arrow);
        fill(guiGraphics, x + 4, y + 5, x + 9, y + 6, arrow);
        fill(guiGraphics, x + 5, y + 6, x + 8, y + 7, arrow);

        fill(guiGraphics, x + 3, y + 6, x + 9, y + 7, outline);
        fill(guiGraphics, x + 2, y + 7, x + 10, y + 10, outline);
        fill(guiGraphics, x + 3, y + 7, x + 9, y + 9, chest);
        fill(guiGraphics, x + 3, y + 9, x + 9, y + 10, CHEST_DARK_COLOR);
        fill(guiGraphics, x + 5, y + 7, x + 7, y + 9, LATCH_COLOR);
    }

    private static void fill(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int color) {
        guiGraphics.fill(left, top, right, bottom, color);
    }
}

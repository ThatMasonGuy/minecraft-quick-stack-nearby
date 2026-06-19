package tempeststudios.quickstacknearby;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class QuickStackTextButton extends Button {
    private final boolean primary;

    public QuickStackTextButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        this(x, y, width, height, message, onPress, false);
    }

    public QuickStackTextButton(
            int x,
            int y,
            int width,
            int height,
            Component message,
            OnPress onPress,
            boolean primary
    ) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.primary = primary;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int color = QuickStackUi.button(
                guiGraphics,
                getX(),
                getY(),
                getWidth(),
                getHeight(),
                isHoveredOrFocused(),
                active,
                primary
        );
        int textX = getX() + (getWidth() - Minecraft.getInstance().font.width(getMessage())) / 2;
        int textY = getY() + (getHeight() - 8) / 2;
        guiGraphics.drawString(Minecraft.getInstance().font, getMessage(), textX, textY, color, false);
    }
}

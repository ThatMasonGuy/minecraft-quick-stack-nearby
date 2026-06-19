package tempeststudios.quickstacknearby;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public final class QuickStackIconButton extends Button {
    private final OnPress secondaryOnPress;

    public QuickStackIconButton(int x, int y, Component tooltip, OnPress onPress) {
        this(x, y, tooltip, onPress, null);
    }

    public QuickStackIconButton(int x, int y, Component tooltip, OnPress onPress, OnPress secondaryOnPress) {
        super(x, y, QuickStackIconButtonRenderer.SIZE, QuickStackIconButtonRenderer.SIZE, Component.empty(), onPress, DEFAULT_NARRATION);
        this.secondaryOnPress = secondaryOnPress;
        setTooltip(Tooltip.create(tooltip));
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (secondaryOnPress != null && event.button() == 1 && active && visible && isMouseOver(event.x(), event.y())) {
            secondaryOnPress.onPress(this);
            return true;
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        QuickStackIconButtonRenderer.render(guiGraphics, getX(), getY(), isHoveredOrFocused());
    }
}

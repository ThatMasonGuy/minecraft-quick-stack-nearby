package tempeststudios.quickstacknearby;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public final class QuickStackIconButton extends Button {
    public QuickStackIconButton(int x, int y, Component tooltip, OnPress onPress) {
        super(x, y, QuickStackIconButtonRenderer.SIZE, QuickStackIconButtonRenderer.SIZE, Component.empty(), onPress, DEFAULT_NARRATION);
        setTooltip(Tooltip.create(tooltip));
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        QuickStackIconButtonRenderer.render(guiGraphics, getX(), getY(), isHoveredOrFocused());
    }
}

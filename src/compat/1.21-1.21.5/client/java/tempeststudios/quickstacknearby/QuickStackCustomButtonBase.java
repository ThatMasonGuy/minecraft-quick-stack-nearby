package tempeststudios.quickstacknearby;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

abstract class QuickStackCustomButtonBase extends Button {
    private final OnPress secondaryOnPress;

    protected QuickStackCustomButtonBase(
            int x,
            int y,
            int width,
            int height,
            Component message,
            OnPress onPress,
            Component tooltip,
            OnPress secondaryOnPress
    ) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.secondaryOnPress = secondaryOnPress;
        if (tooltip != null && !tooltip.getString().isEmpty()) {
            setTooltip(Tooltip.create(tooltip));
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (handleSecondaryClick(mouseX, mouseY, button)) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        paintButton(guiGraphics, mouseX, mouseY, partialTick);
    }

    protected abstract void paintButton(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    protected final void setButtonBounds(int x, int y, int width, int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    private boolean handleSecondaryClick(double mouseX, double mouseY, int button) {
        if (secondaryOnPress != null && button == 1 && active && visible && isMouseOver(mouseX, mouseY)) {
            secondaryOnPress.onPress(this);
            return true;
        }
        return false;
    }
}

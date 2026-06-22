package tempeststudios.quickstacknearby;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

abstract class QuickStackRulesScreenBase extends Screen {
    protected QuickStackRulesScreenBase(Component title) {
        super(title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        paintScreen(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    protected abstract void paintScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
}

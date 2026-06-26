package tempeststudios.quickstacknearby.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tempeststudios.quickstacknearby.QuickStackRulesButtonScreen;

@Mixin(AbstractContainerScreen.class)
public abstract class QuickStackInventoryMouseClickedMixin {
    @Inject(method = "mouseClicked(DDI)Z", at = @At("HEAD"), cancellable = true, require = 0)
    private void quickStackNearby$onMouseClicked(
            double mouseX,
            double mouseY,
            int button,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if ((Object) this instanceof QuickStackRulesButtonScreen screen
                && screen.quickstacknearby$openRulesFromButton(mouseX, mouseY, button)) {
            cir.setReturnValue(true);
        }
    }
}

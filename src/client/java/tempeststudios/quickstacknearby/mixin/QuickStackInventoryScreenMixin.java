package tempeststudios.quickstacknearby.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tempeststudios.quickstacknearby.QuickStackButtonSlotBridge;
import tempeststudios.quickstacknearby.QuickStackClientNetworking;
import tempeststudios.quickstacknearby.ClientScreenCompat;
import tempeststudios.quickstacknearby.QuickStackIconButton;
import tempeststudios.quickstacknearby.QuickStackRulesScreen;
import tempeststudios.quickstacknearby.RecipeBookAwareButtonScreen;
import tempeststudios.quickstacknearby.WindowCompat;

@Mixin(AbstractContainerScreen.class)
public abstract class QuickStackInventoryScreenMixin implements RecipeBookAwareButtonScreen {
    @Unique private static final String quickStackNearby$OWNER = "quick-stack-nearby";
    @Unique private static final String quickStackNearby$SLOT = "quick_stack_nearby";

    @Unique private Button quickStackNearby$button;

    @Inject(method = "init", at = @At("TAIL"))
    private void quickStackNearby$onInit(CallbackInfo ci) {
        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        if (!(screen instanceof InventoryScreen)) {
            quickStackNearby$button = null;
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client == null || client.player == null) {
            quickStackNearby$button = null;
            return;
        }

        QuickStackButtonSlotBridge.releaseOwner(screen, quickStackNearby$OWNER);
        QuickStackButtonSlotBridge.SlotPlacement placement = QuickStackButtonSlotBridge.reservePlayerInventorySlot(
                screen,
                quickStackNearby$OWNER,
                quickStackNearby$SLOT
        );

        Button button = new QuickStackIconButton(
                placement.x(),
                placement.y(),
                Component.literal("Quick stack to nearby containers. Right-click for slot rules."),
                pressed -> {
                    QuickStackClientNetworking.sendQuickStackRequest();
                    quickStackNearby$clearFocus(client, screen, pressed);
                },
                pressed -> {
                    ClientScreenCompat.setScreen(client, new QuickStackRulesScreen(screen, client.player));
                    quickStackNearby$clearFocus(client, screen, pressed);
                }
        );
        ((ScreenAccessor) this).invokeAddRenderableWidget(button);
        quickStackNearby$button = button;
    }

    @Inject(method = {"render", "extractRenderState"}, at = @At("HEAD"), require = 0)
    private void quickStackNearby$onRender(CallbackInfo ci) {
        quickStackNearby$updateButtonPosition();
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true, require = 0)
    private void quickStackNearby$onMouseClicked(CallbackInfoReturnable<Boolean> cir) {
        if (quickStackNearby$button == null) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client == null || client.player == null || !quickStackNearby$isRightMouseButtonDown(client)) {
            return;
        }

        double mouseX = quickStackNearby$scaledMouseX(client);
        double mouseY = quickStackNearby$scaledMouseY(client);
        if (!quickStackNearby$button.isMouseOver(mouseX, mouseY)) {
            return;
        }

        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        ClientScreenCompat.setScreen(client, new QuickStackRulesScreen(screen, client.player));
        quickStackNearby$clearFocus(client, screen, quickStackNearby$button);
        cir.setReturnValue(true);
    }

    @Override
    public void quickstacknearby$updateButtonPositionsFromRecipeBookRender() {
        quickStackNearby$updateButtonPosition();
    }

    @Inject(method = "removed", at = @At("HEAD"))
    private void quickStackNearby$onRemoved(CallbackInfo ci) {
        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        QuickStackButtonSlotBridge.releaseOwner(screen, quickStackNearby$OWNER);
        quickStackNearby$button = null;
    }

    @Unique
    private void quickStackNearby$updateButtonPosition() {
        if (quickStackNearby$button == null) {
            return;
        }

        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        QuickStackButtonSlotBridge.SlotPlacement placement = QuickStackButtonSlotBridge.reservePlayerInventorySlot(
                screen,
                quickStackNearby$OWNER,
                quickStackNearby$SLOT
        );
        quickStackNearby$button.setX(placement.x());
        quickStackNearby$button.setY(placement.y());
    }

    @Unique
    private static void quickStackNearby$clearFocus(Minecraft client, AbstractContainerScreen<?> screen, Button button) {
        client.execute(() -> {
            button.setFocused(false);
            screen.setFocused(null);
        });
    }

    @Unique
    private static boolean quickStackNearby$isRightMouseButtonDown(Minecraft client) {
        return GLFW.glfwGetMouseButton(WindowCompat.handle(client.getWindow()), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
    }

    @Unique
    private static double quickStackNearby$scaledMouseX(Minecraft client) {
        Window window = client.getWindow();
        return client.mouseHandler.xpos() * window.getGuiScaledWidth() / Math.max(1, window.getWidth());
    }

    @Unique
    private static double quickStackNearby$scaledMouseY(Minecraft client) {
        Window window = client.getWindow();
        return client.mouseHandler.ypos() * window.getGuiScaledHeight() / Math.max(1, window.getHeight());
    }
}

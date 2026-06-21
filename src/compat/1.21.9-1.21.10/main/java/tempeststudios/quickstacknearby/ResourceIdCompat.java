package tempeststudios.quickstacknearby;

import net.minecraft.resources.ResourceLocation;

final class ResourceIdCompat {
    private ResourceIdCompat() {
    }

    static ResourceLocation quickStackId(String path) {
        return ResourceLocation.fromNamespaceAndPath(QuickStackNearby.MOD_ID, path);
    }
}

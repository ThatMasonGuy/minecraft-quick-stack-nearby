package tempeststudios.quickstacknearby;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record QuickStackRequestPayload() implements CustomPacketPayload {
    public static final QuickStackRequestPayload INSTANCE = new QuickStackRequestPayload();
    public static final Type<QuickStackRequestPayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(QuickStackNearby.MOD_ID, "quick_stack")
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, QuickStackRequestPayload> CODEC =
            StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

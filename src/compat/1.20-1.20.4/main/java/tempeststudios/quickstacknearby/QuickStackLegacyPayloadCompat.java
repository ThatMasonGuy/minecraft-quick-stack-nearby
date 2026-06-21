package tempeststudios.quickstacknearby;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;

final class QuickStackLegacyPayloadCompat {
    static final PacketType<Packet> TYPE = PacketType.create(
            ResourceIdCompat.quickStackId("quick_stack"),
            Packet::read
    );

    private QuickStackLegacyPayloadCompat() {
    }

    record Packet(QuickStackRequestPayload request) implements FabricPacket {
        @Override
        public void write(FriendlyByteBuf buf) {
            request.write(buf);
        }

        @Override
        public PacketType<?> getType() {
            return TYPE;
        }

        private static Packet read(FriendlyByteBuf buf) {
            return new Packet(QuickStackRequestPayload.read(buf));
        }
    }
}

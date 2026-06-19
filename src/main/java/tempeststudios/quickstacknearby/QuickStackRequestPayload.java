package tempeststudios.quickstacknearby;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public record QuickStackRequestPayload(List<SlotRule> slotRules) implements CustomPacketPayload {
    private static final int MAX_SLOT_RULES = 64;

    public static final QuickStackRequestPayload EMPTY = new QuickStackRequestPayload(List.of());
    public static final Type<QuickStackRequestPayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(QuickStackNearby.MOD_ID, "quick_stack")
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, QuickStackRequestPayload> CODEC =
            CustomPacketPayload.codec(QuickStackRequestPayload::write, QuickStackRequestPayload::read);

    public QuickStackRequestPayload {
        slotRules = List.copyOf(slotRules);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private void write(RegistryFriendlyByteBuf buf) {
        int count = Math.min(slotRules.size(), MAX_SLOT_RULES);
        buf.writeVarInt(count);
        for (int i = 0; i < count; i++) {
            SlotRule rule = slotRules.get(i);
            buf.writeVarInt(rule.slotIndex());
            buf.writeBoolean(rule.locked());
            buf.writeVarInt(Math.max(0, rule.keepCount()));
        }
    }

    private static QuickStackRequestPayload read(RegistryFriendlyByteBuf buf) {
        int count = Math.max(0, Math.min(MAX_SLOT_RULES, buf.readVarInt()));
        List<SlotRule> rules = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int slotIndex = buf.readVarInt();
            boolean locked = buf.readBoolean();
            int keepCount = Math.max(0, buf.readVarInt());
            rules.add(new SlotRule(slotIndex, locked, keepCount));
        }
        return new QuickStackRequestPayload(rules);
    }

    public record SlotRule(int slotIndex, boolean locked, int keepCount) {
    }
}

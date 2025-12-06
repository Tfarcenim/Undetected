package tfar.undetected.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tfar.undetected.Constants;
import tfar.undetected.UndetectedClient;


public record S2CEntityDetectedPacket(int id,Op op) implements S2CModPacket<RegistryFriendlyByteBuf> {
    public static final Type<S2CEntityDetectedPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    "add_entity_detected"
            )
    );

    public enum Op {
        ADD,REMOVE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CEntityDetectedPacket> CODEC = StreamCodec.ofMember(
            S2CEntityDetectedPacket::write, S2CEntityDetectedPacket::new
    );

    private S2CEntityDetectedPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readInt(),buf.readEnum(Op.class));
    }

    private void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeEnum(op);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handleClient() {
        UndetectedClient.handle(this);
    }
}

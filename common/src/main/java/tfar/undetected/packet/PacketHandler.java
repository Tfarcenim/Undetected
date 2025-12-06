package tfar.undetected.packet;

import net.minecraft.server.level.ServerPlayer;
import tfar.undetected.platform.Services;

public class PacketHandler {

    public static void registerPackets() {
        Services.PLATFORM.registerClientPlayPacket(S2CEntityDetectedPacket.TYPE, S2CEntityDetectedPacket.CODEC);
    }

    public static void sendToClient(S2CModPacket<?> packet, ServerPlayer player) {
            Services.PLATFORM.sendToClient(packet, player);
    }
}

package tfar.undetected;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.Entity;
import tfar.undetected.packet.S2CEntityDetectedPacket;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UndetectedClient {

    static Set<UUID> currentlyTracking = new HashSet<>();
    static int detectionCount;

    static final LayeredDraw.Layer layer = (guiGraphics, deltaTracker) -> {
        if (UndetectedConfig.CONFIG.DISPLAY_COUNT.get()) {
            guiGraphics.drawString(Minecraft.getInstance().font, ""+detectionCount,0,0,0xffffff);
        }
    };


    public static void handle(S2CEntityDetectedPacket packet) {
        Entity entity = Minecraft.getInstance().level.getEntity(packet.id());
        if (entity != null) {
            switch (packet.op()) {
                case ADD -> {
                    if (!currentlyTracking.contains(entity.getUUID())) {
                        detectionCount++;
                        currentlyTracking.add(entity.getUUID());
                    }
                }
                case REMOVE -> {
                    currentlyTracking.remove(entity.getUUID());
                }
            }
        }
    }

}

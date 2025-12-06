package tfar.undetected;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import tfar.undetected.packet.PacketHandler;
import tfar.undetected.packet.S2CEntityDetectedPacket;
import tfar.undetected.platform.NeoForgePlatformHelper;
import tfar.undetected.platform.Services;

@Mod(Constants.MOD_ID)
public class UndetectedNeoforge {

    public UndetectedNeoforge(IEventBus eventBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.SERVER,UndetectedConfig.SERVER_SPEC);
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        Undetected.init();
        eventBus.addListener(this::setupPackets);
        NeoForge.EVENT_BUS.addListener(this::onTargetSet);
    }

    void onTargetSet(LivingChangeTargetEvent event) {
        Mob attacker = (Mob)event.getEntity();
        LivingEntity originalTarget = attacker.getTarget();
        LivingEntity newAboutToBeSetTarget = event.getNewAboutToBeSetTarget();
        LivingEntity originalAboutToBeSetTarget = event.getOriginalAboutToBeSetTarget();
        if (newAboutToBeSetTarget instanceof ServerPlayer player) {
            PacketHandler.sendToClient(new S2CEntityDetectedPacket(attacker.getId(), S2CEntityDetectedPacket.Op.ADD),player);
            if (UndetectedConfig.CONFIG.DIE.get()) {
                player.kill();
            }
        } else {
            if (originalTarget instanceof ServerPlayer player) {
                PacketHandler.sendToClient(new S2CEntityDetectedPacket(attacker.getId(), S2CEntityDetectedPacket.Op.REMOVE),player);
            }
        }
    }

    private void setupPackets(final RegisterPayloadHandlersEvent event) {
        NeoForgePlatformHelper.setRegistrar(event.registrar(Constants.MOD_ID).versioned("1").optional());
        PacketHandler.registerPackets();
    }
}
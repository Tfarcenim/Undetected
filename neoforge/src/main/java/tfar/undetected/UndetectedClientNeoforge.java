package tfar.undetected;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@Mod(value = Constants.MOD_ID,dist = Dist.CLIENT)
public class UndetectedClientNeoforge {

    public UndetectedClientNeoforge(IEventBus bus) {
        bus.addListener(this::overlays);
    }


    void overlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,"layer"),UndetectedClient.layer);
    }
}

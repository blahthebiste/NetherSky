package net.starlegacy.nethersky;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.starlegacy.nethersky.skyrenderer.ISkyRenderer;
import net.starlegacy.nethersky.skyrenderer.SkyboxSkyRenderer;

@Mod(modid = "${mod_id}", name = "${mod_name}", version = "${mod_version}")
@Mod.EventBusSubscriber
public class NetherSky {
    private static ISkyRenderer skyRenderer;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        skyRenderer = new SkyboxSkyRenderer();
    }

    @SubscribeEvent
    public static void worldLoad(WorldEvent.Load event) {
        World world = event.getWorld();

        if (world.isRemote && world.provider.getDimensionType() == DimensionType.NETHER) {
            world.provider.setSkyRenderer(new IRenderHandler() {
                @Override
                public void render(float partialTicks, WorldClient world, Minecraft mc) {
                    skyRenderer.render();
                }
            });
        }
    }
}

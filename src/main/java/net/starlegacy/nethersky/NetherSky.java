package net.starlegacy.nethersky;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.starlegacy.nethersky.skyrenderer.ISkyRenderer;
import net.starlegacy.nethersky.skyrenderer.SkyboxSkyRenderer;
import org.jline.utils.Log;

import static net.minecraft.client.Minecraft.getMinecraft;

@Mod(modid = "nethersky", name = "NetherSky", version = "1.0.0")
@Mod.EventBusSubscriber
public class NetherSky {
    private static ISkyRenderer skyRenderer;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        DimensionType.NETHER.clazz = CandyWorldProvider.class;
        skyRenderer = new SkyboxSkyRenderer();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        getMinecraft().effectRenderer.registerParticle(EnumParticleTypes.REDSTONE.getParticleID(),
                (particleID, worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, p_178902_15_) ->
                        new ParticleLaser(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn));
        getMinecraft().getLanguageManager().getLanguages().forEach(lang -> lang.bidirectional = true);
        getMinecraft().fontRenderer.setBidiFlag(true);
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

        Log.info("BIDIRECTIONAL: " + getMinecraft().getLanguageManager().getCurrentLanguage().isBidirectional());
    }
}

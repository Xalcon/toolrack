package xalcon.toolrack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xalcon.toolrack.client.renderer.ToolRackTileEntityRenderer;
import xalcon.toolrack.common.init.ModBlocks;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ToolRackMod.MOD_ID)
public class ToolRackMod {
    public final static String MOD_ID = "toolrack";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public ToolRackMod() {
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(ModBlocks.tileTypeToolRack, ToolRackTileEntityRenderer::new);
    }
}

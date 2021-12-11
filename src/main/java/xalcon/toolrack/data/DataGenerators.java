package xalcon.toolrack.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import xalcon.toolrack.ToolRackMod;
import xalcon.toolrack.data.client.ModBlockStateProvider;
import xalcon.toolrack.data.client.ModItemModelProvider;
import xalcon.toolrack.data.server.ModBlocksLootTableProvider;
import xalcon.toolrack.data.server.ModRecipeProvider;

@Mod.EventBusSubscriber(modid = ToolRackMod.MOD_ID, bus = Bus.MOD)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(new ModBlocksLootTableProvider(gen));
        gen.addProvider(new ModRecipeProvider(gen));
    }
}

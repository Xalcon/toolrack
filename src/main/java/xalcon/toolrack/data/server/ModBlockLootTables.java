package xalcon.toolrack.data.server;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;
import xalcon.toolrack.ToolRackMod;
import xalcon.toolrack.common.init.ModBlocks;

public class ModBlockLootTables extends BlockLootTables
{
    @Override
    protected void addTables()
    {
        dropSelf(ModBlocks.blockToolRack);
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return StreamSupport
            .stream(ForgeRegistries.BLOCKS.spliterator(), false)
            .filter(entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(ToolRackMod.MOD_ID))
            .collect(Collectors.toSet());
    
    }
}

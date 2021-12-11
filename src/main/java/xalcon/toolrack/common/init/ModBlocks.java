package xalcon.toolrack.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;
import xalcon.toolrack.ToolRackMod;
import xalcon.toolrack.common.ToolRackCreativeTab;
import xalcon.toolrack.common.blocks.ToolRackBlock;
import xalcon.toolrack.common.tileentities.ToolRackTileEntity;

@EventBusSubscriber(modid = ToolRackMod.MOD_ID, bus = Bus.MOD)
@ObjectHolder(ToolRackMod.MOD_ID)
public class ModBlocks
{
    @ObjectHolder("toolrack")
    public static ToolRackBlock blockToolRack;
    
    @ObjectHolder("toolrack")
    public static BlockItem itemToolRack;

    @ObjectHolder("toolrack")
    public static TileEntityType<ToolRackTileEntity> tileTypeToolRack;

    @SubscribeEvent
    public static void onRegisterBlocks(final RegistryEvent.Register<Block> event)
    {
        blockToolRack = new ToolRackBlock(Block.Properties.of(Material.WOOD));
        blockToolRack.setRegistryName("toolrack");
        event.getRegistry().register(blockToolRack);
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event)
    {
        itemToolRack = new BlockItem(blockToolRack, new Item.Properties().tab(ToolRackCreativeTab.INSTANCE));
        itemToolRack.setRegistryName("toolrack");
        event.getRegistry().register(itemToolRack);
    }

    @SubscribeEvent
    public static void onRegisterTileEntities(final RegistryEvent.Register<TileEntityType<?>> event)
    {
        tileTypeToolRack = TileEntityType.Builder.of(ToolRackTileEntity::new, blockToolRack).build(null);
        tileTypeToolRack.setRegistryName("toolrack");
        event.getRegistry().register(tileTypeToolRack);
    }
}

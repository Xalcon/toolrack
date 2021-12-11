package xalcon.toolrack.common;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import xalcon.toolrack.ToolRackMod;
import xalcon.toolrack.common.init.ModBlocks;

public class ToolRackCreativeTab extends ItemGroup {

    public static ToolRackCreativeTab INSTANCE = new ToolRackCreativeTab();

    private ToolRackCreativeTab() {
        super(ToolRackMod.MOD_ID);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModBlocks.blockToolRack);
    }
    
}

package xalcon.toolrack.common.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import xalcon.toolrack.common.init.ModBlocks;

public class ToolRackTileEntity extends TileEntity
{
    // private final IItemHandler itemHandler = new ItemStackHandler(4);
    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    public ToolRackTileEntity()
    {
        super(ModBlocks.tileTypeToolRack);
    }

    public ItemStack getItemStackInSlot(int slot)
    {
        if(slot < 0 || slot > 4) return ItemStack.EMPTY;

        return inventory.get(slot);
    }

    // @Override
    // public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    //     if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
    //         return LazyOptional.of(() -> itemHandler).cast();
    //     return super.getCapability(cap, side);
    // }

    public ActionResultType slotInteract(int slot, PlayerEntity player, Hand hand, BlockState state)
    {
        if(slot < 0 || slot > 4) return ActionResultType.FAIL;

        ItemStack containedItem = inventory.get(slot);
        ItemStack playerItem = player.getItemInHand(hand);
        if(containedItem.isEmpty())
        {
            if(playerItem.isStackable() || playerItem.getItem() instanceof BlockItem) return ActionResultType.FAIL;

            ItemStack inputItem = playerItem.copy();
            inputItem.setCount(1);
            inventory.set(slot, inputItem);
            playerItem.shrink(1);

            this.setChanged();
            level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.BLOCK_UPDATE);
        }
        else if(playerItem.isEmpty())
        {
            player.setItemInHand(hand, containedItem.copy());
            containedItem.setCount(0);
            this.setChanged();
            level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.BLOCK_UPDATE);
        }
        return ActionResultType.FAIL;
    }

    public NonNullList<ItemStack> GetInventory()
    {
        return inventory;
    }

    /////////////////////////////////
    // Chunk Save / Load
    /////////////////////////////////
    @Override // Chunk save
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        CompoundNBT nbt = super.save(p_189515_1_);
        saveToNbt(nbt);
        return nbt;
    }

    @Override // Chunk load
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        loadFromNbt(nbt);
    }

    /////////////////////////////////
    // Chunk Sync
    /////////////////////////////////
    @Override // Chunk sync send
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        saveToNbt(nbt);
        return nbt;
    }
    
    @Override // Chunk sync receive
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
        loadFromNbt(tag);
    }

    /////////////////////////////////
    // Block Update
    /////////////////////////////////
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 3, this.getUpdateTag());
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        loadFromNbt(pkt.getTag());
    }

    /////////////////////////////////
    // Save/Load impl
    /////////////////////////////////
    private void saveToNbt(CompoundNBT nbt)
    {
        nbt.put("item0", inventory.get(0).save(new CompoundNBT()));
        nbt.put("item1", inventory.get(1).save(new CompoundNBT()));
        nbt.put("item2", inventory.get(2).save(new CompoundNBT()));
        nbt.put("item3", inventory.get(3).save(new CompoundNBT()));
    }

    private void loadFromNbt(CompoundNBT nbt)
    {
        inventory.set(0, ItemStack.of(nbt.getCompound("item0")));
        inventory.set(1, ItemStack.of(nbt.getCompound("item1")));
        inventory.set(2, ItemStack.of(nbt.getCompound("item2")));
        inventory.set(3, ItemStack.of(nbt.getCompound("item3")));
    }
}

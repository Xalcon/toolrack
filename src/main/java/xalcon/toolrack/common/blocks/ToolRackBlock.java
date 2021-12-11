package xalcon.toolrack.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import xalcon.toolrack.common.tileentities.ToolRackTileEntity;

public class ToolRackBlock extends ContainerBlock {
    // Using a custom property makes the toolrack work with Thermal Expansion Crescent Hammer
    public static final DirectionProperty FACING = DirectionProperty.create("direction", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);

    private VoxelShape[] SHAPES = {
        Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D), // DOWN
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), // UP

        Block.box(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D), // NORTH
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), // SOUTH
        Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), // WEST
        Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D), // EAST
    };

    public ToolRackBlock(Properties properties) {
        super(properties
            .harvestLevel(0)
            .harvestTool(ToolType.AXE)
            .strength(2.0f, 2.0f));
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rtResult) {
        Direction direction = state.getValue(FACING);

        if(world.isClientSide)
        {
            
            if(rtResult.getDirection() != direction) return ActionResultType.PASS;
            return ActionResultType.SUCCESS;
        }
        
        Vector3d hitLoc = rtResult.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        int slot = getSlotIndex(hitLoc.x, hitLoc.y, hitLoc.z, direction);

        TileEntity tile = world.getBlockEntity(pos);
        if(tile instanceof ToolRackTileEntity)
        {
            ToolRackTileEntity toolRack = (ToolRackTileEntity) tile;
            return toolRack.slotInteract(slot, player, hand, state);
        }

        return super.use(state, world, pos, player, hand, rtResult);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if(tileEntity instanceof ToolRackTileEntity)
        {
            InventoryHelper.dropContents(world, pos, ((ToolRackTileEntity)tileEntity).GetInventory());
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    /**
     * Returns the Slot Index from a hit location and a direction
     * | 0 | 1 |
     * | 2 | 3 |
     * @param x
     * @param y
     * @param z
     * @param direction
     * @return
     */
    private static int getSlotIndex(double x, double y, double z, Direction direction)
    {
        switch(direction)
        {
            case DOWN: // Origin bottom left
                if(x <= 0.5f)
                    return z >= 0.5f ? 0 : 2;
                return z >= 0.5f ? 1 : 3;
            case EAST: // Origin bottom right
                if(z >= 0.5f)
                    return y > 0.5f ? 0 : 2;
                return y > 0.5f ? 1 : 3;
            case NORTH: // Origin bottom right
                if(x >= 0.5f)
                    return y > 0.5f ? 0 : 2;
                return y > 0.5f ? 1 : 3;
            case SOUTH: // Origin bottom left
                if(x <= 0.5f)
                    return y > 0.5f ? 0 : 2;
                return y > 0.5f ? 1 : 3;
            case UP: // Origin point top left
                if(x < 0.5f)
                    return z < 0.5f ? 0 : 2;
                return z < 0.5f ? 1 : 3;
            case WEST: // Origin bottom left
                if(z <= 0.5f)
                    return y > 0.5f ? 0 : 2;
                return y > 0.5f ? 1 : 3;
            default:
                break;
        }
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getNearestLookingDirection().getOpposite());
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return new ToolRackTileEntity();
    }

    // @Override
    // public BlockState rotate(BlockState state, Rotation rotation) {
    //     return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    // }

    // @SuppressWarnings("deprecation")
    // @Override
    // public BlockState mirror(BlockState state, Mirror mirror) {
    //     return state.rotate(mirror.getRotation(state.getValue(FACING)));
    // }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        return SHAPES[state.getValue(FACING).get3DDataValue()];
    }
}

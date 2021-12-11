package xalcon.toolrack.data.client;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import xalcon.toolrack.ToolRackMod;
import xalcon.toolrack.common.blocks.ToolRackBlock;
import xalcon.toolrack.common.init.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider
{

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, ToolRackMod.MOD_ID, exFileHelper);
    }

    private static final int DEFAULT_ANGLE_OFFSET = 180;

    @Override
    protected void registerStatesAndModels()
    {
        final ModelFile model = models()
            .withExistingParent("block/toolrack", mcLoc("block"))
            .texture("particle", "#body")
            .texture("border", mcLoc("block/oak_planks"))
            .texture("body", mcLoc("block/oak_planks"))
            .element()
            .from(0, 0, 0)
            .to(16, 6, 16)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#body"))
            .end()
            .element()
            .from(0, 6, 0)
            .to(16, 8, 1)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#border"))
            .end()
            .element()
            .from(0, 6, 15)
            .to(16, 8, 16)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#border"))
            .end()
            .element()
            .from(0, 6, 0)
            .to(1, 8, 16)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#border"))
            .end()
            .element()
            .from(15, 6, 0)
            .to(16, 8, 16)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#border"))
            .end();
            
        getVariantBuilder(ModBlocks.blockToolRack)
            .forAllStates(state -> {
                Direction dir = state.getValue(ToolRackBlock.FACING);
                return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
                    .rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot()) + DEFAULT_ANGLE_OFFSET) % 360)
                    .build();
            });
    }
}

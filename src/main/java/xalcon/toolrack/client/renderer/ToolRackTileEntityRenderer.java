package xalcon.toolrack.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import xalcon.toolrack.common.blocks.ToolRackBlock;
import xalcon.toolrack.common.tileentities.ToolRackTileEntity;

public class ToolRackTileEntityRenderer extends TileEntityRenderer<ToolRackTileEntity> {

    public ToolRackTileEntityRenderer(TileEntityRendererDispatcher renderDispatcher) {
        super(renderDispatcher);
    }

    @Override
    public void render(ToolRackTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int combinedLightsIn, int combinedOverlayIn) {
        
        matrixStackIn.pushPose();
        float scale = 0.425f;
        World world = tileEntityIn.getLevel();

        BlockState state = tileEntityIn.getBlockState();
        Direction direction = state.getValue(ToolRackBlock.FACING);
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        Quaternion q = direction.getRotation();
        q.mul(Vector3f.XP.rotationDegrees(-90.0F));
        matrixStackIn.mulPose(q);

        matrixStackIn.translate(-.20f, .20f, -0.1f);
        matrixStackIn.pushPose();
        matrixStackIn.scale(scale, scale, scale);
        renderItemStack(tileEntityIn.getItemStackInSlot(0), world, matrixStackIn, bufferIn, combinedLightsIn, combinedOverlayIn);
        matrixStackIn.popPose();

        matrixStackIn.translate(.4f, 0f, 0f);
        matrixStackIn.pushPose();
        matrixStackIn.scale(scale, scale, scale);
        renderItemStack(tileEntityIn.getItemStackInSlot(1), world, matrixStackIn, bufferIn, combinedLightsIn, combinedOverlayIn);
        matrixStackIn.popPose();
        
        matrixStackIn.translate(-.4f, -.4f, 0f);
        matrixStackIn.pushPose();
        matrixStackIn.scale(scale, scale, scale);
        renderItemStack(tileEntityIn.getItemStackInSlot(2), world, matrixStackIn, bufferIn, combinedLightsIn, combinedOverlayIn);
        matrixStackIn.popPose();
        
        matrixStackIn.translate(.4f, 0f, 0f);
        matrixStackIn.pushPose();
        matrixStackIn.scale(scale, scale, scale);
        renderItemStack(tileEntityIn.getItemStackInSlot(3), world, matrixStackIn, bufferIn, combinedLightsIn, combinedOverlayIn);
        matrixStackIn.popPose();
        matrixStackIn.popPose();
    }

    private static void renderItemStack(ItemStack item, World world, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightsIn, int combinedOverlayIn)
    {
        if(item.isEmpty()) return;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        IBakedModel model = itemRenderer.getModel(item, world, null);
        itemRenderer.render(item, TransformType.NONE, false, matrixStackIn, bufferIn, combinedLightsIn, combinedOverlayIn, model);
    }
    
}

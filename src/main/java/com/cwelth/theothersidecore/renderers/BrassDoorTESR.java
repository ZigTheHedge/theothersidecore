package com.cwelth.theothersidecore.renderers;

import com.cwelth.theothersidecore.blocks.AllBlocks;
import com.cwelth.theothersidecore.blocks.BrassDoor;
import com.cwelth.theothersidecore.blocks.GearBox;
import com.cwelth.theothersidecore.tileentities.BrassDoorTE;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class BrassDoorTESR extends TileEntitySpecialRenderer<BrassDoorTE> {
    @Override
    public void render(BrassDoorTE te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderPart(te, partialTicks);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    public void renderPart(BrassDoorTE te, float partialTicks)
    {
        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();

        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        World world = te.getWorld();
        GlStateManager.translate(.5F, 0, .5F);
        if(world.getBlockState(te.getPos()).getValue(BrassDoor.FACING) == EnumFacing.NORTH)GlStateManager.rotate(0, 0, 1, 0);
        if(world.getBlockState(te.getPos()).getValue(BrassDoor.FACING) == EnumFacing.EAST)GlStateManager.rotate(270, 0, 1, 0);
        if(world.getBlockState(te.getPos()).getValue(BrassDoor.FACING) == EnumFacing.SOUTH)GlStateManager.rotate(180, 0, 1, 0);
        if(world.getBlockState(te.getPos()).getValue(BrassDoor.FACING) == EnumFacing.WEST)GlStateManager.rotate(90, 0, 1, 0);
        GlStateManager.translate(-.5F, 0, -.5F);

        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
        if(te.isUpper)
            GlStateManager.translate(0, te.cur_position / 16F, 0);
        else
            GlStateManager.translate(0, -te.cur_position / 16F, 0);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        IBlockState state;
        if(te.isUpper)
            state = AllBlocks.brassDoor.getDefaultState().withProperty(BrassDoor.TESRMODEL, BrassDoor.EnumModel.UPPER);
        else
            state = AllBlocks.brassDoor.getDefaultState().withProperty(BrassDoor.TESRMODEL, BrassDoor.EnumModel.LOWER);
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);

        dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), bufferBuilder, true);
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }


}

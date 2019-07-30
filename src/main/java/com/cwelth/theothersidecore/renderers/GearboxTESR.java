package com.cwelth.theothersidecore.renderers;

import com.cwelth.theothersidecore.blocks.AllBlocks;
import com.cwelth.theothersidecore.blocks.GearBox;
import com.cwelth.theothersidecore.tileentities.GearboxTE;
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

public class GearboxTESR extends TileEntitySpecialRenderer<GearboxTE> {

    @Override
    public void render(GearboxTE te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderDoor(te, partialTicks);
        if(te.has_main_gear)renderGear(te, .2F, .2F, 0, .9F, 1, partialTicks);
        if(te.has_second_gear){
            if(te.has_main_gear)
                renderGear(te, 0, -.065F, 0.05F, .8F, -1, partialTicks);
            else
                renderGear(te, 0, -.065F, 0.05F, .8F, 0, partialTicks);
        }

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    public void renderDoor(GearboxTE te, float partialTicks) {
        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();

        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }


        World world = te.getWorld();
        //GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
        GlStateManager.translate(.5F, 0, .5F);
        if(world.getBlockState(te.getPos()).getValue(GearBox.FACING) == EnumFacing.NORTH)GlStateManager.rotate(0, 0, 1, 0);
        if(world.getBlockState(te.getPos()).getValue(GearBox.FACING) == EnumFacing.EAST)GlStateManager.rotate(270, 0, 1, 0);
        if(world.getBlockState(te.getPos()).getValue(GearBox.FACING) == EnumFacing.SOUTH)GlStateManager.rotate(180, 0, 1, 0);
        if(world.getBlockState(te.getPos()).getValue(GearBox.FACING) == EnumFacing.WEST)GlStateManager.rotate(90, 0, 1, 0);
        GlStateManager.translate(-.5F, 0, -.5F);

        GlStateManager.translate(0.05F, 0, 0.05F);
        if(te.is_moving)
            GlStateManager.rotate(te.cur_angle + partialTicks * ((te.delta > 0) ? 1 : -1), 0, 1, 0);
        else
            GlStateManager.rotate(te.cur_angle, 0, 1, 0);
        GlStateManager.translate(-0.05F, 0, -0.05F);

        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        IBlockState state = AllBlocks.gearBox.getDefaultState().withProperty(GearBox.HAS_DOOR, true);
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);

        dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), bufferBuilder, true);
        tessellator.draw();
        GlStateManager.translate(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public void renderGear(GearboxTE te, float tX, float tY, float tZ, float scale, int rotFactor, float partialTicks) {
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
        if(world.getBlockState(te.getPos()).getValue(GearBox.FACING) == EnumFacing.NORTH)GlStateManager.rotate(0, 0, 1, 0);
        if(world.getBlockState(te.getPos()).getValue(GearBox.FACING) == EnumFacing.EAST)GlStateManager.rotate(270, 0, 1, 0);
        if(world.getBlockState(te.getPos()).getValue(GearBox.FACING) == EnumFacing.SOUTH)GlStateManager.rotate(180, 0, 1, 0);
        if(world.getBlockState(te.getPos()).getValue(GearBox.FACING) == EnumFacing.WEST)GlStateManager.rotate(90, 0, 1, 0);
        GlStateManager.translate(-.5F, 0, -.5F);

        GlStateManager.translate(tX, tY, tZ);
        GlStateManager.scale(scale, scale, scale);

        long angle = (System.currentTimeMillis() / 10) % 360;
        GlStateManager.translate(.5F, .5F, 0F);
        GlStateManager.rotate(angle * rotFactor, 0, 0, 1);
        GlStateManager.translate(-.5F, -.5F, 0F);

        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        IBlockState state = AllBlocks.gearBox.getDefaultState().withProperty(GearBox.HAS_GEAR, true);
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);

        dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), bufferBuilder, true);
        tessellator.draw();

        GlStateManager.translate(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());

        GlStateManager.translate(-tX, -tY, -tZ);

        RenderHelper.enableStandardItemLighting();

        GlStateManager.popMatrix();
    }

}

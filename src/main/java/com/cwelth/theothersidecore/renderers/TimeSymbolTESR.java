package com.cwelth.theothersidecore.renderers;

import com.cwelth.theothersidecore.blocks.AllBlocks;
import com.cwelth.theothersidecore.blocks.TimeSymbol;
import com.cwelth.theothersidecore.tileentities.TimeSymbolTE;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class TimeSymbolTESR extends TileEntitySpecialRenderer<TimeSymbolTE> {

    @Override
    public void render(TimeSymbolTE te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderSymbol(te, partialTicks);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    public void renderSymbol(TimeSymbolTE te, float partialTicks)
    {
        GlStateManager.pushMatrix();

        RenderHelper.disableStandardItemLighting();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


        GlStateManager.translate(.5F, 0, .5F);
        long angle = (System.currentTimeMillis() / 10) % 360;
        if (te.isMoving)
            GlStateManager.rotate(-angle, 0, 1, 0);

        World world = te.getWorld();
        GlStateManager.translate(-te.getPos().getX() - .5F, -te.getPos().getY(), -te.getPos().getZ() - .5F);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        IBlockState state = AllBlocks.timeSymbol.getDefaultState().withProperty(TimeSymbol.MODEL_SYMBOL, true);
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);


        dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), bufferBuilder, true);
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

}

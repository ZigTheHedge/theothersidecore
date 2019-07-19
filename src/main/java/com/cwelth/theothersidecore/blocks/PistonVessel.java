package com.cwelth.theothersidecore.blocks;

import com.cwelth.theothersidecore.ModMain;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PistonVessel extends SimpleBlock {
    public PistonVessel() {
        super(Material.GLASS, "pistonvessel");
        setHarvestLevel("pickaxe", 2);
        setCreativeTab(ModMain.toscCreativeTab);
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (blockState != blockAccess.getBlockState(pos.offset(side)))
            return true;
        else
            return false;
    }
}

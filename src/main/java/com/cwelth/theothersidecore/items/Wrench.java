package com.cwelth.theothersidecore.items;

import com.cwelth.theothersidecore.ModMain;
import com.cwelth.theothersidecore.blocks.AllBlocks;
import com.cwelth.theothersidecore.blocks.BrassPipeBent;
import com.cwelth.theothersidecore.blocks.BrassPipeStraight;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.cwelth.theothersidecore.blocks.BrassPipeBent.VERTICAL;
import static com.cwelth.theothersidecore.blocks.CommonBlock.FACING;
import static com.cwelth.theothersidecore.blocks.BrassPipeStraight.DIRECTION;

public class Wrench extends Item {
    public Wrench()
    {
        setRegistryName("wrench");
        setUnlocalizedName(ModMain.MODID + ".wrench");
        setCreativeTab(ModMain.toscCreativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote)return EnumActionResult.PASS;
        IBlockState state = worldIn.getBlockState(pos);
        if(state != null && state.getBlock() == AllBlocks.brassPipeBent) {
            if (playerIn.isSneaking()) {
                BrassPipeBent.PropertyVerticalRotation propVRotation = state.getValue(VERTICAL).next();
                worldIn.setBlockState(pos, state.withProperty(VERTICAL, propVRotation), 2);
            } else {
                EnumFacing propFacing = state.getValue(FACING).rotateAround(EnumFacing.Axis.Y);
                worldIn.setBlockState(pos, state.withProperty(FACING, propFacing), 2);
            }
        }
        if(state != null && state.getBlock() == AllBlocks.brassPipeStraight) {
            worldIn.setBlockState(pos, state.withProperty(DIRECTION, state.getValue(DIRECTION).next()), 2);
        }
        return EnumActionResult.PASS;
    }
}

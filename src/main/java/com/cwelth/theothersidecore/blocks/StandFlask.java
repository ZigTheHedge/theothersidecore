package com.cwelth.theothersidecore.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class StandFlask extends CommonBlock {
    public static PropertyBool WITHFLASK = PropertyBool.create("withflask");

    public StandFlask() {
        super(Material.IRON, "stand_flask");
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(WITHFLASK, false));
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState p_isFullCube_1_) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = super.getStateFromMeta(meta).withProperty(WITHFLASK, ((meta & 8) >> 3) == 1);
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = super.getMetaFromState(state) + (state.getValue(WITHFLASK)? 8: 0);
        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, WITHFLASK);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote)return true;
        if(!playerIn.getHeldItem(hand).isEmpty()){
            if(playerIn.getHeldItem(hand).getItem() == ItemBlock.getItemFromBlock(AllBlocks.flask)) {
                if(state.getValue(WITHFLASK)){
                    playerIn.getHeldItem(hand).grow(1);
                    worldIn.setBlockState(pos, state.withProperty(WITHFLASK, false), 2);
                    worldIn.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else {
                    worldIn.setBlockState(pos, state.withProperty(WITHFLASK, true), 2);
                    playerIn.getHeldItem(hand).shrink(1);
                    worldIn.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        } else
        {
            if(state.getValue(WITHFLASK)) {
                playerIn.setHeldItem(hand, new ItemStack(AllBlocks.flask));
                worldIn.setBlockState(pos, state.withProperty(WITHFLASK, false), 2);
                worldIn.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            } else
                return true;
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if(!worldIn.isRemote)
        {
            if(state.getValue(WITHFLASK))
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(AllBlocks.flask));
        }
    }
}

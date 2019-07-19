package com.cwelth.theothersidecore.blocks;

import com.cwelth.theothersidecore.ModMain;
import com.cwelth.theothersidecore.items.AllItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BrassPipeBent extends CommonBlock {

    public enum PropertyVerticalRotation implements IStringSerializable {
        DOWN,
        SIDE,
        UP;

        @Override
        public String getName() {
            if(this == DOWN) return "down";
            if(this == SIDE) return "side";
            if(this == UP) return "up";
            return null;
        }

        public PropertyVerticalRotation next(){
            if(this == DOWN) return SIDE;
            if(this == SIDE) return UP;
            if(this == UP) return DOWN;
            return null;
        }
    }


    public static final PropertyEnum<PropertyVerticalRotation> VERTICAL = PropertyEnum.create("vertical", PropertyVerticalRotation.class);

    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.2F, 0F, 0.2F, 0.8F, 0.8F, 1F);
    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.2F, 0F, 0F, 0.8F, 0.8F, 0.8F);
    private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.2F, 0F, 0.2F, 1F, 0.8F, 0.8F);
    private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0F, 0F, 0.2F, 0.8F, 0.8F, 0.8F);

    private static final AxisAlignedBB AABB_NORTH_UP = new AxisAlignedBB(0.2F, 0.2F, 0.2F, 0.8F, 1F, 1F);
    private static final AxisAlignedBB AABB_SOUTH_UP = new AxisAlignedBB(0.2F, 0.2F, 0F, 0.8F, 1F, 0.8F);
    private static final AxisAlignedBB AABB_WEST_UP = new AxisAlignedBB(0.2F, 0.2F, 0.2F, 1F, 1F, 0.8F);
    private static final AxisAlignedBB AABB_EAST_UP = new AxisAlignedBB(0F, 0.2F, 0.2F, 0.8F, 1F, 0.8F);

    private static final AxisAlignedBB AABB_WEST_SIDE = new AxisAlignedBB(0.2F, 0.2F, 0.2F, 1F, 0.8F, 1F);
    private static final AxisAlignedBB AABB_EAST_SIDE = new AxisAlignedBB(0F, 0.2F, 0F, 0.8F, 0.8F, 0.8F);
    private static final AxisAlignedBB AABB_SOUTH_SIDE = new AxisAlignedBB(0.2F, 0.2F, 0F, 1F, 0.8F, 0.8F);
    private static final AxisAlignedBB AABB_NORTH_SIDE = new AxisAlignedBB(0F, 0.2F, 0.2F, 0.8F, 0.8F, 1F);

    public BrassPipeBent() {
        super(Material.IRON, "brasspipebent");
        setCreativeTab(ModMain.toscCreativeTab);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(VERTICAL, PropertyVerticalRotation.DOWN));
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
    public void addCollisionBoxToList(IBlockState iBlockState, World world, BlockPos blockPos, AxisAlignedBB aaBB, List<AxisAlignedBB> aaBBList, @Nullable Entity entity, boolean wtf) {
        if(iBlockState.getValue(VERTICAL) == PropertyVerticalRotation.DOWN) {
            if (iBlockState.getValue(FACING) == EnumFacing.NORTH)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_NORTH);
            if (iBlockState.getValue(FACING) == EnumFacing.SOUTH)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_SOUTH);
            if (iBlockState.getValue(FACING) == EnumFacing.WEST)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_WEST);
            if (iBlockState.getValue(FACING) == EnumFacing.EAST)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_EAST);
        } else if(iBlockState.getValue(VERTICAL) == PropertyVerticalRotation.SIDE)
        {
            if (iBlockState.getValue(FACING) == EnumFacing.NORTH)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_NORTH_SIDE);
            if (iBlockState.getValue(FACING) == EnumFacing.SOUTH)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_SOUTH_SIDE);
            if (iBlockState.getValue(FACING) == EnumFacing.WEST)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_WEST_SIDE);
            if (iBlockState.getValue(FACING) == EnumFacing.EAST)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_EAST_SIDE);
        } else
        {
            if (iBlockState.getValue(FACING) == EnumFacing.NORTH)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_NORTH_UP);
            if (iBlockState.getValue(FACING) == EnumFacing.SOUTH)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_SOUTH_UP);
            if (iBlockState.getValue(FACING) == EnumFacing.WEST)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_WEST_UP);
            if (iBlockState.getValue(FACING) == EnumFacing.EAST)
                addCollisionBoxToList(blockPos, aaBB, aaBBList, AABB_EAST_UP);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
        if(blockState.getValue(VERTICAL) == PropertyVerticalRotation.DOWN) {
            if (blockState.getValue(FACING) == EnumFacing.NORTH) return AABB_NORTH;
            if (blockState.getValue(FACING) == EnumFacing.SOUTH) return AABB_SOUTH;
            if (blockState.getValue(FACING) == EnumFacing.EAST) return AABB_EAST;
            if (blockState.getValue(FACING) == EnumFacing.WEST) return AABB_WEST;
        } else if(blockState.getValue(VERTICAL) == PropertyVerticalRotation.SIDE)
        {
            if (blockState.getValue(FACING) == EnumFacing.NORTH) return AABB_NORTH_SIDE;
            if (blockState.getValue(FACING) == EnumFacing.SOUTH) return AABB_SOUTH_SIDE;
            if (blockState.getValue(FACING) == EnumFacing.EAST) return AABB_EAST_SIDE;
            if (blockState.getValue(FACING) == EnumFacing.WEST) return AABB_WEST_SIDE;
        } else
        {
            if (blockState.getValue(FACING) == EnumFacing.NORTH) return AABB_NORTH_UP;
            if (blockState.getValue(FACING) == EnumFacing.SOUTH) return AABB_SOUTH_UP;
            if (blockState.getValue(FACING) == EnumFacing.EAST) return AABB_EAST_UP;
            if (blockState.getValue(FACING) == EnumFacing.WEST) return AABB_WEST_UP;
        }
        return FULL_BLOCK_AABB;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing face = getFacingFromEntity(pos, placer);
        if(face == EnumFacing.DOWN)
            world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(VERTICAL, PropertyVerticalRotation.UP), 2);
        else
            world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(VERTICAL, PropertyVerticalRotation.DOWN), 2);

    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront((meta & 3) + 2)).withProperty(VERTICAL, PropertyVerticalRotation.values()[(meta & 12) >> 2]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facing = state.getValue(FACING).getIndex();
        int vrotation = state.getValue(VERTICAL).ordinal();
        if(facing < 2)return 0;
        return facing - 2 | vrotation << 2;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, VERTICAL);
    }

}

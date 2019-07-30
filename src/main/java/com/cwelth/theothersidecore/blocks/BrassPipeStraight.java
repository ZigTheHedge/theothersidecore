package com.cwelth.theothersidecore.blocks;

import com.cwelth.theothersidecore.ModMain;
import jdk.nashorn.internal.objects.annotations.Property;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static com.cwelth.theothersidecore.blocks.CommonBlock.getFacingFromEntity;

public class BrassPipeStraight extends SimpleBlock {

    public enum EnumDirection implements IStringSerializable {
        UPDOWN,
        EASTWEST,
        NORTHSOUTH;

        @Override
        public String getName() {
            if(this == UPDOWN) return "updown";
            if(this == EASTWEST) return "eastwest";
            if(this == NORTHSOUTH) return "northsouth";
            return null;
        }

        public EnumDirection next(){
            if(this == UPDOWN)return EASTWEST;
            if(this == EASTWEST)return NORTHSOUTH;
            if(this == NORTHSOUTH)return UPDOWN;
            return null;
        }
    }

    public static final AxisAlignedBB AABB_UPDOWN = new AxisAlignedBB(0.2F, 0F, 0.2F, 0.8F, 1F, 0.8F);
    public static final AxisAlignedBB AABB_EASTWEST = new AxisAlignedBB(0F, 0.2F, 0.2F, 1F, 0.8F, 0.8F);
    public static final AxisAlignedBB AABB_NORTHSOUTH = new AxisAlignedBB(0.2F, 0.2F, 0F, 0.8F, 0.8F, 1F);


    public static final PropertyEnum<EnumDirection> DIRECTION = PropertyEnum.create("direction", EnumDirection.class);

    public BrassPipeStraight() {
        super(Material.IRON, "brasspipestraight");
        setCreativeTab(ModMain.toscCreativeTab);
        setBlockUnbreakable();
        setDefaultState(blockState.getBaseState().withProperty(DIRECTION, EnumDirection.NORTHSOUTH));
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
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing face = getFacingFromEntity(pos, placer);
        if(face == EnumFacing.DOWN || face == EnumFacing.UP)
            world.setBlockState(pos, state.withProperty(DIRECTION, EnumDirection.UPDOWN), 2);
        else if (face == EnumFacing.NORTH || face == EnumFacing.SOUTH)
            world.setBlockState(pos, state.withProperty(DIRECTION, EnumDirection.NORTHSOUTH), 2);
        else
            world.setBlockState(pos, state.withProperty(DIRECTION, EnumDirection.EASTWEST), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(DIRECTION, EnumDirection.values()[meta & 3]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(DIRECTION).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DIRECTION);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if(state.getValue(DIRECTION) == EnumDirection.UPDOWN) return AABB_UPDOWN;
        if(state.getValue(DIRECTION) == EnumDirection.EASTWEST) return AABB_EASTWEST;
        if(state.getValue(DIRECTION) == EnumDirection.NORTHSOUTH) return AABB_NORTHSOUTH;
        return super.getBoundingBox(state, source, pos);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if(state.getValue(DIRECTION) == EnumDirection.UPDOWN) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_UPDOWN);
        if(state.getValue(DIRECTION) == EnumDirection.EASTWEST) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EASTWEST);
        if(state.getValue(DIRECTION) == EnumDirection.NORTHSOUTH) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTHSOUTH);
    }
}

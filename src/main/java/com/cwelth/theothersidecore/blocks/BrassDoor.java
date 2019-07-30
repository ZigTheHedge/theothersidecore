package com.cwelth.theothersidecore.blocks;

import com.cwelth.theothersidecore.ModMain;
import com.cwelth.theothersidecore.items.AllItems;
import com.cwelth.theothersidecore.renderers.BrassDoorTESR;
import com.cwelth.theothersidecore.tileentities.BrassDoorTE;
import com.cwelth.theothersidecore.tileentities.CommonTEBlock;
import com.cwelth.theothersidecore.tileentities.TimeSymbolTE;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BrassDoor extends CommonTEBlock<BrassDoorTE> {

    public enum EnumModel implements IStringSerializable {
        NONE,
        UPPER,
        LOWER;

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public String getName() {
            if(this == NONE)return "none";
            if(this == UPPER)return "upper";
            if(this == LOWER)return "lower";
            return null;
        }
    }

    public static final PropertyEnum<EnumModel> TESRMODEL = PropertyEnum.create("tesrmodel", EnumModel.class);
    public static final PropertyBool IS_UPPER = PropertyBool.create("is_upper");

    public static final AxisAlignedBB AABB_NORTHSOUTH = new AxisAlignedBB(0F, 0F, 0.45F, 1F, 1F, 0.55F);
    public static final AxisAlignedBB AABB_EASTWEST = new AxisAlignedBB(0.45F, 0F, 0F, 0.55F, 1F, 1F);

    public BrassDoor() {
        super(Material.IRON, "brass_door");
        setBlockUnbreakable();
        setCreativeTab(ModMain.toscCreativeTab);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(IS_UPPER, false).withProperty(TESRMODEL, EnumModel.NONE));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(BrassDoorTE.class, new BrassDoorTESR());
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
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, IS_UPPER, TESRMODEL);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR;
    }

    @Override
    public Class<BrassDoorTE> getTileEntityClass() {
        return BrassDoorTE.class;
    }

    @Nullable
    @Override
    public BrassDoorTE createTileEntity(World worldIn, IBlockState meta) {
        return new BrassDoorTE(meta.getValue(IS_UPPER));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
        world.setBlockState(pos.up(), state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(IS_UPPER, true), 2);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        worldIn.setBlockToAir(pos);
        dropBlockAsItem(worldIn, pos, state, 0);
        if(state.getValue(IS_UPPER))
            worldIn.setBlockToAir(pos.down());
        else
            worldIn.setBlockToAir(pos.up());
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7)).withProperty(IS_UPPER, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).ordinal() | (state.getValue(IS_UPPER) ? 8 : 0);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        TileEntity te = source.getTileEntity(pos);
        if(te instanceof BrassDoorTE) {
            int curPosition = ((BrassDoorTE) te).cur_position;
            AxisAlignedBB jitAABB;
            if (state.getValue(FACING) == EnumFacing.NORTH || state.getValue(FACING) == EnumFacing.SOUTH) {
                if(state.getValue(IS_UPPER))
                    jitAABB = new AxisAlignedBB(0F, 0F + (curPosition - 1) / 16F, 0.45F, 1F, 1F, 0.55F);
                else
                    jitAABB = new AxisAlignedBB(0F, 0F, 0.45F, 1F,  (17 - curPosition) / 16F, 0.55F);

                return jitAABB;
            } else {
                if(state.getValue(IS_UPPER))
                    jitAABB = new AxisAlignedBB(0.45F, 0F + (curPosition - 1) / 16F, 0F, 0.55F, 1F, 1F);
                else
                    jitAABB = new AxisAlignedBB(0.45F, 0F, 0F, 0.55F,  (17 - curPosition) / 16F, 1F);

                return jitAABB;
            }
        }
        return NULL_AABB;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof BrassDoorTE)
        {
            if(((BrassDoorTE) te).cur_position < 14 ) {
                if (state.getValue(FACING) == EnumFacing.NORTH || state.getValue(FACING) == EnumFacing.SOUTH)
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTHSOUTH);
                else addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EASTWEST);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if (playerIn.getHeldItem(hand).getItem() == AllItems.timeKey) {
                BlockPos possibleTE;
                if (state.getValue(IS_UPPER)) {
                    BrassDoorTE upperTE = (BrassDoorTE) worldIn.getTileEntity(pos);
                    upperTE.activate();
                    BrassDoorTE lowerTE = (BrassDoorTE) worldIn.getTileEntity(pos.down());
                    lowerTE.activate();
                    possibleTE = pos.down(2).south(3);
                } else {
                    BrassDoorTE upperTE = (BrassDoorTE) worldIn.getTileEntity(pos.up());
                    upperTE.activate();
                    BrassDoorTE lowerTE = (BrassDoorTE) worldIn.getTileEntity(pos);
                    lowerTE.activate();
                    possibleTE = pos.down().south(3);
                }

                TileEntity mainTE = worldIn.getTileEntity(possibleTE);
                if (mainTE instanceof TimeSymbolTE) {
                    ((TimeSymbolTE) mainTE).isStructureComplete();
                } else
                    playerIn.sendMessage(new TextComponentString("No TimeSymbol is found on coordinates: "+possibleTE.getX()+", "+possibleTE.getY()+", "+possibleTE.getZ()));

            } else {
                playerIn.sendMessage(new TextComponentString(I18n.format("brassdoor.keyneeded")));
            }
            return true;
        } else
            return true;
    }
}

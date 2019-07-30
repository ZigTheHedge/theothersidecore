package com.cwelth.theothersidecore.blocks;

import com.cwelth.theothersidecore.ModMain;
import com.cwelth.theothersidecore.tileentities.CommonTEBlock;
import com.cwelth.theothersidecore.tileentities.PistonVesselTE;
import com.cwelth.theothersidecore.tileentities.TimeSymbolTE;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class PistonVessel extends CommonTEBlock<PistonVesselTE> {

    public static PropertyBool IS_BROKEN = PropertyBool.create("is_broken");
    public static PropertyBool HAS_LIQUID = PropertyBool.create("has_liquid");

    public static final AxisAlignedBB AABB = new AxisAlignedBB(0.15F, 0, 0.15F, 0.85F, 1F, 0.85F);

    public PistonVessel() {
        super(Material.GLASS, "pistonvessel");
        setHarvestLevel("pickaxe", 2);
        setCreativeTab(ModMain.toscCreativeTab);
        setBlockUnbreakable();
        setDefaultState(blockState.getBaseState().withProperty(IS_BROKEN, false).withProperty(HAS_LIQUID, false));
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

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(IS_BROKEN) ? 1 : 0) | (state.getValue(HAS_LIQUID) ? 1 : 0) << 1;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(IS_BROKEN, ((meta & 1) == 1)? true: false).withProperty(HAS_LIQUID, (((meta & 2) >> 1) == 1)? true : false);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, IS_BROKEN, HAS_LIQUID);
    }

    @Override
    public Class<PistonVesselTE> getTileEntityClass() {
        return PistonVesselTE.class;
    }

    @Nullable
    @Override
    public PistonVesselTE createTileEntity(World worldIn, IBlockState meta) {
        return new PistonVesselTE();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
        {
            PistonVesselTE thisTE = (PistonVesselTE)worldIn.getTileEntity(pos);
            BlockPos thisTEmtp = thisTE.getMainBlockPos();
            //Break pipe!
            if(playerIn.getHeldItem(hand).getItem() != Items.WATER_BUCKET && playerIn.getHeldItem(hand).getItem() != ItemBlock.getItemFromBlock(AllBlocks.pistonVessel)) {
                if (state.getValue(IS_BROKEN)) return true;
                if (state.getValue(HAS_LIQUID)) {
                    state = state.withProperty(HAS_LIQUID, false);
                }
                state = state.withProperty(IS_BROKEN, true);
                worldIn.setBlockState(pos, state, 2);
                thisTE = (PistonVesselTE)worldIn.getTileEntity(pos);
                thisTE.setMainBlockPos(thisTEmtp);
                worldIn.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);

            } else if (playerIn.getHeldItem(hand).getItem() == Items.WATER_BUCKET)
            {
                if(state.getValue(IS_BROKEN)) return true;
                if(state.getValue(HAS_LIQUID)) return true;
                worldIn.setBlockState(pos, state.withProperty(HAS_LIQUID, true), 2);
                thisTE = (PistonVesselTE)worldIn.getTileEntity(pos);
                thisTE.setMainBlockPos(thisTEmtp);
                playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            } else if (playerIn.getHeldItem(hand).getItem() == ItemBlock.getItemFromBlock(AllBlocks.pistonVessel))
            {
                if(!state.getValue(IS_BROKEN)) return true;
                if(state.getValue(HAS_LIQUID)) return true;
                worldIn.setBlockState(pos, state.withProperty(IS_BROKEN, false), 2);
                thisTE = (PistonVesselTE)worldIn.getTileEntity(pos);
                thisTE.setMainBlockPos(thisTEmtp);
                playerIn.getHeldItem(hand).shrink(1);
                worldIn.playSound(null, pos, SoundEvents.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            TileEntity te = worldIn.getTileEntity(pos);
            if(te instanceof PistonVesselTE) {
                TimeSymbolTE tsTE = (TimeSymbolTE) worldIn.getTileEntity(((PistonVesselTE) te).getMainBlockPos());
                if (tsTE.isStructureComplete())
                {
                    tsTE.activate();
                } else {
                    if(tsTE.isMoving)
                        tsTE.deactivate();
                }
            }


        }
        return true;
    }

    @Nullable
    @Override
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB);
    }
}

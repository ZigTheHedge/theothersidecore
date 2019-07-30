package com.cwelth.theothersidecore.blocks;

import com.cwelth.theothersidecore.items.AllItems;
import com.cwelth.theothersidecore.renderers.GearboxTESR;
import com.cwelth.theothersidecore.tileentities.CommonTEBlock;
import com.cwelth.theothersidecore.tileentities.GearboxTE;
import com.cwelth.theothersidecore.tileentities.PistonVesselTE;
import com.cwelth.theothersidecore.tileentities.TimeSymbolTE;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GearBox extends CommonTEBlock<GearboxTE> {

    public static PropertyBool HAS_DOOR = PropertyBool.create("has_door");
    public static PropertyBool HAS_GEAR = PropertyBool.create("has_gear");

    public GearBox() {
        super(Material.IRON, "gearbox");
        setBlockUnbreakable();
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HAS_DOOR, false).withProperty(HAS_GEAR, false));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(GearboxTE.class, new GearboxTESR());
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
    public Class<GearboxTE> getTileEntityClass() {
        return GearboxTE.class;
    }

    @Nullable
    @Override
    public GearboxTE createTileEntity(World worldIn, IBlockState meta) {
        return new GearboxTE();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, HAS_DOOR, HAS_GEAR);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
        {
            TileEntity te = worldIn.getTileEntity(pos);
            if(te instanceof GearboxTE)
            {
                if(playerIn.getHeldItem(hand).getItem() == AllItems.gear)
                {
                    GearboxTE gte = (GearboxTE)te;
                    if(gte.isOpened())
                    {
                        if(!gte.has_main_gear){
                            gte.setMainGearPresence(true);
                            playerIn.getHeldItem(hand).shrink(1);
                            checkStructure(worldIn, pos);
                            return true;
                        }
                        if(!gte.has_second_gear){
                            gte.setSecondGearPresence(true);
                            playerIn.getHeldItem(hand).shrink(1);
                            checkStructure(worldIn, pos);
                            return true;
                        }
                    } else {
                        gte.activate();
                        checkStructure(worldIn, pos);
                    }
                } else {
                    ((GearboxTE) te).activate();
                    checkStructure(worldIn, pos);
                }
            }

        }
        return true;
    }

    private void checkStructure(World worldIn, BlockPos pos)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof GearboxTE) {
            TimeSymbolTE tsTE = (TimeSymbolTE) worldIn.getTileEntity(((GearboxTE) te).getMainBlockPos());
            if (tsTE.isStructureComplete())
            {
                tsTE.activate();
            } else {
                if(tsTE.isMoving)tsTE.deactivate();
            }
        }
    }
}

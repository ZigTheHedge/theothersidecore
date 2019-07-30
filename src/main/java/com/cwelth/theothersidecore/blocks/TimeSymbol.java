package com.cwelth.theothersidecore.blocks;

import com.cwelth.theothersidecore.renderers.TimeSymbolTESR;
import com.cwelth.theothersidecore.tileentities.CommonTEBlock;
import com.cwelth.theothersidecore.tileentities.TimeSymbolTE;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TimeSymbol extends CommonTEBlock<TimeSymbolTE> {
    public static final PropertyBool MODEL_SYMBOL = PropertyBool.create("model_symbol");

    public TimeSymbol() {
        super(Material.ROCK, "timesymbol");
        setBlockUnbreakable();
        setLightLevel(1F);
        setDefaultState(blockState.getBaseState().withProperty(MODEL_SYMBOL, false));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(MODEL_SYMBOL, false);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    }

    @Override
    public Class<TimeSymbolTE> getTileEntityClass() {
        return TimeSymbolTE.class;
    }

    @Nullable
    @Override
    public TimeSymbolTE createTileEntity(World worldIn, IBlockState meta) {
        return new TimeSymbolTE();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(TimeSymbolTE.class, new TimeSymbolTESR());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, MODEL_SYMBOL);
    }
}

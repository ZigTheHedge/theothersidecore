package com.cwelth.theothersidecore.items;

import com.cwelth.theothersidecore.ModMain;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Gear extends Item {
    public Gear()
    {
        setRegistryName("gear");
        setUnlocalizedName(ModMain.MODID + ".gear");
        setCreativeTab(ModMain.toscCreativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}

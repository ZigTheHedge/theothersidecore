package com.cwelth.theothersidecore.items;

import com.cwelth.theothersidecore.ModMain;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TimeIngot extends Item {
    public TimeIngot(){
        setRegistryName("timeingot");
        setUnlocalizedName(ModMain.MODID + ".timeingot");
        setCreativeTab(ModMain.toscCreativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}

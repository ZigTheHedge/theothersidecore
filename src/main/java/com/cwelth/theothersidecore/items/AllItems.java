package com.cwelth.theothersidecore.items;

import com.cwelth.theothersidecore.ModMain;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class AllItems {

    @GameRegistry.ObjectHolder(ModMain.MODID + ":wrench")
    public static Wrench wrench;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        wrench.initModel();
    }

    public static void registerOreDictionary()
    {
    }

}

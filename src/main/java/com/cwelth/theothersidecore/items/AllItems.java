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

    @GameRegistry.ObjectHolder(ModMain.MODID + ":timekey")
    public static TimeKey timeKey;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":gear")
    public static Gear gear;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":timeingot")
    public static TimeIngot timeIngot;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        wrench.initModel();
        timeKey.initModel();
        gear.initModel();
        timeIngot.initModel();
    }

    public static void registerOreDictionary()
    {
        OreDictionary.registerOre("ingotTime", AllItems.timeIngot);
    }

}

package com.cwelth.theothersidecore.gui;

import com.cwelth.theothersidecore.ModMain;
import com.cwelth.theothersidecore.blocks.AllBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TOSCCreativeTab extends CreativeTabs {

    public TOSCCreativeTab() {
        super(ModMain.MODID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(AllBlocks.timeBlock);
    }
}

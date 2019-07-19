package com.cwelth.theothersidecore.blocks;

import com.cwelth.theothersidecore.ModMain;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class TimeBlock extends SimpleBlock {
    public TimeBlock() {
        super(Material.PORTAL, "timeblock");
        setBlockUnbreakable();
        setCreativeTab(ModMain.toscCreativeTab);
    }
}

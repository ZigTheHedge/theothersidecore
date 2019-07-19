package com.cwelth.theothersidecore.blocks;

import com.cwelth.theothersidecore.ModMain;
import net.minecraft.block.material.Material;

public class BrassBrick extends SimpleBlock {
    public BrassBrick() {
        super(Material.ROCK, "brassbrick");
        setBlockUnbreakable();
        setCreativeTab(ModMain.toscCreativeTab);
    }
}

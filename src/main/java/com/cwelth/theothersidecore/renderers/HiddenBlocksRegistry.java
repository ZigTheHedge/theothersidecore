package com.cwelth.theothersidecore.renderers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class HiddenBlocksRegistry {
    public static final HiddenBlocksRegistry hiddenBlocksRegistry = new HiddenBlocksRegistry();
    public static List<HiddenBlock>registry = new ArrayList<>();

    public HiddenBlocksRegistry()
    {

    }

    public void add(BlockPos blockPos, IBlockState blockState)
    {
        registry.add(new HiddenBlock(blockPos, blockState));
    }

    public void remove(BlockPos blockPos)
    {
        for(int i = 0; i < registry.size(); i++)
        {
            if(registry.get(i).blockPos == blockPos) {
                registry.remove(i);
                break;
            }
        }
    }

    public class HiddenBlock{
        public BlockPos blockPos;
        public IBlockState iBlockState;

        public HiddenBlock(BlockPos blockPos, IBlockState blockState)
        {
            this.blockPos = blockPos;
            this.iBlockState = blockState;
        }
    }

}

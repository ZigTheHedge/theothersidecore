package com.cwelth.theothersidecore.worldgen;

import com.cwelth.theothersidecore.blocks.AllBlocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nullable;
import static com.cwelth.theothersidecore.blocks.PistonVessel.HAS_LIQUID;
import static com.cwelth.theothersidecore.blocks.PistonVessel.IS_BROKEN;

public class CapsuleGenProcessor implements ITemplateProcessor {
    @Nullable
    @Override
    public Template.BlockInfo processBlock(World worldIn, BlockPos pos, Template.BlockInfo blockInfoIn) {

        if(blockInfoIn.blockState.getBlock() == AllBlocks.pistonVessel)
        {
            if(worldIn.rand.nextInt(10) == 0)
                return new Template.BlockInfo(pos, blockInfoIn.blockState.withProperty(HAS_LIQUID, true), blockInfoIn.tileentityData);
            if(worldIn.rand.nextInt(2) == 0)
                return new Template.BlockInfo(pos, blockInfoIn.blockState.withProperty(IS_BROKEN, true), blockInfoIn.tileentityData);
        }

        if(blockInfoIn.blockState.getBlock() == AllBlocks.gearBox)
        {
            NBTTagCompound newCompound = blockInfoIn.tileentityData;
            if(worldIn.rand.nextInt(4) == 0)
                newCompound.setBoolean("has_main_gear", false);
            if(worldIn.rand.nextInt(4) == 0)
                newCompound.setBoolean("has_second_gear", false);
            if(worldIn.rand.nextInt(10) == 0)
                newCompound.setInteger("cur_angle", 90);
            return new Template.BlockInfo(pos, blockInfoIn.blockState, newCompound);
        }

        return blockInfoIn;
    }
}

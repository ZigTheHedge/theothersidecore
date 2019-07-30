package com.cwelth.theothersidecore.tileentities;

import com.cwelth.theothersidecore.ModMain;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class PistonVesselTE extends CommonTE {
    public int mtpX = 0;
    public int mtpY = 0;
    public int mtpZ = 0;

    public PistonVesselTE() {
        super(0);
    }

    public void setMainBlockPos(BlockPos mainBlockPos) {
        mtpX = mainBlockPos.getX();
        mtpY = mainBlockPos.getY();
        mtpZ = mainBlockPos.getZ();
        markDirty();
    }

    public BlockPos getMainBlockPos()
    {
        return new BlockPos(mtpX, mtpY, mtpZ);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("mtpX")) mtpX = compound.getInteger("mtpX");
        if(compound.hasKey("mtpY")) mtpY = compound.getInteger("mtpY");
        if(compound.hasKey("mtpZ")) mtpZ = compound.getInteger("mtpZ");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setInteger("mtpX", mtpX);
        compound.setInteger("mtpY", mtpY);
        compound.setInteger("mtpZ", mtpZ);
        return compound;
    }
}

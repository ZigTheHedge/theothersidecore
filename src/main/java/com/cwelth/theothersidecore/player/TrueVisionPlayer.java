package com.cwelth.theothersidecore.player;

import com.cwelth.theothersidecore.blocks.TimeBlock;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class TrueVisionPlayer implements ITrueVisionPlayer {
    boolean trueVisionActive = false;

    @Override
    public NBTTagCompound writeToNBT()
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        nbtTag.setBoolean("trueVisionActive", trueVisionActive);
        return nbtTag;

    }

    @Override
    public void readFromNBT(NBTBase nbtBase)
    {
        NBTTagCompound nbtTag = (NBTTagCompound)nbtBase;
        if(nbtTag.hasKey("trueVisionActive")) trueVisionActive = nbtTag.getBoolean("trueVisionActive");
    }

    @Override
    public void copyPlayer(ITrueVisionPlayer source)
    {
        this.trueVisionActive = source.isTrueVisionActive();
    }

    @Override
    public boolean isTrueVisionActive()
    {
        return trueVisionActive;
    }

    @Override
    public void setTrueVisionActive(boolean trueVisionActive)
    {
        this.trueVisionActive = trueVisionActive;
    }
}

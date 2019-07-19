package com.cwelth.theothersidecore.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public interface ITrueVisionPlayer {
    NBTTagCompound writeToNBT();
    void readFromNBT(NBTBase nbtBase);
    void copyPlayer(ITrueVisionPlayer source);
    boolean isTrueVisionActive();
    void setTrueVisionActive(boolean trueVisionActive);
}

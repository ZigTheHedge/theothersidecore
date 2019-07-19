package com.cwelth.theothersidecore.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TrueVisionStorage implements Capability.IStorage<ITrueVisionPlayer> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ITrueVisionPlayer> capability, ITrueVisionPlayer instance, EnumFacing side) {
        return instance.writeToNBT();
    }

    @Override
    public void readNBT(Capability<ITrueVisionPlayer> capability, ITrueVisionPlayer instance, EnumFacing side, NBTBase nbt) {
        instance.readFromNBT(nbt);
    }
}

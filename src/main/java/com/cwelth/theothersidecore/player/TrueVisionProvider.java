package com.cwelth.theothersidecore.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TrueVisionProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(ITrueVisionPlayer.class)
    public static final Capability<ITrueVisionPlayer> TRUE_VISION_PLAYER_CAPABILITY = null;
    private ITrueVisionPlayer instance = TRUE_VISION_PLAYER_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == TRUE_VISION_PLAYER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == TRUE_VISION_PLAYER_CAPABILITY ? TRUE_VISION_PLAYER_CAPABILITY.cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return TRUE_VISION_PLAYER_CAPABILITY.getStorage().writeNBT(TRUE_VISION_PLAYER_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        TRUE_VISION_PLAYER_CAPABILITY.getStorage().readNBT(TRUE_VISION_PLAYER_CAPABILITY, this.instance, null, nbt);
    }
}

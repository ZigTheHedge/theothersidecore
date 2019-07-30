package com.cwelth.theothersidecore;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class RestoredWorld extends WorldSavedData {
    public static final String CAPSULES_RESTORED_NAME = ModMain.MODID + "_CapsulesRestored";
    private int CAPSULES_RESTORED = 0;
    private int CAPSULES_TOBE_RESTORED = 20;

    public RestoredWorld(){
        super(CAPSULES_RESTORED_NAME);
    }

    public RestoredWorld(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if(nbt.hasKey("capsules_restored"))CAPSULES_RESTORED = nbt.getInteger("capsules_restored");
        if(nbt.hasKey("capsules_tobe_restored"))CAPSULES_TOBE_RESTORED = nbt.getInteger("capsules_tobe_restored");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("capsules_restored", CAPSULES_RESTORED);
        compound.setInteger("capsules_tobe_restored", CAPSULES_TOBE_RESTORED);
        return compound;
    }

    public static RestoredWorld get(World world){
        MapStorage storage = world.getMapStorage();
        RestoredWorld instance = (RestoredWorld) storage.getOrLoadData(RestoredWorld.class, CAPSULES_RESTORED_NAME);

        if (instance == null) {
            instance = new RestoredWorld();
            storage.setData(CAPSULES_RESTORED_NAME, instance);
        }
        return instance;
    }

    public void setCapsulesRestored(int capsulesRestored)
    {
        CAPSULES_RESTORED = capsulesRestored;
        markDirty();
    }

    public int getCapsulesRestored()
    {
        return CAPSULES_RESTORED;
    }

    public void setCapsulesToBeRestored(int capsulesRestored)
    {
        CAPSULES_TOBE_RESTORED = capsulesRestored;
        markDirty();
    }

    public int getCapsulesToBeRestored()
    {
        return CAPSULES_TOBE_RESTORED;
    }

}

package com.cwelth.theothersidecore.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class BrassDoorTE extends CommonTE implements ITickable {

    public boolean isMoving = false;
    public int delta = 0;
    public int cur_position = 0;
    public boolean isUpper = false;

    public BrassDoorTE() {
        super(0);
    }

    public BrassDoorTE(boolean isUpper) {
        super(0);
        this.isUpper = isUpper;
    }


    @Override
    public void update() {
        if(isMoving)
        {
            cur_position += delta;
            if(cur_position < 0)
            {
                cur_position = 0;
                isMoving = false;
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
            if(cur_position > 14)
            {
                cur_position = 14;
                isMoving = false;
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
            if(!world.isRemote)
                markDirty();
        }
    }

    public void activate()
    {
        if(isMoving)return;
        if(cur_position == 0){
            isMoving = true;
            delta = 1;
            if(!world.isRemote)
            {
                markDirty();
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
        } else if(cur_position == 14){
            isMoving = true;
            delta = -1;
            if(!world.isRemote)
            {
                markDirty();
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("delta")) delta = compound.getInteger("delta");
        if(compound.hasKey("cur_position")) cur_position = compound.getInteger("cur_position");
        if(compound.hasKey("isMoving")) isMoving = compound.getBoolean("isMoving");
        if(compound.hasKey("isUpper")) isUpper = compound.getBoolean("isUpper");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setInteger("delta", delta);
        compound.setInteger("cur_position", cur_position);
        compound.setBoolean("isMoving", isMoving);
        compound.setBoolean("isUpper", isUpper);
        return compound;
    }
}

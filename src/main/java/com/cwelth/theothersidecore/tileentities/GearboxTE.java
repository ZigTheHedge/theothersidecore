package com.cwelth.theothersidecore.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class GearboxTE extends CommonTE implements ITickable {

    public int delta = 0;
    public int cur_angle = 0;
    public boolean is_moving = false;
    public boolean has_main_gear = false;
    public boolean has_second_gear = false;
    private BlockPos mtpPos = null;

    public GearboxTE() {
        super(0);
    }

    public void setMainBlockPos(BlockPos mainBlockPos) {
        mtpPos = mainBlockPos;
        markDirty();
    }

    public BlockPos getMainBlockPos() {
        return mtpPos;
    }

    @Override
    public void update() {
        if(is_moving)
        {
            cur_angle += delta;
            if(cur_angle > 90){
                is_moving = false;
                delta = 0;
                cur_angle = 90;
            }
            if(cur_angle < 0){
                is_moving = false;
                delta = 0;
                cur_angle = 0;
            }
            if(!world.isRemote) {
                markDirty();
                //world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
        }
    }

    public void activate()
    {
        if(is_moving)return;
        if(cur_angle == 0){
            is_moving = true;
            delta = 4;
            if(!world.isRemote)
            {
                markDirty();
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
        } else if(cur_angle == 90){
            is_moving = true;
            delta = -4;
            if(!world.isRemote)
            {
                markDirty();
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
        }
    }

    public boolean isOpened()
    {
        return (!is_moving && cur_angle == 90);
    }

    public void setMainGearPresence(boolean presence)
    {
        this.has_main_gear = presence;
        markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }

    public void setSecondGearPresence(boolean presence)
    {
        this.has_second_gear = presence;
        markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("delta")) delta = compound.getInteger("delta");
        if(compound.hasKey("cur_angle")) cur_angle = compound.getInteger("cur_angle");
        if(compound.hasKey("is_moving")) is_moving = compound.getBoolean("is_moving");
        if(compound.hasKey("has_main_gear")) has_main_gear = compound.getBoolean("has_main_gear");
        if(compound.hasKey("has_second_gear")) has_second_gear = compound.getBoolean("has_second_gear");
        if(compound.hasKey("mtpPos"))mtpPos = NBTUtil.getPosFromTag((NBTTagCompound)compound.getTag("mtpPos"));
        else mtpPos = null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setInteger("delta", delta);
        compound.setInteger("cur_angle", cur_angle);
        compound.setBoolean("is_moving", is_moving);
        compound.setBoolean("has_main_gear", has_main_gear);
        compound.setBoolean("has_second_gear", has_second_gear);
        if(mtpPos != null)compound.setTag("mtpPos", NBTUtil.createPosTag(mtpPos));
        return compound;
    }
}

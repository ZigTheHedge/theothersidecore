package com.cwelth.theothersidecore.tileentities;

import com.cwelth.theothersidecore.ModMain;
import com.cwelth.theothersidecore.RestoredWorld;
import com.cwelth.theothersidecore.blocks.PistonVessel;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;

public class TimeSymbolTE extends CommonTE {
    public boolean isMoving = false;
    public boolean structureChecked = false;

    public TimeSymbolTE() {
        super(0);
    }

    public boolean isStructureComplete()
    {
        TileEntity te;
        if(!structureChecked)
        {
            te = world.getTileEntity(pos.south(2).up(3).east(2));
            if(te instanceof PistonVesselTE)
                ((PistonVesselTE) te).setMainBlockPos(pos);
            te = world.getTileEntity(pos.south(2).up(4).east(2));
            if(te instanceof PistonVesselTE)
                ((PistonVesselTE) te).setMainBlockPos(pos);
            te = world.getTileEntity(pos.south(2).up(3).west(2));
            if(te instanceof PistonVesselTE)
                ((PistonVesselTE) te).setMainBlockPos(pos);
            te = world.getTileEntity(pos.south(2).up(4).west(2));
            if(te instanceof PistonVesselTE)
                ((PistonVesselTE) te).setMainBlockPos(pos);
            te = world.getTileEntity(pos.south(2).up(1).west(1));
            if(te instanceof GearboxTE)
                ((GearboxTE) te).setMainBlockPos(pos);
            te = world.getTileEntity(pos.south(2).up(1).east(1));
            if(te instanceof GearboxTE)
                ((GearboxTE) te).setMainBlockPos(pos);

            structureChecked = true;
            markDirty();
        }
        boolean faultFound = false;
        te = world.getTileEntity(pos.south(2).up(3).east(2));
        if(te instanceof PistonVesselTE) {
            if (!world.getBlockState(te.getPos()).getValue(PistonVessel.HAS_LIQUID)) faultFound = true;
        }
        te = world.getTileEntity(pos.south(2).up(4).east(2));
        if(te instanceof PistonVesselTE) {
            if (!world.getBlockState(te.getPos()).getValue(PistonVessel.HAS_LIQUID)) faultFound = true;
        }
        te = world.getTileEntity(pos.south(2).up(3).west(2));
        if(te instanceof PistonVesselTE) {
            if (!world.getBlockState(te.getPos()).getValue(PistonVessel.HAS_LIQUID)) faultFound = true;
        }
        te = world.getTileEntity(pos.south(2).up(4).west(2));
        if(te instanceof PistonVesselTE) {
            if (!world.getBlockState(te.getPos()).getValue(PistonVessel.HAS_LIQUID)) faultFound = true;
        }
        te = world.getTileEntity(pos.south(2).up(1).east(1));
        if(te instanceof GearboxTE) {
            if (!((GearboxTE) te).has_main_gear || !((GearboxTE) te).has_second_gear || ((GearboxTE) te).isOpened()) faultFound = true;
        }
        te = world.getTileEntity(pos.south(2).up(1).west(1));
        if(te instanceof GearboxTE) {
            if (!((GearboxTE) te).has_main_gear || !((GearboxTE) te).has_second_gear || ((GearboxTE) te).isOpened()) faultFound = true;
        }

        return !faultFound;
    }

    public void activate()
    {
        if(isMoving)return;
        if(world.isRemote)return;
        isMoving = true;

        RestoredWorld restoredWorld = RestoredWorld.get(world);
        restoredWorld.setCapsulesRestored(restoredWorld.getCapsulesRestored() + 1);
        int rest = restoredWorld.getCapsulesRestored();
        int toBeRest = restoredWorld.getCapsulesToBeRestored();
        world.getMinecraftServer().getPlayerList().sendMessage(new TextComponentTranslation( "core.worldpartsrestored", rest, toBeRest));
        if(rest >= toBeRest)
        {
            world.getMinecraftServer().getPlayerList().sendMessage(new TextComponentTranslation("core.worldrestored"));
        }
        markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        world.playSound(null, getPos(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    public void deactivate()
    {
        if(!isMoving)return;
        if(world.isRemote)return;
        isMoving = false;

        RestoredWorld restoredWorld = RestoredWorld.get(world);
        restoredWorld.setCapsulesToBeRestored(restoredWorld.getCapsulesToBeRestored() + 2);
        int rest = restoredWorld.getCapsulesRestored();
        int toBeRest = restoredWorld.getCapsulesToBeRestored();
        world.getMinecraftServer().getPlayerList().sendMessage(new TextComponentTranslation("core.worldpartsdamaged", rest, toBeRest));
        markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        world.playSound(null, getPos(), SoundEvents.ENTITY_WITHER_DEATH, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("isMoving")) isMoving = compound.getBoolean("isMoving");
        if(compound.hasKey("structureChecked")) structureChecked = compound.getBoolean("structureChecked");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setBoolean("isMoving", isMoving);
        compound.setBoolean("structureChecked", structureChecked);
        return compound;
    }

}

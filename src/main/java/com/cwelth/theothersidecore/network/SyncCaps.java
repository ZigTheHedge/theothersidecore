package com.cwelth.theothersidecore.network;

import com.cwelth.theothersidecore.player.ITrueVisionPlayer;
import com.cwelth.theothersidecore.player.TrueVisionProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncCaps implements IMessage {
    public NBTTagCompound nbt;
    public int entId;

    public SyncCaps(){}

    public SyncCaps(NBTTagCompound tag, int eId){
        nbt = tag;
        entId = eId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entId = ByteBufUtils.readVarInt(buf, 4);
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, entId, 4);
        ByteBufUtils.writeTag(buf, nbt);
    }

    public static class Handler implements IMessageHandler<SyncCaps, IMessage> {

        @Override
        public IMessage onMessage(SyncCaps message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Minecraft mc = Minecraft.getMinecraft();
                if(mc.player != null){
                    World world = mc.player.world;
                    Entity ent = world.getEntityByID(message.entId);
                    if(ent instanceof EntityPlayer){
                        ITrueVisionPlayer cap = ((EntityPlayer)ent).getCapability(TrueVisionProvider.TRUE_VISION_PLAYER_CAPABILITY, null);
                        if(cap != null)
                            cap.readFromNBT(message.nbt);
                    }
                }
            });
            return null;
        }
    }
}

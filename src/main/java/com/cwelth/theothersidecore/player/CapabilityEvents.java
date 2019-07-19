package com.cwelth.theothersidecore.player;

import com.cwelth.theothersidecore.ModMain;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityEvents {
    public static final ResourceLocation TRUE_VISION_PLAYER_CAPABILITY = new ResourceLocation(ModMain.MODID, "true_vision_player");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(TRUE_VISION_PLAYER_CAPABILITY, new TrueVisionProvider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        ITrueVisionPlayer newCap = player.getCapability(TrueVisionProvider.TRUE_VISION_PLAYER_CAPABILITY, null);
        ITrueVisionPlayer oldCap = event.getOriginal().getCapability(TrueVisionProvider.TRUE_VISION_PLAYER_CAPABILITY, null);
        newCap.copyPlayer(oldCap);
    }
}
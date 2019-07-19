package com.cwelth.theothersidecore.sounds;

import com.cwelth.theothersidecore.ModMain;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(ModMain.MODID)
public class AllSounds {
    /*
    @GameRegistry.ObjectHolder("endgamemusic")
    public static final SoundEvent ENDGAMEMUSIC = createSoundEvent("endgamemusic");

    @GameRegistry.ObjectHolder("blow")
    public static final SoundEvent BLOW = createSoundEvent("blow");

    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation soundID = new ResourceLocation(ModMain.MODID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }

    @Mod.EventBusSubscriber(modid = ModMain.MODID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().registerAll(ENDGAMEMUSIC, BLOW);
        }
    }

     */
}

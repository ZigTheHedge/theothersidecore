package com.cwelth.theothersidecore;

import com.cwelth.theothersidecore.blocks.AllBlocks;
import com.cwelth.theothersidecore.commands.TOSCTrueVisionSetCmd;
import com.cwelth.theothersidecore.gui.TOSCCreativeTab;
import com.cwelth.theothersidecore.items.AllItems;
import com.cwelth.theothersidecore.network.SyncCaps;
import com.cwelth.theothersidecore.player.CapabilityEvents;
import com.cwelth.theothersidecore.proxy.CommonProxy;
import com.cwelth.theothersidecore.worldgen.CapsuleGenerator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.logging.Logger;

@Mod(modid = ModMain.MODID, name = ModMain.NAME, version = ModMain.VERSION, dependencies = "required-after:intimepresence;")
public class ModMain {

    public static final String NAME = "The Other Side modpack Core Mod";
    public static final String MODID = "theothersidecore";
    public static final String VERSION = "1.01";
    public static final CreativeTabs toscCreativeTab = new TOSCCreativeTab();


    public static final Logger logger = Logger.getLogger(MODID);

    @Mod.Instance(ModMain.MODID)
    public static ModMain instance;

    public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @SidedProxy(clientSide = "com.cwelth.theothersidecore.proxy.ClientProxy", serverSide = "com.cwelth.theothersidecore.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        MinecraftForge.EVENT_BUS.register(new EventHandlers());
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());

        network.registerMessage(SyncCaps.Handler.class, SyncCaps.class, 1, Side.CLIENT);

        GameRegistry.registerWorldGenerator(new CapsuleGenerator(), 1);

        AllItems.registerOreDictionary();
        AllBlocks.registerOreDictionary();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new TOSCTrueVisionSetCmd());
    }
}
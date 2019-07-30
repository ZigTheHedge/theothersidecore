package com.cwelth.theothersidecore.proxy;

import com.cwelth.theothersidecore.ModMain;
import com.cwelth.theothersidecore.blocks.*;
import com.cwelth.theothersidecore.items.Gear;
import com.cwelth.theothersidecore.items.TimeIngot;
import com.cwelth.theothersidecore.items.TimeKey;
import com.cwelth.theothersidecore.items.Wrench;
import com.cwelth.theothersidecore.player.ITrueVisionPlayer;
import com.cwelth.theothersidecore.player.TrueVisionPlayer;
import com.cwelth.theothersidecore.player.TrueVisionStorage;
import com.cwelth.theothersidecore.tileentities.BrassDoorTE;
import com.cwelth.theothersidecore.tileentities.GearboxTE;
import com.cwelth.theothersidecore.tileentities.PistonVesselTE;
import com.cwelth.theothersidecore.tileentities.TimeSymbolTE;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {

    }

    public void init(FMLInitializationEvent e) {
        CapabilityManager.INSTANCE.register(ITrueVisionPlayer.class, new TrueVisionStorage(), TrueVisionPlayer.class);

    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //Items
        event.getRegistry().register(new Wrench());
        event.getRegistry().register(new TimeKey());
        event.getRegistry().register(new Gear());
        event.getRegistry().register(new TimeIngot());

        //ItemBlocks
        event.getRegistry().register(new ItemBlock(AllBlocks.timeBlock).setRegistryName(AllBlocks.timeBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(AllBlocks.brassBrick).setRegistryName(AllBlocks.brassBrick.getRegistryName()));
        event.getRegistry().register(new ItemBlock(AllBlocks.brassPipeBent).setRegistryName(AllBlocks.brassPipeBent.getRegistryName()));
        event.getRegistry().register(new ItemBlock(AllBlocks.brassGlass).setRegistryName(AllBlocks.brassGlass.getRegistryName()));
        event.getRegistry().register(new ItemBlock(AllBlocks.brassPipeStraight).setRegistryName(AllBlocks.brassPipeStraight.getRegistryName()));
        event.getRegistry().register(new ItemBlock(AllBlocks.pistonVessel).setRegistryName(AllBlocks.pistonVessel.getRegistryName()));
        event.getRegistry().register(new ItemBlock(AllBlocks.flask).setRegistryName(AllBlocks.flask.getRegistryName()));
        event.getRegistry().register(new ItemBlock(AllBlocks.standFlask).setRegistryName(AllBlocks.standFlask.getRegistryName()));
        event.getRegistry().register(new ItemBlock(AllBlocks.gearBox).setRegistryName(AllBlocks.gearBox.getRegistryName()));
        event.getRegistry().register(new ItemBlock(AllBlocks.brassDoor).setRegistryName(AllBlocks.brassDoor.getRegistryName()));
        event.getRegistry().register(new ItemBlock(AllBlocks.timeSymbol).setRegistryName(AllBlocks.timeSymbol.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //Blocks
        event.getRegistry().register(new TimeBlock());
        event.getRegistry().register(new BrassBrick());
        event.getRegistry().register(new BrassPipeBent());
        event.getRegistry().register(new BrassGlass());
        event.getRegistry().register(new BrassPipeStraight());
        event.getRegistry().register(new PistonVessel());
        event.getRegistry().register(new Flask());
        event.getRegistry().register(new StandFlask());
        event.getRegistry().register(new GearBox());
        event.getRegistry().register(new BrassDoor());
        event.getRegistry().register(new TimeSymbol());

        //TileEntities
        GameRegistry.registerTileEntity(GearboxTE.class, ModMain.MODID + "_gearboxte");
        GameRegistry.registerTileEntity(BrassDoorTE.class, ModMain.MODID + "_brassdoorte");
        GameRegistry.registerTileEntity(TimeSymbolTE.class, ModMain.MODID + "_timesymbolte");
        GameRegistry.registerTileEntity(PistonVesselTE.class, ModMain.MODID + "_pistonvesselte");
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

    }

    @SubscribeEvent
    public static void handleOreDicts(OreDictionary.OreRegisterEvent event)
    {
    }
}
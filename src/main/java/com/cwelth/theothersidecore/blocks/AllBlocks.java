package com.cwelth.theothersidecore.blocks;

import com.cwelth.theothersidecore.ModMain;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AllBlocks {

    @GameRegistry.ObjectHolder(ModMain.MODID + ":timeblock")
    public static TimeBlock timeBlock;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":brassbrick")
    public static BrassBrick brassBrick;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":brasspipebent")
    public static BrassPipeBent brassPipeBent;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":brassglass")
    public static BrassGlass brassGlass;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":brasspipestraight")
    public static BrassPipeStraight brassPipeStraight;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":pistonvessel")
    public static PistonVessel pistonVessel;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":flask")
    public static Flask flask;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":stand_flask")
    public static StandFlask standFlask;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":gearbox")
    public static GearBox gearBox;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":brass_door")
    public static BrassDoor brassDoor;

    @GameRegistry.ObjectHolder(ModMain.MODID + ":timesymbol")
    public static TimeSymbol timeSymbol;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        timeBlock.initModel();
        brassBrick.initModel();
        brassPipeBent.initModel();
        brassGlass.initModel();
        brassPipeStraight.initModel();
        pistonVessel.initModel();
        flask.initModel();
        standFlask.initModel();
        gearBox.initModel();
        brassDoor.initModel();
        timeSymbol.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initBlockItemModels() {
        timeBlock.initItemModel();
        brassBrick.initItemModel();
        brassPipeBent.initItemModel();
        brassGlass.initItemModel();
        brassPipeStraight.initItemModel();
        pistonVessel.initItemModel();
        flask.initItemModel();
        standFlask.initItemModel();
        gearBox.initItemModel();
        brassDoor.initItemModel();
        timeSymbol.initItemModel();
    }

    public static void registerOreDictionary()
    {
    }
}

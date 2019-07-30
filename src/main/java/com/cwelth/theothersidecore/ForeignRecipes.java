package com.cwelth.theothersidecore;

import com.cwelth.theothersidecore.blocks.AllBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreIngredient;

import static com.cwelth.intimepresence.recipies.SelfRecipies.obsidianCauldronRecipes;
import static com.cwelth.intimepresence.recipies.SelfRecipies.steamHammerRecepies;

@Mod.EventBusSubscriber(modid=ModMain.MODID)
public class ForeignRecipes {

    @SubscribeEvent
    public static void initRecipes(RegistryEvent.Register<IRecipe> event)
    {
        //steamHammerRecepies.addRecipe(new OreIngredient("gemDiamond"), new ItemStack(AllItems.diamondShards, 2), 13);
        obsidianCauldronRecipes.addRecipe(new OreIngredient("ingotBrass"), 2, new OreIngredient("blockGlass"), 3, Ingredient.EMPTY, 0, new ItemStack(AllBlocks.pistonVessel), 1200);
    }

}

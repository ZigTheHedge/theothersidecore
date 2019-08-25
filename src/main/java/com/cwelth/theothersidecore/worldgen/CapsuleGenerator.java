package com.cwelth.theothersidecore.worldgen;

import com.cwelth.theothersidecore.ModMain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class CapsuleGenerator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        if(random.nextInt(400) != 0) return;
        if(world.provider.getDimension() != 0)return;

        BlockPos basePos = new BlockPos(chunkX * 16 + random.nextInt(16), 100, chunkZ * 16 + random.nextInt(16));
        basePos = world.getTopSolidOrLiquidBlock(basePos).down();


        final PlacementSettings settings = new PlacementSettings().setRotation(Rotation.NONE);
        final Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(ModMain.MODID, "ancient_capsule"));
        final CapsuleGenProcessor capsuleGenProcessor = new CapsuleGenProcessor();

        template.addBlocksToWorld(world, basePos, capsuleGenProcessor, settings, 2);
        ModMain.logger.info("Capsule generated at: "+basePos.toString());
    }
}

package io.github.franiscoder.mostructures.common.feature;

import io.github.franiscoder.mostructures.MoStructures;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SmallAirFeature extends Feature<DefaultFeatureConfig> {
    private static final Identifier AIR_BALLOON_1 = MoStructures.id("airballoon1");
    private static final Identifier AIR_BALLOON_2 = MoStructures.id("airballoon2");
    private static final Identifier AIR_BALLOON_3 = MoStructures.id("airballoon3");
    private static final Identifier AIRPLANE = MoStructures.id("airplane");
    public static final Identifier[] AIR_FEATURES = new Identifier[]{AIR_BALLOON_1, AIR_BALLOON_2, AIR_BALLOON_3, AIRPLANE};


    public SmallAirFeature() {
        super(DefaultFeatureConfig::deserialize);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        Random random2 = world.getRandom();
        int i = random2.nextInt(AIR_FEATURES.length);
        StructureManager manager = ((ServerWorld) world.getWorld()).getStructureManager();
        Structure structure = manager.getStructureOrBlank(AIR_FEATURES[i]);


        int yToAdd = Math.max(random2.nextInt(100), 40);
        int newHeight = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ()) + yToAdd;
        BlockPos newPos = new BlockPos(pos.getX(), newHeight, pos.getZ());
        BlockRotation blockRotation = BlockRotation.random(random);

        StructurePlacementData structurePlacementData = (new StructurePlacementData()).setMirrored(BlockMirror.NONE).setRotation(blockRotation).setIgnoreEntities(false).setChunkPosition(null);

        structure.place(world, newPos, structurePlacementData);

        return true;
    }
}

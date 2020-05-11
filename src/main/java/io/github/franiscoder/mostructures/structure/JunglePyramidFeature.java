package io.github.franiscoder.mostructures.structure;

import io.github.franiscoder.mostructures.MoStructures;
import io.github.franiscoder.mostructures.generator.JunglePyramidGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class JunglePyramidFeature extends AbstractTempleFeature<DefaultFeatureConfig> {
    public JunglePyramidFeature() {
        super(DefaultFeatureConfig::deserialize);
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return JunglePyramidFeature.Start::new;
    }

    @Override
    public String getName() {
        return MoStructures.MODID + ":Jungle_Pyramid";
    }

    @Override
    public int getRadius() {
        return 8;
    }

    @Override
    protected int getSeedModifier() {
        return 112178942;
    }

    public static class Start extends StructureStart {
        public Start(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
            super(feature, chunkX, chunkZ, box, references, seed);
        }

        @Override
        public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
            JunglePyramidGenerator.addPieces(chunkGenerator, structureManager, new BlockPos(x * 16, 0, z * 16), children, random);
            this.setBoundingBoxFromChildren();
        }

    }
}

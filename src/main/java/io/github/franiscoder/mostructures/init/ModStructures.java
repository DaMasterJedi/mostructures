package io.github.franiscoder.mostructures.init;

import io.github.franiscoder.mostructures.MoStructures;
import io.github.franiscoder.mostructures.feature.*;
import io.github.franiscoder.mostructures.feature.structure.BarnHouseFeature;
import io.github.franiscoder.mostructures.feature.structure.BigPyramidFeature;
import io.github.franiscoder.mostructures.feature.structure.PiglinOutpostFeature;
import io.github.franiscoder.mostructures.generator.BarnHouseGenerator;
import io.github.franiscoder.mostructures.generator.BigPyramidGenerator;
import io.github.franiscoder.mostructures.generator.PiglinOutpostGenerator;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ModStructures {
    public static final Feature<DefaultFeatureConfig> AIR_FEATURES = Registry.register(Registry.FEATURE, MoStructures.id("airballoon"), new SmallAirFeature());
    public static final Feature<DefaultFeatureConfig> FALLEN_TREE = Registry.register(Registry.FEATURE, MoStructures.id("fallen_tree"), new FallenTreeFeature());
    public static final Feature<DefaultFeatureConfig> SMALL_DESERT_FEATURES = Registry.register(Registry.FEATURE, MoStructures.id("dead_tree"), new SmallDryFeature());
    public static final Feature<DefaultFeatureConfig> RUINS = Registry.register(Registry.FEATURE, MoStructures.id("ruins"), new RuinsFeature());
    public static final Feature<DefaultFeatureConfig> LAMPPOST = Registry.register(Registry.FEATURE, MoStructures.id("lamppost"), new LamppostFeature());

    public static final StructureFeature<DefaultFeatureConfig> BARN_HOUSE = Registry.register(Registry.FEATURE, MoStructures.id("barn_house_feature"), new BarnHouseFeature());
    public static final StructurePieceType BARN_HOUSE_PIECE = Registry.register(Registry.STRUCTURE_PIECE, MoStructures.id("barn_house_piece"), BarnHouseGenerator.Piece::new);
    public static final StructureFeature<DefaultFeatureConfig> PIGLIN_OUTPOST = Registry.register(Registry.FEATURE, MoStructures.id("piglin_outpost_feature"), new PiglinOutpostFeature());
    public static final StructurePieceType PIGLIN_OUTPOST_PIECE = Registry.register(Registry.STRUCTURE_PIECE, MoStructures.id("piglin_outpost_piece"), PiglinOutpostGenerator.Piece::new);
    public static final StructureFeature<DefaultFeatureConfig> PYRAMID = Registry.register(Registry.FEATURE, MoStructures.id("big_pyramid_feature"), new BigPyramidFeature());
    public static final StructurePieceType PYRAMID_PIECE = Registry.register(Registry.STRUCTURE_PIECE, MoStructures.id("big_pyramid_piece"), BigPyramidGenerator.Piece::new);

    public static void init() {
        Registry.register(Registry.STRUCTURE_FEATURE, MoStructures.id("barn_house_structure"), BARN_HOUSE);
        Registry.register(Registry.STRUCTURE_FEATURE, MoStructures.id("piglin_outpost_structure"), PIGLIN_OUTPOST);
        Registry.register(Registry.STRUCTURE_FEATURE, MoStructures.id("big_pyramid_structure"), PYRAMID);

        Registry.BIOME.forEach((Biome biome) -> {

            //Features
            if (MoStructures.getConfig().generateAirFeatures) {
                biome.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, AIR_FEATURES
                        .configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(7500 / SmallAirFeature.AIR_FEATURES.length)))
                );
            }
            if (MoStructures.getConfig().generateLandFeatures) {
                biome.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, SMALL_DESERT_FEATURES
                        .configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(500 / SmallDryFeature.IDENTIFIERS.length)))
                );
                biome.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, FALLEN_TREE
                        .configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(11)))
                );
            }

            if (MoStructures.getConfig().generateMiscellaneousStructures) {
                biome.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, LAMPPOST
                        .configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(100)))
                );
            }

            if (MoStructures.getConfig().generateOverworldStructures) {
                biome.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, RUINS
                        .configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(1000)))
                );
                biome.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, BARN_HOUSE
                        .configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.NOPE.configure(new NopeDecoratorConfig()))
                );
                biome.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, PYRAMID
                        .configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.NOPE.configure(new NopeDecoratorConfig()))
                );
                if (biome.getCategory() == Biome.Category.PLAINS || biome == Biomes.SAVANNA) {
                    biome.addStructureFeature(BARN_HOUSE.configure(FeatureConfig.DEFAULT));
                }
                if (biome.getCategory() == Biome.Category.DESERT) {
                    biome.addStructureFeature(PYRAMID.configure(FeatureConfig.DEFAULT));
                }
                Feature.STRUCTURES.put(MoStructures.MODID + ":barn_house", BARN_HOUSE);
                Feature.STRUCTURES.put(MoStructures.MODID + ":big_pyramid", PYRAMID);
            }
            if (MoStructures.getConfig().generateNetherStructures) {
                biome.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, PIGLIN_OUTPOST
                        .configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.NOPE.configure(new NopeDecoratorConfig()))
                );
                //Register structures so it only spawns in said biomes.

                if (biome.getCategory() == Biome.Category.NETHER) {
                    biome.addStructureFeature(PIGLIN_OUTPOST.configure(FeatureConfig.DEFAULT));
                }
                Feature.STRUCTURES.put(MoStructures.MODID + ":piglin_outpost", PIGLIN_OUTPOST);
            }
        });
    }
}

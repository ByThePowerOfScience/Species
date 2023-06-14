package com.ninni.species.world.gen.structure;

import com.ninni.species.tag.SpeciesTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.Map;

public class SpeciesStructures {

    public static void bootstrap(BootstapContext<Structure> bootstapContext) {
        HolderGetter<Biome> holderGetter = bootstapContext.lookup(Registries.BIOME);
        bootstapContext.register(SpeciesStructureKeys.WRAPTOR_COOP, new WraptorCoopStructure(structure(holderGetter.getOrThrow(SpeciesTags.WRAPTOR_COOP_HAS_STRUCTURE), TerrainAdjustment.BEARD_BOX)));
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> holderSet, Map<MobCategory, StructureSpawnOverride> map, GenerationStep.Decoration decoration, TerrainAdjustment terrainAdjustment) {
        return new Structure.StructureSettings(holderSet, map, decoration, terrainAdjustment);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> holderSet, TerrainAdjustment terrainAdjustment) {
        return structure(holderSet, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, terrainAdjustment);
    }
}

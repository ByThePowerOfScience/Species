package com.ninni.species.structure;

import com.google.common.collect.Maps;
import com.ninni.species.Species;
import com.ninni.species.init.SpeciesBlocks;
import com.ninni.species.init.SpeciesLootTables;
import com.ninni.species.init.SpeciesStructurePieceTypes;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PaleontologyDigSiteGenerator {
    public static final Map<ResourceLocation, Integer> HEIGHT_TO_TEMPLATES = Util.make(Maps.newHashMap(), map -> {
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_bigger"), 9);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_crippler"), 6);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_gripper"), 7);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_extender"), 3);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_grinner"), 1);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_lilypadder"), 7);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_loser"), 3);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_shimmer_shell"), 7);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_shimmer_tail"), 5);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_stroker"), 2);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_trampler"), 6);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_tremor"), 4);
        map.put(new ResourceLocation(Species.MOD_ID, "paleontology_dig_site/dig_site_zipper"), 10);
    });

    public static void addPieces(StructureTemplateManager manager, BlockPos pos, StructurePieceAccessor holder, RandomSource random) {
        Set<ResourceLocation> resourceLocations = HEIGHT_TO_TEMPLATES.keySet();
        List<ResourceLocation> templates = new ArrayList<>(resourceLocations.stream().toList());
        ResourceLocation randomTemplate = Util.getRandom(templates, random);
        Rotation rotation = Rotation.getRandom(random);
        holder.addPiece(new Piece(manager, randomTemplate, pos, rotation));
    }

    public static class Piece extends TemplateStructurePiece {
        public static final String ROTATION_KEY = "Rotation";

        public Piece(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, Rotation rotation) {
            super(SpeciesStructurePieceTypes.PALEONTOLOGY_DIG_SITE.get(), HEIGHT_TO_TEMPLATES.get(id), manager, id, id.toString(), createPlacementData(rotation), pos);
        }

        public Piece(StructureTemplateManager manager, CompoundTag nbt) {
            super(SpeciesStructurePieceTypes.PALEONTOLOGY_DIG_SITE.get(), nbt, manager, id -> createPlacementData(Rotation.valueOf(nbt.getString(ROTATION_KEY))));
        }

        private static StructurePlaceSettings createPlacementData(Rotation rotation) {
            return new StructurePlaceSettings()
                    .setRotation(rotation)
                    .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag compoundTag) {
            super.addAdditionalSaveData(structurePieceSerializationContext, compoundTag);
            compoundTag.putString(ROTATION_KEY, this.placeSettings.getRotation().name());
        }

        @Override
        public void postProcess(WorldGenLevel world, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource randomSource, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            BlockPos blockPos1 = this.templatePosition;
            this.templatePosition = this.templatePosition.below(PaleontologyDigSiteGenerator.HEIGHT_TO_TEMPLATES.get(new ResourceLocation(this.templateName)));
            super.postProcess(world, structureManager, chunkGenerator, randomSource, boundingBox, chunkPos, blockPos);
            this.templatePosition = blockPos1;
        }


        @Override
        protected void handleDataMarker(String metadata, BlockPos pos, ServerLevelAccessor world, RandomSource random, BoundingBox boundingBox) {
            if (metadata.equals("COMMON")) {
                this.createRedSuspiciousSand(world, boundingBox, random, pos, SpeciesLootTables.PALEONTOLOGY_DIG_SITE_COMMON);
            }
            if (metadata.equals("RARE")) {
                this.createRedSuspiciousSand(world, boundingBox, random, pos, SpeciesLootTables.PALEONTOLOGY_DIG_SITE_RARE);
            }
            if (metadata.equals("EPIC")) {
                this.createRedSuspiciousSand(world, boundingBox, random, pos, SpeciesLootTables.PALEONTOLOGY_DIG_SITE_EPIC);
            }
        }

        protected void createRedSuspiciousSand(ServerLevelAccessor serverLevelAccessor, BoundingBox boundingBox, RandomSource randomSource, BlockPos blockPos, ResourceLocation resourceLocation) {
            Block redSuspiciousSand = SpeciesBlocks.RED_SUSPICIOUS_SAND.get();

            boolean outsideBoundingBox = !boundingBox.isInside(blockPos);
            boolean stateIsSelf = serverLevelAccessor.getBlockState(blockPos).is(redSuspiciousSand);
            boolean invalidBelowPos = !serverLevelAccessor.getBlockState(blockPos.below()).isFaceSturdy(serverLevelAccessor, blockPos.below(), Direction.UP);

            if (outsideBoundingBox || stateIsSelf || invalidBelowPos) return;

            serverLevelAccessor.setBlock(blockPos, redSuspiciousSand.defaultBlockState(), 2);

            BlockEntity blockEntity = serverLevelAccessor.getBlockEntity(blockPos);

            if (blockEntity instanceof BrushableBlockEntity entity) entity.setLootTable(resourceLocation, randomSource.nextLong());
        }

    }

}
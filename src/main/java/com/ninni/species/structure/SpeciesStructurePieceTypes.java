package com.ninni.species.structure;

import com.ninni.species.Species;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Species.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpeciesStructurePieceTypes {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, Species.MOD_ID);

    public static final RegistryObject<StructurePieceType> WRAPTOR_COOP = register("wraptor_coop", WraptorCoopGenerator.Piece::new);

    private static RegistryObject<StructurePieceType> register(String id, StructurePieceType type) {
        return STRUCTURE_PIECE_TYPES.register(id, () -> type);
    }

    private static RegistryObject<StructurePieceType> register(String id, StructurePieceType.StructureTemplateType type) {
        return register(id, (StructurePieceType) type);
    }
}

package com.ninni.species.block.property;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SpeciesProperties {
    public static final IntegerProperty BIRTS = IntegerProperty.create("birts", 0, 3);
    public static final IntegerProperty EGGS = IntegerProperty.create("eggs", 0, 5);
    public static final BooleanProperty HEATED = BooleanProperty.create("heated");
    public static final BooleanProperty CRUNCHER_EGG_CRACKED = BooleanProperty.create("cracked");
    public static final IntegerProperty CRUNCHER_EGG_TIMER = IntegerProperty.create("timer", 0, 60);
    public static final EnumProperty<BoneSpikeThickness> BONE_SPIKE_THICKNESS = EnumProperty.create("thickness", BoneSpikeThickness.class);
}
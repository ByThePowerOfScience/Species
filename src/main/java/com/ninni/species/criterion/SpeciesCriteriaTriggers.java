package com.ninni.species.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static com.ninni.species.Species.MOD_ID;

public class SpeciesCriteriaTriggers extends SimpleCriterionTrigger<SpeciesCriteriaTriggers.TriggerInstance> {
    private final ResourceLocation ID;

    public SpeciesCriteriaTriggers(String name) {
        ID = new ResourceLocation(MOD_ID, name);
    }

    @Override
    protected TriggerInstance createInstance(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return new SpeciesCriteriaTriggers.TriggerInstance(ID, composite);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, conditions -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ResourceLocation id, EntityPredicate.Composite playerPredicate) {
            super(id, playerPredicate);
        }

    }
}
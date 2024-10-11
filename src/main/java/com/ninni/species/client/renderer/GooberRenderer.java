package com.ninni.species.client.renderer;

import com.ninni.species.client.model.entity.GooberModel;
import com.ninni.species.registry.SpeciesEntityModelLayers;
import com.ninni.species.entity.Goober;
import com.ninni.species.entity.enums.GooberBehavior;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.ninni.species.Species.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class GooberRenderer<T extends LivingEntity> extends MobRenderer<Goober, GooberModel<Goober>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/entity/goober/goober.png");
    public static final ResourceLocation TEXTURE_TIRED = new ResourceLocation(MOD_ID, "textures/entity/goober/goober_tired.png");
    public static final ResourceLocation TEXTURE_VINTAGE = new ResourceLocation(MOD_ID, "textures/entity/goober/goober_vintage.png");
    public static final ResourceLocation TEXTURE_TIRED_VINTAGE = new ResourceLocation(MOD_ID, "textures/entity/goober/goober_tired_vintage.png");

    public GooberRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GooberModel<>(ctx.bakeLayer(SpeciesEntityModelLayers.GOOBER)), 1F);
    }

    @Override
    public ResourceLocation getTextureLocation(Goober entity) {
        if (entity.getName().getString().equalsIgnoreCase("vintage")) return entity.getBehavior() == GooberBehavior.YAWN.getName() ? TEXTURE_TIRED_VINTAGE : TEXTURE_VINTAGE;
        return entity.getBehavior() == GooberBehavior.YAWN.getName() ? TEXTURE_TIRED : TEXTURE;
    }
}
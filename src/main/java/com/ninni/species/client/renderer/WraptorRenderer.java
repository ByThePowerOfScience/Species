package com.ninni.species.client.renderer;

import com.ninni.species.client.model.entity.SpeciesEntityModelLayers;
import com.ninni.species.client.model.entity.WraptorEntityModel;
import com.ninni.species.entity.Wraptor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.ninni.species.Species.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class WraptorRenderer<T extends LivingEntity> extends MobRenderer<Wraptor, WraptorEntityModel<Wraptor>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/entity/wraptor/wraptor.png");
    public static final ResourceLocation TEXTURE_GOTH = new ResourceLocation(MOD_ID, "textures/entity/wraptor/wraptor_goth.png");

    public WraptorRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new WraptorEntityModel<>(ctx.bakeLayer(SpeciesEntityModelLayers.WRAPTOR)), 0.6F);
    }

    @Override public ResourceLocation getTextureLocation(Wraptor entity) {
        if (entity.getName().getString().equalsIgnoreCase("goth") || entity.getName().getString().equalsIgnoreCase("suzie")) {
            return TEXTURE_GOTH;
        } else return TEXTURE;
    }

    @Override
    protected boolean isShaking(Wraptor entity) {
        if (entity.getFeatherStage() == 1) return true;
        if (!entity.level().dimensionType().piglinSafe()) return true;
        return super.isShaking(entity);
    }
}

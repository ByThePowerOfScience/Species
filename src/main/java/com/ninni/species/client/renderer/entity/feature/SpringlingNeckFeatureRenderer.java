package com.ninni.species.client.renderer.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ninni.species.client.model.entity.SpringlingModel;
import com.ninni.species.entity.Springling;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.ninni.species.Species.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class SpringlingNeckFeatureRenderer<T extends Springling, M extends SpringlingModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation NECK = new ResourceLocation(MOD_ID, "textures/entity/springling/springling_neck.png");
    private static final ResourceLocation NECK_PISTON = new ResourceLocation(MOD_ID, "textures/entity/springling/springling_neck_piston.png");
    private static final ResourceLocation NECK_EXTENDING = new ResourceLocation(MOD_ID, "textures/entity/springling/springling_neck_extending.png");
    private static final ResourceLocation NECK_EXTENDING_PISTON = new ResourceLocation(MOD_ID, "textures/entity/springling/springling_neck_extending_piston.png");
    private static final ResourceLocation NECK_EXTENDED = new ResourceLocation(MOD_ID, "textures/entity/springling/springling_neck_extended.png");
    private static final ResourceLocation NECK_EXTENDED_PISTON = new ResourceLocation(MOD_ID, "textures/entity/springling/springling_neck_extended_piston.png");

    public SpringlingNeckFeatureRenderer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        SpringlingNeckFeatureRenderer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.getParentModel(), this.resourceLocation(entity), poseStack, multiBufferSource, i, entity, f, g, j, k, l, h, 1, 1, 1);
    }

    public ResourceLocation resourceLocation(T entity) {
        if (entity.getExtendedAmount() > entity.getMaxExtendedAmount()/3f && entity.getExtendedAmount() < entity.getMaxExtendedAmount()/1.5f) return entity.getName().getString().equalsIgnoreCase("piston") ? NECK_EXTENDING_PISTON : NECK_EXTENDING;
        if (entity.getExtendedAmount() >= entity.getMaxExtendedAmount()/1.5f)  return entity.getName().getString().equalsIgnoreCase("piston") ? NECK_EXTENDED_PISTON : NECK_EXTENDED;
        return entity.getName().getString().equalsIgnoreCase("piston") ? NECK_PISTON : NECK;
    }
}
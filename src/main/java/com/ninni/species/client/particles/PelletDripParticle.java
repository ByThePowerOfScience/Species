package com.ninni.species.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class PelletDripParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;

    public PelletDripParticle(ClientLevel clientLevel, double d, double e, double f, SpriteSet spriteProvider) {
        super(clientLevel, d, e, f);
        this.setSize(0.01f, 0.01f);
        this.gravity = 0.06f;
        this.spriteProvider = spriteProvider;
        this.setSpriteFromAge(spriteProvider);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    protected int getLightColor(float f) {
        return 240;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (this.removed) {
            return;
        }
        this.yd -= this.gravity;
        this.move(this.xd, this.yd, this.zd);
        this.postMoveUpdate();
        if (this.removed) {
            return;
        }
        this.xd *= 0.98f;
        this.yd *= 0.98f;
        this.zd *= 0.98f;
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

    protected void postMoveUpdate() {
    }

    @OnlyIn(Dist.CLIENT)
    static class DripHangParticle extends PelletDripParticle {
        private final ParticleOptions fallingParticle;

        DripHangParticle(ClientLevel clientLevel, double d, double e, double f, ParticleOptions particleOptions, SpriteSet spriteSet) {
            super(clientLevel, d, e, f, spriteSet);
            this.fallingParticle = particleOptions;
            this.gravity *= 0.02f;
            this.lifetime = 40;
        }

        @Override
        protected void preMoveUpdate() {
            if (this.lifetime-- <= 0) {
                this.remove();
                this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }
        }

        @Override
        protected void postMoveUpdate() {
            this.xd *= 0.02;
            this.yd *= 0.02;
            this.zd *= 0.02;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FallAndLandParticle extends FallingParticle {
        protected final ParticleOptions landParticle;

        FallAndLandParticle(ClientLevel clientLevel, double d, double e, double f, ParticleOptions particleOptions, SpriteSet spriteSet) {
            super(clientLevel, d, e, f, spriteSet);
            this.landParticle = particleOptions;
        }

        @Override
        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
                this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class DripLandParticle extends PelletDripParticle {
        DripLandParticle(ClientLevel clientLevel, double d, double e, double f, SpriteSet spriteSet) {
            super(clientLevel, d, e, f, spriteSet);
            this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FallingParticle extends PelletDripParticle {
        FallingParticle(ClientLevel clientLevel, double d, double e, double f, SpriteSet spriteSet) {
            this(clientLevel, d, e, f, (int)(64.0 / (Math.random() * 0.8 + 0.2)), spriteSet);
        }

        FallingParticle(ClientLevel clientLevel, double d, double e, double f, int i, SpriteSet spriteSet) {
            super(clientLevel, d, e, f, spriteSet);
            this.lifetime = i;
        }

        @Override
        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
            }
        }
    }

    public record PelletDripHangProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double v3, double v4, double v5) {
            DripHangParticle dripHangParticle = new DripHangParticle(clientLevel, d, e, f, SpeciesParticles.FALLING_PELLET_DRIP.get(), this.spriteSet);
            dripHangParticle.gravity *= 0.01f;
            dripHangParticle.lifetime = 100;
            dripHangParticle.setColor(0.0784F, 0.860F, 0.980F);
            return dripHangParticle;
        }
    }

    public record PelletDripFallProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double v3, double v4, double v5) {
            FallAndLandParticle dripParticle = new FallAndLandParticle(clientLevel, d, e, f, SpeciesParticles.LANDING_PELLET_DRIP.get(), this.spriteSet);
            dripParticle.gravity = 0.01f;
            dripParticle.setColor(0.0784F, 0.860F, 0.980F);
            return dripParticle;
        }
    }

    public record PelletDripLandProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double v3, double v4, double v5) {
            DripLandParticle dripParticle = new DripLandParticle(clientLevel, d, e, f, this.spriteSet);
            dripParticle.lifetime = (int)(28.0 / (Math.random() * 0.8 + 0.2));
            dripParticle.setColor(0.0784F, 0.860F, 0.980F);
            return dripParticle;
        }
    }

}
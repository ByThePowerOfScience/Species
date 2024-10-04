package com.ninni.species.entity.ai.goal;

import com.ninni.species.entity.Treeper;
import net.minecraft.world.entity.ai.goal.Goal;

public class TreeperUprootGoal extends Goal {
    protected final Treeper treeper;

    public TreeperUprootGoal(Treeper treeper) {
        this.treeper = treeper;
    }

    @Override
    public boolean canUse() {
        return !treeper.isInWater() && treeper.level().isNight() && treeper.onGround() && treeper.isPlanted() && !treeper.isBurned();
    }

    @Override
    public boolean canContinueToUse() {
        return !treeper.isInWater() && treeper.level().isNight() && treeper.onGround() && treeper.isPlanted() && !treeper.isBurned();
    }

    @Override
    public void start() {
        treeper.uproot();
    }

}
package com.example.supiki_mod.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class SupikiFollowPlayerGoal extends Goal {
    private final PathfinderMob mob;
    private final double speedModifier;
    private final float followRange;
    private final float stopDistance;
    private final PathNavigation navigation;

    private Player targetPlayer;

    public SupikiFollowPlayerGoal(PathfinderMob mob, double speedModifier, float followRange, float stopDistance) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followRange = followRange;
        this.stopDistance = stopDistance;
        this.navigation = mob.getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        Player nearestPlayer = this.mob.level().getNearestPlayer(this.mob, this.followRange);
        if (nearestPlayer == null || nearestPlayer.isSpectator()) {
            return false;
        }

        if (this.mob.distanceToSqr(nearestPlayer) <= (double) (this.stopDistance * this.stopDistance)) {
            return false;
        }

        this.targetPlayer = nearestPlayer;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.targetPlayer == null || !this.targetPlayer.isAlive() || this.targetPlayer.isSpectator()) {
            return false;
        }

        double distanceSqr = this.mob.distanceToSqr(this.targetPlayer);
        return distanceSqr > (double) (this.stopDistance * this.stopDistance)
            && distanceSqr < (double) (this.followRange * this.followRange);
    }

    @Override
    public void start() {
        moveToTarget();
    }

    @Override
    public void tick() {
        if (this.targetPlayer == null) {
            return;
        }

        this.mob.getLookControl().setLookAt(this.targetPlayer, 30.0F, 30.0F);
        if (this.mob.tickCount % 10 == 0) {
            moveToTarget();
        }
    }

    @Override
    public void stop() {
        this.targetPlayer = null;
        this.navigation.stop();
    }

    private void moveToTarget() {
        if (this.targetPlayer != null) {
            this.navigation.moveTo(this.targetPlayer, this.speedModifier);
        }
    }
}

package com.example.supiki_mod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SpikiEntity extends PathfinderMob {
    public SpikiEntity(EntityType<? extends SpikiEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        // Basic survival behavior
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // Friendly random movement
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        // Look at nearby players to feel more "friendly"
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));

        // Randomly look around
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    /**
     * Base attributes for Spiki entity.
     * Remember to register this with the Forge attributes event.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes();
    }
}

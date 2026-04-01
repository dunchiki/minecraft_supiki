package com.example.supiki_mod.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SupikiEntity extends PathfinderMob {
    // Enable via JVM option: -Dsupiki_mod.debugVisuals=true
    private static final boolean DEBUG_VISUALS = Boolean.getBoolean("supiki_mod.debugVisuals");

    public SupikiEntity(EntityType<? extends SupikiEntity> type, Level level) {
        super(type, level);

        if (!level.isClientSide() && DEBUG_VISUALS) {
            if (this.getCustomName() == null) {
                this.setCustomName(Component.literal("Supiki"));
            }
            this.setCustomNameVisible(true);
        }
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

    @Override
    public boolean isCurrentlyGlowing() {
        return DEBUG_VISUALS || super.isCurrentlyGlowing();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2D);
    }
}

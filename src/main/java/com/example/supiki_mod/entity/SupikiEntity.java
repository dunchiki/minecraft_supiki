package com.example.supiki_mod.entity;

import com.example.supiki_mod.entity.ai.SupikiFollowPlayerGoal;
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
    private static final boolean DEBUG_VISUALS = false;

    public SupikiEntity(EntityType<? extends SupikiEntity> type, Level level) {
        super(type, level);

        if (level.isClientSide() && DEBUG_VISUALS) {
            this.setGlowingTag(true);
            if (this.getCustomName() == null) {
                this.setCustomName(Component.literal("Supiki"));
            }
            this.setCustomNameVisible(true);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SupikiFollowPlayerGoal(this, 1.1D, 8.0F, 2.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 20.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FOLLOW_RANGE, 16.0D);
    }
}

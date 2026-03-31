package com.example.supiki_mod.entity;

import com.example.supiki_mod.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.level.Level;

/**
 * Spiki is currently a Cow-based mob with minimal tweaks.
 * Future custom AI hooks are separated so additional goals can be added safely.
 */
public class SpikiEntity extends Cow {
    private static final Component DEBUG_NAME = Component.literal("Spiki");

    public SpikiEntity(EntityType<? extends Cow> entityType, Level level) {
        super(entityType, level);

        // Show a nameplate only when the debug-name config option is enabled.
        if (Config.showDebugName) {
            setCustomName(DEBUG_NAME);
            setCustomNameVisible(true);
        }
    }

    @Override
    protected void registerGoals() {
        initDefaultGoals();
        initCustomGoals();
    }

    /**
     * Keeps vanilla Cow AI as the default baseline behavior.
     */
    protected void initDefaultGoals() {
        super.registerGoals();
    }

    /**
     * Extension point for future Spiki-only AI goals.
     */
    protected void initCustomGoals() {
        // No-op for now.
    }

    @Override
    public boolean isCurrentlyGlowing() {
        return true;
    }
}

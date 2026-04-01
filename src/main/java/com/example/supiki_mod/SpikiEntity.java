package com.example.supiki_mod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.level.Level;

public class SpikiEntity extends Cow {
    public SpikiEntity(EntityType<? extends Cow> type, Level level) {
        super(type, level);
    }
}

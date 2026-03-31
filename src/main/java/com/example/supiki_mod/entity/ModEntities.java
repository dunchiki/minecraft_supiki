package com.example.supiki_mod.entity;

import com.example.supiki_mod.SupikiMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Central entity registration point.
 */
public final class ModEntities {
    private ModEntities() {
    }

    public static final float SPIKI_WIDTH = 0.45F;
    public static final float SPIKI_HEIGHT = 0.7F;

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SupikiMod.MODID);

    public static final RegistryObject<EntityType<SpikiEntity>> SPIKI = ENTITY_TYPES.register("spiki", () ->
        EntityType.Builder.of(SpikiEntity::new, MobCategory.CREATURE)
            .sized(SPIKI_WIDTH, SPIKI_HEIGHT)
            .build(SupikiMod.MODID + ":spiki")
    );
}

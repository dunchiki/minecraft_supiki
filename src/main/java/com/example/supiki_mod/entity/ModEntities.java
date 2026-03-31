package com.example.supiki_mod.entity;

import com.example.supiki_mod.SupikiMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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

    private static final String SPIKI_ID = "spiki";

    private static final ResourceKey<EntityType<?>> SPIKI_KEY =
        ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(SupikiMod.MODID, SPIKI_ID));

    public static final RegistryObject<EntityType<SpikiEntity>> SPIKI = ENTITY_TYPES.register(SPIKI_ID, () ->
        EntityType.Builder.<SpikiEntity>of(SpikiEntity::new, MobCategory.CREATURE)
            .sized(SPIKI_WIDTH, SPIKI_HEIGHT)
            .build(SPIKI_KEY)
    );
}

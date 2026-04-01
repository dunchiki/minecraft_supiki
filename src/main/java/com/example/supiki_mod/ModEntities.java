package com.example.supiki_mod.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SupikiMod.MODID);

    public static final RegistryObject<EntityType<SpikiEntity>> SPIKI = ENTITY_TYPES.register("spiki",
        () -> EntityType.Builder.of(SpikiEntity::new, MobCategory.CREATURE)
            .sized(0.9F, 1.4F)
            .build(ENTITY_TYPES.key("spiki"))
    );

    private ModEntities() {
    }
}

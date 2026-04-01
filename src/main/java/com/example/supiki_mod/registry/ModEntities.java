package com.example.supiki_mod.registry;

import com.example.supiki_mod.SupikiMod;
import com.example.supiki_mod.entity.SupikiEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SupikiMod.MODID);

    public static final RegistryObject<EntityType<SupikiEntity>> SUPIKI = ENTITY_TYPES.register("supiki",
        () -> EntityType.Builder.of(SupikiEntity::new, MobCategory.CREATURE)
            .sized(0.9F, 1.4F)
            .build(ENTITY_TYPES.key("supiki"))
    );

    private ModEntities() {
    }
}

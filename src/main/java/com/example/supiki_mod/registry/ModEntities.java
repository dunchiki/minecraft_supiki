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
            .sized(0.45F, 0.7F) // 牛(0.9 x 1.4)の半分。SupikiRenderer#scale() の 0.5F と対応
            .build(ENTITY_TYPES.key("supiki"))
    );

    private ModEntities() {
    }
}

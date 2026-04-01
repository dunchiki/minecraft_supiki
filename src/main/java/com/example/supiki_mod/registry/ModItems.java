package com.example.supiki_mod.registry;

import com.example.supiki_mod.SupikiMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SupikiMod.MODID);

    public static final RegistryObject<Item> SUPIKI_SPAWN_EGG = ITEMS.register("supiki_spawn_egg",
        () -> new SpawnEggItem(
            new Item.Properties()
                .setId(ITEMS.key("supiki_spawn_egg"))
                .spawnEgg(ModEntities.SUPIKI.get())
        )
    );

    private ModItems() {
    }
}

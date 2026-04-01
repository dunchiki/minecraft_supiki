package com.example.supiki_mod;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = SupikiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
        .comment("Whether to log the dirt block on common setup")
        .define("logDirtBlock", true);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
        .comment("A list of item IDs to resolve and log on common setup.")
        .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean logDirtBlock;
    public static Set<Item> items = Set.of();

    private static boolean validateItemName(final Object obj) {
        if (!(obj instanceof final String itemName)) return false;
        var id = Identifier.tryParse(itemName);
        return id != null && ForgeRegistries.ITEMS.containsKey(id);
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        logDirtBlock = LOG_DIRT_BLOCK.get();

        items = ITEM_STRINGS.get().stream()
            .map(Identifier::tryParse)
            .filter(Objects::nonNull)
            .map(ForgeRegistries.ITEMS::getValue)
            .filter(Objects::nonNull)
            .collect(Collectors.toUnmodifiableSet());
    }
}

package com.example.supiki_mod;

import com.example.supiki_mod.entity.SupikiEntity;
import com.example.supiki_mod.registry.ModEntities;
import com.example.supiki_mod.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(SupikiMod.MODID)
public final class SupikiMod {
    public static final String MODID = "supiki_mod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public SupikiMod(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);
        EntityAttributeCreationEvent.getBus(modBusGroup).addListener(SupikiMod::registerAttributes);
        BuildCreativeModeTabContentsEvent.BUS.addListener(SupikiMod::addCreative);

        ModEntities.ENTITY_TYPES.register(modBusGroup);
        ModItems.ITEMS.register(modBusGroup);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Supiki Mod common setup initialized");

        if (Config.logDirtBlock) {
            LOGGER.info("Config item count: {}", Config.items.size());
        }
    }

    private static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SUPIKI.get(), SupikiEntity.createAttributes().build());
    }

    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.SUPIKI_SPAWN_EGG);
        }
    }
}

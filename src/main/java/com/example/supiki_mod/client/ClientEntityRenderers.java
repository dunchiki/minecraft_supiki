package com.example.supiki_mod.client;

import com.example.supiki_mod.SupikiMod;
import com.example.supiki_mod.entity.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-only renderer registration.
 */
@Mod.EventBusSubscriber(modid = SupikiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEntityRenderers {
    private ClientEntityRenderers() {
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SPIKI.get(), SpikiRenderer::new);
    }
}

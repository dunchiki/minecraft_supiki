package com.example.supiki_mod.client;

import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * Renderer for Spiki – delegates entirely to {@link CowRenderer} so Spiki
 * inherits the vanilla cow model, variants and texture handling.
 * Override {@code getTextureLocation} here when a custom texture is added.
 */
public class SpikiRenderer extends CowRenderer {
    public SpikiRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
}

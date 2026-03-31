package com.example.supiki_mod.client;

import com.example.supiki_mod.entity.SpikiEntity;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer intentionally mirrors Cow visuals for now.
 * Texture lookup is split so it can be overridden/replaced later.
 */
public class SpikiRenderer extends MobRenderer<SpikiEntity, CowModel<SpikiEntity>> {
    private static final ResourceLocation COW_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/cow/cow.png");

    public SpikiRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<>(context.bakeLayer(ModelLayers.COW)), 0.35F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpikiEntity entity) {
        return getDefaultTexture();
    }

    protected ResourceLocation getDefaultTexture() {
        return COW_TEXTURE;
    }
}

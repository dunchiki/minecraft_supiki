package com.example.supiki_mod.client;

import com.example.supiki_mod.entity.SupikiEntity;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SupikiRenderer extends MobRenderer<SupikiEntity, CowModel<SupikiEntity>> {

    public SupikiRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<>(context.bakeLayer(ModelLayers.COW)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(SupikiEntity entity) {
        return new ResourceLocation("minecraft", "textures/entity/cow/cow.png");
    }
}

package com.example.supiki_mod.client.renderer.entity;

import com.example.supiki_mod.entity.SupikiEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class SupikiRenderer extends MobRenderer<SupikiEntity, LivingEntityRenderState, CowModel> {

    private static final Identifier COW_TEXTURE = Identifier.withDefaultNamespace("textures/entity/cow/cow.png");

    public SupikiRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel(context.bakeLayer(ModelLayers.COW)), 0.7f);
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return COW_TEXTURE;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    protected void scale(LivingEntityRenderState state, PoseStack poseStack) {
        poseStack.scale(0.5F, 0.5F, 0.5F); // 牛の半分。ModEntities の sized(0.45, 0.7) と対応
        super.scale(state, poseStack);
    }
}

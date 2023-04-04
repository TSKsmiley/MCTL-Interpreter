package net.abaaja.mctl.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.abaaja.mctl.MCTL;
import net.abaaja.mctl.entity.custom.TurtleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TurtleRenderer extends GeoEntityRenderer<TurtleEntity> {

    public TurtleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TurtleModel());
        this.shadowRadius = 0.6F; //change 0.6 to the desired shadow size.
    }

    @Override
    public ResourceLocation getTextureLocation(TurtleEntity animatable) {
        return new ResourceLocation(MCTL.MOD_ID, "textures/entity/turtle_entity_texture.png");
    }

    @Override
    public RenderType getRenderType(TurtleEntity animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource,
                                    @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        // stack.scale(0.5F, 0.5F, 0.5F); //change the scale of the entity
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}

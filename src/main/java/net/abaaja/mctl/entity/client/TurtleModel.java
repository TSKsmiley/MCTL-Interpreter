package net.abaaja.mctl.entity.client;

import net.abaaja.mctl.MCTL;
import net.abaaja.mctl.entity.custom.TurtleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TurtleModel extends AnimatedGeoModel<TurtleEntity> {

    @Override
    public ResourceLocation getModelResource(TurtleEntity object) {
        return new ResourceLocation(MCTL.MOD_ID, "geo/turtle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TurtleEntity object) {
        return new ResourceLocation(MCTL.MOD_ID, "textures/entity/turtle_entity_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TurtleEntity animatable) {
        return new ResourceLocation(MCTL.MOD_ID, "animations/turtle.animation.json");
    }
}

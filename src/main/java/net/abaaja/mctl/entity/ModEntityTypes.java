package net.abaaja.mctl.entity;

import net.abaaja.mctl.MCTL;
import net.abaaja.mctl.entity.custom.TurtleEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MCTL.MOD_ID);

    public static final RegistryObject<EntityType<TurtleEntity>> TURTLE =
                ENTITY_TYPES.register("turtle",
                        () -> EntityType.Builder.of(TurtleEntity::new, MobCategory.MISC)
                                .sized(.6f,.7f)
                                .build(new ResourceLocation(MCTL.MOD_ID, "turtle").toString()));

    public static void register(IEventBus EventBus){
        ENTITY_TYPES.register(EventBus);
    }
}

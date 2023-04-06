package net.abaaja.mctl.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import static software.bernie.geckolib3.util.GeckoLibUtil.createFactory;


public class TurtleEntity extends Mob implements IAnimatable {
    private AnimationFactory factory = createFactory(this);
    public TurtleEntity(EntityType<? extends Mob> entity, Level level) {

        super(entity, level);
    }

    public static AttributeSupplier setAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 99999D)
                .add(Attributes.MOVEMENT_SPEED, .4f).build();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        // skip custom logic if the player is not using the main hand
        if (interactionHand != InteractionHand.MAIN_HAND) return super.mobInteract(player, interactionHand);
        // TODO: Remove testing code

        player.sendSystemMessage(Component.literal("test"));
        if (player.isShiftKeyDown()){
            this.turnLeft();
        } else {
            this.turnRight();
        }
        return super.mobInteract(player, interactionHand);
    }


    private <T extends IAnimatable> PlayState predicate(AnimationEvent<T> Event){
        if (Event.isMoving()){
            Event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.turtle.walk"));
            return PlayState.CONTINUE;
        }

        Event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.turtle.idle"));
        return PlayState.CONTINUE;
    }
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    protected void checkFallDamage(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
        // do nothing
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return false;
    }

    // make the entity snap to the center of the block
    @Override
    public void travel(Vec3 movement) {
        if (this.isEffectiveAi()) {
            // get the closest block center
            // round to closes .5 x and z coordinate
            double x = Math.round(this.getX() - 0.5) + 0.5;
            double y = this.getY();
            double z = Math.round(this.getZ() - 0.5) + 0.5;

            this.setPos(x,y,z);
        }
    }

    // make the entity immovable by pistons


    @Override
    public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand interactionHand) {
        return super.interactAt(player, vec3, interactionHand);
    }

    public boolean moveForward(){
        // see if the block is air
        if (this.level.isEmptyBlock(this.blockPosition().relative(this.getMotionDirection()))) {
            // move the entity forward 1 block

            double x = this.getX() + this.getMotionDirection().getStepX();
            double y = this.getY();
            double z = this.getZ() + this.getMotionDirection().getStepZ();

            this.setPos(x, y, z);
            return true;
        }
        return false;
    }

    public void turnRight(){
        // turn the entity right
        this.yBodyRot += 90;

    }

    public void turnLeft(){
        // turn the entity left 90 degrees
        this.yBodyRot -= 90;

    }

    // a function that makes the entity break the block in front of it
    public void breakBlockInFront() {
        // get the block in front of the entity
        BlockPos blockPos = this.blockPosition().relative(this.getMotionDirection());
        // break the block
        this.level.destroyBlock(blockPos, true);
    }


}

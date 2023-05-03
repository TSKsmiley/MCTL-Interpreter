package net.abaaja.mctl.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static software.bernie.geckolib3.util.GeckoLibUtil.createFactory;


public class TurtleEntity extends Mob implements IAnimatable {
    private final AnimationFactory factory = createFactory(this);

    private Player playerDelete;
    private int timerDelete = -1;

    public TurtleEntity(EntityType<? extends Mob> entity, Level level) {

        super(entity, level);
    }

    public static AttributeSupplier setAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 99999D)
                .add(Attributes.MOVEMENT_SPEED, .4f)
                .build();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        // skip custom logic if the player is not using the main hand
        if (interactionHand != InteractionHand.MAIN_HAND) return super.mobInteract(player, interactionHand);
        // TODO: Remove testing code

        player.sendSystemMessage(Component.literal("test"));
        if (player.isShiftKeyDown()){
            placeFront("minecraft:water");
        } else {
            placeFront(blockUnder());
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
    public void baseTick() {
        if(isCorrectPlayer() && isTimerActive()){
            delete();
        }

        if (this.lastHurtByPlayer != null && !isTimerActive()){
            lastHurtByPlayer.sendSystemMessage(Component.literal("Are you sure you want to stop this turtle?"));
            playerDelete = lastHurtByPlayer;
            startTimer(120);
            lastHurtByPlayer = null;
        }

        timerTick();

        super.baseTick();
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
    // make the entity rotation snap to 90 degree increments
    @Override
    public void setYBodyRot(float p_21333_) {
        // round to closest 90 degree increment
        float angle = Math.round(p_21333_ / 90) * 90;
        super.setYBodyRot(angle);
    }


    @Override
    public void setYRot(float rotation){
        // round to closest 90 degree increment
        float angle = Math.round(rotation / 90) * 90;
        super.setYRot(angle);
    }

    private boolean isCorrectPlayer(){
        return (lastHurtByPlayer != null && playerDelete == lastHurtByPlayer);
    }

    private void startTimer(int ticks){
        timerDelete = ticks;
    }

    private boolean isTimerActive(){
        return timerDelete != -1;
    }

    private void timerTick(){
        if (timerDelete != -1)
            timerDelete--;
    }

    private void delete(){
        this.kill();
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

    public boolean moveBackward(){
        // see if the block is air
        if (this.level.isEmptyBlock(this.blockPosition().relative(this.getMotionDirection().getOpposite()))) {
            // move the entity forward 1 block

            double x = this.getX() - this.getMotionDirection().getStepX();
            double y = this.getY();
            double z = this.getZ() - this.getMotionDirection().getStepZ();

            this.setPos(x, y, z);
            return true;
        }
        return false;
    }

    public boolean moveUp(){
        // see if the block is air
        if (this.level.isEmptyBlock(this.blockPosition().above())) {
            // move the entity forward 1 block

            double x = this.getX();
            double y = this.getY() + 1;
            double z = this.getZ();

            this.setPos(x, y, z);
            return true;
        }
        return false;
    }

    public boolean moveDown(){
        // see if the block is air
        if (this.level.isEmptyBlock(this.blockPosition().below())) {
            // move the entity forward 1 block

            double x = this.getX();
            double y = this.getY() - 1;
            double z = this.getZ();

            this.setPos(x, y, z);
            return true;
        }
        return false;
    }

    public void turnRight(){
        // turn the entity right
        this.yBodyRot += 90;
        this.yHeadRot += 90;
        this.yRotO += 90;

    }

    public void turnLeft(){
        // turn the entity left 90 degrees
        this.yBodyRot -= 90;
        this.yHeadRot -= 90;
        this.yRotO -= 90;
    }


    public void breakFront() {
        BlockPos blockPos = this.blockPosition().relative(this.getMotionDirection());
        this.level.destroyBlock(blockPos, true);
    }

    public void breakUnder() {
        BlockPos blockPos = this.blockPosition().below();
        this.level.destroyBlock(blockPos, true);
    }

    public void breakAbove() {
        BlockPos blockPos = this.blockPosition().above();
        this.level.destroyBlock(blockPos, true);
    }

    public void placeFront(String blockId) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockId));
        BlockPos blockPos = this.blockPosition().relative(this.getMotionDirection());
        assert block != null;
        this.level.setBlockAndUpdate(blockPos, block.defaultBlockState());
    }

    public void placeUnder(String blockId) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockId));
        BlockPos blockPos = this.blockPosition().below();
        assert block != null;
        this.level.setBlockAndUpdate(blockPos, block.defaultBlockState());
    }

    public void placeAbove(String blockId) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockId));
        BlockPos blockPos = this.blockPosition().above();
        assert block != null;
        this.level.setBlockAndUpdate(blockPos, block.defaultBlockState());
    }


    public String blockFront(){
        return getBlockId(blockPosition().relative(this.getMotionDirection()));
    }

    public String blockUnder(){
        return getBlockId(blockPosition().below());
    }

    public String blockAbove() {;
        return getBlockId(blockPosition().above());
    }

    private String getBlockId(BlockPos pos){
        return this.level.getBlockState(pos).getBlock().getDescriptionId();
    }

    public boolean isBlockFront(Block block){
        return blockFront().equals(block.getDescriptionId());
    }

}

package me.cybersteve.equiplib.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.cybersteve.equiplib.item.handheld.base.IEffectHandHeldItem;
import me.cybersteve.equiplib.util.CommonHooks;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private void applyEffectsOnHitForSelf(Entity target, CallbackInfo ci, @Local(ordinal = 4) boolean flag3,
                                          @Local DamageSource source, @Local(ordinal = 0) float f) {
        if (flag3 && this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof IEffectHandHeldItem item) {
            CommonHooks.addEffects(this, item.getEffectsForSelfWhenHit(source, f), this);
        }
    }

    @Inject(method = "attack", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private void applyEffectsOnHitForTarget(Entity target, CallbackInfo ci, @Local(ordinal = 4) boolean flag3,
                                           @Local DamageSource source, @Local(ordinal = 0) float f) {
        if (flag3 && target instanceof LivingEntity entity &&
                this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof IEffectHandHeldItem item) {
            CommonHooks.addEffects(entity, item.getEffectsForTargetWhenHit(source, f), this);
        }
    }
}
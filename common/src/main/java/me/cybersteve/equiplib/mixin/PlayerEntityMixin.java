package me.cybersteve.equiplib.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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


@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean applyEffectsOnHitForSelf(Entity target, DamageSource source, float amount, Operation<Boolean> original) {
        boolean was_hit = original.call(target, source, amount);
        if (was_hit && this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof IEffectHandHeldItem item) {
            CommonHooks.addEffects(this, item.getEffectsForSelfWhenHit(source, amount), this);
        }
        return was_hit;
    }


    @WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean applyEffectsOnHitForTarget(Entity target, DamageSource source, float amount, Operation<Boolean> original) {
        boolean was_hit = original.call(target, source, amount);
        if (was_hit && this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof IEffectHandHeldItem item) {
            CommonHooks.addEffects(this, item.getEffectsForTargetWhenHit(source, amount), this);
        }
        return was_hit;
    }
}
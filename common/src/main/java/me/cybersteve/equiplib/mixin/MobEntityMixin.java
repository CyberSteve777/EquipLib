package me.cybersteve.equiplib.mixin;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.cybersteve.equiplib.item.handheld.base.IEffectHandHeldItem;
import me.cybersteve.equiplib.util.EffectListHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;



@Mixin(Mob.class)
public abstract class MobEntityMixin extends LivingEntity {

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }


    @WrapOperation(method = "doHurtTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean applyEffectsOnAttackForSelf(Entity target, DamageSource source, float amount, Operation<Boolean> original) {
        boolean wasAttacked = original.call(target, source, amount);
        if (wasAttacked && this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof IEffectHandHeldItem item) {
            EffectListHelper.addEffects(this, item.getEffectsForSelfOnAttack(source, this, amount), this);
        }
        return wasAttacked;
    }

    @WrapOperation(method = "doHurtTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean applyEffectsOnAttackForTarget(Entity target, DamageSource source, float amount, Operation<Boolean> original) {
        boolean wasAttacked = original.call(target, source, amount);
        if (wasAttacked && target instanceof LivingEntity entity &&
                this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof IEffectHandHeldItem item) {
            EffectListHelper.addEffects(entity, item.getEffectsForTargetOnAttack(source, this, amount), this);
        }
        return wasAttacked;
    }
}
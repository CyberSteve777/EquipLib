package me.cybersteve.equiplib.mixin;


import com.llamalad7.mixinextras.sugar.Local;
import me.cybersteve.equiplib.item.handheld.base.IEffectHandHeldItem;
import me.cybersteve.equiplib.util.CommonHooks;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(Mob.class)
public abstract class MobEntityMixin extends LivingEntity {

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }


    @Inject(method = "doHurtTarget", at = @At(value = "RETURN"))
    private void applyEffectsOnHitForSelf(Entity target, CallbackInfoReturnable<Boolean> cir,
                                          @Local(ordinal = 0) float f,
                                          @Local DamageSource source) {
        boolean wasAttacked = cir.getReturnValue();
        if (wasAttacked && this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof IEffectHandHeldItem item) {
            CommonHooks.addEffects(this, item.getEffectsForSelfWhenHit(source, f), this);
        }
    }

    @Inject(method = "doHurtTarget", at = @At(value = "RETURN"))
    private void applyEffectsOnHitForTarget(Entity target, CallbackInfoReturnable<Boolean> cir,
                                            @Local(ordinal = 0) float f,
                                            @Local DamageSource source) {
        boolean wasAttacked = cir.getReturnValue();
        if (wasAttacked && target instanceof LivingEntity entity &&
                this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof IEffectHandHeldItem item) {
            CommonHooks.addEffects(entity, item.getEffectsForTargetWhenHit(source, f), entity);
        }
    }
}
package me.cybersteve.equiplib.mixin;

import me.cybersteve.equiplib.item.armor.base.IEffectArmorItemExtension;
import me.cybersteve.equiplib.item.handheld.base.IEffectHandHeldItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import me.cybersteve.equiplib.util.ArmorHooks;
import me.cybersteve.equiplib.util.CommonHooks;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
        equipLib$lastWorn = null;
    }

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Unique
    private IEffectHandHeldItem equipLib$lastUsed;

    @Unique
    private IEffectArmorItemExtension equipLib$lastWorn;

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void checkItemInHand(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() &&
                (this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof IEffectHandHeldItem item)) {
            if (equipLib$lastUsed != null && !item.equals(equipLib$lastUsed)) {
                CommonHooks.removeInfiniteEffects(self,
                        equipLib$lastUsed.getEffectsWhenInHand(self));
            }
            if (CommonHooks.checkEffects(self, item.getEffectsWhenInHand(self))) {
                CommonHooks.addEffects(self, item.getEffectsWhenInHand(self));
            }
            equipLib$lastUsed = item;
        } else if (equipLib$lastUsed != null) {
            CommonHooks.removeInfiniteEffects(self,
                    equipLib$lastUsed.getEffectsWhenInHand(self));
            equipLib$lastUsed = null;
        }
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void applyArmorEffectsForSelfWhenWearing(CallbackInfo ci) {
        ItemStack head = this.getItemBySlot(EquipmentSlot.HEAD);
        LivingEntity self = (LivingEntity) (Object) this;
        if (!head.isEmpty() && head.getItem() instanceof IEffectArmorItemExtension item) {
            if (equipLib$lastWorn != null && !ArmorHooks.hasFullEffectSetArmorOn(self, item.getEffectArmorSet())) {
                CommonHooks.removeInfiniteEffects(self,
                        equipLib$lastWorn.getEffectArmorSet().getEffectsWhenWearing(self));
            }
            if (ArmorHooks.hasFullEffectSetArmorOn(self, item.getEffectArmorSet()) &&
                    CommonHooks.checkEffects(self, item.getEffectArmorSet().getEffectsWhenWearing(self))) {
                CommonHooks.addEffects(self, item.getEffectArmorSet().getEffectsWhenWearing(self));
            }
            equipLib$lastWorn = item;
        } else if (equipLib$lastWorn != null) {
            CommonHooks.removeInfiniteEffects(self, equipLib$lastWorn.getEffectArmorSet().getEffectsWhenWearing(self));
            equipLib$lastWorn = null;
        }
    }

    @Inject(method = "hurt", at = @At(value = "RETURN"))
    private void applyArmorEffectsOnHitForAttacker(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean was_damaged = cir.getReturnValue();
        LivingEntity self = (LivingEntity) (Object) this;
        if (was_damaged) {
            Entity attacker = source.getEntity();
            if (attacker instanceof LivingEntity entity) {
                ItemStack head = this.getItemBySlot(EquipmentSlot.HEAD);
                if (!head.isEmpty() && head.getItem() instanceof IEffectArmorItemExtension item &&
                        ArmorHooks.hasFullEffectSetArmorOn(self, item.getEffectArmorSet())) {
                    CommonHooks.addEffects(entity,
                            item.getEffectArmorSet().getEffectsForAttackerWhenHit(source, amount), self);
                }
            }
        }
    }

    @Inject(method = "hurt", at = @At(value = "RETURN"))
    private void applyArmorEffectsOnHitForSelf(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean was_damaged = cir.getReturnValue();
        LivingEntity self = (LivingEntity) (Object) this;
        if (was_damaged) {
            Entity attacker = source.getEntity();
            if (attacker instanceof LivingEntity) {
                ItemStack head = this.getItemBySlot(EquipmentSlot.HEAD);
                if (!head.isEmpty() && head.getItem() instanceof IEffectArmorItemExtension item &&
                        ArmorHooks.hasFullEffectSetArmorOn(self, item.getEffectArmorSet())) {
                    CommonHooks.addEffects(self,
                            item.getEffectArmorSet().getEffectsForAttackerWhenHit(source, amount), self);
                }
            }
        }
    }
}
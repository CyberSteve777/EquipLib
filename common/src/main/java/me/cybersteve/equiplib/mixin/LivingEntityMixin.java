package me.cybersteve.equiplib.mixin;

import me.cybersteve.equiplib.item.armor.base.IEffectArmorItemExtension;
import me.cybersteve.equiplib.item.handheld.base.IEffectHandHeldItem;
import me.cybersteve.equiplib.util.EffectList;
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
import me.cybersteve.equiplib.util.CommonHooks;

import java.util.HashMap;
import java.util.List;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
        equipLib$currentHandHeldEffects = EffectList.EMPTY;
        equipLib$currentEffectsBySlot = new HashMap<>(5);
    }

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Unique
    private EffectList equipLib$currentHandHeldEffects;

    @Unique
    private final HashMap<EquipmentSlot, EffectList> equipLib$currentEffectsBySlot;

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void checkItemInHand(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof IEffectHandHeldItem item) {
            EffectList newEffects = item.getEffectsWhenInHand(self);
            if (equipLib$currentHandHeldEffects.isEmpty() && !newEffects.equals(equipLib$currentHandHeldEffects)) {
                CommonHooks.removeEffects(self, equipLib$currentHandHeldEffects);
            }
            if (CommonHooks.checkEffects(self, newEffects)) {
                CommonHooks.addEffects(self, newEffects);
            }
            equipLib$currentHandHeldEffects = newEffects;
        } else if (equipLib$currentHandHeldEffects.isEmpty()) {
            CommonHooks.removeEffects(self,
                    equipLib$currentHandHeldEffects);
            equipLib$currentHandHeldEffects = EffectList.EMPTY;
        }
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void applySetArmorEffectsForSelfWhenWearing(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        for (EquipmentSlot slot: List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET,
                EquipmentSlot.BODY)) {
            ItemStack stack = this.getItemBySlot(slot);
            EffectList currentSlotEffects = equipLib$currentEffectsBySlot.getOrDefault(slot, EffectList.EMPTY);
            if (!stack.isEmpty() && stack.getItem() instanceof IEffectArmorItemExtension item) {
                EffectList newEffectsBySlot = item.getEffectArmorSet().getEffectsWhenWearing(self);
                if (!currentSlotEffects.isEmpty()) {
                    CommonHooks.removeEffects(self, currentSlotEffects);
                }
                if (CommonHooks.checkEffects(self, newEffectsBySlot)) {
                    CommonHooks.addEffects(self, newEffectsBySlot);
                }
                equipLib$currentEffectsBySlot.put(slot, newEffectsBySlot);
            } else if (!currentSlotEffects.isEmpty()) {
                CommonHooks.removeEffects(self, currentSlotEffects);
                equipLib$currentEffectsBySlot.put(slot, EffectList.EMPTY);
            }
        }
    }

    @Inject(method = "hurt", at = @At(value = "RETURN"))
    private void applySetArmorEffectsOnHitForAttacker(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean was_damaged = cir.getReturnValue();
        LivingEntity self = (LivingEntity) (Object) this;
        if (was_damaged) {
            Entity attacker = source.getEntity();
            if (attacker instanceof LivingEntity entity) {
                for (EquipmentSlot slot: List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                        EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.BODY)) {
                    ItemStack stack = this.getItemBySlot(slot);
                    if (!stack.isEmpty() && stack.getItem() instanceof IEffectArmorItemExtension item) {
                        CommonHooks.addEffects(entity,
                                item.getEffectArmorSet().getEffectsForAttackerWhenHit(source, self, amount), self);
                    }
                }
            }
        }
    }

    @Inject(method = "hurt", at = @At(value = "RETURN"))
    private void applySetArmorEffectsOnHitForSelf(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean was_damaged = cir.getReturnValue();
        LivingEntity self = (LivingEntity) (Object) this;
        if (was_damaged) {
            for (EquipmentSlot slot: List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                    EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.BODY)) {
                ItemStack stack = this.getItemBySlot(slot);
                if (!stack.isEmpty() && stack.getItem() instanceof IEffectArmorItemExtension item) {
                    CommonHooks.addEffects(self,
                            item.getEffectArmorSet().getEffectsForSelfWhenHit(source, self, amount), self);
                }
            }
        }
    }
}
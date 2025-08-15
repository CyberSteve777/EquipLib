package me.cybersteve.equiplib.item.handheld.base;

import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface IEffectHandHeldItem {
    EffectList getEffectsWhenInHand(LivingEntity entity);
    EffectList getEffectsForSelfWhenHit(DamageSource source, float amount);
    EffectList getEffectsForTargetWhenHit(DamageSource source, float amount);
}

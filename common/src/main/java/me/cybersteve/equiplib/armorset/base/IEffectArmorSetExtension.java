package me.cybersteve.equiplib.armorset.base;

import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface IEffectArmorSetExtension {
    EffectList getEffectsWhenWearing(LivingEntity entity);
    EffectList getEffectsForSelfWhenHit(DamageSource source, LivingEntity target, float amount);
    EffectList getEffectsForAttackerWhenHit(DamageSource source, LivingEntity target, float amount);
}

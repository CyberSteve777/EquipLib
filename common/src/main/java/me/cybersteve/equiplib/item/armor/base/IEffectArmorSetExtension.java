package me.cybersteve.equiplib.item.armor.base;

import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface IEffectArmorSetExtension {
    EffectList getEffectsWhenWearing(LivingEntity entity);
    EffectList getEffectsForSelfWhenHit(DamageSource source, float amount);
    EffectList getEffectsForAttackerWhenHit(DamageSource source, float amount);
}

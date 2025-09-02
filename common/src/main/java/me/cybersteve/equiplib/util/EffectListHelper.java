package me.cybersteve.equiplib.util;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

/**
 * Common helper for equipment.
 */
public class EffectListHelper {
    /**
     * Check if need to apply the effects from EffectList (to prevent flashing).
     *
     * @param entity  the entity
     * @param effects the effects
     * @return the boolean
     */
    public static boolean checkIfNeedToApply(LivingEntity entity, EffectList effects) {
        for (var effectEntry : effects.data().entrySet()) {
            Holder<MobEffect> effect = effectEntry.getKey();
            EffectMeta meta = effectEntry.getValue();
            MobEffectInstance instance = entity.getEffect(effect);
            if (instance != null) {
                return meta.duration() == MobEffectInstance.INFINITE_DURATION ||
                        instance.getAmplifier() < meta.amplifier();
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Add effects of attacker to target entity.
     *
     * @param entity   the entity
     * @param effects  the effects
     * @param attacker the attacker
     */
    public static void addEffects(LivingEntity entity, EffectList effects, LivingEntity attacker) {
        for (var effectEntry : effects.data().entrySet()) {
            Holder<MobEffect> effect = effectEntry.getKey();
            EffectMeta meta = effectEntry.getValue();
            if (!effect.value().isInstantenous()) {
                if (entity.hasEffect(effect)) {
                    MobEffectInstance instance = entity.getEffect(effect);
                    if (!instance.isInfiniteDuration() && instance.getAmplifier() < meta.amplifier()) {
                        entity.removeEffect(effect);
                    }
                }
                entity.addEffect(new MobEffectInstance(effect, meta.duration(), meta.amplifier(),
                        meta.ambient(), meta.showParticles(), meta.showIcon()), attacker);
            }
        }
    }

    /**
     * Overloaded add effects if applying effects to self. Since there's no attacker here, null is passed.
     *
     * @param entity  the entity
     * @param effects the effects
     */
    public static void addEffects(LivingEntity entity, EffectList effects) {
        addEffects(entity, effects, null);
    }

    /**
     * Remove effects from entity. Effects are passed via EffectList and each effect is only removed from entity if
     * their amplifier matches amplifier in passed EffectList
     *
     * @param entity  the entity
     * @param effects the effects
     */
    public static void removeEffects(LivingEntity entity, EffectList effects) {
        for (var effectEntry : effects.data().entrySet()) {
            Holder<MobEffect> effect = effectEntry.getKey();
            EffectMeta meta = effectEntry.getValue();
            if (effect != null && entity.hasEffect(effect) &&
                    entity.getEffect(effect).getAmplifier() == meta.amplifier()) {
                entity.removeEffect(effect);
            }
        }
    }
}

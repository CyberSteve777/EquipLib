package me.cybersteve.equiplib.armorset.base;

import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/**
 * The interface Effect armor set extension.
 */
public interface IEffectArmorSetExtension {

    /**
     * Gets effects when wearing.
     *
     * @param entity the LivingEntity that is currently wearing the ArmorSet
     * @return the effects when wearing
     */
    default EffectList getEffectsWhenWearing(LivingEntity entity) {
        return EffectList.getEmptyList();
    }

    /**
     * Gets effects for self when hit.
     *
     * @param source the DamageSource
     * @param wearer the set wearer LivingEntity
     * @param amount the amount of damage wearer is about to receive
     * @return the effects for self when hit
     */
    default EffectList getEffectsForSelfWhenHit(DamageSource source, LivingEntity wearer, float amount) {
        return EffectList.getEmptyList();
    }

    /**
     * Gets effects for attacker when hit.
     *
     * @param source the DamageSource
     * @param wearer the set wearer LivingEntity
     * @param amount the amount of damage wearer is about to receive
     * @return the effects for attacker when hit
     */
    default EffectList getEffectsForAttackerWhenHit(DamageSource source, LivingEntity wearer, float amount) {
        return EffectList.getEmptyList();
    }
}

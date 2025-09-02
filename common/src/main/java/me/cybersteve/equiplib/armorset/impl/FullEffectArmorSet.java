package me.cybersteve.equiplib.armorset.impl;

import me.cybersteve.equiplib.armorset.base.EffectArmorSet;
import me.cybersteve.equiplib.util.ArmorSetHelper;
import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Function;

/**
 * The type Full effect armor set.
 */
public class FullEffectArmorSet extends EffectArmorSet {
    /**
     * The function that gets effects when wearing full set.
     */
    protected Function<LivingEntity, EffectList> whenWearing;
    /**
     * The function that gets effects on hit for attacker.
     */
    protected TriFunction<DamageSource, LivingEntity, Float, EffectList> onHitForAttacker;
    /**
     * The function that gets effects on hit for self.
     */
    protected TriFunction<DamageSource, LivingEntity, Float, EffectList> onHitForSelf;

    /**
     * Instantiates a new Full effect armor set.
     *
     * @param id               the id
     * @param whenWearing      the when wearing
     * @param onHitForSelf     the on hit for self
     * @param onHitForAttacker the on hit for attacker
     */
    public FullEffectArmorSet(ResourceLocation id,
                              Function<LivingEntity, EffectList> whenWearing,
                              TriFunction<DamageSource, LivingEntity, Float, EffectList> onHitForSelf,
                              TriFunction<DamageSource, LivingEntity, Float, EffectList> onHitForAttacker) {
        super(id);
        this.whenWearing = whenWearing;
        this.onHitForSelf = onHitForSelf;
        this.onHitForAttacker = onHitForAttacker;
    }

    @Override
    public EffectList getEffectsWhenWearing(LivingEntity entity) {
        if (ArmorSetHelper.hasFullEffectSetArmorOn(entity, this)) {
            return whenWearing.apply(entity);
        }
        return EffectList.getEmptyList();
    }

    @Override
    public EffectList getEffectsForSelfWhenHit(DamageSource source, LivingEntity target, float amount) {
        if (ArmorSetHelper.hasFullEffectSetArmorOn(target, this)) {
            return onHitForSelf.apply(source, target, amount);
        }
        return EffectList.getEmptyList();
    }

    @Override
    public EffectList getEffectsForAttackerWhenHit(DamageSource source, LivingEntity target, float amount) {
        if (ArmorSetHelper.hasFullEffectSetArmorOn(target, this)) {
            return onHitForAttacker.apply(source, target, amount);
        }
        return EffectList.getEmptyList();
    }
}

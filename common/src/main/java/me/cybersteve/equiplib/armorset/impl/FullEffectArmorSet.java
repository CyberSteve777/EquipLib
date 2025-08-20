package me.cybersteve.equiplib.armorset.impl;

import me.cybersteve.equiplib.armorset.base.EffectArmorSet;
import me.cybersteve.equiplib.util.ArmorHooks;
import me.cybersteve.equiplib.util.EffectList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Function;

public class FullEffectArmorSet extends EffectArmorSet {
    protected Function<LivingEntity, EffectList> whenWearing;
    protected TriFunction<DamageSource, LivingEntity, Float, EffectList> onHitForAttacker;
    protected TriFunction<DamageSource, LivingEntity, Float, EffectList> onHitForSelf;

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
        if (ArmorHooks.hasFullEffectSetArmorOn(entity, this)) {
            return whenWearing.apply(entity);
        }
        return EffectList.EMPTY;
    }

    @Override
    public EffectList getEffectsForSelfWhenHit(DamageSource source, LivingEntity target, float amount) {
        if (ArmorHooks.hasFullEffectSetArmorOn(target, this)) {
            return onHitForSelf.apply(source, target, amount);
        }
        return EffectList.EMPTY;
    }

    @Override
    public EffectList getEffectsForAttackerWhenHit(DamageSource source, LivingEntity target, float amount) {
        if (ArmorHooks.hasFullEffectSetArmorOn(target, this)) {
            return onHitForAttacker.apply(source, target, amount);
        }
        return EffectList.EMPTY;
    }
}

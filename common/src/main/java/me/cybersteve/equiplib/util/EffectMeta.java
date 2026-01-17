package me.cybersteve.equiplib.util;

/**
 * Effect meta is like MobEffectInstance.Details, but a bit cut in functionality and with custom methods for creation
 */
public record EffectMeta(int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
}
package me.cybersteve.equiplib.armorset.base;

import net.minecraft.resources.ResourceLocation;

/**
 * The type Effect armor set.
 */
public abstract class EffectArmorSet implements IEffectArmorSetExtension, Comparable<EffectArmorSet> {
    /**
     * The Id.
     */
    protected ResourceLocation id;

    /**
     * Instantiates a new Effect armor set.
     *
     * @param id the id
     */
    public EffectArmorSet(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EffectArmorSet other && other.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(EffectArmorSet other) {
        return id.compareTo(other.id);
    }
}

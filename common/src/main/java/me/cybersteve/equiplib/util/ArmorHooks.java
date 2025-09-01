package me.cybersteve.equiplib.util;

import me.cybersteve.equiplib.armorset.base.EffectArmorSet;
import me.cybersteve.equiplib.item.armor.base.IEffectArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

/**
 * Armor hooks.
 */
public class ArmorHooks {
    /**
     * Has full effect set armor on boolean.
     *
     * @param entity the entity
     * @param set    the set
     * @return the boolean
     */
    public static boolean hasFullEffectSetArmorOn(LivingEntity entity, EffectArmorSet set) {
        List<EquipmentSlot> armorSlots = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET);
        return (entity.getItemBySlot(EquipmentSlot.BODY).getItem() instanceof IEffectArmorItem effectArmorItem
                && effectArmorItem.getEffectArmorSet().equals(set)) ||
                armorSlots.stream().map(entity::getItemBySlot).allMatch(
                itemStack -> (itemStack.getItem() instanceof IEffectArmorItem armorItem &&
                        armorItem.getEffectArmorSet().equals(set)));
    }
}

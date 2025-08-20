package me.cybersteve.equiplib.util;

import me.cybersteve.equiplib.armorset.base.EffectArmorSet;
import me.cybersteve.equiplib.item.armor.base.IEffectArmorItemExtension;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class ArmorHooks {
    public static boolean hasFullEffectSetArmorOn(LivingEntity entity, EffectArmorSet set) {
        List<EquipmentSlot> armorSlots = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET);
        return (entity.getItemBySlot(EquipmentSlot.BODY).getItem() instanceof IEffectArmorItemExtension effectArmorItem
                && effectArmorItem.getEffectArmorSet().equals(set)) ||
                armorSlots.stream().map(entity::getItemBySlot).allMatch(
                itemStack -> (itemStack.getItem() instanceof IEffectArmorItemExtension armorItem &&
                        armorItem.getEffectArmorSet().equals(set)));
    }
}

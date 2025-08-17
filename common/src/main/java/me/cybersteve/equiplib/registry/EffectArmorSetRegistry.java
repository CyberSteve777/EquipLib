package me.cybersteve.equiplib.registry;

import me.cybersteve.equiplib.Constants;
import me.cybersteve.equiplib.item.armor.base.EffectArmorSet;
import me.cybersteve.equiplib.registration.RegistrationProvider;
import net.minecraft.resources.ResourceLocation;

public class EffectArmorSetRegistry {
    private static final RegistrationProvider<EffectArmorSet> REGISTRY = RegistrationProvider.get(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "effect_armor_sets"),
            Constants.MOD_ID);


//    public static RegistryObject<EffectArmorSet, SimpleEffectArmorSet> EXAMPLE_SET = REGISTRY.register("example_set",
//            () -> new SimpleEffectArmorSet(new EffectArmorSet.Builder()
//                    .addArmorMaterials()
//                    .addArmorItems(),
//                    new EffectList.Builder().build(),
//                    new EffectList.Builder().build(),
//                    new EffectList.Builder().build()));


    public static void init() {
        Constants.LOGGER.info("Adding EffectArmorSet registry");
    }
}

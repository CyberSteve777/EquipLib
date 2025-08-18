package me.cybersteve.equiplib.registry;

import me.cybersteve.equiplib.Constants;
import me.cybersteve.equiplib.item.armor.base.EffectArmorSet;
import me.cybersteve.equiplib.platform.Services;
import me.cybersteve.equiplib.registration.RegistrationProvider;
import me.cybersteve.equiplib.registration.registries.RegistryFeatureType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class EffectArmorSetRegistry {
    public static final RegistrationProvider<EffectArmorSet> REGISTRY = RegistrationProvider.get(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "effect_armor_sets"),
            Constants.MOD_ID);

    public static Registry<EffectArmorSet> getRegistryForRegistration() {
        return REGISTRY.registryBuilder().withFeature(RegistryFeatureType.SYNCED).build();
    }

    public static void init() {
        Constants.LOGGER.info("Adding EffectArmorSet registry...");
        Services.REGISTRY.addCustomRegistry();
        Constants.LOGGER.info("EffectArmorSet registry was successfully added");
    }
}

package me.cybersteve.equiplib.events;

import me.cybersteve.equiplib.registry.EffectArmorSetRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import me.cybersteve.equiplib.Constants;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class RegistryEventsHandler {
    @SubscribeEvent // on the mod event bus
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(EffectArmorSetRegistry.getRegistryForRegistration());
    }
}

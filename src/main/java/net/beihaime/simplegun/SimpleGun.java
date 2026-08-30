package net.beihaime.simplegun;

import com.mojang.logging.LogUtils;
import net.beihaime.simplegun.client.GunClientEvents;
import net.beihaime.simplegun.creativeTab.ModCreativeTabs;
import net.beihaime.simplegun.events.FireEvent;
import net.beihaime.simplegun.registry.ModEntities;
import net.beihaime.simplegun.registry.ModItem;
import net.beihaime.simplegun.registry.ModSounds;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import net.beihaime.simplegun.network.ModNetwork;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SimpleGun.MOD_ID)
public class SimpleGun {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "simplegun";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public SimpleGun(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        LOGGER.info(MOD_ID+" has been initialized");

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new FireEvent());
        ModSounds.SOUNDS.register(modEventBus);
        ModItem.ITEMS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(GunClientEvents.class);

        ModNetwork.register();

        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        // Register the item to a creative tab
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}

package net.beihaime.tntgun;

import com.mojang.logging.LogUtils;
import net.beihaime.tntgun.client.GunClientEvents;
import net.beihaime.tntgun.creativeTab.ModCreativeTabs;
import net.beihaime.tntgun.events.Gun;
import net.beihaime.tntgun.registry.ModEntities;
import net.beihaime.tntgun.registry.ModItem;
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
import net.beihaime.tntgun.network.ModNetwork;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TntGun.MOD_ID)
public class TntGun {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "tntgun";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public TntGun(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        LOGGER.info(MOD_ID+" has been initialized");

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Gun());

        ModItem.ITEMS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(GunClientEvents.class);

        ModNetwork.register();

        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}

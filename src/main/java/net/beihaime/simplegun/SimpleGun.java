package net.beihaime.simplegun;

import com.mojang.logging.LogUtils;
import net.beihaime.simplegun.client.GunClientEvents;
import net.beihaime.simplegun.creativeTab.ModCreativeTabs;
import net.beihaime.simplegun.events.GunEvents;
import net.beihaime.simplegun.events.Reload;
import net.beihaime.simplegun.events.FireEvent;
import net.beihaime.simplegun.network.ModNetwork;
import net.beihaime.simplegun.registry.ModEntities;
import net.beihaime.simplegun.registry.ModItem;
import net.beihaime.simplegun.registry.ModKeys;
import net.beihaime.simplegun.registry.ModSounds;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.beihaime.simplegun.events.ReloadManager;
import org.slf4j.Logger;

@Mod(SimpleGun.MOD_ID)
public class SimpleGun {
    public static final String MOD_ID = "simplegun";
    private static final Logger LOGGER = LogUtils.getLogger();


    public SimpleGun(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        LOGGER.info(MOD_ID+" has been initialized");
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new FireEvent());
        ModSounds.SOUNDS.register(modEventBus);
        ModItem.ITEMS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(GunClientEvents.class);
        MinecraftForge.EVENT_BUS.register(new Reload());
        modEventBus.register(ModKeys.class);
        MinecraftForge.EVENT_BUS.register(ReloadManager.class);
        MinecraftForge.EVENT_BUS.register(GunEvents.class);
        ModNetwork.register();
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}

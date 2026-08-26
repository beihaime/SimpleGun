package net.beihaime.simplegun.client;

import net.beihaime.simplegun.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "simplegun",
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(
            EntityRenderersEvent.RegisterRenderers event
    ) {
        event.registerEntityRenderer(
                ModEntities.PROJECTILE_TNT.get(),
                RocketRenderer::new
        );
        event.registerEntityRenderer(
                ModEntities.BULLET.get(),
                BulletRenderer::new
        );
    }
}
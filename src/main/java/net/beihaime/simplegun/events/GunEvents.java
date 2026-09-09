package net.beihaime.simplegun.events;

import net.beihaime.simplegun.SimpleGun;
import net.beihaime.simplegun.item.GunItem;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimpleGun.MOD_ID)
public class GunEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Player player = event.player;


        if (player.level().isClientSide) {
            return;
        }

        if (player.getMainHandItem().getItem() instanceof GunItem gun) {
            gun.setEffect(player);
        }
    }
}
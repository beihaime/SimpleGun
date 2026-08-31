package net.beihaime.simplegun.events;

import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.network.FirePacket;
import net.beihaime.simplegun.network.ModNetwork;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FireEvent {

    @SubscribeEvent
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {

        Player player = event.getEntity();

        if (!player.level().isClientSide) {
            return;
        }

        if (!(player.getMainHandItem().getItem() instanceof GunItem)) {
            return;
        }

        ModNetwork.CHANNEL.sendToServer(
                new FirePacket()
        );
    }

    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {

        Player player = event.getEntity();

        if (!(player.getMainHandItem().getItem() instanceof GunItem)) {
            return;
        }

        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {

        Player player = event.getEntity();

        if (!(player.getMainHandItem().getItem() instanceof GunItem)) {
            return;
        }

        event.setCanceled(true);
    }
}
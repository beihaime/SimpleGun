package net.beihaime.simplegun.events;

import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.network.FirePacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.beihaime.simplegun.network.ModNetwork;

public class FireEvent {
    private static int holdTicks = 0;

    @SubscribeEvent
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        String playerName = player.getName().getString();
        if (event.getSide().isClient()) {
            if (player.getMainHandItem().getItem() instanceof GunItem launcher) {
                ModNetwork.CHANNEL.sendToServer(new FirePacket());
            }
        }

    }

    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        if (!(player.getMainHandItem().getItem() instanceof GunItem)) {
            return;
        }
        event.setCanceled(true);
        if (event.getSide().isClient()) {
            ModNetwork.CHANNEL.sendToServer(new FirePacket());
        }
    }

    @SubscribeEvent
    public void onAttackEntity(net.minecraftforge.event.entity.player.AttackEntityEvent event) {
        Player player = event.getEntity();
        if (!(player.getMainHandItem().getItem() instanceof GunItem)) {
            return;
        }
        event.setCanceled(true);
        if (player.level().isClientSide) {
            ModNetwork.CHANNEL.sendToServer(new FirePacket());
        }
    }
}

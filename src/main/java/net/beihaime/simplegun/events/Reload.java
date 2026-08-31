package net.beihaime.simplegun.events;

import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.network.ModNetwork;
import net.beihaime.simplegun.network.ReloadPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.beihaime.simplegun.registry.ModKeys.ChargeKey;

public class Reload {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        while (ChargeKey.get().consumeClick()) {

            Player player = Minecraft.getInstance().player;

            if (player == null) {
                return;
            }

            if (!(player.getMainHandItem().getItem() instanceof GunItem)) {
                return;
            }

            System.out.println("CLIENT: R pressed");

            ModNetwork.CHANNEL.sendToServer(
                    new ReloadPacket()
            );
        }
    }
}
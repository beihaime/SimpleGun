package net.beihaime.simplegun.events;


import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.network.ChargePacket;
import net.beihaime.simplegun.network.ModNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.beihaime.simplegun.registry.ModKeys.ChargeKey;

public class Charge {

    private int remainingTicks = 0;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (remainingTicks > 0) {

            remainingTicks--;

            while (ChargeKey.get().consumeClick()) {
            }

            return;
        }

        if (ChargeKey.get().consumeClick()) {

            Player player = Minecraft.getInstance().player;

            if (player == null) {
                return;
            }

            if (player.getMainHandItem().getItem() instanceof GunItem launcher) {

                ModNetwork.CHANNEL.sendToServer(
                        new ChargePacket()
                );

                remainingTicks = (int) launcher.getChargeTime();
            }
        }
    }
}
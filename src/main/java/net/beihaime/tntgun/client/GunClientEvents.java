package net.beihaime.tntgun.client;

import net.beihaime.tntgun.item.GunItem;
import net.beihaime.tntgun.network.FirePacket;
import net.beihaime.tntgun.network.ModNetwork;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GunClientEvents {
    private static boolean wasAttackDown = false;

    @SubscribeEvent
    public static void onFovModifier(ViewportEvent.ComputeFov event) {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        if (minecraft.player.isUsingItem()
                && minecraft.player.getUseItem().getItem() instanceof GunItem) {

            event.setFOV(event.getFOV() * 0.25);
        }
    }
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) {
            return;
        }

        if (!(mc.player.getMainHandItem().getItem() instanceof GunItem gun)) {
            wasAttackDown = false;
            return;
        }

        boolean attackDown = mc.options.keyAttack.isDown();


        if (attackDown && !wasAttackDown) {

            if (!mc.player.getCooldowns().isOnCooldown(gun)) {
                ModNetwork.CHANNEL.sendToServer(new FirePacket());
            }
        }

        wasAttackDown = attackDown;
    }
}
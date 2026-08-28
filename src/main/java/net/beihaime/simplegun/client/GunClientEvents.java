package net.beihaime.simplegun.client;

import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.network.FirePacket;
import net.beihaime.simplegun.network.ModNetwork;
import net.beihaime.simplegun.registry.ModItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GunClientEvents {

    private static double originalSensitivity;
    private static boolean aiming = false;
    private static boolean wasAttackDown = false;

    @SubscribeEvent
    public static void onFovModifier(ViewportEvent.ComputeFov event) {
            Minecraft mc = Minecraft.getInstance();

            if (mc.player == null) {
                return;
            }

            if (mc.player.isUsingItem()
                    && mc.player.getUseItem().getItem() instanceof GunItem launcher
                    && launcher.canAim()) {

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

        boolean isAiming = mc.player.isUsingItem()
                && mc.player.getUseItem().getItem() instanceof GunItem;

        if (isAiming && !aiming) {
            originalSensitivity = mc.options.sensitivity().get();
            mc.options.sensitivity().set(originalSensitivity * 0.25);
            aiming = true;
        }
        if (!isAiming && aiming) {
            mc.options.sensitivity().set(originalSensitivity);
            aiming = false;
        }

        if (!(mc.player.getMainHandItem().getItem() instanceof GunItem gun)) {
            wasAttackDown = false;
            return;
        }

        boolean attackDown = mc.options.keyAttack.isDown();
        boolean shouldFire = gun.isAutomatic()
                ? attackDown
                : (attackDown && !wasAttackDown);

        if (shouldFire && !mc.player.getCooldowns().isOnCooldown(gun)) {
            ModNetwork.CHANNEL.sendToServer(new FirePacket());
        }
        wasAttackDown = attackDown;
    }


}
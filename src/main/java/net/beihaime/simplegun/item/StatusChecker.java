package net.beihaime.simplegun.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StatusChecker {

    public static boolean isOnCoolDown(Player player) {
        return player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem());
    }

    public static boolean hasAmmo(Player player) {
        if (player.isCreative()) {
            return true;
        }
        if (!(player.getMainHandItem().getItem() instanceof GunItem gun)) {
            return false;
        }
        Item ammo = gun.getAmmo();
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ammo)) {
                return true;
            }
        }
        return false;
    }

    public static void consumeAmmo(Player player) {
        if (player.isCreative()) {
            return;
        }
        if (!(player.getMainHandItem().getItem() instanceof GunItem gun)) {
            return;
        }
        Item ammo = gun.getAmmo();
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ammo)) {
                stack.shrink(1);
                return;
            }
        }
    }
}
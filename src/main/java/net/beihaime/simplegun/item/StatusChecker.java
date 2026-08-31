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

    public static boolean hasMagazine(Player player) {

        if (!(player.getMainHandItem().getItem() instanceof GunItem gun)) {
            return false;
        }

        ItemStack gunStack = player.getMainHandItem();

        return gun.getMagazineAmmo(gunStack) > 0;
    }

    public static int consumeAmmo(Player player, int amount) {

        if (player.isCreative()) {
            return amount;
        }

        if (!(player.getMainHandItem().getItem() instanceof GunItem gun)) {
            return 0;
        }

        Item ammo = gun.getAmmo();
        int consumed = 0;

        for (ItemStack stack : player.getInventory().items) {

            if (!stack.is(ammo)) {
                continue;
            }

            int consume = Math.min(
                    amount - consumed,
                    stack.getCount()
            );

            stack.shrink(consume);
            consumed += consume;

            if (consumed >= amount) {
                break;
            }
        }

        return consumed;
    }

    public static int getReserveAmmo(Player player) {
        if (!(player.getMainHandItem().getItem() instanceof GunItem gun)) {
            return 0;
        }

        Item ammo = gun.getAmmo();
        int count = 0;

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ammo)) {
                count += stack.getCount();
            }
        }

        return count;
    }
}
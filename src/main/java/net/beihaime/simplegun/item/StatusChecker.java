package net.beihaime.simplegun.item;

import net.beihaime.simplegun.registry.ModItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class StatusChecker {
    public static boolean isOnCoolDown(Player player) {
        if (player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem())) {
            return true;
        }
        return false;
    }
    public static boolean hasAmmo(Player player) {
        if (player.isCreative()) {
            return true;
        }
        for (ItemStack itemStack : player.getInventory().items) {
            if (player.getMainHandItem().getItem() == ModItem.RPG.get()
                    && itemStack.is(ModItem.RPG_PROJECTILE.get()))  {
                return true;
            } else if (player.getMainHandItem().getItem() == ModItem.PISTOL.get()
                    && itemStack.is(ModItem.BULLET.get())) {
                return true;
            }
        }
        return false;
    }
    public static void consumeRocket(Player player) {
        if (!player.isCreative()) {
            for (ItemStack stack : player.getInventory().items) {
                if (stack.is(ModItem.RPG_PROJECTILE.get())) {
                    stack.shrink(1);
                    break;
                }
            }
        }
    }
}

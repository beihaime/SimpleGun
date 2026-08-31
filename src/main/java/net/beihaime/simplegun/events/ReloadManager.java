package net.beihaime.simplegun.events;

import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.item.StatusChecker;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class ReloadManager {

    private static final Map<UUID, ReloadState> RELOAD_TIMERS =
            new HashMap<>();

    public static void start(
            ServerPlayer player,
            ItemStack stack,
            int ticks
    ) {
        RELOAD_TIMERS.put(
                player.getUUID(),
                new ReloadState(stack, ticks)
        );
    }

    public static boolean isReloading(ServerPlayer player) {
        return RELOAD_TIMERS.containsKey(player.getUUID());
    }

    public static int getRemainingTicks(ServerPlayer player) {

        ReloadState state =
                RELOAD_TIMERS.get(player.getUUID());

        if (state == null) {
            return 0;
        }

        return state.remainingTicks;
    }

    @SubscribeEvent
    public static void onServerTick(
            TickEvent.ServerTickEvent event
    ) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Iterator<Map.Entry<UUID, ReloadState>> iterator =
                RELOAD_TIMERS.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<UUID, ReloadState> entry =
                    iterator.next();

            ServerPlayer player =
                    event.getServer()
                            .getPlayerList()
                            .getPlayer(entry.getKey());

            // Remove the reload state if the player is offline
            if (player == null) {
                iterator.remove();
                continue;
            }

            ReloadState state = entry.getValue();

            ItemStack currentStack =
                    player.getMainHandItem();

            // Cancel reload when switching to another ItemStack
            if (currentStack != state.stack) {
                iterator.remove();
                continue;
            }
            // Count down on the server
            state.remainingTicks--;

            // Reload finished
            if (state.remainingTicks <= 0) {

                finishReload(
                        player,
                        currentStack
                );

                iterator.remove();

                System.out.println("Reload finished");
            }
        }
    }

    private static void finishReload(
            ServerPlayer player,
            ItemStack gunStack
    ) {

        if (!(gunStack.getItem() instanceof GunItem gun)) {
            return;
        }

        // Get current magazine ammo
        int currentAmmo =
                gun.getMagazineAmmo(gunStack);

        // Get magazine capacity
        int magazineSize =
                gun.getMagazineSize();

        // Calculate required ammo
        int needed =
                magazineSize - currentAmmo;

        if (needed <= 0) {
            return;
        }

        // Get reserve ammo
        int reserveAmmo =
                StatusChecker.getReserveAmmo(player);

        if (player.isCreative()) {
            gun.setMagazineAmmo(
                    gunStack,
                    magazineSize
            );
            return;
        }

        if (reserveAmmo <= 0) {
            return;
        }

        // Calculate the actual reload amount
        int reloadAmount =
                Math.min(
                        needed,
                        reserveAmmo
                );

        // Remove ammo from the inventory
        int consumed =
                StatusChecker.consumeAmmo(
                        player,
                        reloadAmount
                );

        // Add ammo to the magazine
        gun.setMagazineAmmo(
                gunStack,
                currentAmmo + consumed
        );

        System.out.println(
                "Reload: "
                        + currentAmmo
                        + " -> "
                        + (currentAmmo + consumed)
                        + " | Reserve: "
                        + (reserveAmmo - consumed)
        );
    }

    private static class ReloadState {

        private final ItemStack stack;
        private int remainingTicks;

        private ReloadState(
                ItemStack stack,
                int remainingTicks
        ) {
            this.stack = stack;
            this.remainingTicks = remainingTicks;
        }
    }
}
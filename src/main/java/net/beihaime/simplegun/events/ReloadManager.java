package net.beihaime.simplegun.events;

import net.beihaime.simplegun.item.GunItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class ReloadManager {

    private static final Map<UUID, ReloadState> RELOAD_TIMERS = new HashMap<>();

    public static void start(ServerPlayer player, ItemStack stack, int ticks) {
        RELOAD_TIMERS.put(
                player.getUUID(),
                new ReloadState(stack, ticks)
        );
    }

    public static boolean isReloading(Player player) {
        return RELOAD_TIMERS.containsKey(player.getUUID());
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Iterator<Map.Entry<UUID, ReloadState>> iterator =
                RELOAD_TIMERS.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<UUID, ReloadState> entry = iterator.next();

            UUID uuid = entry.getKey();
            ReloadState state = entry.getValue();

            ServerPlayer player =
                    event.getServer()
                            .getPlayerList()
                            .getPlayer(uuid);

            if (player == null) {
                iterator.remove();
                continue;
            }

            ItemStack currentStack = player.getMainHandItem();

            if (currentStack != state.stack) {


                iterator.remove();

                continue;
            }

            state.remainingTicks--;


            if (state.remainingTicks <= 0) {

                iterator.remove();


            }
        }
    }

    private static class ReloadState {

        private final ItemStack stack;
        private int remainingTicks;

        private ReloadState(ItemStack stack, int remainingTicks) {
            this.stack = stack;
            this.remainingTicks = remainingTicks;
        }
    }
}
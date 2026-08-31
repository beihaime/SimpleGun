package net.beihaime.simplegun.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class ReloadManager {

    private static final Map<UUID, Integer> RELOAD_TIMERS = new HashMap<>();

    public static void start(ServerPlayer player, int ticks) {
        RELOAD_TIMERS.put(player.getUUID(), ticks);
    }

    public static boolean isReloading(ServerPlayer player) {
        return RELOAD_TIMERS.containsKey(player.getUUID());
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Iterator<Map.Entry<UUID, Integer>> iterator =
                RELOAD_TIMERS.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<UUID, Integer> entry = iterator.next();

            int remaining = entry.getValue() - 1;

            if (remaining <= 0) {

                iterator.remove();

                System.out.println("Reload finished");

            } else {

                entry.setValue(remaining);
            }
        }
    }
}
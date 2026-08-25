package net.beihaime.tntgun.events;

import net.beihaime.tntgun.item.LauncherItem;
import net.beihaime.tntgun.registry.ModItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class PlayerEvents {
    @SubscribeEvent
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        String playerName = player.getName().getString();
        if (player.getMainHandItem().getItem() instanceof LauncherItem launcher) {
            player.sendSystemMessage(Component.literal(playerName + "Fire the RPG"));
            player.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.0F);

            launcher.fire(player);

        }
    }
}

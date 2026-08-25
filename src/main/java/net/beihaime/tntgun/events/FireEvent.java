package net.beihaime.tntgun.events;

import net.beihaime.tntgun.item.GunItem;
import net.beihaime.tntgun.network.FirePacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.beihaime.tntgun.network.ModNetwork;
import org.spongepowered.asm.mixin.MixinEnvironment;

public class FireEvent {
    @SubscribeEvent
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        String playerName = player.getName().getString();
        if (event.getSide().isClient()) {
            if (player.getMainHandItem().getItem() instanceof GunItem launcher) {
                ModNetwork.CHANNEL.sendToServer(new FirePacket());
            }
        }

    }
    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        if (event.getSide().isClient()) {
            if (player.getMainHandItem().getItem() instanceof GunItem launcher) {
                event.setCanceled(true);
                ModNetwork.CHANNEL.sendToServer(new FirePacket());
            }
        }

    }
}

package net.beihaime.tntgun.network;

import net.beihaime.tntgun.item.StatusChecker;
import net.beihaime.tntgun.item.GunItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.network.NetworkEvent;
import net.beihaime.tntgun.events.Fire;

import java.util.function.Supplier;

public class FirePacket {

    public FirePacket() {
    }

    public FirePacket(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {

            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            if (player.getMainHandItem().getItem() instanceof GunItem launcher) {

                if (!StatusChecker.hasRocket(player) && StatusChecker.isOnCoolDown(player)) {
                    player.playSound(SoundEvents.VILLAGER_NO, 0.5F, 1.0F);
                    return;
                }

                StatusChecker.consumeRocket(player);

                Fire.fireTnt(player, launcher);
                player.playSound(SoundEvents.GENERIC_EXPLODE, 0.5F, 1.0F);
                player.getCooldowns().addCooldown(
                        launcher,
                        (int) launcher.getCooldown()
                );
            }
        });

        context.setPacketHandled(true);
    }
}
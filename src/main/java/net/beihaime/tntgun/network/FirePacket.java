package net.beihaime.tntgun.network;

import net.beihaime.tntgun.item.StatusChecker;
import net.beihaime.tntgun.item.GunItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
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
                if (StatusChecker.isOnCoolDown(player)) {
                    return;
                }
                if (!StatusChecker.hasRocket(player)) {
                    player.sendSystemMessage(Component.translatable("no_ammo_warning"));
                    player.level().playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.VILLAGER_NO,
                            SoundSource.PLAYERS,
                            0.5F,
                            1.0F
                    );
                    return;
                }

                StatusChecker.consumeRocket(player);
                ItemStack gunStack = player.getMainHandItem();
                gunStack.hurtAndBreak(1,player,p ->
                        p.broadcastBreakEvent(net.minecraft.world.InteractionHand.MAIN_HAND)
                );
                Fire.fireTnt(player, launcher);
                player.level().playSound(
                        null,                       // null = 谁都能听见，包括自己
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.GENERIC_EXPLODE,
                        SoundSource.PLAYERS,
                        0.5F,
                        1.0F
                );
                player.getCooldowns().addCooldown(
                        launcher,
                        (int) launcher.getCooldown()
                );
            }
        });

        context.setPacketHandled(true);
    }
}
package net.beihaime.simplegun.network;

import net.beihaime.simplegun.item.StatusChecker;
import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.sound.PlaySounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.beihaime.simplegun.events.Fire;

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
                if (!StatusChecker.hasAmmo(player)) {
                    player.sendSystemMessage(Component.translatable("no_ammo_warning"));
                    PlaySounds.warnSound(player);
                    return;
                }

                StatusChecker.consumeAmmo(player);
                ItemStack gunStack = player.getMainHandItem();
                gunStack.hurtAndBreak(1,player,p ->
                        p.broadcastBreakEvent(net.minecraft.world.InteractionHand.MAIN_HAND)
                );

                Fire.fire(player, launcher);
                player.getCooldowns().addCooldown(
                        launcher,
                        (int) launcher.getCooldown()
                );
            }
        });

        context.setPacketHandled(true);
    }
}
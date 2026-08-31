package net.beihaime.simplegun.network;

import net.beihaime.simplegun.events.ReloadManager;
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

            if (!(player.getMainHandItem().getItem() instanceof GunItem launcher)) {
                return;
            }

            ItemStack gunStack = player.getMainHandItem();

            if (ReloadManager.isReloading(player)) {

                double remainingSeconds =
                        ReloadManager.getRemainingTicks(player) / 20.0;

                player.sendSystemMessage(
                        Component.translatable(
                                "message.simplegun.reloading",
                                String.format("%.1f", remainingSeconds)
                        )
                );

                return;
            }

            if (StatusChecker.isOnCoolDown(player)) {
                return;
            }

            int ammo = launcher.getMagazineAmmo(gunStack);

            System.out.println(
                    "Magazine: "
                            + ammo
                            + "/"
                            + launcher.getMagazineSize()
            );

            if (ammo <= 0) {
                PlaySounds.warnSound(player);
                player.sendSystemMessage(Component.translatable("no_ammo_warning"));
                return;
            }

            launcher.setMagazineAmmo(
                    gunStack,
                    ammo - 1
            );

            Fire.fire(player, launcher);

            player.getCooldowns().addCooldown(
                    launcher,
                    (int) launcher.getCooldown()
            );
        });

        context.setPacketHandled(true);
    }
}
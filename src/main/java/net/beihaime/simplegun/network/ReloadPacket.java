package net.beihaime.simplegun.network;

import net.beihaime.simplegun.events.ReloadManager;
import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.item.StatusChecker;
import net.beihaime.simplegun.sound.PlaySounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReloadPacket {

    public ReloadPacket() {
    }

    public static void encode(
            ReloadPacket packet,
            FriendlyByteBuf buffer
    ) {
    }

    public static ReloadPacket decode(
            FriendlyByteBuf buffer
    ) {
        return new ReloadPacket();
    }

    public static void handle(
            ReloadPacket packet,
            Supplier<NetworkEvent.Context> supplier
    ) {

        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {

            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            ItemStack gunStack = player.getMainHandItem();

            if (!(gunStack.getItem() instanceof GunItem gun)) {
                return;
            }

            // Already reloading
            if (ReloadManager.isReloading(player)) {
                return;
            }

            int currentAmmo =
                    gun.getMagazineAmmo(gunStack);

            // Magazine is already full
            if (currentAmmo >= gun.getMagazineSize()) {
                return;
            }

            int reserveAmmo =
                    StatusChecker.getReserveAmmo(player);

            // No reserve ammo
            if (reserveAmmo <= 0 && !player.isCreative()) {
                return;
            }

            int reloadTime =
                    (int) gun.getChargeTime();

            // Start server-side reload
            ReloadManager.start(
                    player,
                    gunStack,
                    reloadTime
            );

            PlaySounds.chargeSound(player);
        });

        context.setPacketHandled(true);
    }
}
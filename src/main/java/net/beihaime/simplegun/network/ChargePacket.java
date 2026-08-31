package net.beihaime.simplegun.network;

import net.beihaime.simplegun.events.ReloadManager;
import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.sound.PlaySounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChargePacket {

    public ChargePacket() {
    }

    public static void encode(
            ChargePacket packet,
            FriendlyByteBuf buffer
    ) {
    }

    public static ChargePacket decode(
            FriendlyByteBuf buffer
    ) {
        return new ChargePacket();
    }

    public static void handle(
            ChargePacket packet,
            Supplier<NetworkEvent.Context> supplier
    ) {

        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {

            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            if (ReloadManager.isReloading(player)) {
                System.out.println("BLOCKED: player is reloading");
                return;
            }

            if (player.getMainHandItem().getItem() instanceof GunItem gun) {

                int chargeTime = (int) gun.getChargeTime();

                System.out.println("START RELOAD: " + chargeTime);

                ReloadManager.start(
                        player,
                        player.getMainHandItem(),
                        chargeTime
                );

                PlaySounds.chargeSound(player);
            }
        });

        context.setPacketHandled(true);
    }
}
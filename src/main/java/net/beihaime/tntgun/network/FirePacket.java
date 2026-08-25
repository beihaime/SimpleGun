package net.beihaime.tntgun.network;

import net.beihaime.tntgun.item.LauncherItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
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

            if (player.getMainHandItem().getItem() instanceof LauncherItem launcher) {
                if (player.getCooldowns().isOnCooldown(launcher)) {
                    return;
                }
                Fire.fireTnt(
                        player,
                        launcher.getSpeed()
                        );
                player.getCooldowns().addCooldown(
                        (Item) launcher,
                        (int) launcher.getCooldown());
            }
        });

        context.setPacketHandled(true);
    }
}
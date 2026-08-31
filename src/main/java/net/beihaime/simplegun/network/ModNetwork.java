package net.beihaime.simplegun.network;

import net.beihaime.simplegun.events.Charge;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork {

    public static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(
                    new ResourceLocation("simplegun", "main"),
                    () -> "1",
                    "1"::equals,
                    "1"::equals
            );

    public static void register() {
        CHANNEL.registerMessage(
                0,
                FirePacket.class,
                FirePacket::encode,
                FirePacket::new,
                FirePacket::handle
        );
        CHANNEL.registerMessage(
                1,
                ChargePacket.class,
                ChargePacket::encode,
                ChargePacket::decode,
                ChargePacket::handle
        );
    }
}
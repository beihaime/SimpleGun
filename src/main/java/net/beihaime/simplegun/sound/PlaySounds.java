package net.beihaime.simplegun.sound;

import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.registry.ModItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class PlaySounds {
    public static void warnSound(Player player) {
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
    }
    public static void shootSound(Player player, GunItem launcher) {
        if  (launcher == ModItem.RPG.get()) {
            player.level().playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.GENERIC_EXPLODE,
                    SoundSource.PLAYERS,
                    0.5F,
                    1.0F
            );
        }
        else if (launcher == ModItem.PISTOL.get()) {
            player.level().playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.CROSSBOW_SHOOT,
                    SoundSource.PLAYERS,
                    0.5F,
                    1.0F
            );
        }
    }
}

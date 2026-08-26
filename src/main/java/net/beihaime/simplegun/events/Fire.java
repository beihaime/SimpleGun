package net.beihaime.simplegun.events;

import net.beihaime.simplegun.entity.Bullet;
import net.beihaime.simplegun.entity.Rocket;
import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.registry.ModItem;
import net.beihaime.simplegun.sound.PlaySounds;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


public class Fire {
    private static Vec3 getFireVelocity(Player player, GunItem launcher) {

        Vec3 look = player.getLookAngle();
        Vec3 velocity = player.getDeltaMovement();

        return new Vec3(
                look.x * launcher.getSpeed() + velocity.x,
                look.y * launcher.getSpeed(),
                look.z * launcher.getSpeed() + velocity.z
        );
    }

    private static Vec3 getProjectilePosition(Player player, Vec3 look) {
        return new Vec3(
                player.getX() + look.x * 0.3,
                player.getEyeY() + look.y * 0.3,
                player.getZ() + look.z * 0.3
        );
    }

    public static void fire(Player player, GunItem launcher) {
        if (launcher == ModItem.RPG.get()) {
            fireTnt(player,launcher);
        } else if (launcher == ModItem.PISTOL.get()) {
            fireBullet(player, launcher);

        }

    }
    public static void fireTnt(Player player, GunItem launcher) {
        Vec3 look = player.getLookAngle();
        Level level = player.level();
        Rocket tnt = new Rocket(
                level,
                getProjectilePosition(player, look),
                2F,
                player
        );

        tnt.setDeltaMovement(
                getFireVelocity(player, launcher)
        );

        level.addFreshEntity(tnt);
        PlaySounds.shootSound(player, launcher);
    }

    public static void fireBullet (Player player, GunItem launcher) {

        Vec3 look = player.getLookAngle();
        Level level = player.level();
        Bullet  bullet = new Bullet(
                level,
                getProjectilePosition(player, look),
                player

        );

        bullet.setDeltaMovement(
                getFireVelocity(player, launcher)
        );
        level.addFreshEntity(bullet);
        PlaySounds.shootSound(player,launcher);
    }
}
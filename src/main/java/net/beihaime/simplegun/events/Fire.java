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
    private static Vec3 getMuzzlePosition(Player player, double forward, double right, double down) {
        Vec3 look = player.getLookAngle();
        Vec3 rightDir = new Vec3(-look.z, 0.0, look.x);
        if (rightDir.lengthSqr() < 1.0E-6) {
            rightDir = new Vec3(1.0, 0.0, 0.0);
        } else {
            rightDir = rightDir.normalize();
        }
        return player.getEyePosition()
                .add(look.scale(forward))
                .add(rightDir.scale(right))
                .add(0.0, -down, 0.0);
    }

    private static Vec3 getProjectilePosition(Player player, Vec3 look) {
        return new Vec3(
                player.getX() + look.x * 0.3,
                player.getEyeY() + look.y * 0.3,
                player.getZ() + look.z * 0.3
        );
    }

    public static void fire(Player player, GunItem launcher) {
        boolean reloading = ReloadManager.isReloading(player);
        if (!reloading) {
            if (launcher.getAmmo() == ModItem.ROCKET.get()) {
                fireRocket(player,launcher);
            } else if (launcher.getAmmo() == ModItem.BULLET.get()) {
                fireBullet(player, launcher );

            }
        }

    }

    public static void fireBullet(Player player, GunItem launcher) {
        Level level = player.level();
        Vec3 look = player.getLookAngle();
        Vec3 pos = player.getEyePosition().add(look.scale(0.4)).add(0.0, -0.2, 0.0);;

        Bullet bullet = new Bullet(level, pos, player,launcher.getDamage());
        bullet.setOwner(player);
        bullet.setNoGravity(true);
        bullet.setDeltaMovement(look.scale(launcher.getSpeed()));

        level.addFreshEntity(bullet);
        PlaySounds.shootSound(player, launcher);
    }

    public static void fireRocket(Player player, GunItem launcher) {
        Level level = player.level();
        Vec3 muzzle = getMuzzlePosition(player, 1.2, 0.40, 0.20);

        Rocket rocket = new Rocket(level, muzzle, 2.0F, player,launcher.getDamage());
        rocket.setDeltaMovement(player.getLookAngle().scale(launcher.getSpeed()));

        level.addFreshEntity(rocket);
        PlaySounds.shootSound(player, launcher);
    }
}
package net.beihaime.tntgun.events;

import net.beihaime.tntgun.entity.ProjectileTnt;
import net.beihaime.tntgun.item.GunItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.beihaime.tntgun.registry.ModEntities;


public class Fire {

    public static void fireTnt(Player player, GunItem launcher) {

        Vec3 look = player.getLookAngle();
        Vec3 playerVelocity = player.getDeltaMovement();
        Level level = player.level();
        ProjectileTnt tnt = new ProjectileTnt(
                level,
                player.getX() + look.x * 0.3,
                player.getEyeY() + look.y * 0.3,
                player.getZ() + look.z * 0.3,
                3.0F,
                player
        );

        tnt.setPos(
                player.getX() + look.x,
                player.getEyeY() + look.y,
                player.getZ() + look.z
        );

        tnt.setDeltaMovement(
                look.x * launcher.getSpeed() + player.getDeltaMovement().x,
                look.y * launcher.getSpeed() + player.getDeltaMovement().y,
                look.z * launcher.getSpeed() + player.getDeltaMovement().z
        );

        level.addFreshEntity(tnt);
    }
}
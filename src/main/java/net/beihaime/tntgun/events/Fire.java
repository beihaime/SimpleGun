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
        Level level = player.level();

        ProjectileTnt tnt = new ProjectileTnt(
                ModEntities.PROJECTILE_TNT.get(),
                level
        );

        tnt.setPos(
                player.getX() + look.x,
                player.getEyeY() + look.y,
                player.getZ() + look.z
        );

        tnt.setDeltaMovement(
                look.x * launcher.getSpeed(),
                look.y * launcher.getSpeed(),
                look.z * launcher.getSpeed()
        );

        level.addFreshEntity(tnt);
    }
}
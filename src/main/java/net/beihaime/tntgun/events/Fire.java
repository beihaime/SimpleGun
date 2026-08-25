package net.beihaime.tntgun.events;

import net.beihaime.tntgun.entity.ProjectileTnt;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.beihaime.tntgun.registry.ModEntities;

public class Fire {
    public static void fireTnt(Player player, double speed) {
        Vec3 look = player.getLookAngle();
        Level level = player.level();
        ProjectileTnt tnt = new ProjectileTnt(
                ModEntities.PROJECTILE_TNT.get(),
                level
        );
        tnt.setPos(
                player.getX() + look.x * 0.5,
                player.getEyeY() + look.y * 0.5,
                player.getZ() + look.z * 0.5
        );
        tnt.setDeltaMovement(
                look.x * speed,
                look.y * speed,
                look.z * speed
        );
        level.addFreshEntity(tnt);
    }
}
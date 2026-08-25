package net.beihaime.tntgun.events;

import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Fire {
    public static void fireTnt(Player player, double speed) {
        Vec3 look = player.getLookAngle();
        Level level = player.level();
        PrimedTnt tnt = new PrimedTnt(
                level, player.getX(),
                player.getEyeY(),
                player.getZ(),
                player);
        tnt.setDeltaMovement(
                look.x * speed,
                look.y * speed,
                look.z * speed
        );
        level.addFreshEntity(tnt);
    }
}
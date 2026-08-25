package net.beihaime.tntgun.item;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LauncherItem extends Item {
    public LauncherItem(Properties properties) {
        super(properties);

    }
    public void fire(Player player) {
        Vec3 look = player.getLookAngle();
        Level level = player.level();
        PrimedTnt tnt = new PrimedTnt(
                level,player.getX() ,
                player.getEyeY(),
                player.getZ(),
                player);
        tnt.setDeltaMovement(
                look.x * 4,
                look.y * 1.5,
                look.z * 4
        );
        level.addFreshEntity(tnt);
    }
}

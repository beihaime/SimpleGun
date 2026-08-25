package net.beihaime.tntgun.item;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LauncherItem extends Item {
    public LauncherItem(Properties properties) {
        super(properties);

    }
    public void fire(Player player) {
        Level level = player.level();
        Snowball snowball = new Snowball(level,player);
        snowball.setPos(player.getX(), player.getY(), player.getZ());
        snowball.shootFromRotation(player,player.getXRot(),player.getYRot(),0,4F,0);
        level.addFreshEntity(snowball);
    }
}

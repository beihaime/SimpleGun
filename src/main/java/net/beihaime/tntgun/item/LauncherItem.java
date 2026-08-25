package net.beihaime.tntgun.item;


import net.beihaime.tntgun.events.Fire;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;


public class LauncherItem extends Item {
    private final double speed;
    private final double cooldown;
    public LauncherItem(Properties properties, double speed,double cooldown) {
        super(properties);
        this.speed = speed;
        this.cooldown = cooldown * 20;

    }
    public double getSpeed() {
        return speed;
    }

    public double getCooldown() {
        return cooldown;
    }
}

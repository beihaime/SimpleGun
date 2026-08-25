package net.beihaime.tntgun.item;


import net.beihaime.tntgun.events.Fire;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;


public class LauncherItem extends Item {
    private final double speed;

    public LauncherItem(Properties properties, double speed) {
        super(properties);
        this.speed = speed;

    }
    public double getSpeed() {
        return speed;
    }

}

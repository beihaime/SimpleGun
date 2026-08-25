package net.beihaime.tntgun.item;


import net.minecraft.world.item.Item;


public class GunItem extends Item {
    private final double speed;
    private final double cooldown;
    public GunItem(Properties properties, double speed, double cooldown) {
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

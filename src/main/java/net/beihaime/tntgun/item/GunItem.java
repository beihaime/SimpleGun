package net.beihaime.tntgun.item;


import net.minecraft.world.item.Item;


public class GunItem extends Item {
    private final double speed;
    private final double cooldown;
    private final double durability;
    public GunItem(Properties properties, double speed, double cooldown,double durability) {
        super(properties);
        this.speed = speed;
        this.cooldown = cooldown * 20;
        this.durability = durability;

    }
    public double getSpeed() {
        return speed;
    }

    public double getCooldown() {
        return cooldown;
    }

    public double getDurability() {
        return durability;
    }


}

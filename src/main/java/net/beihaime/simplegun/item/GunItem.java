package net.beihaime.simplegun.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class GunItem extends Item {

    private final double speed;
    private final double cooldown;
    private final float damage;
    private final boolean automatic;
    private final boolean aim;
    private final Supplier<Item> ammo;
    private final double chargeTime;
    private final int magazineSize;
    public GunItem(Properties properties,
                   float damage,
                   double speed,
                   double cooldown,
                   Supplier ammo,
                   boolean automatic,
                   boolean aim,
                   double chargeTime,
                   int magazineSize
    ) {
        super(properties);
        this.speed = speed;
        this.damage = damage;
        this.cooldown = cooldown * 20;
        this.automatic = automatic;
        this.ammo = ammo;
        this.aim = aim;
        this.chargeTime = chargeTime * 20;
        this.magazineSize = magazineSize;
    }

    public double getChargeTime() {
        return chargeTime;
    }

    public int getMagazineSize() {
        return magazineSize;
    }


    public int getMagazineAmmo(ItemStack stack) {
        if (!stack.getOrCreateTag().contains("MagazineAmmo")) {
            setMagazineAmmo(stack, magazineSize);
        }

        return stack.getOrCreateTag().getInt("MagazineAmmo");
    }

    public void setMagazineAmmo(ItemStack stack, int ammo) {
        stack.getOrCreateTag().putInt("MagazineAmmo", ammo);
    }


    public float getDamage() {
        return this.damage;
    }

    public Item getAmmo() {
        return ammo.get();
    }

    public boolean canAim() {
        return aim;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public double getCooldown() {
        return cooldown;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(
            Level level,
            Player player,
            InteractionHand hand
    ) {
        if (!canAim()) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        player.playSound(
                SoundEvents.SPYGLASS_USE,
                1.0F,
                1.0F
        );
        player.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.startUsingInstantly(
                level,
                player,
                hand
        );
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPYGLASS;
    }

}
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

    @Override
    public InteractionResultHolder<ItemStack> use(
            Level level,
            Player player,
            InteractionHand hand
    ) {
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
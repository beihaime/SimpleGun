package net.beihaime.simplegun.item;

import net.beihaime.simplegun.sound.PlaySounds;
import net.minecraft.network.chat.Component;
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
    private final boolean automatic;
    private final boolean aim;
    private final Supplier<Item> ammo;
    public GunItem(Properties properties,
                   double speed,
                   double cooldown,
                   Supplier ammo,
                   boolean automatic,
                   boolean aim
    ) {
        super(properties);
        this.speed = speed;
        this.cooldown = cooldown * 20;
        this.automatic = automatic;
        this.ammo = ammo;
        this.aim = aim;
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
            PlaySounds.warnSound(player);
            player.sendSystemMessage(Component.translatable("cannot_aim"));
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
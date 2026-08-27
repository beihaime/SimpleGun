package net.beihaime.simplegun.entity;

import net.minecraft.world.entity.LivingEntity;

public class DamageHelper {
    public static void hit(LivingEntity target, LivingEntity attacker, net.minecraft.world.entity.Entity projectile, float amount) {
        target.invulnerableTime = 0;
        target.hurt(
                projectile.damageSources().mobProjectile(projectile, attacker),
                amount
        );
    }
}

package net.beihaime.simplegun.entity;

import net.beihaime.simplegun.item.GunItem;
import net.beihaime.simplegun.registry.ModEntities;
import net.beihaime.simplegun.registry.ModItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class Bullet extends Arrow {
    private float damage;
    public Bullet(EntityType<? extends Arrow> type, Level level) {
        super(type, level);
    }

    public Bullet(Level level, Vec3 position, LivingEntity owner,float damage) {
        super(ModEntities.BULLET.get(), level);
        this.setPos(position.x, position.y, position.z);
        this.setOwner(owner);
        this.setNoGravity(true);
        this.setKnockback(4);
        this.damage = damage;
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        if (entity == this.getOwner()) {
            return false;
        }
        return super.canHitEntity(entity);
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public void tick() {
        if (level().isClientSide) {
            this.move(MoverType.SELF, this.getDeltaMovement());
            return;
        }

        Vec3 motion = this.getDeltaMovement();
        double speed = motion.length();
        if (speed < 1.0E-6) {
            discard();
            return;
        }

        int steps = Math.max(1, (int) Math.ceil(speed)); // 每步约 1 格
        Vec3 step = motion.scale(1.0 / steps);
        LivingEntity owner = this.getOwner() instanceof LivingEntity l ? l : null;

        for (int i = 0; i < steps; i++) {
            Vec3 start = this.position();
            Vec3 end = start.add(step);

            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                    this,
                    start,
                    end,
                    new AABB(start, end).inflate(1.0),
                    e -> e.isAlive()
                            && e instanceof LivingEntity
                            && e != owner
                            && (owner == null || !e.isPassengerOfSameVehicle(owner)),
                    start.distanceToSqr(end) + 1.0
            );
            if (entityHit != null && entityHit.getEntity() instanceof LivingEntity living) {
                    DamageHelper.hit(living, owner, this,damage);
                    discard();
                    return;

            }

            var blockHit = level().clip(new net.minecraft.world.level.ClipContext(
                    start,
                    end,
                    net.minecraft.world.level.ClipContext.Block.COLLIDER,
                    net.minecraft.world.level.ClipContext.Fluid.NONE,
                    this
            ));
            if (blockHit.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
                this.setPos(blockHit.getLocation());
                discard();
                return;
            }

            this.setPos(end);
        }

        this.setDeltaMovement(motion.scale(0.99D));
    }


}

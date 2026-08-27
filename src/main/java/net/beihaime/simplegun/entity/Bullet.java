package net.beihaime.simplegun.entity;

import net.beihaime.simplegun.registry.ModEntities;
import net.beihaime.simplegun.registry.ModItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.EntityHitResult;

public class Bullet extends Arrow {

    public Bullet(EntityType<? extends Arrow> type, Level level) {
        super(type, level);
    }

    public Bullet(Level level, Vec3 position, LivingEntity owner) {
        super(ModEntities.BULLET.get(), level);
        this.setPos(position.x, position.y, position.z);
        this.setOwner(owner);
        this.setNoGravity(true);
        this.setKnockback(4);
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
        Vec3 oldPosition = this.position();
        Vec3 intended = oldPosition.add(this.getDeltaMovement());

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.99D));

        if (level().isClientSide) {
            return;
        }

        LivingEntity owner = this.getOwner() instanceof LivingEntity living ? living : null;
        double reach = oldPosition.distanceToSqr(intended);

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                this,
                oldPosition,
                intended,
                this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0),
                e -> e.isAlive()
                        && e instanceof LivingEntity
                        && e != owner
                        && (owner == null || !e.isPassengerOfSameVehicle(owner)),
                reach
        );

        if (entityHit != null && entityHit.getEntity() instanceof LivingEntity living) {
            living.hurt(this.damageSources().mobProjectile(this, owner), 8.0F);
            discard();
            return;
        }

        if (this.horizontalCollision || this.verticalCollision) {
            discard();
            return;
        }

        var hit = level().clip(new net.minecraft.world.level.ClipContext(
                oldPosition,
                intended,
                net.minecraft.world.level.ClipContext.Block.COLLIDER,
                net.minecraft.world.level.ClipContext.Fluid.NONE,
                this
        ));
        if (hit.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
            this.setPos(hit.getLocation());
            discard();
        }
    }

}

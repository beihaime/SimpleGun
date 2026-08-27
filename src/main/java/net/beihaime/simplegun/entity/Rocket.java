package net.beihaime.simplegun.entity;

import net.beihaime.simplegun.registry.ModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Rocket extends PrimedTnt {

    private LivingEntity shooter;
    private final float explodeRadius;

    public Rocket(EntityType<? extends PrimedTnt> type, Level level) {
        super(type, level);
        this.explodeRadius = 10.0F;
        this.setFuse(200);
    }

    public Rocket(Level level,
                  Vec3 position,
                  float explodeRadius, LivingEntity owner) {
        super(ModEntities.ROCKET.get(), level);
        this.setPos(position.x, position.y, position.z);
        this.shooter = owner;
        this.explodeRadius = explodeRadius;
        this.setFuse(200);
    }

    @Override
    public LivingEntity getOwner() {
        return shooter != null ? shooter : super.getOwner();
    }

    @Override
    public void explode() {
        this.level().explode(
                this,
                this.getX(),
                this.getY(0.0625D),
                this.getZ(),
                this.explodeRadius,
                Level.ExplosionInteraction.TNT
        );
    }

    @Override
    public void tick() {

        int fuse = this.getFuse();
        if (fuse > 0) {
            this.setFuse(fuse - 1);
        } else {
            if (!level().isClientSide) {
                explode();
                discard();
            }
            return;
        }

        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.0005D, 0.0D));
        }

        Vec3 oldPosition = this.position();
        this.move(MoverType.SELF, this.getDeltaMovement());
        Vec3 newPosition = this.position();
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));

        Vec3 v = this.getDeltaMovement();
        if (v.lengthSqr() > 0.001) {
            this.setYRot((float) (Math.atan2(v.x, v.z) * (180.0 / Math.PI)));
            this.setXRot((float) (Math.atan2(-v.y, Math.sqrt(v.x * v.x + v.z * v.z)) * (180.0 / Math.PI)));
        }

        if (level().isClientSide) {
            return;
        }

        LivingEntity owner = this.getOwner();
        AABB collisionBox = new AABB(oldPosition, newPosition).inflate(0.15D);

        for (Entity entity : level().getEntities(this, collisionBox, Entity::isAlive)) {
            if (owner != null && (entity == owner || entity.isPassengerOfSameVehicle(owner))) {
                continue;
            }
            if (entity instanceof LivingEntity living) {
                DamageHelper.hit(living, owner, this, 100.0F);
            }
            explode();
            discard();
            return;
        }

        if (horizontalCollision || verticalCollision) {
            boolean tooCloseToOwner = owner != null && this.distanceToSqr(owner) < 2.5D;
            if (!tooCloseToOwner) {
                explode();
                discard();
            }
        }
    }
}
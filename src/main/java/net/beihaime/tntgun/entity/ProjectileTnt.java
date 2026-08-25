package net.beihaime.tntgun.entity;

import net.beihaime.tntgun.registry.ModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ProjectileTnt extends PrimedTnt {
    private LivingEntity shooter;
    public ProjectileTnt(EntityType<? extends PrimedTnt> type, Level level) {
        super(type, level);
        this.setFuse(Integer.MAX_VALUE);
    }

    public ProjectileTnt(Level level, double x, double y, double z,
                         float explodeRadius, LivingEntity owner) {
        super(ModEntities.PROJECTILE_TNT.get(), level);
        this.setPos(x, y, z);
        this.shooter = owner;
        this.setFuse(200);
    }
    @Override
    public LivingEntity getOwner() {
        return shooter != null ? shooter : super.getOwner();
    }

    @Override
    public void explode(){
        this.level().explode(
                (Entity) this,
                this.getX(),
                this.getY(0.0625D),
                this.getZ(),
                3,
                Level.ExplosionInteraction.TNT
        );
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.0005D, 0.0D));
        }

        Vec3 oldPosition = this.position();
        this.move(MoverType.SELF, this.getDeltaMovement());
        Vec3 newPosition = this.position();
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));

        if (level().isClientSide) {
            return;
        }

        LivingEntity owner = this.getOwner();

        AABB collisionBox = new AABB(oldPosition, newPosition).inflate(0.15D);

        for (Entity entity : level().getEntities(this, collisionBox, Entity::isAlive)) {
            if (owner != null && (entity == owner || entity.isPassengerOfSameVehicle(owner))) {
                continue;
            }
            discard();
            explode();
            return;
        }

        if (horizontalCollision || verticalCollision) {
            boolean tooCloseToOwner = owner != null && this.distanceToSqr(owner) < 2.5D;
            if (!tooCloseToOwner) {
                discard();
                explode();
            }
        }
    }

}
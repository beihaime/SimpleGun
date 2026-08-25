package net.beihaime.tntgun.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ProjectileTnt extends PrimedTnt {
    private int graceTicks = 3;
    public ProjectileTnt(
            Level level,
            double x,
            double y,
            double z,
            float explodeRadius,
            LivingEntity owner
    ) {
        super(level, x, y, z, owner);
        this.setFuse(Integer.MAX_VALUE);

    }

    public ProjectileTnt(
            EntityType<ProjectileTnt> entityType,
            Level level
    ) {
        super(entityType, level);
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
        if (graceTicks > 0) {
            graceTicks--;
        }

        if (!this.isNoGravity()) {
            this.setDeltaMovement(
                    this.getDeltaMovement().add(0.0D, -0.0005D, 0.0D)
            );
        }


        Vec3 oldPosition = this.position();

        this.move(
                MoverType.SELF,
                this.getDeltaMovement()
        );

        Vec3 newPosition = this.position();

        this.setDeltaMovement(
                this.getDeltaMovement().scale(0.98D)
        );

        if (!level().isClientSide) {
            if (graceTicks > 0) {
                return;
            }
            AABB collisionBox = new AABB(
                    oldPosition,
                    newPosition
            ).inflate(0.15D);

            for (Entity entity : level().getEntities(
                    this,
                    collisionBox,
                    e -> e.isAlive() && e != this.getOwner()
            )) {

                discard();
                explode();
                return;
            }

            if (horizontalCollision || verticalCollision) {
                discard();
                explode();
            }
        }
    }
}
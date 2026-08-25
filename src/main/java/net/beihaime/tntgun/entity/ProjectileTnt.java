package net.beihaime.tntgun.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

public class ProjectileTnt extends PrimedTnt {
    public ProjectileTnt(
            Level level,
            double x,
            double y,
            double z,
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
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(
                    this.getDeltaMovement().add(0.0D, -0.0005D, 0.0D)
            );
        }

        this.move(
                MoverType.SELF,
                this.getDeltaMovement()
        );

        this.setDeltaMovement(
                this.getDeltaMovement().scale(0.98D)
        );


        if (!level().isClientSide &&
                (horizontalCollision || verticalCollision)) {
            discard();
            explode();
        }
    }
}


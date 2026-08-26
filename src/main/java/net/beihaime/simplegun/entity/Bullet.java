package net.beihaime.simplegun.entity;

import net.beihaime.simplegun.registry.ModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Bullet extends Arrow {

    public Bullet(EntityType<? extends Arrow> type, Level level) {
        super(type, level);
    }

    public Bullet(Level level, Vec3 position, LivingEntity owner) {
        super(ModEntities.BULLET.get(), level);
        this.setPos(position.x, position.y, position.z);
        this.setOwner(owner);
        this.setNoGravity(true);
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        if (entity == this.getOwner()) {
            return false;
        }
        return super.canHitEntity(entity);
    }
}
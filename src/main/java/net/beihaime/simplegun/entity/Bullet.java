package net.beihaime.simplegun.entity;

import net.beihaime.simplegun.registry.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Bullet extends Arrow {
    private LivingEntity shooter;
    public Bullet(EntityType<? extends Arrow> type, Level level) {
        super(type, level);
        this.shooter = (LivingEntity) this.getOwner();
    }
    public Bullet(Level level, Vec3 position, LivingEntity owner) {
        super(ModEntities.BULLET.get(), level);
        setPos(position.x(), position.y(), position.z());
        this.shooter = (LivingEntity) owner;
    }
    @Override
    public LivingEntity getOwner() {return shooter != null ? shooter : (LivingEntity) super.getOwner();}
}

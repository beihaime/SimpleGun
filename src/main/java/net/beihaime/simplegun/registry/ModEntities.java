package net.beihaime.simplegun.registry;

import net.beihaime.simplegun.entity.Bullet;
import net.beihaime.simplegun.entity.Rocket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(
                    ForgeRegistries.ENTITY_TYPES,
                    "simplegun"
            );

    public static final RegistryObject<EntityType<Rocket>> PROJECTILE_TNT =
            ENTITIES.register(
                    "projectile_tnt",
                    () -> EntityType.Builder
                            .of(
                                    (EntityType<Rocket> type, Level level)
                                            -> new Rocket(type, level),
                                    MobCategory.MISC
                            )
                            .sized(0.98F, 0.98F)
                            .build("projectile_tnt")
            );

    public static final RegistryObject<EntityType<Bullet>> BULLET =
            ENTITIES.register(
                    "bullet",
                    () -> EntityType.Builder
            .of(
                    (EntityType<Bullet> type, Level level )
                        ->new Bullet(type,level),
                    MobCategory.MISC
            )
                            .sized(0.98F,0.98F)
                    .build("bullet")
            );
}
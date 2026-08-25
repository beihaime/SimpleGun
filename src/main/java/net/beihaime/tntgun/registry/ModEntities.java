package net.beihaime.tntgun.registry;

import net.beihaime.tntgun.entity.ProjectileTnt;
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
                    "tntgun"
            );

    public static final RegistryObject<EntityType<ProjectileTnt>> PROJECTILE_TNT =
            ENTITIES.register(
                    "projectile_tnt",
                    () -> EntityType.Builder
                            .of(
                                    (EntityType<ProjectileTnt> type, Level level)
                                            -> new ProjectileTnt(type, level),
                                    MobCategory.MISC
                            )
                            .sized(0.98F, 0.98F)
                            .build("projectile_tnt")
            );
}
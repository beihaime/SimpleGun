package net.beihaime.simplegun.registry;

import net.beihaime.simplegun.item.GunItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS,"simplegun");

    public static final RegistryObject<Item> RPG = ITEMS.register(
            "rpg",
            () -> new GunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(30),
                    100,
                    5,
                    3,
                    ModItem.ROCKET,
                    false,
                    true,
                    6,
                    1,
                    MobEffects.MOVEMENT_SLOWDOWN,
                    1

            )
    );

    public static final RegistryObject<Item> PISTOL = ITEMS.register(
            "pistol",
            () -> new GunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(50),
                    5,
                    50,
                    0.3,
                    ModItem.BULLET,
                    false,
                    true,
                    2,
                    9,
                    MobEffects.MOVEMENT_SPEED,
                    2

            )
    );

    public static final RegistryObject<Item> AK47 = ITEMS.register(
            "ak47",
            () -> new GunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(400),
                    7,
                    100,
                    0.1,
                    ModItem.BULLET,
                    true,
                    true,
                    4,
                    30,
                    MobEffects.MOVEMENT_SPEED,
                    0
            )
    );

    public static final RegistryObject<Item> AWP = ITEMS.register(
            "awp",
            () -> new GunItem(
                    new Item.Properties(),
                    100,
                    200,
                    2,
                    ModItem.BULLET,
                    false,
                    true,
                    5,
                    6,
                    MobEffects.MOVEMENT_SPEED,
                    2
            )
    );

    public static final RegistryObject<Item> ROCKET = ITEMS.register(
            "rocket",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .fireResistant())
    );

    public static final RegistryObject<Item> BULLET = ITEMS.register(
            "bullet",
            () -> new Item(new Item.Properties()
                .stacksTo(64)
            )
    );
}

package net.beihaime.simplegun.registry;

import net.beihaime.simplegun.item.GunItem;
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
                    3,
                    4
            )
    );

    public static final RegistryObject<Item> PISTOL = ITEMS.register(
            "pistol",
            () -> new GunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(50),
                    50,
                    0.1)
    );

    public static final RegistryObject<Item> RPG_PROJECTILE = ITEMS.register(
            "rpg_projectile",
            () -> new Item(new Item.Properties()
                    .stacksTo(128)
                    .fireResistant())
    );

    public static final RegistryObject<Item> BULLET = ITEMS.register(
            "bullet",
            () -> new Item(new Item.Properties()
                .stacksTo(64)
            )
    );
}

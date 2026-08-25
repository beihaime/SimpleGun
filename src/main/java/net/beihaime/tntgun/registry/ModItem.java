package net.beihaime.tntgun.registry;

import net.beihaime.tntgun.item.GunItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS,"tntgun");

    public static final RegistryObject<Item> RPG = ITEMS.register(
            "rpg",
            () -> new GunItem(new Item.Properties()
                    .stacksTo(1)
                    .durability(30),
                    5,
                    1
            )
    );

    public static final RegistryObject<Item> RPG_PROJECTILE = ITEMS.register(
            "rpg_projectile",
            () -> new Item(new Item.Properties()
                    .stacksTo(128)
                    .fireResistant())
    );
}

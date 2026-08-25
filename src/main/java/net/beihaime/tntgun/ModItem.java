package net.beihaime.tntgun;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS,"tntgun");

    public static final RegistryObject<Item> RPG = ITEMS.register(
            "rpg",
            () -> new Item(new Item.Properties())
            );

}

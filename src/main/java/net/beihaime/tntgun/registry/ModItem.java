package net.beihaime.tntgun.registry;

import net.beihaime.tntgun.item.LauncherItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS,"tntgun");

    public static final RegistryObject<Item> RPG = ITEMS.register(
            "rpg",
            () -> new LauncherItem(new Item.Properties(),4,3)
            );

}

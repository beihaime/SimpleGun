package net.beihaime.simplegun.creativeTab;

import net.beihaime.simplegun.registry.ModItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "simplegun");


    public static final RegistryObject<CreativeModeTab> TNT_GUN_TAB =
            CREATIVE_MODE_TABS.register(
                    "tnt_gun",
                    () -> CreativeModeTab.builder()
                            .icon(() -> new ItemStack(ModItem.ROCKET.get()))
                            .title(Component.translatable("itemGroup.simplegun.title"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept(ModItem.RPG.get());
                                pOutput.accept(ModItem.PISTOL.get());
                                pOutput.accept(ModItem.ROCKET.get());
                                pOutput.accept(ModItem.BULLET.get());
                            })
                            .build()
            );
}
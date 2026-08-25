package net.beihaime.tntgun.CreativeTab;

import net.beihaime.tntgun.registry.ModItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "tntgun");


    public static final RegistryObject<CreativeModeTab> TNT_GUN_TAB = CREATIVE_MODE_TABS.register("tnt_gun",
                    () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.GUNPOWDER))
                            .title(Component.translatable("itemGroup.tntgun.title"))
                            .displayItems((pParameters, pOutput) ->
                                    pOutput.accept(ModItem.RPG.get()))
                            .build());
    }


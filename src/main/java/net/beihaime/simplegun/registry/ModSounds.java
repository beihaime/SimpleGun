package net.beihaime.simplegun.registry;

import net.beihaime.simplegun.SimpleGun;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SimpleGun.MOD_ID);

    public static final RegistryObject<SoundEvent> PISTOL_SHOOT =
            SOUNDS.register("pistol_shoot",
                    () -> SoundEvent.createVariableRangeEvent(
                            new ResourceLocation(SimpleGun.MOD_ID, "pistol_shoot")
                    )
            );

    public static final RegistryObject<SoundEvent> RPG_SHOOT =
            SOUNDS.register("rpg_shoot",
                    () -> SoundEvent.createVariableRangeEvent(
                            new ResourceLocation(SimpleGun.MOD_ID, "rpg_shoot")
                    )
            );
}
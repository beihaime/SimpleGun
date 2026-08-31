package net.beihaime.simplegun.registry;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class ModKeys {
    public static final Lazy<KeyMapping> ChargeKey =Lazy.of(() -> new KeyMapping(
                    "key.simplegun.charge", // Will be localized using this translation key
                    InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
                    GLFW.GLFW_KEY_R,
                    "key.categories.simplegun" // Mapping will be in the misc category
            ));

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(ChargeKey.get());
    }
}

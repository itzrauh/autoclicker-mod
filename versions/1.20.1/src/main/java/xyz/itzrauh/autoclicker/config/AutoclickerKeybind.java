package xyz.itzrauh.autoclicker.config;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@UtilityClass
public class AutoclickerKeybind {

    private static final String CATEGORY = "key.category.autoclicker.keybinds";

    @Getter
    private static KeyBinding toggleKey;
    @Getter private static KeyBinding nextProfileKey;
    @Getter private static KeyBinding previousProfileKey;
    @Getter private static KeyBinding openConfigKey;

    public static void register() {
        toggleKey          = register("key.autoclicker.toggle",           GLFW.GLFW_KEY_V);
        nextProfileKey     = register("key.autoclicker.next_profile",     GLFW.GLFW_KEY_N);
        previousProfileKey = register("key.autoclicker.previous_profile", GLFW.GLFW_KEY_M);
        openConfigKey      = register("key.autoclicker.open_config",      GLFW.GLFW_KEY_UNKNOWN);
    }

    private static KeyBinding register(String translationKey, int defaultKey) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                translationKey,
                InputUtil.Type.KEYSYM,
                defaultKey,
                CATEGORY
        ));
    }
}
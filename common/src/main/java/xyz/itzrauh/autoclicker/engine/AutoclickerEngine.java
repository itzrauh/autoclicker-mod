package xyz.itzrauh.autoclicker.engine;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import xyz.itzrauh.autoclicker.AutoclickerMod;
import xyz.itzrauh.autoclicker.config.AutoclickerKeybind;
import xyz.itzrauh.autoclicker.manager.ProfileManager;
import xyz.itzrauh.autoclicker.mixin.MinecraftClientInvoker;
import xyz.itzrauh.autoclicker.model.ClickButton;
import xyz.itzrauh.autoclicker.model.ConfigProfile;
import xyz.itzrauh.autoclicker.screen.ProfileManagerScreen;

@Slf4j
@UtilityClass
public class AutoclickerEngine {

    @Getter
    private static boolean enabled = false;

    private static long lastClickTime = 0;
    private static boolean wasTogglePressed = false;
    private static boolean wasNextPressed = false;
    private static boolean wasPreviousPressed = false;
    private static boolean wasConfigPressed = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(AutoclickerEngine::onTick);
    }

    private static void onTick(MinecraftClient client) {
        if (client.player == null) return;

        handleToggle(client);
        handleProfileSwitch(client);
        handleOpenConfig(client);

        if (!enabled) return;
        if (client.currentScreen != null) return;

        handleClicking(client);
    }

    private static void handleOpenConfig(MinecraftClient client) {
        boolean isPressed = AutoclickerKeybind.getOpenConfigKey().isPressed();
        if (isPressed && !wasConfigPressed && client.currentScreen == null) {
            client.setScreen(new ProfileManagerScreen(null));
        }
        wasConfigPressed = isPressed;
    }

    private static void handleToggle(MinecraftClient client) {
        boolean isPressed = AutoclickerKeybind.getToggleKey().isPressed();
        if (isPressed && !wasTogglePressed) {
            enabled = !enabled;
            AutoclickerMod.LOGGER.info("Autoclicker toggled: {}", enabled ? "ON" : "OFF");

            String profileName = ProfileManager.getActive().getName();
            assert client.player != null;
            client.player.sendMessage(
                    Text.literal("Autoclicker: ").formatted(Formatting.WHITE)
                            .append(Text.translatable(enabled
                                            ? "autoclicker.message.toggle.on"
                                            : "autoclicker.message.toggle.off")
                                    .formatted(enabled ? Formatting.GREEN : Formatting.RED))
                            .append(Text.literal(" "))
                            .append(Text.literal(" [" + profileName + "]").formatted(Formatting.GRAY)),
                    true
            );
        }
        wasTogglePressed = isPressed;
    }

    private static void handleProfileSwitch(MinecraftClient client) {
        boolean nextPressed = AutoclickerKeybind.getNextProfileKey().isPressed();
        if (nextPressed && !wasNextPressed) {
            ProfileManager.nextProfile();
            notifyProfileChange(client);
        }
        wasNextPressed = nextPressed;

        boolean prevPressed = AutoclickerKeybind.getPreviousProfileKey().isPressed();
        if (prevPressed && !wasPreviousPressed) {
            ProfileManager.previousProfile();
            notifyProfileChange(client);
        }
        wasPreviousPressed = prevPressed;
    }

    private static void notifyProfileChange(MinecraftClient client) {
        ConfigProfile active = ProfileManager.getActive();
        assert client.player != null;
        client.player.sendMessage(
                Text.translatable("autoclicker.actionbar.profile")
                        .append(Text.literal(active.getName()).formatted(Formatting.AQUA)),
                true
        );
    }

    private static void handleClicking(MinecraftClient client) {
        ConfigProfile profile = ProfileManager.getActive();
        long now = System.currentTimeMillis();

        if (now - lastClickTime >= profile.getDelayMs()) {
            lastClickTime = now;
            click(client, profile);
        }
    }

    private static void click(MinecraftClient client, ConfigProfile profile) {
        sendClick(client, profile.getClickButton());
        if (profile.isDoubleClick()) {
            sendClick(client, profile.getClickButton());
        }
    }

    private static void sendClick(MinecraftClient client, ClickButton button) {
        MinecraftClientInvoker invoker = (MinecraftClientInvoker) client;
        if (button == ClickButton.LEFT) {
            invoker.invokeDoAttack();
        } else {
            invoker.invokeDoItemUse();
        }
    }
}

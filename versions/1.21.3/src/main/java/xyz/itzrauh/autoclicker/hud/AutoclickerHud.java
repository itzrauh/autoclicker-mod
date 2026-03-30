package xyz.itzrauh.autoclicker.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import xyz.itzrauh.autoclicker.engine.AutoclickerEngine;
import xyz.itzrauh.autoclicker.manager.ProfileManager;
import xyz.itzrauh.autoclicker.model.ConfigProfile;

public class AutoclickerHud {

    public static void register() {
        HudRenderCallback.EVENT.register((graphics, tickDelta) -> render(graphics));
    }

    private static void render(DrawContext graphics) {
        if (!AutoclickerEngine.isEnabled()) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        ConfigProfile active = ProfileManager.getActive();

        Text line1 = Text.empty()
                .append(Text.literal("Autoclicker: ").formatted(Formatting.WHITE))
                .append(Text.translatable("autoclicker.message.toggle.on").formatted(Formatting.GREEN))
                .append(Text.literal(" | ").formatted(Formatting.DARK_GRAY))
                .append(Text.literal(active.getName()).formatted(Formatting.AQUA));

        graphics.drawText(client.textRenderer, line1, 10, 10, 0xFFFFFFFF, true);
    }
}
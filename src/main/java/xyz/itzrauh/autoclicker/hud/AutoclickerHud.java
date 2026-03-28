package xyz.itzrauh.autoclicker.hud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import xyz.itzrauh.autoclicker.AutoclickerMod;
import xyz.itzrauh.autoclicker.engine.AutoclickerEngine;
import xyz.itzrauh.autoclicker.manager.ProfileManager;
import xyz.itzrauh.autoclicker.model.ConfigProfile;

public class AutoclickerHud {
    public static void register() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.of(AutoclickerMod.MOD_ID, "status"),
                AutoclickerHud::render
        );
    }

    private static void render(DrawContext graphics, RenderTickCounter tickCounter) {
        if (!AutoclickerEngine.isEnabled()) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        ConfigProfile active = ProfileManager.getActive();

        Text line1 = Text.empty()
                .append(Text.literal("Autoclicker: ").formatted(Formatting.WHITE))
                .append(Text.translatable("autoclicker.message.toggle.on").formatted(Formatting.GREEN))
                .append(Text.literal(" | ").formatted(Formatting.DARK_GRAY))
                .append(Text.literal(active.getName()).formatted(Formatting.WHITE));

        graphics.drawText(client.textRenderer, line1, 10, 10, 0xFFFFFF55, true);
    }
}

// Odio la versión 1.21.10. ¿Por qué? pues porque renderizar esta más bugeado que otra cosa
// posiblemente he estado como 3 horas en estas lineas viendo porque FALLA, para que hoy coja cambie dos cosas y funcione todo
// no voy a volver a hacer un mod de fabric 1.21.10+ porque odio todo lo que lo compone.
// Larga vida a Scratch

// UPDATE: HE actualizado el codigo para que tenga perfiles y ADIVINA, NO FUNCIONA TOCA DEBUGEAR OTRAS 2 HORAS
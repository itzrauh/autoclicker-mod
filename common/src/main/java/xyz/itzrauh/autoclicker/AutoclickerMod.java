package xyz.itzrauh.autoclicker;

import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.itzrauh.autoclicker.config.AutoclickerKeybind;
import xyz.itzrauh.autoclicker.engine.AutoclickerEngine;
import xyz.itzrauh.autoclicker.extension.AutoclickerClothConfig;
import xyz.itzrauh.autoclicker.hud.AutoclickerHud;

@Slf4j
public class AutoclickerMod implements ClientModInitializer {
	public static final String MOD_ID = "autoclicker";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing Autoclicker Mod");
		AutoclickerClothConfig.register();
		AutoclickerKeybind.register();
		AutoclickerEngine.register();
		AutoclickerHud.register();
	}
}
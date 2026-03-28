package xyz.itzrauh.autoclicker.extension;

import lombok.Getter;
import lombok.Setter;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import xyz.itzrauh.autoclicker.model.ClickButton;
import xyz.itzrauh.autoclicker.model.ConfigProfile;

import java.util.ArrayList;
import java.util.List;

@Config(name = "autoclicker")
@Getter
@Setter
public class AutoclickerClothConfig implements ConfigData {

    // profiles
    @ConfigEntry.Gui.Excluded
    private List<ConfigProfile> profiles = new ArrayList<>(List.of(
            new ConfigProfile("Default",  100, false, ClickButton.LEFT),
            new ConfigProfile("Farming",  150, false, ClickButton.RIGHT),
            new ConfigProfile("Mining",    60, true,  ClickButton.LEFT)
    ));

    @ConfigEntry.Gui.Excluded
    private int activeProfile = 0;

    public ConfigProfile getActive() {
        if (activeProfile < 0 || activeProfile >= profiles.size()) {
            activeProfile = 0;
        }
        return profiles.get(activeProfile);
    }

    public static AutoclickerClothConfig get() {
        return AutoConfig.getConfigHolder(AutoclickerClothConfig.class).getConfig();
    }

    public static void register() {
        AutoConfig.register(AutoclickerClothConfig.class, GsonConfigSerializer::new);
    }
}
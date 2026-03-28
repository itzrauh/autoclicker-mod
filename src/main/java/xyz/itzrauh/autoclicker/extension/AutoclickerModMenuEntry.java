package xyz.itzrauh.autoclicker.extension;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import xyz.itzrauh.autoclicker.screen.ProfileManagerScreen;

public class AutoclickerModMenuEntry implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ProfileManagerScreen::new;
    }
}

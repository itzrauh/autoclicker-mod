package xyz.itzrauh.autoclicker.manager;

import lombok.experimental.UtilityClass;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.text.Text;
import xyz.itzrauh.autoclicker.extension.AutoclickerClothConfig;
import xyz.itzrauh.autoclicker.model.ClickButton;
import xyz.itzrauh.autoclicker.model.ConfigProfile;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ProfileManager {

    // importante no subir esto mucho porque entonces puede petar la screen de config sabes?
    private static final int MAX_PROFILES = 10;

    public static AutoclickerClothConfig config() {
        return AutoclickerClothConfig.get();
    }

    public static List<ConfigProfile> getProfiles() {
        return config().getProfiles();
    }

    public static ConfigProfile getActive() {
        return config().getActive();
    }

    public static int getActiveIndex() {
        return config().getActiveProfile();
    }

    public static void setActiveIndex(int index) {
        List<ConfigProfile> profiles = getProfiles();
        if (index < 0 || index >= profiles.size()) return;
        config().setActiveProfile(index);
        save();
    }

    public static void nextProfile() {
        int next = (getActiveIndex() + 1) % getProfiles().size();
        setActiveIndex(next);
    }

    public static void previousProfile() {
        int size = getProfiles().size();
        int prev = (getActiveIndex() - 1 + size) % size;
        setActiveIndex(prev);
    }

    public static void createProfile(String name) {
        List<ConfigProfile> profiles = getProfiles();
        if (profiles.size() >= MAX_PROFILES) return;

        String safeName = sanitizeName(name, profiles);
        ConfigProfile newProfile = new ConfigProfile(safeName, 100, false, ClickButton.LEFT);
        profiles.add(newProfile);
        config().setActiveProfile(profiles.size() - 1);
        save();
    }

    public static int duplicateActive() {
        ConfigProfile active = getActive();
        return createProfileFromTemplate(active.copyWithName(active.getName() + " (copy)"));
    }

    private static int createProfileFromTemplate(ConfigProfile template) {
        List<ConfigProfile> profiles = getProfiles();
        if (profiles.size() >= MAX_PROFILES) return -1;

        profiles.add(template);
        config().setActiveProfile(profiles.size() - 1);
        save();
        return profiles.size() - 1;
    }

    public static boolean deleteProfile(int index) {
        List<ConfigProfile> profiles = getProfiles();
        if (profiles.size() <= 1) return false;
        if (index < 0 || index >= profiles.size()) return false;

        profiles.remove(index);

        int active = getActiveIndex();
        if (active >= profiles.size()) {
            config().setActiveProfile(profiles.size() - 1);
        } else if (active > index) {
            config().setActiveProfile(active - 1);
        }

        save();
        return true;
    }

    public static boolean deleteActive() {
        return deleteProfile(getActiveIndex());
    }

    public static void setActiveDoubleClick(boolean doubleClick) {
        getActive().setDoubleClick(doubleClick);
        save();
    }

    public static void setActiveClickButton(ClickButton button) {
        getActive().setClickButton(button);
        save();
    }

    public static void save() {
        AutoConfig.getConfigHolder(AutoclickerClothConfig.class).save();
    }

    private static String sanitizeName(String name, List<ConfigProfile> existing) {
        String base = (name == null || name.isBlank()) ? Text.translatable("autoclicker.profile.default_name").getString() : name.trim();

        if (existing.stream().noneMatch(p -> p.getName().equalsIgnoreCase(base))) {
            return base;
        }

        int suffix = 1;
        while (true) {
            String candidate = base + " " + suffix;
            if (existing.stream().noneMatch(p -> p.getName().equalsIgnoreCase(candidate))) {
                return candidate;
            }
            suffix++;
        }
    }

    public static List<String> getProfileNames() {
        List<String> names = new ArrayList<>();
        for (ConfigProfile p : getProfiles()) {
            names.add(p.getName());
        }
        return names;
    }
}
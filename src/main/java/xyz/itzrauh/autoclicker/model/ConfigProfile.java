package xyz.itzrauh.autoclicker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigProfile {

    private String name;
    private int delayMs;
    private boolean doubleClick;
    private ClickButton clickButton;

    public ConfigProfile(String name, int delayMs, boolean doubleClick, ClickButton clickButton) {
        this.name = name;
        this.delayMs = delayMs;
        this.doubleClick = doubleClick;
        this.clickButton = clickButton;
    }

    public ConfigProfile copyWithName(String newName) {
        return new ConfigProfile(newName, delayMs, doubleClick, clickButton);
    }

    @Override
    public String toString() {
        return name;
    }

    public ConfigProfile() {}
}

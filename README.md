<p align="center">
  <img src="assets/banner.png" width="600"/>
</p>

> A Fabric Minecraft mod with multi-profile support, configurable click settings, and full traductions (🇬🇧 English · 🇪🇸 Español · 🇫🇷 Français)

---

## Supported Versions

| Branch | Minecraft | Fabric API |
|--------|-----------|------------|
| `1.20.1` | 1.20.1 | ✅ |
| `1.20.4` | 1.20.4 | ✅ |
| `1.21.1 – 1.21.10` | 1.21.1 → 1.21.10 | ✅ |

> :common is 1.21.10 (base code) and inside :versions are other versions which are patches on the base code for that specific Minecraft version.

---

## Features

- **Auto-clicking** — left or right click, with configurable delay (ms)
- **Double-click mode** — send two clicks per tick for ultra-fast actions
- **Multi-profile system** — up to 10 named profiles (Default, Farming, Mining…)
- **Keybinds** — toggle, cycle profiles, and open the config screen without leaving the game
- **HUD overlay** — shows active profile name when the autoclicker is on
- **Translations** — English, Spanish and French built-in
- **ModMenu integration** — config screen accessible from the mods list

---

## Default Keybinds

| Action | Default Key |
|--------|-------------|
| Toggle autoclicker | `V` |
| Next profile | `N` |
| Previous profile | `M` |
| Open config screen | *(unbound — set in Controls)* |

All keybinds can be rebound in **Options → Controls → AutoClicker**.

---

## Profile System

Each profile stores:
- **Name** — up to 24 characters
- **Delay** — milliseconds between clicks
- **Button** — Left or Right click
- **Double-click** — enabled/disabled

You can create, duplicate, and delete profiles from the in-game config screen (ModMenu → AutoClicker, or via the keybind).

Profiles are saved automatically to `.minecraft/config/autoclicker.json`.

---

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for your Minecraft version.
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) and place it in your `mods/` folder.
3. *(Optional)* Install [ModMenu](https://modrinth.com/mod/modmenu) to access the config screen from the mods list.
4. Download the correct `.jar` for your version from [Releases](../../releases) and drop it in `mods/`.
5. Launch Minecraft. Done ✅

### Required dependencies

| Mod | Required |
|-----|----------|
| Fabric API | ✅ Yes |
| ModMenu | Optional (for GUI config) |
| Cloth Config | ✅ Yes (bundled) |

---

## Translations

The mod ships with translations for:

- 🇬🇧 **English** (`en_us`)
- 🇪🇸 **Español** (`es_es`)
- 🇫🇷 **Français** (`fr_fr`)

Want to add your language? PRs are welcome!

---

## Building from source

```bash
git clone https://github.com/<your-user>/autoclicker-mod.git
cd autoclicker-mod         # e.g. 1.20.1
./gradlew build
# Output: common/build/libs/autoclicker-*.jar or versions/{version}/build/libs/autoclicker-*.jar
```

Requires **Java 17+** and an internet connection for the first build (Gradle downloads dependencies).

---

## Disclaimer

This mod is intended for **single-player** use or on servers that explicitly allow automation mods. Use responsibly. The author is not responsible for bans or other consequences of use on servers where such mods are prohibited.

---

## License

[CC0 1.0 Universal](LICENSE) — this mod is released into the public domain. You can copy, modify, distribute and use it for any purpose, commercial or non-commercial, without asking permission.

---

## Contributing
Contributions are welcome! Whether it's bug fixes, new features, or translations, feel free to open an issue or submit a pull request.
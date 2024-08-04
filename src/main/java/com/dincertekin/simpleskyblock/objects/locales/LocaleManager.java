package com.dincertekin.simpleskyblock.objects.locales;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LocaleManager {

    private String locale;
    private YamlConfiguration file;

    public LocaleManager() { this.initialise(); }

    public void initialise() {
        this.locale = (String) SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().get("general.locale");
        this.file = YamlConfiguration.loadConfiguration(new File(SimpleSkyblock.getPlugin(SimpleSkyblock.class).getDataFolder() + "/locales/" + this.getLocale() + ".yml"));
    }

    public String getLocale() {
        return this.locale;
    }

    public String getString(String path) {
        return this.file.getString(path);
    }
}
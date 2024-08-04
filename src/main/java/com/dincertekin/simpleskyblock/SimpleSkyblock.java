package com.dincertekin.simpleskyblock;

import com.dincertekin.simpleskyblock.commands.CommandManager;
import com.dincertekin.simpleskyblock.events.*;
import com.dincertekin.simpleskyblock.gui.IslandPanel;
import com.dincertekin.simpleskyblock.objects.grids.GridManager;
import com.dincertekin.simpleskyblock.objects.islands.IslandManager;
import com.dincertekin.simpleskyblock.objects.locales.LocaleManager;
import com.dincertekin.simpleskyblock.objects.schematics.SchematicManager;
import com.dincertekin.simpleskyblock.storage.SqlManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SimpleSkyblock extends JavaPlugin {

    private static GridManager gridManager;
    private static SqlManager sqlManager;
    private static IslandManager islandManager;
    private static SchematicManager schematicManager;
    private static LocaleManager localeManager;

    @Override
    public void onEnable() {
        this.initConfig();
        this.initObjects();
        this.initEvents();
        this.initCommands();
        getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }

    public void initConfig() {
        // Load default config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Load default locales
        File file;
        file = new File(getDataFolder().getAbsolutePath() + "locales/en_US.yml");
        if (!file.exists()) {
            saveResource("locales/en_US.yml", false);
        }
        file = new File(getDataFolder().getAbsolutePath() + "schematics/island.schem");
        if (!file.exists()) {
            saveResource("schematics/island.schem", false);
        }
    }

    public void initCommands() {
        getCommand("island").setExecutor(new CommandManager());
        getCommand("skyblockadmin").setExecutor(new CommandManager());
    }

    public void initObjects() {
        gridManager = new GridManager();
        sqlManager = new SqlManager();
        islandManager = new IslandManager();
        schematicManager = new SchematicManager();
        localeManager = new LocaleManager();

        sqlManager.populate();
        islandManager.loadIslands();
    }

    public void initEvents() {
        if (getConfig().getBoolean("island.buildProtection", true)) {
            getServer().getPluginManager().registerEvents(new OnBlockBreak(), this);
            getServer().getPluginManager().registerEvents(new OnBlockPlace(), this);
            getServer().getPluginManager().registerEvents(new OnBlockClick(), this);
        }
        getServer().getPluginManager().registerEvents(new OnBlockForm(), this);
        if (getConfig().getBoolean("island.pvpProtection", true)) {
            getServer().getPluginManager().registerEvents(new OnEntityTarget(), this);
            getServer().getPluginManager().registerEvents(new OnPlayerDamage(), this);
            getServer().getPluginManager().registerEvents(new OnEntityDamageByEntity(), this);
        }
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerTeleport(), this);
        getServer().getPluginManager().registerEvents(new IslandPanel(), this);
    }

    public static GridManager getGridManager() {
        return gridManager;
    }

    public static SqlManager getSqlManager() {
        return sqlManager;
    }

    public static IslandManager getIslandManager() {
        return islandManager;
    }

    public static SchematicManager getSchematicManager() {
        return schematicManager;
    }

    public static LocaleManager getLocaleManager() {
        return localeManager;
    }

}
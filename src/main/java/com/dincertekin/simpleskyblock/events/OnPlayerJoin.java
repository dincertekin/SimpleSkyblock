package com.dincertekin.simpleskyblock.events;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import com.dincertekin.simpleskyblock.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().getBoolean("island.spawnIslandLogin")) {
            if (SimpleSkyblock.getIslandManager().getPlayerIsland(event.getPlayer()) != null) {
                SimpleSkyblock.getIslandManager().getPlayerIsland(event.getPlayer()).getSpawn().setWorld(Bukkit.getWorld((String) SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().get("general.world")));
                event.getPlayer().teleport(SimpleSkyblock.getIslandManager().getPlayerIsland(event.getPlayer()).getSpawn());
                ScoreboardManager.createScoreboard(event.getPlayer());
            }
        }
    }
}
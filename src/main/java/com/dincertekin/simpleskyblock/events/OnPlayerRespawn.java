package com.dincertekin.simpleskyblock.events;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnPlayerRespawn implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().getBoolean("island.respawnIsland")) {
            if (SimpleSkyblock.getIslandManager().getPlayerIsland(event.getPlayer()) != null) {
                new BukkitRunnable() {
                    public void run() {
                        try {
                            SimpleSkyblock.getIslandManager().getPlayerIsland(event.getPlayer()).getSpawn().setWorld(Bukkit.getWorld((String) SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().get("general.world")));
                            event.getPlayer().teleport(SimpleSkyblock.getIslandManager().getPlayerIsland(event.getPlayer()).getSpawn());
                        } catch (Exception ex) {}
                    }
                }.runTaskLater(SimpleSkyblock.getPlugin(SimpleSkyblock.class), 2);
            }
        }
    }
}
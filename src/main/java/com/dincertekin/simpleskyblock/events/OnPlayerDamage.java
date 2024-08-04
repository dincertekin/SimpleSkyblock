package com.dincertekin.simpleskyblock.events;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnPlayerDamage implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (SimpleSkyblock.getGridManager().isInPlayerIsland(player, player.getLocation()) == 1) {
                event.setCancelled(true);
            }
        }
    }
}

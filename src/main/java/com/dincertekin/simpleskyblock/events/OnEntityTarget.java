package com.dincertekin.simpleskyblock.events;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class OnEntityTarget implements Listener {

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player) {
            Player player = ((Player) event.getTarget()).getPlayer();
            assert player != null;
            if (SimpleSkyblock.getGridManager().isInPlayerIsland(player, player.getLocation()) == 1) {
                event.setCancelled(true);
            }
        }
    }
}
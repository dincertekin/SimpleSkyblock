package com.dincertekin.simpleskyblock.events;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnBlockClick implements Listener {

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (SimpleSkyblock.getGridManager().isInPlayerIsland(event.getPlayer(), event.getPlayer().getLocation()) == 1) {
                if (event.getPlayer().hasPermission("simpleskyblock.event.click")) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
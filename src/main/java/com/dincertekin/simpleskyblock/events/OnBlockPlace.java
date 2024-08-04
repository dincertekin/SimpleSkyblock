package com.dincertekin.simpleskyblock.events;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnBlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (SimpleSkyblock.getGridManager().isInPlayerIsland(event.getPlayer(), event.getPlayer().getLocation()) == 1) {
            if (event.getPlayer().hasPermission("simpleskyblock.event.place")){
                event.setCancelled(false);
            } else {
                event.getPlayer().sendMessage(SimpleSkyblock.getLocaleManager().getString("player-place"));
                event.setCancelled(true);
            }
        }
    }
}
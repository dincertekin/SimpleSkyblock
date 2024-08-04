package com.dincertekin.simpleskyblock.events;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (SimpleSkyblock.getGridManager().isInPlayerIsland(event.getPlayer(), event.getBlock().getLocation()) == 1) {
            if (event.getPlayer().hasPermission("simpleskyblock.event.break")) {
                event.setCancelled(false);
            } else {
                event.getPlayer().sendMessage(SimpleSkyblock.getLocaleManager().getString("player-break"));
                event.setCancelled(true);
            }
        }
    }
}
package com.dincertekin.simpleskyblock.events;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnEntityDamageByEntity implements Listener {

    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = ((Player) event.getDamager()).getPlayer();
            assert player != null;
            if (SimpleSkyblock.getGridManager().isInPlayerIsland(player, player.getLocation()) == 1) {
                event.setCancelled(true);
            }
        }
    }
}
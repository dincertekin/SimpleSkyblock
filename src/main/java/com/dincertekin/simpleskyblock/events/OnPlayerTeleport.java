package com.dincertekin.simpleskyblock.events;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import com.dincertekin.simpleskyblock.objects.islands.Island;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class OnPlayerTeleport implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Location toLocation = event.getTo();
        if (toLocation == null) return;

        if (SimpleSkyblock.getGridManager().isInPlayerIsland(event.getPlayer(), toLocation) == 1) {
            for (Island island : SimpleSkyblock.getIslandManager().getIslands()) {
                Location center = SimpleSkyblock.getGridManager().getCenterFromId(island.getId());

                int minX = center.getBlockX() - 256;
                int maxX = center.getBlockX() + 256;
                int minZ = center.getBlockZ() - 256;
                int maxZ = center.getBlockZ() + 256;

                if (toLocation.getBlockX() >= minX && toLocation.getBlockX() <= maxX &&
                        toLocation.getBlockZ() >= minZ && toLocation.getBlockZ() <= maxZ) {

                    OfflinePlayer owner = island.getOwner();
                    event.getPlayer().sendTitle("§aWelcome", "§fTo " + owner.getName() + "'s island", 10, 20, 10);
                    break;
                }
            }
        }
    }
}

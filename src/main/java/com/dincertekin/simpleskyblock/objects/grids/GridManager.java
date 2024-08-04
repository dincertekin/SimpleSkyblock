package com.dincertekin.simpleskyblock.objects.grids;

import com.dincertekin.simpleskyblock.objects.islands.Island;
import com.dincertekin.simpleskyblock.SimpleSkyblock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GridManager {

    public GridManager() { this.initialise(); }

    public void initialise() {}

    public Location getCenterFromId(int id) {
        int x = (id*769);
        int z = (int) (Math.floor(x / 384500)*769);
        return new Location(Bukkit.getWorld("world"), x, 100, z);
    }

    public int isInPlayerIsland(Player player, Location location) {
        if (location.getWorld() == Bukkit.getWorld(SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().getString("general.world"))) {
            if (SimpleSkyblock.getIslandManager().getPlayerIsland(player) != null) {
                Island island = SimpleSkyblock.getIslandManager().getPlayerIsland(player);
                Location center = getCenterFromId(island.getId());

                int minX = center.getBlockX() - 256;
                int maxX = center.getBlockX() + 256;

                int minZ = center.getBlockZ() - 256;
                int maxZ = center.getBlockZ() + 256;

                if (location.getBlockX() >= minX && location.getBlockX() <= maxX) {
                    if (location.getBlockZ() >= minZ && location.getBlockZ() <= maxZ) {
                        return 2;
                    }
                }
                return 1;
            }
            return 1;
        }
        return 0;
    }

}

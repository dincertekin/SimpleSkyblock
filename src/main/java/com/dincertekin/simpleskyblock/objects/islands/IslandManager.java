package com.dincertekin.simpleskyblock.objects.islands;

import com.dincertekin.simpleskyblock.storage.queries.IslandDao;
import com.dincertekin.simpleskyblock.SimpleSkyblock;
import com.dincertekin.simpleskyblock.types.Rank;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.world.World;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class IslandManager {

    public Set<Island> islands;

    public IslandManager() { this.initialise(); };

    public void initialise() {
        this.islands = new HashSet<>();
    }

    public void loadIslands() {
        this.islands = IslandDao.getIslands();
    }

    public void create(OfflinePlayer owner) {
        int islandId = IslandDao.addIsland();

        Location location = SimpleSkyblock.getGridManager().getCenterFromId(islandId);
        Location spawn = new Location(Bukkit.getWorld(SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().getString("general.world")), location.getBlockX(), 100, location.getBlockZ() + 2);

        Island island = new Island(islandId, Biome.BADLANDS, 0, spawn);
        this.islands.add(island);
        island.addPlayer(owner, Rank.OWNER);

        owner.getPlayer().sendMessage(ChatColor.GREEN + "Creating island...");

        island.setSpawn(spawn);
        island.save();

        SimpleSkyblock.getSchematicManager().load(SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().getString("island.schematic"), location.getX(), location.getY(), location.getZ());
        owner.getPlayer().teleport(spawn);
    }

    public static void delete(Island island) {
        Location center = SimpleSkyblock.getGridManager().getCenterFromId(island.getId());

        // Change this method to improve performance.
        Vector min = new Vector(center.getBlockX() - 256, island.getExtensionLevel(), center.getBlockZ() - 256);
        Vector max = new Vector(center.getBlockX() + 256, center.getWorld().getMaxHeight(), center.getBlockZ() + 256);

        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    Block block = center.getWorld().getBlockAt(x, y, z);
                    block.setType(Material.AIR);
                }
            }
        }



        int islandId = island.getId();
        IslandDao.delete(islandId);
    }

    public Island getIslandOwnedBy(OfflinePlayer player) {
        return this.getIslands().stream().filter(island -> island.getOwner().getUniqueId().equals(player.getUniqueId())).findAny().orElse(null);
    }

    public Island getPlayerIsland(OfflinePlayer player) {
        return this.getIslands().stream().filter(island -> island.getPlayers().keySet().stream().filter(offlinePlayer -> offlinePlayer.getUniqueId().equals(player.getUniqueId())).findAny().orElse(null) != null).findAny().orElse(null);
    }

    public Set<Island> getIslands() {
        return this.islands;
    }

}

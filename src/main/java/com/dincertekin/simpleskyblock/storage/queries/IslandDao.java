package com.dincertekin.simpleskyblock.storage.queries;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import com.dincertekin.simpleskyblock.objects.islands.Island;
import com.dincertekin.simpleskyblock.types.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;

public class IslandDao {

    public static void createTables() {
        try {
            Connection conn = SimpleSkyblock.getSqlManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "CREATE TABLE IF NOT EXISTS `islands` (" +
                    "  `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  `biome` TEXT NOT NULL," +
                    "  `extension_level` INTEGER NOT NULL," +
                    "  `spawn_x` REAL NOT NULL DEFAULT 0," +
                    "  `spawn_y` REAL NOT NULL DEFAULT 0," +
                    "  `spawn_z` REAL NOT NULL DEFAULT 0," +
                    "  `spawn_yaw` REAL NOT NULL DEFAULT 0," +
                    "  `spawn_pitch` REAL NOT NULL DEFAULT 0" +
                ")"
            );
            stmt.executeUpdate();
        } catch (SQLException e) {
            SimpleSkyblock.getPlugin(SimpleSkyblock.class).getLogger().info("Something went wrong: " + e);
        }

        try {
            Connection conn = SimpleSkyblock.getSqlManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `islands_users` (" +
                            "  `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  `uuid` TEXT NOT NULL," +
                            "  `island_id` INTEGER NOT NULL," +
                            "  `rank` INTEGER NOT NULL" +
                            ")"
            );
            stmt.executeUpdate();
        } catch (SQLException e) {
            SimpleSkyblock.getPlugin(SimpleSkyblock.class).getLogger().info("Something went wrong: " + e);
        }
    }

    public static Set<Island> getIslands() {
        Set<Island> islands = new HashSet<>();
        try {
            Connection conn = SimpleSkyblock.getSqlManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM islands"
            );
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Island island = new Island(
                        resultSet.getInt("id"),
                        Biome.BAMBOO_JUNGLE,
                        resultSet.getInt("extension_level"),
                        new Location(Bukkit.getWorld(SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().getString("general.world")), resultSet.getFloat("spawn_x"), resultSet.getFloat("spawn_y"), resultSet.getFloat("spawn_z"), resultSet.getFloat("spawn_yaw"), resultSet.getFloat("spawn_pitch")));
                islands.add(island);
            }
        } catch (SQLException e) {
            SimpleSkyblock.getPlugin(SimpleSkyblock.class).getLogger().info("Something went wrong: " + e);
        }
        return islands;
    }

    public static Map<OfflinePlayer, Rank> getPlayers(int islandId) {
        Map<OfflinePlayer, Rank> players = new HashMap<>();
        try {
            Connection conn = SimpleSkyblock.getSqlManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM islands_users WHERE island_id = ?"
            );
            stmt.setInt(1, islandId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                players.put(Bukkit.getOfflinePlayer(UUID.fromString(resultSet.getString("uuid"))), Rank.fromInt(resultSet.getInt("rank")));
            }
        } catch (SQLException e) {
            SimpleSkyblock.getPlugin(SimpleSkyblock.class).getLogger().info("Something went wrong: " + e);
        }
        return players;
    }

    public static int addIsland() {
        try {
            Connection conn = SimpleSkyblock.getSqlManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO islands(biome, extension_level) VALUES(?, ?);",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, "DEFAULT");
            stmt.setInt(2, 0);
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating island failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            SimpleSkyblock.getPlugin(SimpleSkyblock.class).getLogger().info("Something went wrong: " + e);
        }
        return -1;
    }

    public static void save(Island island) {
        try {
            Connection conn = SimpleSkyblock.getSqlManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "UPDATE islands SET biome = ?, extension_level = ?, spawn_x = ?, spawn_y = ?, spawn_z = ?, spawn_yaw = ?, spawn_pitch = ? WHERE id = ?;"
            );
            stmt.setString(1, String.valueOf(island.getBiome()));
            stmt.setInt(2, island.getExtensionLevel());
            stmt.setFloat(3, island.getSpawn().getBlockX());
            stmt.setFloat(4, island.getSpawn().getBlockY());
            stmt.setFloat(5, island.getSpawn().getBlockZ());
            stmt.setFloat(6, island.getSpawn().getYaw());
            stmt.setFloat(7, island.getSpawn().getPitch());
            stmt.setInt(8, island.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            SimpleSkyblock.getPlugin(SimpleSkyblock.class).getLogger().info("Something went wrong: " + e);
        }
    }

    public static void delete(int islandId) {
        try {
            Connection conn = SimpleSkyblock.getSqlManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "DELETE FROM islands WHERE id = ?;"
            );
            stmt.setInt(1, islandId);
            stmt.execute();
        } catch (SQLException e) {
            SimpleSkyblock.getPlugin(SimpleSkyblock.class).getLogger().info("Something went wrong: " + e);
        }
    }

    public static void addPlayer(OfflinePlayer player, Island island, Rank rank) {
        try {
            Connection conn = SimpleSkyblock.getSqlManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO islands_users(`uuid`, `island_id`, `rank`) VALUES(?, ?, ?);"
            );
            stmt.setString(1, player.getUniqueId().toString());
            stmt.setInt(2, island.getId());
            stmt.setInt(3, rank.rank);
            stmt.execute();
        } catch (SQLException e) {
            SimpleSkyblock.getPlugin(SimpleSkyblock.class).getLogger().info("Something went wrong: " + e);
        }
    }

    public static void removePlayer(OfflinePlayer player) {
        try {
            Connection conn = SimpleSkyblock.getSqlManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "DELETE FROM islands_users WHERE uuid = ?;"
            );
            stmt.setString(1, player.getUniqueId().toString());
            stmt.execute();
        } catch (SQLException e) {
            SimpleSkyblock.getPlugin(SimpleSkyblock.class).getLogger().info("Something went wrong: " + e);
        }
    }

    public static void setPlayerIsland(Player player, Island island) {
        try {
            Connection conn = SimpleSkyblock.getSqlManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "UPDATE islands_users SET island_id = ?, rank = ? WHERE uuid = ?;"
            );
            stmt.setInt(1, island.getId());
            stmt.setInt(2, 1);
            stmt.setString(3, player.getUniqueId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            SimpleSkyblock.getPlugin(SimpleSkyblock.class).getLogger().info("Something went wrong: " + e);
        }
    }

}

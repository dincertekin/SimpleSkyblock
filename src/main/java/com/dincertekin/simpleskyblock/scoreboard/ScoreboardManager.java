package com.dincertekin.simpleskyblock.scoreboard;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class ScoreboardManager {

    public static void createScoreboard(Player player) {
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().getString("scoreboard.title")));

        List<String> lines = SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().getStringList("scoreboard.lines");
        int counter = lines.size();
        List<String> placeholderedLines = PlaceholderAPI.setPlaceholders(player, lines);
        for (String line : placeholderedLines) {

            Score score = objective.getScore(ChatColor.translateAlternateColorCodes('&', line));
            score.setScore(counter);
            counter--;
        }



        player.setScoreboard(scoreboard);
    }

}

package com.dincertekin.simpleskyblock.commands.subcommands;

import com.dincertekin.simpleskyblock.interfaces.ISubCommand;
import com.dincertekin.simpleskyblock.SimpleSkyblock;
import com.dincertekin.simpleskyblock.objects.islands.Island;
import com.dincertekin.simpleskyblock.objects.islands.IslandManager;
import com.dincertekin.simpleskyblock.types.Rank;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class LeaveCommand implements ISubCommand {

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leave the current island";
    }

    @Override
    public String getPermission() { return "simpleskyblock.command.leave"; }

    @Override
    public String getSyntax() {
        return "/island leave";
    }

    @Override
    public List<String> getArgs() { return Arrays.asList(); }

    @Override
    public void perform(Player player, String[] args) {
        Island island = SimpleSkyblock.getIslandManager().getPlayerIsland(player);
        if (island != null) {
            if (island.getRank(player).equals(Rank.OWNER)) {
                island.removePlayer(player);
                IslandManager.delete(island);
                player.sendMessage(ChatColor.GREEN + "You left your island and it was deleted.");
                for (OfflinePlayer offlinePlayer : island.getPlayers().keySet()) {
                    if (offlinePlayer.isOnline()) {
                        Player member = offlinePlayer.getPlayer();
                        member.sendMessage(ChatColor.RED + "" + player.getName() + " deleted the island.");
                        island.removePlayer(member);
                    }
                }
            } else {
                island.removePlayer(player);
                player.sendMessage(ChatColor.GREEN + "You left the island.");
                for (OfflinePlayer offlinePlayer : island.getPlayers().keySet()) {
                    if (offlinePlayer.isOnline()) {
                        Player member = offlinePlayer.getPlayer();
                        member.sendMessage(ChatColor.RED + "" + player.getName() + " left your island.");
                    }
                }
            }
        } else {
            player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-no-island"));
        }
    }
}
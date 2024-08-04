package com.dincertekin.simpleskyblock.commands.subcommands;

import com.dincertekin.simpleskyblock.interfaces.ISubCommand;
import com.dincertekin.simpleskyblock.SimpleSkyblock;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class VisitCommand implements ISubCommand {

    @Override
    public String getName() {
        return "visit";
    }

    @Override
    public String getDescription() {
        return "Visit player island";
    }

    @Override
    public String getPermission() { return "simpleskyblock.command.visit"; }

    @Override
    public String getSyntax() {
        return "/island visit {player}";
    }

    @Override
    public List<String> getArgs() { return Arrays.asList(); }

    @Override
    public void perform(Player player, String[] args) {
        Player target = null;

        if (Bukkit.getPlayer(args[1]) != null) {
            target = Bukkit.getPlayer(args[1]);
        } else {
            OfflinePlayer offlinePlayer = SimpleSkyblock.getPlugin(SimpleSkyblock.class).getServer().getOfflinePlayer(args[1]);
            if (offlinePlayer.hasPlayedBefore()) {
                target = offlinePlayer.getPlayer();
            }
        }

        if (target != null && SimpleSkyblock.getIslandManager().getPlayerIsland(player) != null) {
            player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-visit-island").replace("{0}", target.getName()));
            SimpleSkyblock.getIslandManager().getPlayerIsland(target).getSpawn().setWorld(Bukkit.getWorld((String) SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().get("general.world")));
            player.teleport(SimpleSkyblock.getIslandManager().getPlayerIsland(target).getSpawn());
        } else {
            player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-offline-island").replace("{0}", args[1]));
        }
    }
}

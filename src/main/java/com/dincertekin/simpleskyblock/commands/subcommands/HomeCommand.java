package com.dincertekin.simpleskyblock.commands.subcommands;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import com.dincertekin.simpleskyblock.interfaces.ISubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class HomeCommand implements ISubCommand {

    @Override
    public String getName() {
        return "home";
    }

    @Override
    public String getDescription() {
        return "Teleport to island home";
    }

    @Override
    public String getPermission() { return "simpleskyblock.command.home"; }

    @Override
    public String getSyntax() {
        return "/island home";
    }

    @Override
    public List<String> getArgs() { return Arrays.asList(); }

    @Override
    public void perform(Player player, String[] args) {
        if (SimpleSkyblock.getIslandManager().getPlayerIsland(player) != null) {
            player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-teleport-island"));
            SimpleSkyblock.getIslandManager().getPlayerIsland(player).getSpawn().setWorld(Bukkit.getWorld((String) SimpleSkyblock.getPlugin(SimpleSkyblock.class).getConfig().get("general.world")));
            player.teleport(SimpleSkyblock.getIslandManager().getPlayerIsland(player).getSpawn());
        } else {
            player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-no-island"));
        }
    }
}
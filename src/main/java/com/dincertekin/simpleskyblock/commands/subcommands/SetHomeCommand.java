package com.dincertekin.simpleskyblock.commands.subcommands;

import com.dincertekin.simpleskyblock.interfaces.ISubCommand;
import com.dincertekin.simpleskyblock.SimpleSkyblock;
import com.dincertekin.simpleskyblock.storage.queries.IslandDao;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SetHomeCommand implements ISubCommand {

    @Override
    public String getName() {
        return "sethome";
    }

    @Override
    public String getDescription() {
        return "Set home of your island to your position";
    }

    @Override
    public String getPermission() { return "simpleskyblock.command.sethome"; }

    @Override
    public String getSyntax() {
        return "/island sethome";
    }

    @Override
    public List<String> getArgs() { return Arrays.asList(); }

    @Override
    public void perform(Player player, String[] args) {
        if (SimpleSkyblock.getGridManager().isInPlayerIsland(player, player.getLocation()) != 2) {
            player.sendMessage(SimpleSkyblock.getLocaleManager().getString("sethome-out-island"));
        } else {
            SimpleSkyblock.getIslandManager().getPlayerIsland(player).setSpawn(player.getLocation());
            IslandDao.save(SimpleSkyblock.getIslandManager().getPlayerIsland(player));
            player.sendMessage(SimpleSkyblock.getLocaleManager().getString("sethome-success"));
        }
    }
}
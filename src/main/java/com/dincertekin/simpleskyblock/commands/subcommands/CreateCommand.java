package com.dincertekin.simpleskyblock.commands.subcommands;

import com.dincertekin.simpleskyblock.interfaces.ISubCommand;
import com.dincertekin.simpleskyblock.SimpleSkyblock;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CreateCommand implements ISubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create an island";
    }

    @Override
    public String getPermission() { return "simpleskyblock.command.create"; }

    @Override
    public String getSyntax() {
        return "/island create";
    }

    @Override
    public List<String> getArgs() { return Arrays.asList(); }

    @Override
    public void perform(Player player, String[] args) {
        if (SimpleSkyblock.getIslandManager().getPlayerIsland(player) == null) {
            SimpleSkyblock.getIslandManager().create(player);
        } else {
            player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-already-have-island"));
        }
    }
}

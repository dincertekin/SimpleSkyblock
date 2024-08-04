package com.dincertekin.simpleskyblock.commands.subcommands;

import com.dincertekin.simpleskyblock.gui.IslandPanel;
import com.dincertekin.simpleskyblock.interfaces.ISubCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class PanelCommand implements ISubCommand {

    @Override
    public String getName() {
        return "panel";
    }

    @Override
    public String getDescription() {
        return "Open GUI menu";
    }

    @Override
    public String getPermission() { return "simpleskyblock.command.panel"; }

    @Override
    public String getSyntax() {
        return "/island panel";
    }

    @Override
    public List<String> getArgs() { return Arrays.asList(); }

    @Override
    public void perform(Player player, String[] args) {
        IslandPanel.open(player);
    }
}

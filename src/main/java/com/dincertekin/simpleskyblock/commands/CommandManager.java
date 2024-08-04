package com.dincertekin.simpleskyblock.commands;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import com.dincertekin.simpleskyblock.commands.subcommands.*;
import com.dincertekin.simpleskyblock.interfaces.ISubCommand;
import org.antlr.v4.runtime.misc.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {

    private final ArrayList<ISubCommand> subcommands = new ArrayList<>();

    public CommandManager(){
        subcommands.add(new CreateCommand());
        subcommands.add(new HomeCommand());
        subcommands.add(new SetHomeCommand());
        subcommands.add(new VisitCommand());
        subcommands.add(new InviteCommand());
        subcommands.add(new LeaveCommand());
        subcommands.add(new PanelCommand());

        this.initTabCompleter();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("island")) {
            if (args.length > 0){
                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        if (sender.hasPermission(getSubcommands().get(i).getPermission())) {
                            getSubcommands().get(i).perform((Player) sender, args);
                        } else {
                            sender.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-noperm"));
                        }
                    }
                }
            } else if (args.length == 0) {
                for (int i = 0; i < getSubcommands().size(); i++){
                    sender.sendMessage("§e" + getSubcommands().get(i).getSyntax() + " §8- §7" + getSubcommands().get(i).getDescription());
                }
            }
        }
        if (command.getName().equals("skyblockadmin")) {
            if (sender.hasPermission("simpleskyblock.admin")) {
                if (args.length == 0) {
                    sender.sendMessage("§e/skyblockadmin reset §8- §7Reset the island.");
                    sender.sendMessage("§e/skyblockadmin delete {player} §8- §7Delete an island.");
                }
            }
        }
        return true;
    }

    public void initTabCompleter() {
        SimpleSkyblock.getPlugin(SimpleSkyblock.class).getCommand("island").setTabCompleter(new TabCompleter() {
            @Override
            public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
                if (command.getName().equalsIgnoreCase("island")) {
                    List<String> l = new ArrayList<>();
                    if (args.length == 1) {
                        getSubcommands().forEach(subCommand -> {
                            l.add(subCommand.getName());
                        });
                    } else if (args.length == 2) {
                        ISubCommand subCommand = getSubcommands().stream().filter(subCommandFilter -> subCommandFilter.getName().equals(args[0])).findAny().orElse(null);
                        if (subCommand != null) {
                            if (subCommand.getName().equals("visit")) {
                                for (Player o : Bukkit.getOnlinePlayers()) {
                                    l.add(o.getName());
                                }
                            } else {
                                subCommand.getArgs().forEach(arg -> {
                                    l.add(arg);
                                });
                            }
                        }
                    }
                    return l;
                }
                return null;
            }
        });
    }

    public ArrayList<ISubCommand> getSubcommands(){
        return subcommands;
    }

}

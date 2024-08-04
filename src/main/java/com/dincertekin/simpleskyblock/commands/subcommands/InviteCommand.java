package com.dincertekin.simpleskyblock.commands.subcommands;

import com.dincertekin.simpleskyblock.SimpleSkyblock;
import com.dincertekin.simpleskyblock.interfaces.ISubCommand;
import com.dincertekin.simpleskyblock.objects.islands.Island;
import com.dincertekin.simpleskyblock.types.Rank;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InviteCommand implements ISubCommand {

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Invite player to join your island";
    }

    @Override
    public String getPermission() { return "simpleskyblock.command.invite"; }

    @Override
    public String getSyntax() {
        return "/island invite {add | accept | deny}";
    }

    @Override
    public List<String> getArgs() { return Arrays.asList("add", "accept", "deny"); }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length < 3) {
            player.sendMessage(SimpleSkyblock.getLocaleManager().getString("invalid-syntax").replace("{0}", "/island invite <add | accept | deny> <player>"));
            return;
        }

        Island island = SimpleSkyblock.getIslandManager().getPlayerIsland(player);
        Player target = Bukkit.getPlayer(args[2]);

        if(target == null) {
            player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-offline").replace("{0}", args[1]));
            return;
        }

        if (Objects.equals(args[1], "add")) {
            if (island != null) {
                if (island.getRank(player) == Rank.OWNER) {
                    if (SimpleSkyblock.getIslandManager().getPlayerIsland(target) != null && SimpleSkyblock.getIslandManager().getPlayerIsland(target) == island) {
                        player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-already-on-island").replace("{0}", target.getName()));
                        return;
                    }

                    if (island.getInvites() != null && island.getInvites().get(target) == null) {
                        player.sendMessage(ChatColor.GREEN + "Sending an invitation to " + target.getName() + "...");
                        target.sendMessage(ChatColor.GREEN + " ");
                        target.sendMessage(ChatColor.GRAY + player.getName() + " invited you to play on his island? If you accept your island will be deleted.");
                        target.sendMessage(ChatColor.GREEN + " ");

                        TextComponent messageYes = new TextComponent(ChatColor.GREEN + "[ACCEPT]");
                        messageYes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/island accept " + player.getName()));

                        TextComponent messageNo = new TextComponent(ChatColor.RED + "[DECLINE] ");
                        messageNo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/island deny " + player.getName()));

                        messageYes.addExtra(" ");
                        messageYes.addExtra(messageNo);

                        target.spigot().sendMessage(messageYes);
                        target.sendMessage(ChatColor.GREEN + " ");

                        island.getInvites().put(target, player);
                    } else {
                        player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-already-invited").replace("{0}", island.getInvites().get(target).getName()).replace("{1}", target.getName()));
                    }
                } else {
                    player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-no-owner"));
                }
            } else {
                player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-no-island"));
            }
        } else if (Objects.equals(args[1], "accept")) {
            Island newIsland = SimpleSkyblock.getIslandManager().getPlayerIsland(target);
            if (!newIsland.getInvites().isEmpty() && newIsland.getInvites().get(player) != null) {
                if (island != null) {
                    island.removePlayer(player);
                    newIsland.addPlayer(player, Rank.fromInt(1));
                }
                newIsland.addPlayer(player, Rank.fromInt(1));
                target.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-join-island").replace("{0}", player.getName()));
                player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-join-island-success").replace("{0}", target.getName()));
                player.performCommand("is home");
            } else {
                player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-no-invited").replace("{0}", args[1]));
            }
        } else if (Objects.equals(args[1], "deny")) {
            if (!island.getInvites().isEmpty() && island.getInvites().get(player) != null) {
                island.getInvites().remove(player);
                player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-decline-invitation").replace("{0}", target.getName()));
                target.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-decline-your-invitation").replace("{0}", player.getName()));
            } else {
                player.sendMessage(SimpleSkyblock.getLocaleManager().getString("player-no-invited").replace("{0}", args[1]));
            }
        }
    }
}

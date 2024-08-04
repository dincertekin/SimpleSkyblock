package com.dincertekin.simpleskyblock.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class OnBlockForm implements Listener {

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        if (event.getNewState().getType() == Material.COBBLESTONE) {
            double d = Math.random();
            if (d < 0.01) {
                // 1% chance for diamond
                event.getNewState().setType(Material.DIAMOND_ORE);
            } else if (d < 0.08) {
                // 7% chance for iron
                event.getNewState().setType(Material.IRON_ORE);
            } else if (d < 0.13) {
                // 5% chance for gold
                event.getNewState().setType(Material.GOLD_ORE);
            } else if (d < 0.15) {
                // 2% chance for emerald
                event.getNewState().setType(Material.EMERALD_ORE);
            } else if (d < 0.20) {
                // 5% chance for coal
                event.getNewState().setType(Material.COAL_ORE);
            } else {
                // 80% chance for cobblestone
                event.getNewState().setType(Material.COBBLESTONE);
            }
        }
    }
}
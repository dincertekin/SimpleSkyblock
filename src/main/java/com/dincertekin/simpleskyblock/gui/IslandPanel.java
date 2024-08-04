package com.dincertekin.simpleskyblock.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class IslandPanel implements Listener {

    static Inventory inventory;

    public static void open(Player player) {
        inventory = Bukkit.createInventory(null, 18, "Skyblock Menu");

        inventory.addItem(createGuiItem(Material.GRASS_BLOCK, "§aGo to your island", "§a→ §fLeft click to teleport!"));
        inventory.addItem(createGuiItem(Material.OAK_SIGN, "§aSet your home(spawnpoint)", "§a→ §fLeft click to set!"));
        inventory.addItem(createGuiItem(Material.BEDROCK, "§cLeave island", "§c→ §fLeft click to leave!"));

        player.openInventory(inventory);
    }

    public static ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().equals(inventory)) return;
        e.setCancelled(true);
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;
        Player p = (Player) e.getWhoClicked();
        switch (e.getRawSlot()) {
            case 0: {
                p.performCommand("is home");
                break;
            }
            case 1: {
                p.performCommand("is sethome");
                break;
            }
            case 2: {
                p.performCommand("is leave");
                break;
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryDragEvent e) {
        if (e.getInventory().equals(inventory)) {
            e.setCancelled(true);
        }
    }

}

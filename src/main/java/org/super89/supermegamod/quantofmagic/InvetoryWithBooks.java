package org.super89.supermegamod.quantofmagic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class InvetoryWithBooks implements Listener {
    public void OpenInventory(Player player){

        Inventory inventory = Bukkit.createInventory(null, 54, "Все книги");
        setItem(Material.BOOK, 1000, inventory, 0, "Книга Стана");
        setItem(Material.BOOK, 1002, inventory, 1, "Книга телепорта");
        setItem(Material.BOOK, 1230, inventory, 2, "Книга левитации");
        setItem(Material.BOOK, 1010101, inventory, 3, "Книга Вызывателя");
        setItem(Material.BOOK, 1231, inventory, 4, "Книга шахтера");
        setItem(Material.BOOK, 1001, inventory, 5, "Книга Взрыва");
        setItem(Material.IRON_SWORD, 1488 , inventory, 6, "Ненасытный меч");

        player.openInventory(inventory);


    }
    public void setItem(Material material, int custommodeldata, Inventory inv, int slot, String s){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(custommodeldata);
        meta.setDisplayName("§f" + s);
        item.setItemMeta(meta);
        inv.setItem(slot, item);

    }
    @EventHandler
    public void PlayerInteractE(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction().name().contains("RIGHT_CLICK") && Objects.requireNonNull(event.getClickedBlock()).getType() == Material.FLETCHING_TABLE && event.getClickedBlock() != null){
            OpenInventory(player);
        }
    }

}
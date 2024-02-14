package org.super89.supermegamod.customserver;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class TeleportBook implements Listener {

    private final JavaPlugin plugin;

    public TeleportBook(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Проверяем, является ли предмет книгой
        if (item.getType() == Material.BOOK && event.getAction().name().contains("RIGHT") && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1002 ) {

            // Проверяем, является ли название книги "Teleport Book"
            if(item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1002) {
                // Телепортируем игрока на 5 блоков по направлению курсора
                Location location = player.getLocation();
                Vector direction = location.getDirection().normalize().multiply(5);
                Location teleportLocation = location.add(direction);
                player.teleport(teleportLocation);
            }
        }
    }
}

package org.super89.supermegamod.customserver;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ExplosionBook implements Listener {


    private CustomServer plugin;

    public ExplosionBook(CustomServer plugin) {
        this.plugin = plugin;
    }
    Mana mana = new Mana(plugin);


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Проверяем, является ли предмет книгой
        if (event.getAction().name().contains("RIGHT") && item.getType() == Material.BOOK) {
            if(item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1001 && mana.getNowPlayerMana(player) >= 25){
                mana.setNowPlayerMana(player, mana.getNowPlayerMana(player)-25);




            // Проверяем, является ли название книги "Explosion Book"
                // Создаем взрыв в направлении курсора игрока
                Location location = player.getLocation();
                Vector direction = location.getDirection();
                location.add(direction);
                location.getWorld().createExplosion(location, 4.0f);

        }
        }
    }
}

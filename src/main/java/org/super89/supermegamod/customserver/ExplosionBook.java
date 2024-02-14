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

    private final JavaPlugin plugin;

    public ExplosionBook(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Проверяем, является ли предмет книгой
        if (event.getAction().name().contains("RIGHT") && item.getType() == Material.BOOK) {
            if(item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1001){



            // Проверяем, является ли название книги "Explosion Book"
                // Создаем взрыв в направлении курсора игрока
                Location location = player.getLocation();
                Vector direction = location.getDirection();
                location.add(direction);
                location.getWorld().createExplosion(location, 4.0f);

        }
        }
        if (item != null && item.getType() == Material.DIAMOND_SWORD && item.getItemMeta().hasEnchant(Enchantment.DAMAGE_ALL)) {
            // Добавьте дополнительные проверки, если необходимо

            // Здесь вы можете добавить свою логику для Зенита

            // Пример: при использовании правой кнопки мыши
            if (event.getAction().toString().contains("RIGHT")) {
                // Действия, которые происходят при использовании Зенита
                player.sendMessage("Вы использовали Зенит!");

                // Воспроизводим звук
                player.playSound(player.getLocation(), "entity.player.attack.crit", 1.0f, 1.0f);

                // Создаем партиклы вокруг игрока
                new BukkitRunnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        if (count >= 20) {
                            cancel();
                            return;
                        }

                        player.getWorld().spawnParticle(org.bukkit.Particle.CRIT_MAGIC, player.getLocation().add(0, 1, 0), 10, 0.5, 0.5, 0.5);
                        count++;
                    }
                }.runTaskTimer(new CustomServer(), 0, 1);
            }
        }
    }
}

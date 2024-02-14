package org.super89.supermegamod.customserver;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomSword implements Listener {

    private final JavaPlugin plugin;

    public CustomSword(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        // Проверяем, что атакующий - игрок
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();

            // Проверяем, что предмет в руке игрока - меч
            if (item.getType() == Material.IRON_SWORD && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1488) {
                // Наносим урон врагу и отнимаем голод у игрока
                event.setDamage(6); // 3 сердца = 6 единиц урона
                player.setFoodLevel(player.getFoodLevel() - 6);
            }
        }
    }
}

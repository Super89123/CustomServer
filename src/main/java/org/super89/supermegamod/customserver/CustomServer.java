package org.super89.supermegamod.customserver;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class CustomServer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1000);
        meta.setDisplayName(ChatColor.GOLD + "Книга стана");
        item.setItemMeta(meta);
        getServer().getPluginManager().registerEvents(this, this);
        ShapedRecipe shapedRecipe = new ShapedRecipe(item);
        shapedRecipe.shape("   ", " O ", "   ");
        shapedRecipe.setIngredient('O', Material.DIAMOND);
        Bukkit.addRecipe(shapedRecipe);

        // Plugin startup logic
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Проверяем, что игрок правым кликом использовал книгу
        if (player.getInventory().getItemInMainHand().getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1000) {
            // Получаем позицию, на которую игрок смотрит
            Location targetLocation = player.getTargetBlock(null, 100).getLocation();

            // Создаем куб из черных партиклов
            createParticleCube(targetLocation, 5, 5, 5, Particle.SCULK_SOUL);

            // Замораживаем игроков, попавших в партиклы, и наносим им урон
            freezeAndDamagePlayersInParticles(targetLocation, 5, 5, 5, 3, 0.1);
        }
    }

    private void createParticleCube(Location location, int width, int height, int depth, Particle particle) {
        double offsetX = 0.5; // Смещение по X для центрирования куба
        double offsetY = 0.5; // Смещение по Y для центрирования куба
        double offsetZ = 0.5; // Смещение по Z для центрирования куба

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    double particleX = location.getX() + offsetX + x;
                    double particleY = location.getY() + offsetY + y;
                    double particleZ = location.getZ() + offsetZ + z;

                    location.getWorld().spawnParticle(particle, particleX, particleY, particleZ, 1);
                }
            }
        }
    }

    private void freezeAndDamagePlayersInParticles(Location location, int width, int height, int depth, int freezeDurationSeconds, double damagePerSecond) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isPlayerInCube(player.getLocation(), location, width, height, depth)) {
                // Замораживаем игрока на freezeDurationSeconds секунд
                freezePlayer(player, freezeDurationSeconds);

                // Наносим игроку урон damagePerSecond сердечка в секунду
                damagePlayer(player, damagePerSecond, freezeDurationSeconds);
            }
        }
    }

    private boolean isPlayerInCube(Location playerLocation, Location cubeLocation, int width, int height, int depth) {
        double playerX = playerLocation.getX();
        double playerY = playerLocation.getY();
        double playerZ = playerLocation.getZ();

        double cubeX = cubeLocation.getX();
        double cubeY = cubeLocation.getY();
        double cubeZ = cubeLocation.getZ();

        return playerX >= cubeX && playerX < cubeX + width &&
                playerY >= cubeY && playerY < cubeY + height &&
                playerZ >= cubeZ && playerZ < cubeZ + depth;
    }

    private void freezePlayer(Player player, int durationSeconds) {
        player.setWalkSpeed(0); // Устанавливаем скорость ходьбы 0
        player.setFlySpeed(0); // Устанавливаем скорость полета 0

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setWalkSpeed(0.2f); // Восстанавливаем скорость ходьбы
                player.setFlySpeed(0.1f); // Восстанавливаем скорость полета
            }
        }.runTaskLater(this, durationSeconds * 20L); // Замораживаем игрока на durationSeconds секунд (20 тиков = 1 секунда)
    }

    private void damagePlayer(Player player, double damagePerSecond, int durationSeconds) {
        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                player.damage(damagePerSecond / 2); // Наносим половину урона каждые 10 тиков (0.5 секунды)
                ticks++;

                if (ticks >= durationSeconds * 2) {
                    cancel();
                }
            }
        }.runTaskTimer(this, 0L, 10L); // Запускаем задачу с интервалом 10 тиков (0.5 секунды)
    }
}
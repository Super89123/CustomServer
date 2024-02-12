package org.super89.supermegamod.customserver;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class CustomServer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        // Plugin startup logic
    }
        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            Player player = event.getPlayer();

            // Проверяем, что игрок правым кликом использовал книгу
            if (player.getInventory().getItemInMainHand().getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK")) {
                // Получаем позицию, на которую игрок смотрит
                Location targetLocation = player.getTargetBlock(null, 100).getLocation();

                // Создаем прямоугольник из черных партиклов
                createParticleRectangle(targetLocation, 5, 5, Particle.PORTAL);

                // Замораживаем игроков, попавших в партиклы
                freezePlayersInParticles(targetLocation, 5, 5);
            }
        }

        private void createParticleRectangle(Location location, int width, int height, Particle particle) {
            double offsetX = 0.5; // Смещение по X для центрирования прямоугольника
            double offsetY = 0.5; // Смещение по Y для центрирования прямоугольника
            double offsetZ = 0.5; // Смещение по Z для центрирования прямоугольника

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    double particleX = location.getX() + offsetX + x;
                    double particleY = location.getY() + offsetY;
                    double particleZ = location.getZ() + offsetZ + y;

                    location.getWorld().spawnParticle(particle, particleX, particleY, particleZ, 1);
                }
            }
        }

        private void freezePlayersInParticles(Location location, int width, int height) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (isPlayerInRectangle(player.getLocation(), location, width, height)) {
                    // Замораживаем игрока на 1 секунду
                    freezePlayer(player);
                }
            }
        }

        private boolean isPlayerInRectangle(Location playerLocation, Location rectangleLocation, int width, int height) {
            double playerX = playerLocation.getX();
            double playerY = playerLocation.getY();
            double playerZ = playerLocation.getZ();

            double rectangleX = rectangleLocation.getX();
            double rectangleY = rectangleLocation.getY();
            double rectangleZ = rectangleLocation.getZ();

            return playerX >= rectangleX && playerX < rectangleX + width &&
                    playerY >= rectangleY && playerY < rectangleY + 1 &&
                    playerZ >= rectangleZ && playerZ < rectangleZ + height;
        }

        private void freezePlayer(Player player) {
            player.setWalkSpeed(0); // Устанавливаем скорость ходьбы 0
            player.setFlySpeed(0); // Устанавливаем скорость полета 0

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setWalkSpeed(0.2f); // Восстанавливаем скорость ходьбы
                    player.setFlySpeed(0.1f); // Восстанавливаем скорость полета
                }
            }.runTaskLater(this, 40L); // Замораживаем игрока на 1 секунду (20 тиков = 1 секунда)
        }
    }
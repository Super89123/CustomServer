package org.super89.supermegamod.customserver;

import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public final class CustomServer extends JavaPlugin implements Listener {
    Mana mana = new Mana(this);
    private static CustomServer plugin;

    LifeStealEnchantmentBook lifeStealEnchantmentBook = new LifeStealEnchantmentBook(this);

    @Override
    public void onEnable() {
        ItemStack stanbook = new ItemStack(Material.BOOK);
        ItemStack TeleportBook = new ItemStack(Material.BOOK);
        ItemStack ExplosionBook = new ItemStack(Material.BOOK);
        ItemMeta TeleportBookMeta = TeleportBook.getItemMeta();
        ItemMeta stanbookmeta = stanbook.getItemMeta();
        stanbookmeta.setCustomModelData(1000);
        stanbookmeta.setDisplayName(ChatColor.DARK_GRAY + "Книга Стана");
        stanbook.setItemMeta(stanbookmeta);
        getServer().getPluginManager().registerEvents(this, this);
        ShapedRecipe shapedRecipe = new ShapedRecipe(stanbook);
        shapedRecipe.shape("PPP", "OBO", "PPP");
        shapedRecipe.setIngredient('O', Material.DIAMOND);
        shapedRecipe.setIngredient('B', Material.BOOK);
        shapedRecipe.setIngredient('P', Material.HONEY_BOTTLE);
        Bukkit.addRecipe(shapedRecipe);

        TeleportBookMeta.setCustomModelData(1002);
        TeleportBookMeta.setDisplayName(ChatColor.DARK_PURPLE + "Книга Телепорта");
        TeleportBook.setItemMeta(TeleportBookMeta);
        ShapedRecipe TeleportBookRecipe = new ShapedRecipe(TeleportBook);
        TeleportBookRecipe.shape("EEE", "DXD", "EEE");
        TeleportBookRecipe.setIngredient('E', Material.ENDER_PEARL);
        TeleportBookRecipe.setIngredient('X', Material.BOOK);
        TeleportBookRecipe.setIngredient('D', Material.DIAMOND);
        Bukkit.addRecipe(TeleportBookRecipe);

        ItemMeta ExplosionBookMeta = ExplosionBook.getItemMeta();
        ExplosionBookMeta.setCustomModelData(1001);
        ExplosionBookMeta.setDisplayName(ChatColor.GOLD + "Книга Взрыва");
        ExplosionBook.setItemMeta(ExplosionBookMeta);
        ShapedRecipe ExplosionBookRecipe = new ShapedRecipe(ExplosionBook);
        ExplosionBookRecipe.shape("BBB", "DPD", "BBB");
        ExplosionBookRecipe.setIngredient('P', Material.BOOK);
        ExplosionBookRecipe.setIngredient('D', Material.DIAMOND);
        ExplosionBookRecipe.setIngredient('B', Material.GUNPOWDER);
        Bukkit.addRecipe(ExplosionBookRecipe);

        Bukkit.getPluginManager().registerEvents(new TeleportBook(this), this);
        Bukkit.getPluginManager().registerEvents(new ExplosionBook(this), this);
        Bukkit.getPluginManager().registerEvents(mana, this);


        ItemStack Hungry_sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta Hungry_swordMeta = Hungry_sword.getItemMeta();
        Hungry_swordMeta.setCustomModelData(1488);
        Hungry_swordMeta.setDisplayName(ChatColor.DARK_RED + "Ненасытный меч");
        Hungry_sword.setItemMeta(Hungry_swordMeta);
        ShapedRecipe Hungry_swordRecipe = new ShapedRecipe(Hungry_sword);
        Hungry_swordRecipe.shape("BEB", "MSM", "BNB");
        Hungry_swordRecipe.setIngredient('B', Material.BONE);
        Hungry_swordRecipe.setIngredient('S', Material.IRON_SWORD);
        Hungry_swordRecipe.setIngredient('E', Material.SPIDER_EYE);
        Hungry_swordRecipe.setIngredient('M', Material.ROTTEN_FLESH);
        Hungry_swordRecipe.setIngredient('N', Material.ENDER_PEARL);
        Bukkit.addRecipe(Hungry_swordRecipe);
        plugin = this;


        getServer().getPluginCommand("giveenchantmentbook").setExecutor(new commands());
        // Plugin startup logic

        new BukkitRunnable() {
            @Override
            public void run() {
                if(Bukkit.getOnlinePlayers().isEmpty()){
                    return;
                }
                for (Player player : Bukkit.getOnlinePlayers()){
                    String uuid = player.getUniqueId().toString();
                    File playerDataFile = new File(getPlugin(CustomServer.class).getDataFolder(), "playerdata.yml");
                    FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
                    int maxmana = playerDataConfig.getInt(uuid + "." + "maxmana");
                    int nowmana = playerDataConfig.getInt(uuid + "." + "nowmana");
                    if(nowmana<maxmana){
                        int newmana = (int) (maxmana * 0.05);
                        if(newmana > maxmana){
                            newmana = maxmana;
                        }
                        playerDataConfig.set(uuid + "." + "nowmana", nowmana+newmana);
                        try {
                            playerDataConfig.save(playerDataFile);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" actionbar [{\"text\":\"Мана:" + nowmana + "/"+ maxmana + "\",\"color\":\"aqua\"}]");

                }
            }
        }.runTaskTimer(this, 0L, 40L);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Проверяем, что игрок правым кликом использовал книгу
        if (player.getInventory().getItemInMainHand().getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1000 && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && mana.getNowPlayerMana(player)>=10) {
            mana.setNowPlayerMana(player, mana.getNowPlayerMana(player)-10);
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
    public static CustomServer getPlugin() {
        return plugin;
    }
}
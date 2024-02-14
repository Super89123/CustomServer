package org.super89.supermegamod.customserver;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class LifeStealEnchantmentBook implements Listener {



    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Проверяем, что игрок нажал правой кнопкой мыши с книгой зачарования
        if (event.getAction().toString().contains("RIGHT_CLICK") && item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();

            // Проверяем, что книга зачарования содержит только одно зачарование
            if (meta.getStoredEnchants().size() == 1) {
                // Проверяем, что зачарование - это наше собственное зачарование на увеличение кражи жизни
                if (meta.getStoredEnchants().containsKey(getLifeStealEnchantment())) {
                    // Применяем зачарование на меч
                    item.removeEnchantment(getLifeStealEnchantment());
                    item.addEnchantment(getLifeStealEnchantment(), meta.getStoredEnchantLevel(getLifeStealEnchantment()));
                    player.sendMessage("Книга зачарования на увеличение кражи жизни успешно применена к мечу!");
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        if (damager instanceof Player) {
            Player player = (Player) damager;
            ItemStack item = player.getInventory().getItemInMainHand();

            // Проверяем, что меч имеет зачарование на увеличение кражи жизни
            if (item.containsEnchantment(getLifeStealEnchantment())) {
                int level = item.getEnchantmentLevel(getLifeStealEnchantment());
                double healthToSteal = event.getFinalDamage() * (level * 0.1); // Увеличиваем кражу жизни в зависимости от уровня зачарования

                // Восстанавливаем здоровье игроку
                double newHealth = player.getHealth() + healthToSteal;
                if (newHealth > player.getMaxHealth()) {
                    newHealth = player.getMaxHealth();
                }
                player.setHealth(newHealth);
            }
        }
    }
    public static ItemStack createEnchantmentBook() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        meta.addStoredEnchant(getLifeStealEnchantment(), 1, true);
        book.setItemMeta(meta);
        return book;
    }

    private static Enchantment getLifeStealEnchantment() {
        // Создаем собственное зачарование на увеличение кражи жизни
        NamespacedKey key = new NamespacedKey(new CustomServer(), "life_steal");
        Enchantment.registerEnchantment(new LifeStealEnchantment(key));
        return Enchantment.getByKey(key);
    }

    private static class LifeStealEnchantment extends Enchantment {

        protected LifeStealEnchantment(NamespacedKey key) {
            super(key);
        }

        @Override
        public String getName() {
            return "LifeSteal";
        }

        @Override
        public int getMaxLevel() {
            return 3;
        }

        @Override
        public int getStartLevel() {
            return 1;
        }

        @Override
        public EnchantmentTarget getItemTarget() {
            return EnchantmentTarget.WEAPON;
        }

        @Override
        public boolean isTreasure() {
            return true;
        }

        @Override
        public boolean isCursed() {
            return false;
        }

        @Override
        public boolean canEnchantItem(ItemStack item) {
            return item.getType().name().endsWith("_SWORD");
        }

        @Override
        public @NotNull Component displayName(int i) {
            return null;
        }

        @Override
        public boolean isTradeable() {
            return false;
        }

        @Override
        public boolean isDiscoverable() {
            return false;
        }

        @Override
        public @NotNull EnchantmentRarity getRarity() {
            return null;
        }

        @Override
        public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
            return 0;
        }

        @Override
        public @NotNull Set<EquipmentSlot> getActiveSlots() {
            return null;
        }

        @Override
        public boolean conflictsWith(Enchantment other) {
            return false;
        }

        @Override
        public @NotNull String translationKey() {
            return null;
        }
    }
}
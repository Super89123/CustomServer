package org.super89.supermegamod.quantofmagic;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class TeleportBook implements Listener {

    private QuantofMagic plugin;

    public TeleportBook(QuantofMagic plugin) {
        this.plugin = plugin;
    }
    Mana mana = new Mana(QuantofMagic.getPlugin());

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Проверяем, является ли предмет книгой
        if (item.getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1002) {

            // Проверяем, является ли название книги "Teleport Book"
            if(item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1002 && mana.getNowPlayerMana(player)>= 20 && event.getClickedBlock() == null) {
                // Телепортируем игрока на 5 блоков по направлению курсора
                mana.setNowPlayerMana(player, mana.getNowPlayerMana(player)-20);
                Location location = player.getLocation();
                Vector direction = location.getDirection().normalize().multiply(5);
                Location teleportLocation = location.add(direction);
                player.teleport(teleportLocation);
                player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
            }
        }
    }
}

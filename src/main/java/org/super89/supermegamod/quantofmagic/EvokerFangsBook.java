package org.super89.supermegamod.quantofmagic;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class EvokerFangsBook implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getItemMeta().getCustomModelData() == 1010101 && event.getItem().getItemMeta().hasCustomModelData()) {
                Player player = event.getPlayer();
                Location location = player.getEyeLocation().clone(); // Изменение здесь
                Vector direction = player.getLocation().getDirection();


                // Summon the evoker_fangs
                for (int i = 0; i < 10; i++) {
                    Location evokerFangsLocation = location.clone().add(direction.multiply(1.5 * i));
                    player.getWorld().spawnEntity(evokerFangsLocation, EntityType.EVOKER_FANGS);

                    // Set the direction of the evoker_fangs
                    Entity evokerFangs = player.getWorld().getNearbyEntities(evokerFangsLocation, 1, 1, 1).stream()
                            .filter(entity -> entity.getType() == EntityType.EVOKER_FANGS)
                            .findFirst()
                            .orElse(null);
                    if (evokerFangs != null) {
                        evokerFangs.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
                    }
            }
        }
    }
}
}


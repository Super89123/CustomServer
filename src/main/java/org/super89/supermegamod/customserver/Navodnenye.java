package org.super89.supermegamod.customserver;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Navodnenye implements Listener {
    boolean nav = false;
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if(nav) {
            Player player = e.getPlayer();
            Location location = player.getLocation();
            double y = location.getY();

        }

    }
}

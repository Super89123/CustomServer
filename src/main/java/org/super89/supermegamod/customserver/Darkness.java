package org.super89.supermegamod.customserver;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public final class Darkness implements Listener{

    private CustomServer plugin;

    public Darkness(CustomServer plugin){this.plugin=plugin;}
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        while (true){
            if (player.getLocation().getBlock().getState().getLightLevel() < 6) {
                player.setHealth(player.getHealth() - 2d);
            }
        }
    }


}

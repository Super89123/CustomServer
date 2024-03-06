package org.super89.supermegamod.customserver;

import org.bukkit.entity.Player;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class Madness implements Listener {
    //ещё надо доработать
    private CustomServer plugin;
    private HashMap<String, Integer> madnessmap = new HashMap<String, Integer>();
    public Madness(CustomServer plugin){this.plugin=plugin;}
    @EventHandler
    public void onKill(PlayerDeathEvent e)
    {
        Player player= e.getEntity().getKiller();
        String uid = player.getUniqueId().toString();
        if (madnessmap.get(uid) == 5){
            plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1800, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1800, 2));
                }
            }, 1800);

        }
        else{
            int ee = madnessmap.get(uid);
            madnessmap.remove(uid);
            madnessmap.put(uid, ee+1);
        }




    }
}

package org.super89.supermegamod.customserver;

import org.bukkit.World.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Madness extends Thread implements Listener{
    //готово
    private CustomServer plugin;

    public Madness(CustomServer plugin){this.plugin=plugin;}
    @EventHandler
    public void onKill(PlayerDeathEvent e) throws InterruptedException {
        Player player= e.getEntity().getKiller();
        if(player != null && player instanceof Player) {
            String uid = player.getUniqueId().toString();
            File playerDataFile = new File(CustomServer.getPlugin().getDataFolder(), "madnesslevel.yml");
            FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
            if (!player.hasPlayedBefore()) {
                playerDataConfig.set(uid + "." + "madnesslevel", 1);
                try {
                    playerDataConfig.save(playerDataFile);
                }catch (IOException ee){
                    ee.printStackTrace();
                }
            } else {
                int ml = playerDataConfig.getInt(uid + "." + "madnesslevel") + 1;
                playerDataConfig.set(uid + "." + "madnesslevel", ml);
                try {
                    playerDataConfig.save(playerDataFile);
                }catch (IOException ee){
                    ee.printStackTrace();
                }
            }

            if (playerDataConfig.getInt(uid + "." + "madnesslevel") == 5) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 5));
                playerDataConfig.set(uid + "." + "madnesslevel", 0);
                try {
                    playerDataConfig.save(playerDataFile);
                }catch (IOException ee){
                    ee.printStackTrace();
                }
                Thread.sleep(30000);//обязательная фигня
                player.chat("/" + "playsound minecraft:HorseStepping master " + player.getName() + " ~ ~ ~");
            }




        }
    }

}

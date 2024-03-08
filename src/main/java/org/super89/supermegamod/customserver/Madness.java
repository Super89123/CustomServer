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
import java.util.HashMap;

public class Madness extends Thread implements Listener{
    //готово
    private CustomServer plugin;

    public Madness(CustomServer plugin){this.plugin=plugin;}
    @EventHandler
    public void onKill(PlayerDeathEvent e) throws InterruptedException {
        Player player= e.getEntity().getKiller();
        String uid = player.getUniqueId().toString();
        File playerDataFile = new File(CustomServer.getPlugin().getDataFolder(), "madnesslevel.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        if(!player.hasPlayedBefore()){
            playerDataConfig.set(uid + "." + "madnesslevel", 1);
        }else{
            int ml = playerDataConfig.getInt(uid + "." + "madnesslevel") + 1;
            playerDataConfig.set(uid + "." + "madnesslevel", ml);
        }

        if(playerDataConfig.getInt(uid + "." + "madnesslevel") == 5){
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 1 ));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 5));
            playerDataConfig.set(uid + "." + "madnesslevel", 0);
            player.chat("/" + "playsound minecraft:HorseSteppin master " + player.getName() + " ~ ~ ~");
        }

        Thread.sleep(30000);


        }

}

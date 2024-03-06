package org.super89.supermegamod.customserver;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class Mana implements Listener {
    private CustomServer plugin;
    public Mana(CustomServer plugin){this.plugin=plugin;}


    public int getNowPlayerMana(Player player) {
        String playerUUID = player.getUniqueId().toString();
        File playerDataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        int a = playerDataConfig.getInt(playerUUID + "." + "nowmana");

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }
        public void setNowPlayerMana(Player player, int mana) {
            String playerUUID = player.getUniqueId().toString();
            File playerDataFile = new File(plugin.getDataFolder(), "playerdata.yml");
            FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
            playerDataConfig.set(playerUUID + "." + "nowmana", mana);

            try {
                playerDataConfig.save(playerDataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }




        }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        File playerDataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        if(!player.hasPlayedBefore()){
            playerDataConfig.set(uuid + "." + "maxmana", 10);
            playerDataConfig.set(uuid + "." + "nowmana", 10);


        }
        try {
            playerDataConfig.save(playerDataFile);
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
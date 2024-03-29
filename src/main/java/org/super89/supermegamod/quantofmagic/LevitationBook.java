package org.super89.supermegamod.quantofmagic;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LevitationBook implements Listener {
    Mana mana = new Mana(QuantofMagic.getPlugin());

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1230 && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && mana.getNowPlayerMana(player)>=10){
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10 ,1, false, false,false));

        }
    }
}

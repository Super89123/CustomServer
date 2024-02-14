package org.super89.supermegamod.customserver;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("giveenchantmentbook")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ItemStack book = LifeStealEnchantmentBook.createEnchantmentBook();
                player.getInventory().addItem(book);
                player.sendMessage("Вы получили книгу зачарования на увеличение кражи жизни!");
                return true;
            } else {
                sender.sendMessage("Эту команду можно использовать только в игре!");
                return false;
            }
        }
        return false;
    }
}


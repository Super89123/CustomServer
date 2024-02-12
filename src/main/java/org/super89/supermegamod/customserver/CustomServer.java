package org.super89.supermegamod.customserver;

import org.bukkit.plugin.java.JavaPlugin;

public final class CustomServer extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Navodnenye(), this);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

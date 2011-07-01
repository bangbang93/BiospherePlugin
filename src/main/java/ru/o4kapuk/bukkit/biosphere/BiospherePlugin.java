
package ru.o4kapuk.bukkit.biosphere;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World;
import org.bukkit.World.Environment;

/**
 * Sample plugin for Bukkit
 *
 * @author o4kapuk
 */
public class BiospherePlugin extends JavaPlugin {
    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled" );
    }

    public void onEnable() {
        World bio = getServer().getWorld("biosphere");
        if(null == bio) {
            BiosphereGenerator gen = new BiosphereGenerator();
            bio = getServer().createWorld("biosphere",Environment.SKYLANDS,getServer().getWorlds().get(0).getSeed(), gen);
        }
        
        // Register our commands
        getCommand("biosphere").setExecutor(new BiosphereCommand(this));

        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " enabled" );
    }
}

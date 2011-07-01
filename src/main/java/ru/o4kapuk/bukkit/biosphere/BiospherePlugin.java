
package ru.o4kapuk.bukkit.biosphere;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

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
        // Register our commands
        getCommand("biosphere").setExecutor(new BiosphereCommand(this));

        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " enabled" );
    }
}

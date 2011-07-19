
package ru.o4kapuk.bukkit.biosphere;

import ru.o4kapuk.bukkit.biosphere.command.BiosphereCommand;
import ru.o4kapuk.bukkit.biosphere.command.BiomeCommand;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event;
/**
 * Sample plugin for Bukkit
 *
 * @author o4kapuk
 */
public class BiospherePlugin extends JavaPlugin {
    private final BiospherePlayerListener playerListener = new BiospherePlayerListener();

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled" );
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);
        reload();

        World bio = getServer().getWorld("biosphere");
        if(null == bio) {
            BiosphereGenerator gen = new BiosphereGenerator();
            bio = getServer().createWorld("biosphere",Environment.SKYLANDS,getServer().getWorlds().get(0).getSeed(), gen);
        }
        
        // Register our commands
        getCommand("biosphere").setExecutor(new BiosphereCommand(this));
        getCommand("biome").setExecutor(new BiomeCommand(this));

        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " enabled" );
    }

    public void reload()
    {
    	getConfiguration().load();
        BiosphereGenerator.NOISE = getConfiguration().getBoolean("biosphere.noise", true);
        BiosphereGenerator.DOME_TYPE = (byte) getConfiguration().getInt("biosphere.dome_type", 20);
        BiosphereGenerator.BRIDGE_SIZE = (byte) getConfiguration().getInt("biosphere.bridge_size", 3);
        BiosphereGenerator.BRIDGE_SUPPORT = (byte) getConfiguration().getInt("biosphere.bridge_support", 5);
        BiosphereGenerator.BRIDGE_RAIL = (byte) getConfiguration().getInt("biosphere.bridge_rail", 85);
        BiosphereGenerator.SPECIAL_RADIUS = getConfiguration().getInt("biosphere.special_radius", 7);
        BiosphereGenerator.LAVA_LEVEL = (byte) getConfiguration().getInt("biosphere.lava_level", 24);
    	getConfiguration().save();
    }
}

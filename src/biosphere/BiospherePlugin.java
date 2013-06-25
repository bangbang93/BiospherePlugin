package biosphere;


import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
//import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import biosphere.command.BiomeCommand;
import biosphere.command.BiosphereCommand;
import biosphere.command.TestCommand;
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
//        PluginManager pm = getServer().getPluginManager();
//        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);
//        pm.registerEvent(Event.Type.WORLD_INIT, worldListener, Event.Priority.High, this);
//        reload();
    	
        World bio = getServer().getWorld("biosphere");
        if(null == bio) {
//            BiosphereGenerator gen = new BiosphereGenerator();
//            bio = getServer().createWorld("biosphere",Environment.NORMAL,getServer().getWorlds().get(0).getSeed(), gen);
        	/*
        	WorldCreator wc = new WorldCreator("biosphere");
            wc.environment(Environment.NORMAL);
            wc.seed(getServer().getWorlds().get(0).getSeed());
            wc.generator((ChunkGenerator) (bio = getServer().createWorld(wc)));
            */
        	WorldCreator wc = new WorldCreator("biosphere");
            wc.environment(Environment.NORMAL);
            wc.seed(getServer().getWorlds().get(0).getSeed());
            BiosphereGenerator gen=new BiosphereGenerator();
            wc.generator(gen);
            System.out.printf("start making world");
            bio=getServer().createWorld(wc);
        }
        
        // Register our commands
        getCommand("biosphere").setExecutor(new BiosphereCommand(this));
        getCommand("biome").setExecutor(new BiomeCommand(this));
        getCommand("test").setExecutor(new TestCommand(this));

        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " enabled" );
    }

    public void reload()
    {
//    	getConfig().load();
    	getConfig().toString();
        BiosphereGenerator.NOISE = getConfig().getBoolean("biosphere.noise", true);
        BiosphereGenerator.DOME_TYPE = (byte) getConfig().getInt("biosphere.dome_type", 20);
        BiosphereGenerator.BRIDGE_SIZE = (byte) getConfig().getInt("biosphere.bridge_size", 3);
        BiosphereGenerator.BRIDGE_SUPPORT = (byte) getConfig().getInt("biosphere.bridge_support", 5);
        BiosphereGenerator.BRIDGE_RAIL = (byte) getConfig().getInt("biosphere.bridge_rail", 85);
        BiosphereGenerator.SPECIAL_RADIUS = getConfig().getInt("biosphere.special_radius", 7);
        BiosphereGenerator.LAVA_LEVEL = (byte) getConfig().getInt("biosphere.lava_level", 24);
//        getConfig().save();
        getConfig().saveToString();
    }
    
    //old version
//    public void reload()
//    {
//    	getConfiguration().load();
//        BiosphereGenerator.NOISE = getConfiguration().getBoolean("biosphere.noise", true);
//        BiosphereGenerator.DOME_TYPE = (byte) getConfiguration().getInt("biosphere.dome_type", 20);
//        BiosphereGenerator.BRIDGE_SIZE = (byte) getConfiguration().getInt("biosphere.bridge_size", 3);
//        BiosphereGenerator.BRIDGE_SUPPORT = (byte) getConfiguration().getInt("biosphere.bridge_support", 5);
//        BiosphereGenerator.BRIDGE_RAIL = (byte) getConfiguration().getInt("biosphere.bridge_rail", 85);
//        BiosphereGenerator.SPECIAL_RADIUS = getConfiguration().getInt("biosphere.special_radius", 7);
//        BiosphereGenerator.LAVA_LEVEL = (byte) getConfiguration().getInt("biosphere.lava_level", 24);
//    	getConfiguration().save();
//    }
}

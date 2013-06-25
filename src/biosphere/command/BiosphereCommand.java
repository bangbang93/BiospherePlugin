package biosphere.command;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//import org.bukkit.generator.ChunkGenerator;

import biosphere.BiosphereGenerator;
import biosphere.BiospherePlugin;

/**
 *
 * @author o4kapuk
 */
public class BiosphereCommand implements CommandExecutor {
    private final BiospherePlugin plugin;

    public BiosphereCommand(BiospherePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        
        if(sender instanceof Player) {
            sender.sendMessage("Will try");
            
            World bio = plugin.getServer().getWorld("biosphere");
            if(null == bio) {
//            	
            	/*
            	WorldCreator wc = new WorldCreator("biosphere");
                wc.environment(Environment.NORMAL);
                wc.seed(plugin.getServer().getWorlds().get(0).getSeed());
                wc.generator((ChunkGenerator) (bio = plugin.getServer().createWorld(wc)));
                */
            	WorldCreator wc = new WorldCreator("biosphere");
                wc.environment(Environment.NORMAL);
                wc.seed(plugin.getServer().getWorlds().get(0).getSeed());
                BiosphereGenerator bg=new BiosphereGenerator();
                wc.generator(bg);
//                bio=plugin.getServer().getWorld("biosphere");
                bio=plugin.getServer().createWorld(wc);
//            	bio = plugin.getServer().createWorld("biosphere",Environment.THE_END,plugin.getServer().getWorlds().get(0).getSeed(), gen);
            }
            Player player = (Player) sender;
            Location loc = bio.getSpawnLocation();
            
//            loc.setY(4);
            player.teleport(loc);
        }
        
        

        return true;
    }
}

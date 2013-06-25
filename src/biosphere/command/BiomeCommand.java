package biosphere.command;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biosphere.BiosphereGenerator;
import biosphere.BiospherePlugin;

/**
 *
 * @author o4kapuk
 */
public class BiomeCommand implements CommandExecutor {
    public BiomeCommand(BiospherePlugin plugin) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            Location loc = player.getLocation();
            
            player.sendMessage(BiosphereGenerator.getBiome(loc.getBlock().getChunk()).toString());
            player.sendMessage(loc.getBlock().getBiome().toString());
            player.sendMessage("Temperature is " + loc.getBlock().getTemperature());            
        }
        return true;
    }
   
}

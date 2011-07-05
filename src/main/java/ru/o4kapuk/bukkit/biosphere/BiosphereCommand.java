/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.Location;

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
            
            BiosphereGenerator gen = new BiosphereGenerator();
            World bio = plugin.getServer().getWorld("biosphere");
            if(null == bio) {
                bio = plugin.getServer().createWorld("biosphere",Environment.SKYLANDS,plugin.getServer().getWorlds().get(0).getSeed(), gen);
            }
            Player player = (Player) sender;
            Location loc = bio.getSpawnLocation();
            
//            loc.setY(4);
            player.teleport(loc);
        }
        
        

        return true;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere.command;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.o4kapuk.bukkit.biosphere.BiosphereGenerator;
import ru.o4kapuk.bukkit.biosphere.BiospherePlugin;

/**
 *
 * @author o4kapuk
 */
public class BiomeCommand implements CommandExecutor {
    private final BiospherePlugin plugin;

    public BiomeCommand(BiospherePlugin plugin) {
        this.plugin = plugin;
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

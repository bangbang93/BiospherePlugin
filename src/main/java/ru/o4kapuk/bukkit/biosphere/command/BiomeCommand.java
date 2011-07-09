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
            int highest = player.getWorld().getHighestBlockYAt(loc) - 1;
            loc.setY(highest);
            Block highestBlock = player.getWorld().getBlockAt(loc);
            player.sendMessage("Highest is " + highest + ", material is " + highestBlock.getType().name());
            Chunk chunk = player.getWorld().getChunkAt(loc);
            player.sendMessage("Chunk " + chunk.getX() + ":" + chunk.getZ());
            int i = chunk.getX() << 4;
            int k = chunk.getZ() << 4;
            int i1 = chunk.getX() << 5;
            int k1 = chunk.getZ() << 5;
            player.sendMessage("Coords " + i + ":" + k);
            player.sendMessage("Coords2 " + i1 + ":" + k1);
        }
        return true;
    }
   
}

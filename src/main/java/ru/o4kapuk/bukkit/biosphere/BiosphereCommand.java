/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        sender.sendMessage("Will try");
        return true;
    }
}

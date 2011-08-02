/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldInitEvent;
import ru.o4kapuk.bukkit.biosphere.util.BiosphereWorldChunkManager;
/**
 *
 * @author nharitonov
 */
public class BiosphereWorldListener extends WorldListener {
    @Override
    public void onWorldInit(WorldInitEvent event) {
        net.minecraft.server.World world = ((org.bukkit.craftbukkit.CraftWorld) event.getWorld()).getHandle();
        world.worldProvider.b = new BiosphereWorldChunkManager(world);
        System.out.println("New world chunk manager is set");
    }
}

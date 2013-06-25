package biosphere;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import biosphere.util.BiosphereWorldChunkManager;
/**
 *
 * @author nharitonov
 */
public class BiosphereWorldListener implements Listener {
    public void onWorldInit(WorldInitEvent event) {
        net.minecraft.server.v1_4_R1.World world = ((org.bukkit.craftbukkit.v1_4_R1.CraftWorld) event.getWorld()).getHandle();
        world.worldProvider.d = new BiosphereWorldChunkManager(world);
        System.out.println("New world chunk manager is set");
    }
}

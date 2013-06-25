package biosphere;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.block.Biome;

/**
 *
 * @author o4kapuk
 */
public class BiospherePlayerListener implements Listener {
    
    public void onPlayerMove(PlayerMoveEvent event) {
        if(event.getPlayer().isOp()) {
            Biome from = BiosphereGenerator.getBiome(event.getFrom().getBlock().getChunk());
            Biome to = BiosphereGenerator.getBiome(event.getTo().getBlock().getChunk());

            if(from != to) {
                event.getPlayer().sendMessage("Leaving biome " + from.toString());
                event.getPlayer().sendMessage("Entering biome " + to.toString());
            }
        }
    }
}

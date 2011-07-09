/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere.populator;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import ru.o4kapuk.bukkit.biosphere.BiosphereGenerator;

/**
 *
 * @author o4kapuk
 */
public class TaigaPopulator extends CommonPopulator {

    @Override
    public void populate(World world, Random random, Chunk source) {

        if(BiosphereGenerator.getBiome(source) != Biome.TAIGA) {
            return;
        }
//        System.out.print("Taiga populator called");
        for(int i = source.getX() << 4; i < 16 + source.getX() << 4; i++) {
            for(int k = source.getZ() << 4; k < 16 + source.getZ() << 4; k++) {
                int j = world.getHighestBlockYAt(i, k);
                if(world.getBlockAt(i, j, k).getTypeId() == Material.AIR.getId()
                    && (world.getBlockAt(i, j - 1, k).getTypeId() == Material.GRASS.getId()
                     || world.getBlockAt(i, j - 1, k).getTypeId() == Material.LEAVES.getId())) {
                    world.getBlockAt(i, j, k).setType(Material.SNOW);
                }
//                System.out.print(i + ":" + j + ":" + k);
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere;


import java.util.Random;

import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.World;
import org.bukkit.Chunk;
import org.bukkit.Material;

/**
 *
 * @author o4kapuk
 */
public class FlowerPopulator extends BlockPopulator {
    private Material flower;
    private int rarity;
    
    public FlowerPopulator(Material flover, int rarity) {
        this.flower = flover;
        this.rarity = rarity;
    }
    
    @Override
    public void populate(World world, Random random, Chunk source) {
        int x = source.getX() << 4 + random.nextInt(16) + 8;
        int z = source.getZ() << 4 + random.nextInt(16) + 8;

        int y = 64;
        ChunkGenerator gen = world.getGenerator();
        if(gen instanceof BiosphereGenerator) {
            y = ((BiosphereGenerator) gen).getSurfaceLevel(x, z);
        }
        
        if( 0 == random.nextInt(rarity) ) {
            for (int l = 0; l < 64; ++l) {
                int i1 = x + random.nextInt(8) - random.nextInt(8);
                int j1 = y + random.nextInt(4) - random.nextInt(4);
                int k1 = z + random.nextInt(8) - random.nextInt(8);

                int under = world.getBlockTypeIdAt(i1, j1 - 1, k1);
                if (world.getBlockTypeIdAt(i1, j1, k1) == Material.AIR.getId()                    
                        && (under == Material.GRASS.getId() || under == Material.DIRT.getId() || under == Material.SOIL.getId())) {
                    world.getBlockAt(i1, j1, k1).setType(flower);
                }
            }
        } 
    }
}

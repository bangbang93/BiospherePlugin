/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere;

import java.util.Random;

import org.bukkit.generator.BlockPopulator;
import org.bukkit.World;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;

/**
 *
 * @author o4kapuk
 */
public class DesertPopulator extends BlockPopulator {

    private boolean isBuildable(Material material) {
        return 
                material != Material.AIR
             && material != Material.WATER
             && material != Material.LAVA
             && material != Material.YELLOW_FLOWER
             && material != Material.RED_ROSE
             && material != Material.FIRE
             && material != Material.SNOW
             ;
    }
    
    private boolean canPlaceCactus(World world, int i, int j, int k) {
        int under = world.getBlockTypeIdAt(i, j - 1, k);
        
        if(isBuildable(world.getBlockAt(i - 1, j, k).getType())
        || isBuildable(world.getBlockAt(i + 1, j, k).getType())
        || isBuildable(world.getBlockAt(i, j, k - 1).getType())
        || isBuildable(world.getBlockAt(i, j, k + 1).getType())
        ) {
            return false;
        }
        
        if(under != Material.CACTUS.getId() && under != Material.SAND.getId()) {
            return false;            
        }

        return true;
    }
    
    public void generateCactus(World world, Random random, int i, int j, int k) {
        for (int l = 0; l < 8; ++l) {
            int i1 = i + random.nextInt(16) - random.nextInt(16);
            int j1 = j + random.nextInt(4) - random.nextInt(4);
            int k1 = k + random.nextInt(16) - random.nextInt(16);

            if (world.getBlockTypeIdAt(i1, j1, k1) == Material.AIR.getId()) {
                int l1 = 1 + random.nextInt(random.nextInt(3) + 1);

                for (int i2 = 0; i2 < l1; ++i2) {
                    if (canPlaceCactus(world, i1, j1 + i2, k1)) {
                        world.getBlockAt(i1, j1, k1).setType(Material.CACTUS);
                    }
                }
            }    
        }
    }
    
    @Override
    public void populate(World world, Random random, Chunk source) {
        if(BiosphereGenerator.getBiome(source) != Biome.DESERT) {
            return;
        }
        
        int i;
        int k = source.getX() << 4;
        int l = source.getZ() << 4;
        
        int k1 = k + random.nextInt(16);
        int l1 = l + random.nextInt(16);
        int midY = 32 + random.nextInt(64);

        for(i = 0; i < random.nextInt(5); i++) {
            generateCactus(world, random, k1, midY, l1);
        }

        for (i = 0; i < 128; ++i) {
            int x = k + random.nextInt(32) - random.nextInt(32);
            int y = midY + random.nextInt(4) - random.nextInt(4);
            int z = l + random.nextInt(32) - random.nextInt(32);

            int under = world.getBlockTypeIdAt(x, y - 1, z);
            if (world.getBlockTypeIdAt(x, y, z) == Material.AIR.getId()                    
                    && (under == Material.SAND.getId())) {
                world.getBlockAt(x, y, z).setType(Material.DEAD_BUSH);
            }
        }
    
    }
}

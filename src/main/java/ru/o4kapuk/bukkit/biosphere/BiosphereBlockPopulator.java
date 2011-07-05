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
import org.bukkit.block.Block;
import org.bukkit.block.Biome;

import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.TreeType;

/**
 *
 * @author o4kapuk
 */
public class BiosphereBlockPopulator extends BlockPopulator {
    
    Random random;
    World world;
    
    public void generateClay(int param, int i, int j, int k) {
        if(world.getBlockTypeIdAt(i, j, k) == Material.WATER.getId()) {
            float f = random.nextFloat() * 3.1415927F;
            double d0 = (double) ((float) (i + 8) + MathHelper.a(f) * (float) param / 8.0F);
            double d1 = (double) ((float) (i + 8) - MathHelper.a(f) * (float) param / 8.0F);
            double d2 = (double) ((float) (k + 8) + MathHelper.b(f) * (float) param / 8.0F);
            double d3 = (double) ((float) (k + 8) - MathHelper.b(f) * (float) param / 8.0F);
            double d4 = (double) (j + random.nextInt(3) + 2);
            double d5 = (double) (j + random.nextInt(3) + 2);            for (int l = 0; l <= param; ++l) {
                double d6 = d0 + (d1 - d0) * (double) l / (double) param;
                double d7 = d4 + (d5 - d4) * (double) l / (double) param;
                double d8 = d2 + (d3 - d2) * (double) l / (double) param;
                double d9 = random.nextDouble() * (double) param / 16.0D;
                double d10 = (double) (MathHelper.a((float) l * 3.1415927F / (float) param) + 1.0F) * d9 + 1.0D;
                double d11 = (double) (MathHelper.a((float) l * 3.1415927F / (float) param) + 1.0F) * d9 + 1.0D;

                for (int i1 = (int) (d6 - d10 / 2.0D); i1 <= (int) (d6 + d10 / 2.0D); ++i1) {
                    for (int j1 = (int) (d7 - d11 / 2.0D); j1 <= (int) (d7 + d11 / 2.0D); ++j1) {
                        for (int k1 = (int) (d8 - d10 / 2.0D); k1 <= (int) (d8 + d10 / 2.0D); ++k1) {
                            double d12 = ((double) i1 + 0.5D - d6) / (d10 / 2.0D);
                            double d13 = ((double) j1 + 0.5D - d7) / (d11 / 2.0D);
                            double d14 = ((double) k1 + 0.5D - d8) / (d10 / 2.0D);

                            if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
                                if (Material.SAND.getId() == world.getBlockTypeIdAt(i1, j1, k1)) {
                                    world.getBlockAt(i1, j1, k1).setType(Material.CLAY);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*
    public void generateMineable(Material material, int param, int i, int j, int k) {
        float f = random.nextFloat() * 3.1415927F;
        double d0 = (double) ((float) (i + 8) + MathHelper.a(f) * (float) param / 8.0F);
        double d1 = (double) ((float) (i + 8) - MathHelper.a(f) * (float) param / 8.0F);
        double d2 = (double) ((float) (k + 8) + MathHelper.b(f) * (float) param / 8.0F);
        double d3 = (double) ((float) (k + 8) - MathHelper.b(f) * (float) param / 8.0F);
        double d4 = (double) (j + random.nextInt(3) + 2);
        double d5 = (double) (j + random.nextInt(3) + 2);

        for (int l = 0; l <= param; ++l) {
            double d6 = d0 + (d1 - d0) * (double) l / (double) param;
            double d7 = d4 + (d5 - d4) * (double) l / (double) param;
            double d8 = d2 + (d3 - d2) * (double) l / (double) param;
            double d9 = random.nextDouble() * (double) param / 16.0D;
            double d10 = (double) (MathHelper.a((float) l * 3.1415927F / (float) param) + 1.0F) * d9 + 1.0D;
            double d11 = (double) (MathHelper.a((float) l * 3.1415927F / (float) param) + 1.0F) * d9 + 1.0D;
            int i1 = (int) (d6 - d10 / 2.0D);
            int j1 = (int) (d7 - d11 / 2.0D);
            int k1 = (int) (d8 - d10 / 2.0D);
            int l1 = (int) (d6 + d10 / 2.0D);
            int i2 = (int) (d7 + d11 / 2.0D);
            int j2 = (int) (d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; ++k2) {
                double d12 = ((double) k2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D) {
                    for (int l2 = j1; l2 <= i2; ++l2) {
                        double d13 = ((double) l2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D) {
                            for (int i3 = k1; i3 <= j2; ++i3) {
                                double d14 = ((double) i3 + 0.5D - d8) / (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getBlockTypeIdAt(k2, l2, i3) == Material.STONE.getId()) {
                                    world.getBlockAt(k2, l2, i3).setType(material);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void generateFlowers(Material material, int i, int j, int k) {
        for (int l = 0; l < 64; ++l) {
            int i1 = i + random.nextInt(8) - random.nextInt(8);
            int j1 = j + random.nextInt(4) - random.nextInt(4);
            int k1 = k + random.nextInt(8) - random.nextInt(8);
            
            int under = world.getBlockTypeIdAt(i1, j1 - 1, k1);
            if (world.getBlockTypeIdAt(i1, j1, k1) == Material.AIR.getId()                    
                    && (under == Material.GRASS.getId() || under == Material.DIRT.getId() || under == Material.SOIL.getId())) {
                world.getBlockAt(i1, j1, k1).setType(material);
            }
        }
    }
    */
    
    private boolean canPlaceReed(int i, int j, int k) {
        int under = world.getBlockTypeIdAt(i, j - 1, k);
        if(under == Material.SUGAR_CANE_BLOCK.getId()) {
            return true;
        }
        if(under != Material.GRASS.getId() && under != Material.DIRT.getId()) {
            return false;
        }
        if(world.getBlockTypeIdAt(i - 1, j - 1, k) == Material.WATER.getId()
                || world.getBlockTypeIdAt(i + 1, j - 1, k) == Material.WATER.getId()
                || world.getBlockTypeIdAt(i, j - 1, k + 1) == Material.WATER.getId()
                || world.getBlockTypeIdAt(i, j - 1, k - 1) == Material.WATER.getId()) {
            return true;
        }
        return false;
    }
    
    public void generateReed(int i, int j, int k) {
        for (int l = 0; l < 20; ++l) {
            int i1 = i + random.nextInt(4) - random.nextInt(4);
            int j1 = j;
            int k1 = k + random.nextInt(4) - random.nextInt(4);

            if (world.getBlockTypeIdAt(i1, j, k1) == Material.AIR.getId()
                    && (world.getBlockTypeIdAt(i1 - 1, j - 1, k1) == Material.WATER.getId()
                        || world.getBlockTypeIdAt(i1 + 1, j - 1, k1) == Material.WATER.getId()
                        || world.getBlockTypeIdAt(i1, j - 1, k1 - 1) == Material.WATER.getId()
                        || world.getBlockTypeIdAt(i1, j - 1, k1 + 1) == Material.WATER.getId())) {
                int l1 = 2 + random.nextInt(random.nextInt(3) + 1);

                for (int i2 = 0; i2 < l1; ++i2) {
                    if(canPlaceReed(i1, j1, k1)) {
                        world.getBlockAt(i1, j1, k1).setType(Material.SUGAR_CANE_BLOCK);
                    }
                }
            }
        }
    }
    
    public void generatePumpkin(int i, int j, int k) {
        for (int l = 0; l < 64; ++l) {
            int i1 = i + random.nextInt(8) - random.nextInt(8);
            int j1 = j + random.nextInt(4) - random.nextInt(4);
            int k1 = k + random.nextInt(8) - random.nextInt(8);

            if (world.getBlockTypeIdAt(i1, j1, k1) == Material.AIR.getId()
                    && world.getBlockTypeIdAt(i1, j1 - 1, k1) == Material.GRASS.getId()) {
                Block pumpkin = world.getBlockAt(i1, j1, k1);
                pumpkin.setTypeIdAndData(Material.PUMPKIN.getId(), (byte)random.nextInt(4), false);
            }
        }
    }

    public void generateFire(int i, int j, int k) {
        for (int l = 0; l < 64; ++l) {
            int i1 = i + random.nextInt(8) - random.nextInt(8);
            int j1 = j + random.nextInt(4) - random.nextInt(4);
            int k1 = k + random.nextInt(8) - random.nextInt(8);

            if (world.getBlockTypeIdAt(i1, j1, k1) == Material.AIR.getId() && world.getBlockTypeIdAt(i1, j1 - 1, k1) == Material.NETHERRACK.getId()) {
                world.getBlockAt(i1, j1, k1).setType(Material.FIRE);
            }
        }
    }
    
    @Override
    public void populate(World world, Random random, Chunk source) {
        this.random = random;
        this.world = world;
        
        Biome biome = BiosphereGenerator.getBiome(source.getX(), source.getZ());
        if(null == biome) {
            System.out.print("boime at " + source.getX() + ":" + source.getZ() + " is null :-(");
        }

        int i;
        int k = source.getX() << 4;
        int l = source.getZ() << 4;

        for(i = 0; i < 10; i++) {
            this.generateClay(32, k + random.nextInt(16), random.nextInt(64), l + random.nextInt(16));
        }

//        for(i = 0; i < 20; i++) {
//            this.generateMineable(Material.COAL_ORE, 16, k + random.nextInt(16), random.nextInt(64), l + random.nextInt(16));
//        }
//        
//        for(i = 0; i < 20; i++) {
//            this.generateMineable(Material.IRON_ORE, 8, k + random.nextInt(16), random.nextInt(64), l + random.nextInt(16));
//        }
//        
//        for(i = 0; i < 2; i++) {
//            this.generateMineable(Material.GOLD_ORE, 8, k + random.nextInt(16), random.nextInt(64), l + random.nextInt(16));
//        }
//        
//        for(i = 0; i < 8; i++) {
//            this.generateMineable(Material.REDSTONE_ORE, 7, k + random.nextInt(16), random.nextInt(64), l + random.nextInt(16));
//        }
        
        int treeCount = 0;
        if(random.nextInt(10) == 0)
            treeCount += 5;
                    
        if(null != biome) {
            switch(biome) {
                case SEASONAL_FOREST:
                    treeCount += 10;
                    break;
                case RAINFOREST:
                case FOREST:
                case TAIGA:
                    treeCount += 50;
                    break;
                case DESERT:
                case TUNDRA:
                case PLAINS:
                treeCount -= 100;
            }
        }
        
        for(i = 0; i < treeCount; i++)
        {
            int x = k + random.nextInt(16) + 8;
            int z = l + random.nextInt(16) + 8;
            int y = 32 + random.nextInt(64);
            Location loc = new Location(world, x, y, z);
            world.generateTree(loc, random.nextInt(10) == 0 ? TreeType.BIG_TREE : TreeType.TREE);
        }
        
//        for(i = 0; i < 2; i++) {
//            this.generateFlowers(Material.YELLOW_FLOWER, k + random.nextInt(16) + 8, random.nextInt(128), l + random.nextInt(16) + 8);
//        }
//
//        if(random.nextInt(2) == 0) {
//            this.generateFlowers(Material.RED_ROSE, k + random.nextInt(16) + 8, random.nextInt(128), l + random.nextInt(16) + 8);
//        }
//        
//        if(random.nextInt(4) == 0) {
//            this.generateFlowers(Material.BROWN_MUSHROOM, k + random.nextInt(16) + 8, random.nextInt(128), l + random.nextInt(16) + 8);
//        }
//        
//        if(random.nextInt(8) == 0) {
//            this.generateFlowers(Material.RED_MUSHROOM, k + random.nextInt(16) + 8, random.nextInt(128), l + random.nextInt(16) + 8);
//        }
        
        for(i = 0; i < 10; i++) {
            this.generateReed(k + random.nextInt(16), random.nextInt(128), l + random.nextInt(16));
        }
        
        if(random.nextInt(32) == 0) {
            this.generatePumpkin(k + random.nextInt(16), random.nextInt(128), l + random.nextInt(16));
        }
        
        // TODO: tall grass
        
        int k1 = k + random.nextInt(16);
        int l1 = l + random.nextInt(16);
        int midY = 32 + random.nextInt(64);
        if(null != biome) {
            switch(biome) {
                case HELL:
                    if(random.nextBoolean()) {
                        this.generateFire(k1, midY, l1);
                    }
                    break;
            }
        }
        //TODO: snow blocks in cold areas
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere;

import ru.o4kapuk.bukkit.biosphere.util.MathHelper;
import java.util.Random;

import org.bukkit.generator.BlockPopulator;
import org.bukkit.World;
import org.bukkit.Chunk;
import org.bukkit.Material;
/**
 *
 * @author o4kapuk
 */
public class MineablePopulator extends BlockPopulator {
    private Material ore;
    private int multiplier;
    private int count;
    
    public MineablePopulator(Material ore, int multiplier, int count) {
        super();
        this.ore = ore;
        this.multiplier = multiplier;
        this.count = count;
    }
    
    @Override
    public void populate(World world, Random random, Chunk source) {
            
            BiosphereGenerator generator = (BiosphereGenerator)world.getGenerator();
            generator.setNoise(source);

            for(int m = 0; m < multiplier; m++) {
            int i = (source.getX() * 16) + random.nextInt(16);
            int j = random.nextInt(128);
            int k = (source.getZ() * 16) + random.nextInt(16);
            
            if(generator.getMainDistance(i, j, k) > generator.sphereRadius) {
                continue;
            }
            
            float f = random.nextFloat() * 3.1415927F;
            double d0 = (double) ((float) (i + 8) + MathHelper.sin(f) * (float) count / 8.0F);
            double d1 = (double) ((float) (i + 8) - MathHelper.sin(f) * (float) count / 8.0F);
            double d2 = (double) ((float) (k + 8) + MathHelper.cos(f) * (float) count / 8.0F);
            double d3 = (double) ((float) (k + 8) - MathHelper.cos(f) * (float) count / 8.0F);
            double d4 = (double) (j + random.nextInt(3) + 2);
            double d5 = (double) (j + random.nextInt(3) + 2);

            for (int l = 0; l <= count; ++l) {
                double d6 = d0 + (d1 - d0) * (double) l / (double) count;
                double d7 = d4 + (d5 - d4) * (double) l / (double) count;
                double d8 = d2 + (d3 - d2) * (double) l / (double) count;
                double d9 = random.nextDouble() * (double) count / 16.0D;
                double d10 = (double) (MathHelper.sin((float) l * 3.1415927F / (float) count) + 1.0F) * d9 + 1.0D;
                double d11 = (double) (MathHelper.sin((float) l * 3.1415927F / (float) count) + 1.0F) * d9 + 1.0D;
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
                                        world.getBlockAt(k2, l2, i3).setType(ore);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }    
}

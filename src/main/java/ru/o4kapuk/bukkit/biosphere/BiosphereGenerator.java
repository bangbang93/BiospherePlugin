/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere;

import java.util.Random;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.World;
import org.bukkit.Material;

/**
 *
 * @author nharitonov
 */
public class BiosphereGenerator extends ChunkGenerator {
    
    @Override
    public byte[] generate(World world, Random random, int x, int z) {
        byte res[] = new byte[32768];

     for (int _x = 0; _x < 16; _x++) {
         for (int _z = 0; _z < 16; _z++) {
             for (int _y = 0; _y < 128; _y++) {
                 if(_y < 2) {
                     res[(_x * 16 + _z) * 128 + _y] = (byte)Material.BEDROCK.getId();
                 }
                 // result[(x * 16 + z) * 128 + y] = ??;
             }
         }
     }        
        return res;
    }
    
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }
}

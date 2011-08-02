/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere.util;

import net.minecraft.server.BiomeBase;
import net.minecraft.server.World;
import net.minecraft.server.WorldChunkManager;
import ru.o4kapuk.bukkit.biosphere.BiosphereGenerator;
/**
 *
 * @author nharitonov
 */
public class BiosphereWorldChunkManager extends WorldChunkManager {
    public BiosphereWorldChunkManager(World world) {
        super(world);
    }
    
    public static final BiomeBase biomeToBiomeBase(org.bukkit.block.Biome b) {
        if (b == org.bukkit.block.Biome.RAINFOREST) {
            return BiomeBase.RAINFOREST;
        } else if (b == org.bukkit.block.Biome.SWAMPLAND) {
            return BiomeBase.SWAMPLAND;
        } else if (b == org.bukkit.block.Biome.SEASONAL_FOREST) {
            return BiomeBase.SEASONAL_FOREST;
        } else if (b == org.bukkit.block.Biome.FOREST) {
            return BiomeBase.FOREST;
        } else if (b == org.bukkit.block.Biome.SAVANNA) {
            return BiomeBase.SAVANNA;
        } else if (b == org.bukkit.block.Biome.SHRUBLAND) {
            return BiomeBase.SHRUBLAND;
        } else if (b == org.bukkit.block.Biome.TAIGA) {
            return BiomeBase.TAIGA;
        } else if (b == org.bukkit.block.Biome.DESERT) {
            return BiomeBase.DESERT;
        } else if (b == org.bukkit.block.Biome.PLAINS) {
            return BiomeBase.PLAINS;
        } else if (b == org.bukkit.block.Biome.ICE_DESERT) {
            return BiomeBase.ICE_DESERT;
        } else if (b == org.bukkit.block.Biome.TUNDRA) {
            return BiomeBase.TUNDRA;
        } else if (b == org.bukkit.block.Biome.HELL) {
            return BiomeBase.HELL;
        } else if (b == org.bukkit.block.Biome.SKY) {
            return BiomeBase.SKY;
        }

        return null;
    }

    public static BiomeBase getSphereBiome(int localX, int localZ) {
        org.bukkit.block.Biome biome = BiosphereGenerator.getBiome(localX >> 4, localZ >> 4);
        return biomeToBiomeBase(biome);
    }
    
    public BiomeBase[] a(BiomeBase[] abiomebase, int i, int j, int k, int l) {
        if (abiomebase == null || abiomebase.length < k * l) {
            abiomebase = new BiomeBase[k * l];
        }

        int i1 = 0;

        for (int j1 = 0; j1 < k; ++j1) {
            for (int k1 = 0; k1 < l; ++k1) {
                abiomebase[i1++] = getSphereBiome(i + j1, j + k1);
            }
        }

        return abiomebase;
    }
}

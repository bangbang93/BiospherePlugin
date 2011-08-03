/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere.util;

import net.minecraft.server.BiomeBase;
import net.minecraft.server.World;
import net.minecraft.server.WorldChunkManager;
import org.bukkit.block.Biome;
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
        switch(b) {
            case RAINFOREST:
                return BiomeBase.RAINFOREST;
            case SWAMPLAND:
                return BiomeBase.SWAMPLAND;
            case SEASONAL_FOREST:
                return BiomeBase.SEASONAL_FOREST;
            case FOREST:
                return BiomeBase.FOREST;
            case SAVANNA:
                return BiomeBase.SAVANNA;
            case SHRUBLAND:
                return BiomeBase.SHRUBLAND;
            case TAIGA:
                return BiomeBase.TAIGA;
            case DESERT:
                return BiomeBase.DESERT;
            case PLAINS:
                return BiomeBase.PLAINS;
            case ICE_DESERT:
                return BiomeBase.ICE_DESERT;
            case TUNDRA:
                return BiomeBase.TUNDRA;
            case HELL:
                return BiomeBase.HELL;
            case SKY:
                return BiomeBase.SKY;
        }
        return null;
    }
    
    public static double biomeToTemperature(org.bukkit.block.Biome b) {
        switch(b) {
            case RAINFOREST:
            case SEASONAL_FOREST:
            case SAVANNA:
            case DESERT:
                return 0.96D;
            case SWAMPLAND:
            case FOREST:
            case SHRUBLAND:
            case PLAINS:
                return 0.75D;
            case TAIGA:
                return 0.4D;
            case ICE_DESERT:
            case TUNDRA:
                return 0.05D;
            case HELL:
                return 1D;
            case SKY:
                return 0.5D;
        }
        return 0.5D;
    }
    
    public static double biomeToHumidity(org.bukkit.block.Biome b) {
        switch(b) {
            case RAINFOREST:
            case SWAMPLAND:
                return 0.8D;

            case SEASONAL_FOREST:
            case FOREST:
                return 0.6D;

            case SHRUBLAND:
            case SAVANNA:
            case PLAINS:
            case TAIGA:
                return 0.4D;
            case ICE_DESERT:
            case TUNDRA:
            case DESERT:
                return 0.05D;
            case HELL:
                return 0D;
            case SKY:
                return 0.5D;
        }
        return 0.5D;
    }
    
//    public static final 

    public BiomeBase[] a(BiomeBase[] abiomebase, int i, int j, int k, int l) {
        if (abiomebase == null || abiomebase.length < k * l) {
            abiomebase = new BiomeBase[k * l];
        }

        int i1 = 0;

        for (int j1 = 0; j1 < k; ++j1) {
            for (int k1 = 0; k1 < l; ++k1) {
                org.bukkit.block.Biome b = BiosphereGenerator.getBiome(i + j1, j + k1);
                BiomeBase biome = biomeToBiomeBase(b);
                
                this.temperature[i1] = biomeToTemperature(b);
                this.rain[i1] = biomeToHumidity(b);                
                abiomebase[i1++] = biome;
            }
        }

        return abiomebase;
    }
}

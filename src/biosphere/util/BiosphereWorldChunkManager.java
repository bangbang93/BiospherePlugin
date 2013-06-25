package biosphere.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import net.minecraft.server.v1_4_R1.BiomeBase;
import net.minecraft.server.v1_4_R1.World;
import net.minecraft.server.v1_4_R1.WorldChunkManager;
import biosphere.BiosphereGenerator;
/**
 *
 * @author nharitonov
 */
public class BiosphereWorldChunkManager extends WorldChunkManager {
    private final double[] temperature = null;
	private final double[] rain = null;

	public BiosphereWorldChunkManager(World world) {
        super(world);
    }
    
    public static final BiomeBase biomeToBiomeBase(org.bukkit.block.Biome b) {
        switch(b) {
            case JUNGLE:
                return BiomeBase.JUNGLE;
            case SWAMPLAND:
                return BiomeBase.SWAMPLAND;
//            case SEASONAL_FOREST:
//                return BiomeBase.SEASONAL_FOREST;
            case JUNGLE_HILLS:
              return BiomeBase.JUNGLE_HILLS;
            case FOREST:
                return BiomeBase.FOREST;
//            case SAVANNA:
//                return BiomeBase.SAVANNA;
//            case SHRUBLAND:
//                return BiomeBase.SHRUBLAND;
            case SMALL_MOUNTAINS:
              return BiomeBase.SMALL_MOUNTAINS;
          case FOREST_HILLS:
              return BiomeBase.FOREST_HILLS;
            case TAIGA:
                return BiomeBase.TAIGA;
            case DESERT:
                return BiomeBase.DESERT;
            case PLAINS:
                return BiomeBase.PLAINS;
//            case ICE_DESERT:
//                return BiomeBase.ICE_DESERT;
            case BEACH:
              return BiomeBase.BEACH;
            case ICE_PLAINS:
                return BiomeBase.ICE_PLAINS;
            case HELL:
                return BiomeBase.HELL;
            case SKY:
                return BiomeBase.SKY;
        }
        return null;
    }
    
    public static double biomeToTemperature(org.bukkit.block.Biome b) {
        switch(b) {
            case JUNGLE:
            case JUNGLE_HILLS:
            case SMALL_MOUNTAINS:
            case DESERT:
                return 0.96D;
            case SWAMPLAND:
            case FOREST:
            case FOREST_HILLS:
            case PLAINS:
                return 0.75D;
            case TAIGA:
                return 0.4D;
            case BEACH:
            case ICE_PLAINS:
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
            case JUNGLE:
            case SWAMPLAND:
                return 0.8D;

            case JUNGLE_HILLS:
            case FOREST:
                return 0.6D;

            case FOREST_HILLS:
            case SMALL_MOUNTAINS:
            case PLAINS:
            case TAIGA:
                return 0.4D;
            case BEACH:
            case ICE_PLAINS:
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

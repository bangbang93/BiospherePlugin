/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Biome;

/**
 *
 * @author nharitonov
 */
public class BiosphereGenerator extends ChunkGenerator {
    World world;
    
    public static final int GRID_SIZE = 9;
    
    public final Random rndSphere;    
    
    public int midX;
    public int midY;
    public int midZ;
    

    public int oreMidX;
    public int oreMidY;
    public int oreMidZ;
    
    public double sphereRadius;


    public static final byte DOME_TYPE = 20;
    public static final byte BRIDGE_SUPPORT = 5;
    public static final byte BRIDGE_RAIL = 85;
    public static final byte BRIDGE_SIZE = 3;
    public static final int SPECIAL_RADIUS = 7;
    

    public static final boolean NOISE = true;
    public static final boolean WATERWORLD = false;

    public boolean hasLake;
    public boolean lavaLake;
    public int lakeMidY;
    public double lakeRadius;
    public double lakeEdgeRadius;
    
    Biome biome;
    
    private NoiseGeneratorOctaves noiseGen;
    public final Random rndNoise;
    public double noise[];
    public double noiseMin;
    public double noiseMax;
    
    public BiosphereGenerator() {
        super();

//        caveGen = new BiosphereCaveGen();
        noiseMin = 1.7976931348623157E+308D;
        noiseMax = 4.9406564584124654E-324D;
        noise = new double[256];
        rndSphere = new Random(1);
        if(NOISE)
        {
            rndNoise = new Random(1);
            noiseGen = new NoiseGeneratorOctaves(rndNoise, 4);
        } else
        {
            rndNoise = null;
        }
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        List<BlockPopulator> res = super.getDefaultPopulators(world);
        res.add(new BiosphereBlockPopulator());
        return res;
    }

    public void preGenerateChunk(int chunkX, int chunkZ, byte blockArray[])
    {
//        biome.
        
        int localChunkX = chunkX << 4;
        int localChunkZ = chunkZ << 4;
        for(int z = 0; z < 16; z++)
        {
            for(int x = 0; x < 16; x++)
            {
                midY = getSurfaceLevel(x, z);
                for(int y = 127; y >= 0; y--)
                {
                    double mainDistance = getMainDistance(localChunkX + x, y, localChunkZ + z);
                    double oreDistance = getOreDistance(localChunkX + x, y, localChunkZ + z);
                    int index = (x * 16 + z) * 128 + y;
                    byte blockID = 0;
                    if(y > midY)
                    {
                        if(mainDistance == sphereRadius)
                        {
                            if(y >= midY + 4 || Math.abs((localChunkX + x) - midX) > BRIDGE_SIZE && Math.abs((localChunkZ + z) - midZ) > BRIDGE_SIZE)
                                blockID = DOME_TYPE;
                        } else
                        if(hasLake && NOISE && biome != Biome.DESERT && (mainDistance == lakeRadius + 1.0D || mainDistance == lakeRadius + 2D))
                        { // в непустынных биомах обсадить озеро травой
                            if(y == lakeMidY)
                                blockID = (byte)Material.GRASS.getId();
                            else
                            if(y < lakeMidY)
                                blockID = (byte)Material.DIRT.getId();
                        } else
                        if(hasLake && NOISE && biome != Biome.DESERT && mainDistance <= lakeRadius)
                        {
                            if(y == lakeMidY && biome == Biome.TUNDRA)
                                blockID = (byte)Material.ICE.getId();
                            else
                            if(y <= lakeMidY)
                                blockID = (byte)(lavaLake ? Material.LAVA.getId() : Material.WATER.getId());
                        } else
                        if(WATERWORLD && y <= midY + 4 && mainDistance > sphereRadius && (Math.abs((localChunkX + x) - midX) == BRIDGE_SIZE || Math.abs((localChunkZ + z) - midZ) == BRIDGE_SIZE))
                            blockID = DOME_TYPE;
                        else
                        if(WATERWORLD && y == midY + 4 && mainDistance > sphereRadius && (Math.abs((localChunkX + x) - midX) < BRIDGE_SIZE || Math.abs((localChunkZ + z) - midZ) < BRIDGE_SIZE))
                            blockID = DOME_TYPE;
                        else
                        if(WATERWORLD && y < midY + 4 && mainDistance > sphereRadius && (Math.abs((localChunkX + x) - midX) < BRIDGE_SIZE || Math.abs((localChunkZ + z) - midZ) < BRIDGE_SIZE))
                            blockID = 0;
                        else
                        if(WATERWORLD && mainDistance > sphereRadius)
                            blockID = (byte)Material.WATER.getId();
                        else
                        if(y == midY + 1 && mainDistance > sphereRadius && (Math.abs((localChunkX + x) - midX) == BRIDGE_SIZE || Math.abs((localChunkZ + z) - midZ) == BRIDGE_SIZE))
                            blockID = BRIDGE_RAIL;
                    } else
                    if(mainDistance == sphereRadius)
                        blockID = (byte)Material.STONE.getId();
                    else
                    if(hasLake && biome != Biome.DESERT && mainDistance <= lakeRadius)
                    {
                        if(y == lakeMidY && biome == Biome.TUNDRA)
                            blockID = (byte)Material.ICE.getId();
                        else
                        if(y <= lakeMidY)
                            blockID = (byte)(lavaLake ? Material.LAVA.getId() : Material.WATER.getId());
                    } else
                    if(hasLake && y < lakeMidY - 1 && biome != Biome.DESERT && mainDistance <= lakeEdgeRadius)
                        blockID = (byte)(lavaLake ? Material.GRAVEL.getId() : Material.SAND.getId());
                    else
                    if(mainDistance < sphereRadius)
                    {
                        if(y == midY)
                            blockID = (byte)Material.GRASS.getId();
                        else
                        if(y == midY - 1)
                            blockID = (byte)Material.DIRT.getId();
                        else
                            blockID = (byte)Material.STONE.getId();
                    } else
                    if(y == midY && mainDistance > sphereRadius && (Math.abs((localChunkX + x) - midX) < BRIDGE_SIZE + 1 || Math.abs((localChunkZ + z) - midZ) < BRIDGE_SIZE + 1))
                        blockID = BRIDGE_SUPPORT;
                    else
                    if(WATERWORLD && mainDistance > sphereRadius)
                        blockID = (byte)Material.WATER.getId();
                    if(oreDistance <= (double)SPECIAL_RADIUS)
                    {
                        int weight = rndSphere.nextInt(200);
                        int id = Material.STONE.getId();
                        if(weight < 1)
                            id = Material.DIAMOND_ORE.getId();
                        else
                        if(weight < 2)
                            id = Material.LAPIS_ORE.getId();
                        blockID = (byte)id;
                    }
                    blockArray[index] = blockID;
                }

            }

        }

    }

    public void setRand(int chunkX, int chunkZ)
    {
        midX = (chunkX - (int)Math.floor(Math.IEEEremainder(chunkX, GRID_SIZE)) << 4) + 8;
        midZ = (chunkZ - (int)Math.floor(Math.IEEEremainder(chunkZ, GRID_SIZE)) << 4) + 8;
        oreMidX = (midX + (GRID_SIZE / 2) * 16) - SPECIAL_RADIUS;
        oreMidZ = (midZ + (GRID_SIZE / 2) * 16) - SPECIAL_RADIUS;
        rndSphere.setSeed(1L);
        long seedX = (rndSphere.nextLong() / 2L) * 2L + 1L;
        long seedZ = (rndSphere.nextLong() / 2L) * 2L + 1L;
        long seed = ((long)midX * seedX + (long)midZ * seedZ) * 0x2656c0L ^ 1L;
        rndSphere.setSeed(seed);
        sphereRadius = Math.round(16D + rndSphere.nextDouble() * 32D + rndSphere.nextDouble() * 16D);
        lakeRadius = Math.round(sphereRadius / 4D);
        lakeEdgeRadius = lakeRadius + 2D;
        
        biome = world.getEmptyChunkSnapshot(chunkX, chunkZ, true, false).getBiome(8, 8);
        lavaLake = biome == Biome.HELL || biome != Biome.TUNDRA && rndSphere.nextInt(10) == 0;
        hasLake = rndSphere.nextInt(2) == 0;
        oreMidY = SPECIAL_RADIUS + 1 + rndSphere.nextInt(127 - (SPECIAL_RADIUS + 1));
        if(NOISE)
        {
            setNoise(midX >> 4, midZ >> 4);
            noiseMin = 1.7976931348623157E+308D;
            for(int i = 0; i < noise.length; i++)
                if(noise[i] < noiseMin)
                    noiseMin = noise[i];

            lakeMidY = (int)Math.round(64D + noiseMin * 8D);
            setNoise(chunkX, chunkZ);
        } else
        {
            lakeMidY = midY;
        }
    }

    
    public void setNoise(int chunkX, int chunkZ)
    {
        if(NOISE)
        {
            double d1 = 0.0078125D;
            noise = noiseGen.a(noise, chunkX * 16, 109.0134D, chunkZ * 16, 16, 1, 16, d1, 1.0D, d1);
        }
    }
    
    @Override
    public byte[] generate(World world, Random random, int x, int z) {
//        System.out.print("Generating chunk " + x + ":" + z);
        this.world = world;
//        this.rndSphere = random;
        byte res[] = new byte[32768];
        
        this.setRand(x, z);
        this.preGenerateChunk(x, z, res);

        for (int _x = 0; _x < 16; _x++) {
            for (int _z = 0; _z < 16; _z++) {
                for (int _y = 0; _y < 128; _y++) {
                    if(_y < 2) {
                        res[(_x * 16 + _z) * 128 + _y] = (byte)Material.BEDROCK.getId();
                        continue;
                    }
                    if(_y < 3) {
                        res[(_x * 16 + _z) * 128 + _y] = (byte)Material.STATIONARY_WATER.getId();
                        continue;
                    }
                     // result[(x * 16 + z) * 128 + y] = ??;
                }
            }
        }
        
        
        return res;
    }
    
    @Override
    public boolean canSpawn(World world, int x, int z) {
        Block highest = world.getBlockAt(x, world.getHighestBlockYAt(x, z), z);

                return highest.getType() != Material.AIR
                        && highest.getType() != Material.WATER
                        && highest.getType() != Material.STATIONARY_WATER
                        && highest.getType() != Material.BEDROCK
                        && highest.getType() != Material.LAVA;
    }


    public static double getInverseDistance(double x1, double y1, double z1, double x2, 
            double y2, double z2)
    {
        return Math.sqrt(-Math.pow(y2 - y1, 2D) + Math.pow(x2 - x1, 2D) + Math.pow(z2 - z1, 2D));
    }

    public static double getDistance(double x1, double y1, double z1, double x2, 
            double y2, double z2)
    {
        return Math.sqrt(Math.pow(y2 - y1, 2D) + Math.pow(x2 - x1, 2D) + Math.pow(z2 - z1, 2D));
    }

    public double getMainDistance(int x, int y, int z)
    {
        return (double)Math.round(getDistance(x, y, z, midX, midY, midZ));
    }

    public double getOreDistance(int x, int y, int z)
    {
        return (double)Math.round(getDistance(x, y, z, oreMidX, oreMidY, oreMidZ));
    }

    public int getSurfaceLevel(int x, int z)
    {
        if(NOISE)
            return (int)Math.round(64D + noise[z + x * 16] * 8D);
        else
            return 64;
    }
    
}

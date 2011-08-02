/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.o4kapuk.bukkit.biosphere;

import java.util.Random;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Biome;
import org.bukkit.Location;

import ru.o4kapuk.bukkit.biosphere.populator.ForestPopulator;
import ru.o4kapuk.bukkit.biosphere.util.NoiseGeneratorOctaves;
import ru.o4kapuk.bukkit.biosphere.populator.DesertPopulator;
import ru.o4kapuk.bukkit.biosphere.populator.TaigaPopulator;

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


    public static byte DOME_TYPE = 20;
    public static byte BRIDGE_SUPPORT = 5;
    public static byte BRIDGE_RAIL = 85;
    public static byte BRIDGE_SIZE = 3;
    public static byte LAVA_LEVEL = 24;
    public static int SPECIAL_RADIUS = 7;
    

    public static boolean NOISE = true;
    public static boolean WATERWORLD = false;

    public boolean hasLake;
    public boolean lavaLake;
    public int lakeMidY;
    public double lakeRadius;
    public double lakeEdgeRadius;
    
//    Biome biome;
    
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
        res.add(new MineablePopulator(Material.COAL_ORE, 20, 16));
        res.add(new MineablePopulator(Material.IRON_ORE, 40, 16));
        res.add(new MineablePopulator(Material.GOLD_ORE, 6, 8));
        res.add(new MineablePopulator(Material.REDSTONE_ORE, 8, 14));
        res.add(new FlowerPopulator(Material.YELLOW_FLOWER, 2));
        res.add(new FlowerPopulator(Material.RED_ROSE, 2));
        res.add(new FlowerPopulator(Material.BROWN_MUSHROOM, 4));
        res.add(new FlowerPopulator(Material.RED_MUSHROOM, 8));
        res.add(new DesertPopulator());
        res.add(new TaigaPopulator());
        res.add(new ForestPopulator());
        System.out.println("Populators set");
        return res;
    }

    public void preGenerateChunk(int chunkX, int chunkZ, byte blockArray[]) 
    {
//        biome.
        Biome biome = getBiome(chunkX, chunkZ);
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
                        if(hasLake && NOISE && biome != Biome.DESERT && biome != Biome.HELL && (mainDistance == lakeRadius + 1.0D || mainDistance == lakeRadius + 2D))
                        { // � ����������� ������ �������� ����� ������
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
                        if(WATERWORLD && y < midY + 4 && mainDistance > sphereRadius && (Math.abs((localChunkX + x) - midX) < BRIDGE_SIZE || Math.abs((localChunkZ + z) - midZ) < BRIDGE_SIZE) && (BRIDGE_SIZE > 0))
                            blockID = 0;
                        else
                        if(WATERWORLD && mainDistance > sphereRadius)
                            blockID = (byte)Material.WATER.getId();
                        else
                        if(y == midY + 1 && mainDistance > sphereRadius && (Math.abs((localChunkX + x) - midX) == BRIDGE_SIZE || Math.abs((localChunkZ + z) - midZ) == BRIDGE_SIZE))
                            blockID = BRIDGE_RAIL;
                    } else
                    if(mainDistance == sphereRadius) {
                        switch(biome) {
                            case DESERT:
                                blockID = (byte)Material.SANDSTONE.getId();
                                break;
                            case HELL:
                                blockID = (byte)Material.OBSIDIAN.getId();
                                break;
                            default:                        
                                blockID = (byte)Material.STONE.getId();
                        }
                    }
                    else
                    if(hasLake && biome != Biome.DESERT && mainDistance <= lakeRadius)
                    {
                        if(y == lakeMidY && (biome == Biome.TUNDRA || biome == Biome.ICE_DESERT))
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
                        switch(biome) {
                            case DESERT:
                                if(y <= midY && y > midY - 5)
                                    blockID = (byte)Material.SAND.getId();
                                else
                                if(y <= midY - 5 && y > midY - 7)
                                    blockID = (byte)Material.SANDSTONE .getId();
                                else
                                    blockID = (byte)Material.STONE .getId();
                                break;
                            case ICE_DESERT:
                                if(y <= midY && y > midY - 4)
                                    blockID = (byte)Material.SNOW_BLOCK .getId();
                                else
                                    blockID = (byte)Material.STONE .getId();
                                break;
                            case HELL:
                                blockID = (byte)Material.NETHERRACK.getId();
                                break;
                            default:
                                if(y == midY)
                                    blockID = (byte)Material.GRASS.getId();
                                else
                                if(y < midY && y > midY - 3)
                                    blockID = (byte)Material.DIRT.getId();
                                else
                                    blockID = (byte)Material.STONE .getId();
                                break;
                        }
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

    public static Biome getBiome(Chunk chunk) {
        return getBiome(chunk.getX(), chunk.getZ());
    }
    
    public static Biome getBiome(int chunkX, int chunkZ) {
        
        int sphereX = (chunkX - (int)Math.floor(Math.IEEEremainder(chunkX, GRID_SIZE)) << 4) + 8;
        int sphereZ = (chunkZ - (int)Math.floor(Math.IEEEremainder(chunkZ, GRID_SIZE)) << 4) + 8;

        int pseudoRandomLast = 4;
        try {
            StringBuilder s = new StringBuilder();
            s.append(sphereX).append(":").append(sphereZ);
            MessageDigest md5 = MessageDigest.getInstance("SHA");
            byte[] thedigest = md5.digest(s.toString().getBytes());

//            Random random = new Random();
 //           random.setSeed(sphereX);

    //        int pseudoRandom = random.nextInt(Math.abs(sphereZ));
//            pseudoRandomLast = 8 + (int) (Math.floor(Math.IEEEremainder(thedigest[0], 16)));
            pseudoRandomLast = thedigest[0] & 0xf;
//            pseudoRandomLast = pseudoRandom - 20*Math.round(pseudoRandom/20);
        } catch (NoSuchAlgorithmException e) {
        }
        
        
//        System.out.print("pseudoRandomLast " + pseudoRandomLast);

        Biome[] biomes = new Biome[] {
            Biome.HELL,
            Biome.FOREST,
            Biome.FOREST,
            Biome.DESERT,
            Biome.DESERT,
            Biome.ICE_DESERT,
            Biome.RAINFOREST,
            Biome.RAINFOREST,
            Biome.PLAINS,
            Biome.PLAINS,
            Biome.SAVANNA,
            Biome.SEASONAL_FOREST,
            Biome.SAVANNA,
            Biome.SWAMPLAND,
            Biome.TUNDRA,
            Biome.TAIGA,
        };

        if (pseudoRandomLast < 0)
            pseudoRandomLast = 0;
        if (pseudoRandomLast >= biomes.length)
            pseudoRandomLast = biomes.length - 1;

        return biomes[pseudoRandomLast];
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
        lakeRadius = Math.round(0.75D * rndSphere.nextDouble() * sphereRadius);
        lakeEdgeRadius = lakeRadius + 2D;
        
        Biome biome = getBiome(chunkX, chunkZ);
        
        lavaLake = biome == Biome.HELL || biome == Biome.DESERT && rndSphere.nextInt(10) == 0;
        lavaLake = biome == Biome.HELL;

        switch(biome) {
            case RAINFOREST:
            case SWAMPLAND:
                hasLake = true;
                break;
            case DESERT:
            case ICE_DESERT:
            case SAVANNA:
            case FOREST:
                hasLake = false;
                break;
            default:
                hasLake = rndSphere.nextInt(2) == 0;
        }

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

    public void setNoise(Chunk chunk) {
        setNoise(chunk.getX(), chunk.getZ());
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
        BiosphereCaveGenerator caveGenerator = new BiosphereCaveGenerator(this);
        caveGenerator.generate(world, x, z, res);

//        for (int _x = 0; _x < 16; _x++) {
//            for (int _z = 0; _z < 16; _z++) {
//                for (int _y = 0; _y < 128; _y++) {
//                    if(_y < 2) {
//                        res[(_x * 16 + _z) * 128 + _y] = (byte)Material.BEDROCK.getId();
//                        continue;
//                    }
//                    if(_y < 3) {
//                        res[(_x * 16 + _z) * 128 + _y] = (byte)Material.STATIONARY_WATER.getId();
//                        continue;
//                    }
//                }
//            }
//        }
        
        
        return res;
    }
    
    @Override
    public Location getFixedSpawnLocation(World world, Random rndNoise) {
        Location loc = new Location(world, 0, 52, 0);
        return loc;
    }
    
    @Override
    public boolean canSpawn(World world, int x, int z) {
        int y = world.getHighestBlockYAt(x, z);
        Block highest = world.getBlockAt(x, y, z);
        return highest.getType() == Material.SAND || 
                highest.getType() == Material.GRASS;
//        System.out.print("Highest block: " + highest.getTypeId() + " (" + highest.getType().name());
//
//                return highest.getType() != Material.AIR
//                        && highest.getType() != Material.WATER
//                        && highest.getType() != Material.STATIONARY_WATER
//                        && highest.getType() != Material.BEDROCK
//                        && highest.getType() != Material.LAVA;
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
        if(NOISE) {
            double noiseHeight = 8;
            if(getBiome(x >> 4, z >> 4) == Biome.TAIGA) {
                noiseHeight = 20;
            }
            return (int)Math.round(64D + noise[z + x * 16] * noiseHeight);
        }
        else
            return 64;
    }
    
    
}

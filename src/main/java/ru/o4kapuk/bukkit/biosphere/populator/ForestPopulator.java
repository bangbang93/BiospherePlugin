package ru.o4kapuk.bukkit.biosphere.populator;

import org.bukkit.Chunk;
import org.bukkit.GrassSpecies;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import ru.o4kapuk.bukkit.biosphere.BiosphereGenerator;

import java.util.Random;

/**
 * @author Doodlez
 */
public class ForestPopulator extends BlockPopulator {
    private Random rnd;

    public ForestPopulator() {
        rnd = new Random(System.currentTimeMillis());
    }

    /**
     * Populates biome with tall grass.
     * @param world World given biome belongs to.
     * @param random Random seed generator.
     * @param chunk Current chunk.
     */
    @Override
    public void populate(World world, Random random, Chunk chunk) {
        Biome biome = BiosphereGenerator.getBiome(chunk);

        if (biome != Biome.FOREST
            && biome != Biome.RAINFOREST
            && biome != Biome.SEASONAL_FOREST
            && biome != Biome.PLAINS) {
            return;
        }

        for (int x = chunk.getX() << 4; x < 16 + chunk.getX() << 4; x++) {
            for (int z = chunk.getZ() << 4; z < 16 + chunk.getZ() << 4; z++) {
                int y = world.getHighestBlockYAt(x, z);

                // To prevent IndexOutOfBoundsException:
                if (y <= 0)
                    continue;

                Block underBlock = world.getBlockAt(x, y - 1, z);
                Block block = world.getBlockAt(x, y, z);

                if ((block.getType() == Material.AIR
                    || block.getType() == Material.SNOW)
                    && (underBlock.getType() == Material.GRASS)) {

                    if (biome == Biome.PLAINS) {
                        SetGrassAndMushrooms(block, 15, 20000);
                    }

                    if (biome == Biome.FOREST) {
                        SetGrassAndMushrooms(block, 40, 10000);
                    }

                    if (biome == Biome.SEASONAL_FOREST) {
                        SetGrassAndMushrooms(block, 50, 5000);
                    }

                    if (biome == Biome.RAINFOREST) {
                        SetGrassAndMushrooms(block, 100, 2000);
                    }
                }
            }
        }
    }

    /**
     * Sets given block with tall grass or mushrooms.
     * @param block Block to be set.
     * @param grassCoefficient Probability of placing grass.
     * @param mushroomCoefficient Probability of placing mushroom.
     */
    private void SetGrassAndMushrooms(Block block, int grassCoefficient, int mushroomCoefficient) {
        byte grassType;
        int grassRnd = rnd.nextInt(grassCoefficient);
        int mushroomRnd = rnd.nextInt(mushroomCoefficient);
        int fernRnd = rnd.nextInt(10); // if this is equal to 0, then grass will be fern, otherwise — normal tall grass.
        boolean isRedMushroom = rnd.nextBoolean();

        if (grassRnd == 0) {
            grassType = fernRnd == 0 ? GrassSpecies.FERN_LIKE.getData() : GrassSpecies.NORMAL.getData();
            block.setTypeIdAndData(Material.LONG_GRASS.getId(), grassType, false);
            return;
        }

        if (mushroomRnd == 42) {
            if (isRedMushroom) {
                block.setType(Material.RED_MUSHROOM);
            }
            else {
                block.setType(Material.BROWN_MUSHROOM);
            }
        }
    }
}

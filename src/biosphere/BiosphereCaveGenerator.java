package biosphere;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;

import biosphere.util.MathHelper;

/**
 *
 * @author o4kapuk
 */
public class BiosphereCaveGenerator {
    protected BiosphereGenerator generator = null;
    
    public BiosphereCaveGenerator(BiosphereGenerator generator) {
        this.generator = generator;
    }
    
    public void generate(World world, int x, int z, byte blockArray[]) {
        Random rand = new Random();
        rand.setSeed(world.getSeed());
        int k = 8;
        rand.setSeed(world.getSeed());
        long l = (rand.nextLong() / 2L) * 2L + 1L;
        long l1 = (rand.nextLong() / 2L) * 2L + 1L;
        for(int i1 = x - k; i1 <= x + k; i1++)
        {
            for(int j1 = z - k; j1 <= z + k; j1++)
            {
                rand.setSeed((long)i1 * l + (long)j1 * l1 ^ world.getSeed());
                func_868_a(world, rand, i1, j1, x, z, blockArray);
            }
        }        
    }

    protected void func_868_a(World world, Random rand, int i, int j, int k, int l, byte abyte0[])
    {
        int i1 = rand.nextInt(rand.nextInt(rand.nextInt(10) + 1) + 1);
        if(rand.nextInt(5) != 0)
        {
            i1 = 0;
        }
        for(int j1 = 0; j1 < i1; j1++)
        {
            double d = i * 16 + rand.nextInt(16);
            double d1 = rand.nextInt(128);
            double d2 = j * 16 + rand.nextInt(16);
            int k1 = 1;
            if(rand.nextInt(4) == 0)
            {
                a(rand, k, l, abyte0, d, d1, d2);
                k1 += rand.nextInt(4);
            }
            for(int l1 = 0; l1 < k1; l1++)
            {
                float f = rand.nextFloat() * 3.141593F * 2.0F;
                float f1 = ((rand.nextFloat() - 0.5F) * 2.0F) / 8F;
                float f2 = rand.nextFloat() * 2.0F + rand.nextFloat();
                a(rand, k, l, abyte0, d, d1, d2, f2 * 5F, f, f1, 0, 0, 0.5D);
            }

        }
    }

    protected void a(Random rand, int i, int j, byte abyte0[], double d, double d1, 
            double d2)
    {
        a(rand, i, j, abyte0, d, d1, d2, 10F + rand.nextFloat() * 20F, 0.0F, 0.0F, -1, -1, 0.5D);
    }

    protected void a(Random rand, int i, int j, byte abyte0[], double d, double d1, 
            double d2, float f, float f1, float f2, int k, int l, 
            double d3)
    {
        if(generator != null)
        {
            generator.setNoise(i, j);
        }
        double d4 = i * 16 + 8;
        double d5 = j * 16 + 8;
        float f3 = 0.0F;
        float f4 = 0.0F;
        Random random = new Random(rand.nextLong());
        if(l <= 0)
        {
            int i1 = 8 * 16 - 16;
            l = i1 - random.nextInt(i1 / 4);
        }
        boolean flag = false;
        if(k == -1)
        {
            k = l / 2;
            flag = true;
        }
        int j1 = random.nextInt(l / 2) + l / 4;
        boolean flag1 = random.nextInt(6) == 0;
        for(; k < l; k++)
        {
            
            double d6 = 1.5D + (double)(MathHelper.sin(((float)k * 3.141593F) / (float)l) * f * 1.0F);
            double d7 = d6 * d3;
            float f5 = MathHelper.cos(f2);
            float f6 = MathHelper.sin(f2);
            d += MathHelper.cos(f1) * f5;
            d1 += f6;
            d2 += MathHelper.sin(f1) * f5;
            if(flag1)
            {
                f2 *= 0.92F;
            } else
            {
                f2 *= 0.7F;
            }
            f2 += f4 * 0.1F;
            f1 += f3 * 0.1F;
            f4 *= 0.9F;
            f3 *= 0.75F;
            f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4F;
            if(!flag && k == j1 && f > 1.0F)
            {
                a(rand, i, j, abyte0, d, d1, d2, random.nextFloat() * 0.5F + 0.5F, f1 - 1.570796F, f2 / 3F, k, l, 1.0D);
                a(rand, i, j, abyte0, d, d1, d2, random.nextFloat() * 0.5F + 0.5F, f1 + 1.570796F, f2 / 3F, k, l, 1.0D);
                return;
            }
            if(!flag && random.nextInt(4) == 0)
            {
                continue;
            }
            double d8 = d - d4;
            double d9 = d2 - d5;
            double d10 = l - k;
            double d11 = f + 2.0F + 16F;
            if((d8 * d8 + d9 * d9) - d10 * d10 > d11 * d11)
            {
                return;
            }
            if(d < d4 - 16D - d6 * 2D || d2 < d5 - 16D - d6 * 2D || d > d4 + 16D + d6 * 2D || d2 > d5 + 16D + d6 * 2D)
            {
                continue;
            }
            int k1 = MathHelper.floor_double(d - d6) - i * 16 - 1;
            int l1 = (MathHelper.floor_double(d + d6) - i * 16) + 1;
            int i2 = MathHelper.floor_double(d1 - d7) - 1;
            int j2 = MathHelper.floor_double(d1 + d7) + 1;
            int k2 = MathHelper.floor_double(d2 - d6) - j * 16 - 1;
            int l2 = (MathHelper.floor_double(d2 + d6) - j * 16) + 1;
            if(k1 < 0)
            {
                k1 = 0;
            }
            if(l1 > 16)
            {
                l1 = 16;
            }
            if(i2 < 1)
            {
                i2 = 1;
            }
            if(j2 > 120)
            {
                j2 = 120;
            }
            if(k2 < 0)
            {
                k2 = 0;
            }
            if(l2 > 16)
            {
                l2 = 16;
            }
            boolean flag2 = false;
            for(int k3 = k1; !flag2 && k3 < l1; k3++)
            {
                for(int i4 = k2; !flag2 && i4 < l2; i4++)
                {
                    for(int j4 = j2 + 1; !flag2 && j4 >= i2 - 1; j4--)
                    {
                        int i3 = (k3 * 16 + i4) * 128 + j4;
                        if(j4 >= 0 && j4 < 128)
                        {
                            if(abyte0[i3] == Material.WATER.getId() || abyte0[i3] == Material.STATIONARY_WATER.getId() || abyte0[i3] == Material.LAVA.getId() || abyte0[i3] == Material.STATIONARY_LAVA.getId())
                            {
                                flag2 = true;
                            }
                            if(j4 != i2 - 1 && k3 != k1 && k3 != l1 - 1 && i4 != k2 && i4 != l2 - 1)
                            {
                                j4 = i2;
                            }
                        }
                    }

                }

            }

            if(flag2)
            {
                continue;
            }
            for(int l3 = k1; l3 < l1; l3++)
            {
                double d12 = (((double)(l3 + i * 16) + 0.5D) - d) / d6;
                for(int j3 = k2; j3 < l2; j3++)
                {
                    generator.midY = generator.getSurfaceLevel(l3, j3);
                    double d13 = (((double)(j3 + j * 16) + 0.5D) - d2) / d6;
                    int k4 = (l3 * 16 + j3) * 128 + j2;
                    for(int l4 = j2 - 1; l4 >= i2; l4--)
                    {
                        double d14 = (((double)l4 + 0.5D) - d1) / d7;
                        if(d14 > -0.69999999999999996D && d12 * d12 + d14 * d14 + d13 * d13 < 1.0D)
                        {
                            byte byte0 = abyte0[k4];
                            if(byte0 == Material.STONE.getId() || byte0 == Material.SAND.getId() ||  byte0 == Material.GRAVEL.getId()  || byte0 == Material.DIAMOND_ORE.getId()  || byte0 == Material.LAPIS_ORE.getId() )
                            {
                                double d15 = generator.getMainDistance((int)Math.round((d4 + (double)l3) - 8D), l4 - 1, (int)Math.round((d5 + (double)j3) - 8D));
                                if(l4 < BiosphereGenerator.LAVA_LEVEL)
                                {
                                    if(generator != null)
                                    {
                                        if(d15 >= generator.sphereRadius && d15 < generator.sphereRadius + 5D)
                                        {
                                            abyte0[k4] = (byte)Material.BEDROCK.getId();
                                        } else
                                        if(d15 < generator.sphereRadius)
                                        {
                                            abyte0[k4] = (byte)Material.LAVA.getId();
                                        }
                                    } else
                                    {
                                        abyte0[k4] = (byte)Material.LAVA.getId();
                                    }
                                } else
                                if((l4 < generator.midY - 2 || l4 > generator.midY - 1) && (d15 <= 2 + generator.sphereRadius))
                                {
                                    abyte0[k4] = 0;
                                }
                            }
                        }
                        k4--;
                    }

                }

            }

            if(flag)
            {
                break;
            }
        }

    }

}

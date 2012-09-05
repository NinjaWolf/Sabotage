package com.github.NinjaWolf.Sabotage.Generator;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class Arena_Generator extends ChunkGenerator {
    public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid)
    {
        byte[][] result = new byte[16][];
        int x,z;

        for(x = 0; x < 16; x++) {
            for(z = 0; z < 16; z++) {
                setBlock(result, x, 64, z, (byte)Material.BEDROCK.getId());
            }
        }
        return result;
    }
    
    void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
        if (result[y >> 4] == null) {
            result[y >> 4] = new byte[4096];
        }
        result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
    }
}

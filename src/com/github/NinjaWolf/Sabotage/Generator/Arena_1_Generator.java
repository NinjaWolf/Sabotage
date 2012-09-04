package com.github.NinjaWolf.Sabotage.Generator;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class Arena_1_Generator extends ChunkGenerator {
  public byte[] generate(World world, Random random, int cx, int cz) {
    return new byte[32768];
  }

  public Location getSpawnLocation(World world, Random random) {
    return new Location(world, 0.0D, 66.0D, 0.0D);
  }
}
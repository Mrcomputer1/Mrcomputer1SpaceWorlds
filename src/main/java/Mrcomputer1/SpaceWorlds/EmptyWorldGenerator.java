package Mrcomputer1.SpaceWorlds;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

/**
 * Empty world generator
 * 
 * @author Mrcomputer1
 */
public class EmptyWorldGenerator extends ChunkGenerator{
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
		ChunkData chunk = createChunkData(world);
		return chunk;
	}
	
}

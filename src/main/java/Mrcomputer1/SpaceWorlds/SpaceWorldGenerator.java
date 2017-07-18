package Mrcomputer1.SpaceWorlds;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

/**
 * Empty world with stars generator.
 * 
 * @author Mrcomputer1
 */
public class SpaceWorldGenerator extends ChunkGenerator {
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
		ChunkData cd = createChunkData(world);
		for(int x2 = 0; x2 <= 15; x2++){
			for(int z2 = 0; z2 <= 15; z2++){
				int r = random.nextInt(50);
				if(r == 25){
					cd.setBlock(x2, z2, random.nextInt(255), Material.ENDER_STONE);
				}
			}
		}
		return cd;
	}
	
}

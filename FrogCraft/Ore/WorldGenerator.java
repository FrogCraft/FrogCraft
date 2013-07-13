package FrogCraft.Ore;

import java.util.Random;

import FrogCraft.api.fcItems;


import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator{
	public static boolean genCarnallite,genFluorapatite,genClay;
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		//BiomeGenBase bio=world.getBiomeGenForCoords(chunkX, chunkZ);
		//String bio_name=bio.biomeName;
		int j,k;
		if(genCarnallite){
			j = chunkX*16+rand.nextInt(16);
        	k = chunkZ*16+rand.nextInt(16);
        	new WorldGen_Claylike(fcItems.oreID,0,3).generate(world, rand, j, world.getTopSolidOrLiquidBlock(j, k), k);
		}
    	
    	if(genFluorapatite){
    		j = chunkX*16+rand.nextInt(16);
    		k = chunkZ*16+rand.nextInt(16);
    		for (int i=0;i<2;i++)
    			new WorldGenMinable(fcItems.oreID,1,12,Block.stone.blockID).generate(world, rand, j, 10+rand.nextInt(40), k);
    	}
    	
    	if(genClay){
    		j = chunkX*16+rand.nextInt(16);
    		k = chunkZ*16+rand.nextInt(16);
    		for (int i=0;i<2;i++)
    			new WorldGenMinable(Block.blockClay.blockID,0,12,Block.stone.blockID).generate(world, rand, j, 25+rand.nextInt(40), k);    	
    	}
	}

	//Currently useless
	void gen_ore(Random rand, int chunkX, int chunkZ, World world ,int startY,int rangeY, int blockID){
		for (int i = 0; i < 5; i++) {
			//Coord of the center
			int x = chunkX*16 + rand.nextInt(16);
			int y = startY+rand.nextInt(rangeY);
			int z = chunkZ*16 + rand.nextInt(16);
			
			int x1=5,y1=5,z1=5;
			
			if (world.getBlockId(x, y+2, z)==Block.waterStill.blockID&&world.getBlockId(x, y-1, z)!=Block.waterStill.blockID)
			for (int w=0;w<rand.nextInt(x1-1)+1;w++){
				for(int t=0;t<rand.nextInt(y1-1)+1;t++){
					for(int h=0;h<rand.nextInt(z1-1)+1;h++){
							world.setBlock(x+(w-8/2),y+(t-t/2),z+(h-h/2),blockID);
					}
				}
			}
		}		
	}
}

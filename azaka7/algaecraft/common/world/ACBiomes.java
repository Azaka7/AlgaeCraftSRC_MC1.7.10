package azaka7.algaecraft.common.world;

import azaka7.algaecraft.common.world.WorldTypeAC;
import azaka7.algaecraft.common.ACConfiguration;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class ACBiomes {
	
	static final BiomeGenBase.Height height_Lake = new BiomeGenBase.Height(-0.4F, 0.02F);
	
	public static BiomeGenBase greatLake;
	//public static BiomeGenBase greatLakeFlowered;
	
	public ACBiomes(){
		
	}
	
	public static void initBiomes(){
		greatLake = new BiomeGenGreatLake(getOpenID("Great Lake ID", 100), true, false).setHeight(height_Lake).setColor(48).setBiomeName("Great Lake");
		//greatLakeFlowered = new BiomeGenGreatLake(greatLake.biomeID+128, true, true).setHeight(height_Lake).setColor(48).setBiomeName("Flowered Great Lake");

		ACConfiguration.startSection("Biomes");
		int greatLakeWeight = ACConfiguration.getInt("Great Lake Spawn Weight", 10, "Determines the relative weight of a Great Lake biome generating. (Default = 10)");
		ACConfiguration.endSection();
		if(greatLakeWeight > 0){
			BiomeManager.addSpawnBiome(greatLake);
			BiomeManager.addBiome(BiomeType.COOL, new BiomeManager.BiomeEntry(greatLake, greatLakeWeight));
		}
	}
	
	private static int getOpenID(String biome, int def){
		BiomeGenBase[] array = BiomeGenBase.getBiomeGenArray();
		ACConfiguration.startSection("Biomes");
		
		int configID = ACConfiguration.getInt(biome, def);
		
		if(array[configID] == null){
			return configID;
		}
		
		for(int i = array.length-1; i > 0; i--){
			if(array[i] != null){
				return i;
			}
		}
		ACConfiguration.endSection();
		return 127;
	}
	
	public static boolean isBiomeIDInList(BiomeGenBase biome, int[] ints){
		for(int i : ints){
			if(biome.biomeID == i){
				return true;
			}
		}
		return false;
	}
	
}
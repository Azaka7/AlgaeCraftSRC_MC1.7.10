package azaka7.algaecraft.common;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;

public class ACGameData {

	public static final ACGameData INSTANCE = new ACGameData();
	
	public static boolean detectThaumcraft;
	public static boolean enableThaumicItems;
	
	public static boolean detectThermalExpansion;
	public static boolean enableRFItems;
	public static boolean enableRFDevices;
	
	public static Map<String, Integer[]> intArrays = new HashMap<String, Integer[]>();
	public static Map<String, Boolean> booleans = new HashMap<String, Boolean>();

	public static int[] biomeIDSwampList;
	public static int[] biomeIDOceanList;
	
	public static boolean generateGuayule;
	//public static int waterLightDecrement;
	public static boolean spongeNeighborUpdates = true;
	public static boolean sporousMossGen = true;

	public static int algaeModelID;
	public static int spongeModelID;
	public static int coralModelID;
	public static int seaweedModelID;
	public static int greekFireModelID;
	public static int brazierModelID;
	
	public static int maxFishSpawn = 128;
	public static int spawnFishWeight = 55;
	public static int spawnLobsterWeight = 20;

	public static double algaeGrowthChance = 0.125;
	public static double coralGrowthChance = 0.05;
	public static double seaweedGrowthChance = 0.015625;
	public static double aerosGrowthChance = 0.05;
	public static double spongeGrowthChance = 0.08;
	public static double guayuleGrowthChance = 0.75;
	
	public static int filterUpdateRate = 40;

	public static boolean genShips = true;

	public static double shipGenChance = 0.0625;
	
	public static boolean enableSCUBA = true;
	public static boolean enableAltTridents = true;
	public static boolean enableFlasks = true;
	
	public static int algaeGenRate = 4;
	public static int coralGenRate = 16;
	public static int seaweedGenRate = 64;
	public static int guayuleGenRate = 8;
	public static int sedimentGenRate = 64;
	
	
	private static final Class<?>[][] paramTypes = new Class[][] {{EnumCreatureType.class, Class.class, int.class, Material.class, boolean.class, boolean.class}};

		public void configure(){
			BiomeGenBase[] oceans = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.OCEAN);
			int[] oceanIDs = new int[oceans.length];
			for(int i = 0; i < oceans.length; i++){
				oceanIDs[i] = oceans[i].biomeID;
			}
			BiomeGenBase[] swamps = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.SWAMP);
			int[] swampIDs = new int[swamps.length];
			for(int i = 0; i < swamps.length; i++){
				swampIDs[i] = swamps[i].biomeID;
			}
			ACConfiguration.startSection("General");
			genShips = ACConfiguration.getBool("Generate Ships", true, "Should ships and wrecks be generated?");
			maxFishSpawn = ACConfiguration.getInt("Max Fish Count", 128, "Determines water mob spawning cap (AC Default = 128, Vanilla = 5)");
			spawnFishWeight = ACConfiguration.getInt("Spawn Fish Weight", 55, "The weighted chance for fish to spawn (squid = 10)");
			spawnLobsterWeight = ACConfiguration.getInt("Spawn Lobster Weight", 20, "The weighted chance for lobsters to spawn (squid = 10)");
			generateGuayule = ACConfiguration.getBool("Do Generate Guayule", true, "Does Guayule generate in the world? Note: setting to false will make certain parts of AlgaeCraft inaccessable without another mod that adds rubber.");
			spongeNeighborUpdates = ACConfiguration.getBool("Do Sponge Neighbor Updates", true, "Does Sponge update when neighboring blocks change? WARNING: Changing to false will disable certain important mechanics of Sponge.");
			filterUpdateRate = ACConfiguration.getInt("Water Filter Update Rate", 40, "Water filters should update once every this many ticks. (Default = 40)");
			sporousMossGen = ACConfiguration.getBool("Sporous Filter Moss", true, "Does the Sporous Filter try to convert cobblestone and stone brick to the mossy type?");
			shipGenChance = ACConfiguration.getDouble("Ship Gen Chance", 0.0625, "The chance that a ship or wreck will try to generate in each chunk. (Default = 0.0625 = 6.25%)");
			ACConfiguration.endSection();
			
			ACConfiguration.startSection("Growth Rates");
			
			algaeGrowthChance = ACConfiguration.getDouble("Algae Growth Rate", 0.125, "The chance that Algae will try to grow in each direction each random block tick. (Default = 0.125 = 12.5% chance)");
			coralGrowthChance = ACConfiguration.getDouble("Coral Growth Rate", 0.05, "The chance that Coral will try to grow each random block tick. (Default = 0.05 = 5% chance)");
			seaweedGrowthChance = ACConfiguration.getDouble("Seaweed Growth Rate", 0.015625, "The chance that Seaweed will try to grow each random block tick. (Default = 0.015625 = 1.5625% chance)");
			aerosGrowthChance = ACConfiguration.getDouble("Aeros Growth Rate", 0.05, "The chance that Aeros Plantae will try to grow each random block tick. (Default = 0.05 = 5% chance)");
			spongeGrowthChance = ACConfiguration.getDouble("Max Sponge Growth Rate", 0.08, "The max chance that Sponge Spores will try to grow each random block tick (Top half of chance based on light level). (Default = 0.08 = 4%-8% chance)");
			guayuleGrowthChance = ACConfiguration.getDouble("Guayule Growth Rate", 0.05, "The chance that Guayule will try to grow each random block tick. (Default = 0.05 = 5% chance)");
			
			ACConfiguration.endSection();
			
			ACConfiguration.startSection("Biome Definitions");
			biomeIDSwampList = ACConfiguration.getIntArray("Swamp Biome IDs", swampIDs);
			biomeIDOceanList = ACConfiguration.getIntArray("Ocean Biome IDs", oceanIDs);//BiomeGenBase.ocean.biomeID,BiomeGenBase.deepOcean.biomeID});
			ACConfiguration.endSection();
			
			ACConfiguration.startSection("Model IDs");
			ACConfiguration.addComment("Leave ModelID as -1 to have Forge determine an available ID. Specify a value at your own discretion.");
			//specialCoralRender = ACConfiguration.getBool("Enable Special Water Plant/Coral Rendering", false);
			algaeModelID = ACConfiguration.getInt("Algae Model ID", -1);
			if(algaeModelID < 0){algaeModelID = RenderingRegistry.getNextAvailableRenderId();}
			coralModelID = ACConfiguration.getInt("Coral Model ID", -1);
			if(coralModelID < 0){coralModelID = RenderingRegistry.getNextAvailableRenderId();}
			spongeModelID = ACConfiguration.getInt("Sponge Spore Model ID", -1);
			if(spongeModelID < 0){spongeModelID = RenderingRegistry.getNextAvailableRenderId();}
			seaweedModelID = ACConfiguration.getInt("Seaweed Model ID", -1);
			if(seaweedModelID < 0){seaweedModelID = RenderingRegistry.getNextAvailableRenderId();}
			greekFireModelID = ACConfiguration.getInt("Greek Fire Model ID", -1);
			if(greekFireModelID < 0){greekFireModelID = RenderingRegistry.getNextAvailableRenderId();}
			brazierModelID = ACConfiguration.getInt("Brazier Model ID", -1);
			if(brazierModelID < 0){brazierModelID = RenderingRegistry.getNextAvailableRenderId();}
			//waterBlockModelID = ACConfiguration.getInt("Special Wet Blocks Model ID", -1);
			//if(waterBlockModelID < 0){waterBlockModelID = RenderingRegistry.getNextAvailableRenderId();}
			
			
			ACConfiguration.endSection();
			ACConfiguration.startSection("Inter-Mod Handling");
			ACConfiguration.addComment("Handle AlgaeCraft's detection of and adaption to other mods");
			
			detectThaumcraft = ACConfiguration.getBool("Detect Thaumcraft", true, "Should AC try to detect Thaumcraft and enable compatabilities");
			enableThaumicItems = ACConfiguration.getBool("Enable Thaumic Items", true, "Should AC add Thaumcraft-dependent blocks and items if TC is detected");
			
			detectThermalExpansion = ACConfiguration.getBool("Detect ThermalExpansion", true, "Should AC try to detect ThermalExpansion and enable compatabilities");
			enableRFItems = ACConfiguration.getBool("Enable RF Items", true, "Should AC add RF-dependent blocks and items if TE is detected");
			enableRFDevices = ACConfiguration.getBool("Enable RF Devices", true, "Allow AlgaeCraft to automatically enable RF usage on certain devices when ThermalExpansion is detected (see forum for list)");
			
			ACConfiguration.endSection();
			
			ACConfiguration.startSection("Content");
			ACConfiguration.addComment("This section allows the user to completely disable portions of AlgaeCraft. When disabled, the content is completely inaccessable. This may cause conflics if a client disables content and attempts to join a server that has not disabled the content.");
			enableSCUBA = ACConfiguration.getBool("Enable SCUBA Gear", true, "If set to false, all dive equipment will not exist in game.");
			enableAltTridents = ACConfiguration.getBool("Enable Alternative Tridents", true, "If set to false, tridents made of non-vanilla tool materials (like bone and emerald) will not exist in game.");
			enableFlasks = ACConfiguration.getBool("Enable Flasks", true, "If set to false, flasks and Greek fire will not exist in game.");
			
			ACConfiguration.endSection();
			ACConfiguration.startSection("World Gen Rates");
			ACConfiguration.addComment("This section allows the user to change the generaton rates of various AlgaeCraft features.");

			algaeGenRate = ACConfiguration.getInt("Algae Gen Rate", 4, "Algae will try to generate up to this many times per chunk. (Default = 4");
			coralGenRate = ACConfiguration.getInt("Coral Gen Rate", 16, "Coral will try to generate up to this many times per chunk. (Default = 16");
			seaweedGenRate = ACConfiguration.getInt("Seaweed Gen Rate", 64, "Seaweed will try to generate up to this many times per chunk. (Default = 64");
			guayuleGenRate = ACConfiguration.getInt("Guayule Gen Rate", 8, "Guayule will try to generate up to this many times per chunk. (Default = 8");
			sedimentGenRate = ACConfiguration.getInt("Sediment Gen Rate", 64, "Ocean Floor Sediment will try to generate up to this many times per chunk. (Default = 64");
			
			ACConfiguration.endSection();
		}
		
		public static BiomeGenBase[] getOceanBiomes(){
			
			BiomeGenBase[] ret = new BiomeGenBase[biomeIDOceanList.length];
			int trueLength = 0;
			for(int i  = 0; i < biomeIDOceanList.length; i++){
				BiomeGenBase biome = BiomeGenBase.getBiome(biomeIDOceanList[i]);
				if(biome != null){
					ret[i] = biome;
					trueLength++;
				} else {
					System.out.println("[AlgaeCraft] Biome ID "+biomeIDOceanList[i]+" is invalid. Ignoring.");
				}
			}
			BiomeGenBase[] ret2 = new BiomeGenBase[trueLength];
			int shift = 0;
			for(int i = 0; i < biomeIDOceanList.length && i - shift < trueLength; i++){
				BiomeGenBase biome = BiomeGenBase.getBiome(biomeIDOceanList[i]);
				if(biome != null){
					ret2[i - shift] = biome;
				} else {
					shift++;
				}
			}
			return ret2;
		}

}

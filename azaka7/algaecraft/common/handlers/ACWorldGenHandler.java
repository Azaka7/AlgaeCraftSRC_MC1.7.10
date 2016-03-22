package azaka7.algaecraft.common.handlers;

import java.util.Random;

import azaka7.algaecraft.common.ACConfiguration;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.blocks.ACBlocks;
import azaka7.algaecraft.common.world.ACBiomes;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

public class ACWorldGenHandler implements IWorldGenerator {
	
	private static final ACWorldGenHandler INSTANCE = new ACWorldGenHandler();
	private static final WorldGenMinable sedimentGen = new WorldGenMinable(ACBlocks.sediment, 32, Blocks.gravel);
	
	
	public static void register(){
		MinecraftForge.TERRAIN_GEN_BUS.register(INSTANCE);
		GameRegistry.registerWorldGenerator(INSTANCE, 0);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId){
			case -1: generateNether(world, random, chunkX, chunkZ);
			case 0: generateSurface(world, random, chunkX, chunkZ);
			case 1: generateEnd(world, random, chunkX, chunkZ);
		}
	}

	private void generateEnd(World world, Random random, int i, int j) {
		
	}

	private void generateSurface(World world, Random random, int i, int j) {
		
		generateAlgaeForChunk(world, random, i, j);
		if(ACGameData.generateGuayule){
			generateGuayuleForChunk(world, random, i, j);
		}
		generateSedimentForChunk(world, random, i, j);
				
		int microX = Math.round(i/3);
		int microZ = Math.round(j/3);
		int chunkSeed = (int) ((Math.pow(microX, 2)*microZ)+microX); 
		boolean flag1 = random.nextBoolean();
		Random rand = new Random(); rand.setSeed(chunkSeed);
		boolean flag2 = rand.nextBoolean();
		if(flag1 != flag2){
			if(flag1){
				this.generateCoralForChunk(world, random, i, j);
			}
			else if(flag2){
				for (int c = 0; c < 12; ++c)
		        {
		            int k = (i*16) + random.nextInt(16) + 8;
		            int l = (j*16) + random.nextInt(16) + 8;
		            if(BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(i*16, j*16), BiomeDictionary.Type.OCEAN)){
		            	int i1 = 36;
		            	this.generateSeaweedForPos(world, random, k, i1, l);
		            }
		        }
			}
		}
	}

	private void generateNether(World world, Random random, int i, int j) {
		
	}

	private void generateSedimentForChunk(World world, Random random, int i, int j) {
		int x0 = i * 16;
		int z0 = j * 16;
		for(int c = 0; c < 64; c++){
			int x1 = x0 + random.nextInt(16);
			int z1 = z0 + random.nextInt(16);
			if(BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(x1, z1), BiomeDictionary.Type.OCEAN)){
				int y1 = 28 + random.nextInt(56-28);
				sedimentGen.generate(world, random, x1, y1, z1);
			}
		}
	}

	private void generateGuayuleForChunk(World world, Random random, int i, int j) {
		int x = i*16;
		int z = j*16;
		int y = world.getHeightValue(x, z);
		for(int c = 0; c < 8; c++){
			int x1 = x + 8 + random.nextInt(8) - random.nextInt(8);
			int z1 = z + 8 + random.nextInt(8) - random.nextInt(8);
			int y1 = y + random.nextInt(4) - random.nextInt(4);
			if(BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(x1, z1), BiomeDictionary.Type.SANDY)
					&& BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(x1, z1), BiomeDictionary.Type.HOT)
					&& BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(x1, z1), BiomeDictionary.Type.DRY)){
				if(world.isAirBlock(x1, y1, z1) && ACBlocks.guayule.canBlockStay(world, x1, y1, z1)){
					world.setBlock(x1, y1, z1, ACBlocks.guayule);
				}
			}
		}
	}
	
	private void generateAlgaeForChunk(World world, Random random, int i, int j){
		for(int n = 0; n < 4; n++){
			int x = (i*16) + random.nextInt(16);
			int z = (j*16) + random.nextInt(16);
			if(ACBiomes.isBiomeIDInList(world.getBiomeGenForCoords(x, z), ACGameData.biomeIDSwampList)){
				int y = 63 + random.nextInt(3);
				if(world.getBlock(x, y, z) == Blocks.air && (world.getBlock(x, y-1, z) == Blocks.water)){
					world.setBlock(x, y, z, ACBlocks.algae);
				}
			}
		}
	}
	
	public boolean generateSeaweedForPos(World world, Random rand, int i, int j, int k)
    {
        for (int l = 0; l < 64; ++l)
        {
            int i1 = i + rand.nextInt(8) - rand.nextInt(8);
            int j1 = j + rand.nextInt(6) - rand.nextInt(5);
            int k1 = k + rand.nextInt(8) - rand.nextInt(8);

            if (world.getBlock(i1, j1, k1) == Blocks.water && ACBlocks.seaweed.canBlockStay(world, i1, j1, k1))
            {
                world.setBlock(i1, j1, k1, ACBlocks.seaweed, 0, 2);
            }
            j1 += 1;
            if (world.getBlock(i1, j1, k1) == Blocks.water && ACBlocks.seaweed.canBlockStay(world, i1, j1, k1))
            {
                world.setBlock(i1, j1, k1, ACBlocks.seaweed, 0, 2);
            }
            j1 += 1;
            if (world.getBlock(i1, j1, k1) == Blocks.water && ACBlocks.seaweed.canBlockStay(world, i1, j1, k1))
            {
                world.setBlock(i1, j1, k1, ACBlocks.seaweed, 0, 2);
            }
        }

        return true;
    }
	
	private void generateCoralForChunk(World world, Random random, int i, int j){
		for(int n = 0; n < 16; n++){
			int x = (i*16) + random.nextInt(16);
			int z = (j*16) + random.nextInt(16);
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
			if(BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(x, z), BiomeDictionary.Type.OCEAN)){
				int y = 44;//Math.round((((biome.rootHeight + 2)/4)*128)-8);
				y += random.nextInt(10);//random.nextInt(20);
				if(world.getBlock(x, y, z) == Blocks.water && world.getBlock(x, y-1, z).getMaterial().isSolid() && world.getBlock(x, y-1, z) != ACBlocks.limestone){
					int dx = random.nextInt(3)+3;
					int dz = random.nextInt(3)+3;
					int dy = 0;
					
					for(int rx = -dx; rx <= dx; rx++){
						for(int rz = -dz; rz <= dz; rz++){
							int ry = (int) -Math.floor(Math.max(Math.abs(dx), Math.abs(dz)) / (Math.max(Math.abs(rx), Math.abs(rz))+1));
							ry = ry + dy;
							int thisX = x+rx;
							int thisZ = z + rz;
							for(ry = ry+0; ry <= 0; ry++){
								Block block = (random.nextInt(5) < 4 ? ACBlocks.limestone : Blocks.water);
								if(ry > -2 && block == Blocks.water && world.getBlock(thisX, y+ry-1, thisZ).getMaterial().isSolid()){
									int flag = random.nextInt(3);
									if(flag == 0){
										world.setBlock(thisX, y+ry, thisZ, ACBlocks.coral, 8+random.nextInt(8), 2);
										if(random.nextInt(64)==42){
											world.setBlock(thisX, y+ry, thisZ, ACBlocks.aerosPlantae, 0, 2);
										}
									}else if(flag == 1){
										world.setBlock(thisX, y+ry, thisZ, random.nextBoolean() ? ACBlocks.spongeSpore : ACBlocks.spongeRedSpore, random.nextInt(3), 2);
									}
									else{
										world.setBlock(thisX, y+ry, thisZ, Blocks.water);
									}
								}
								else{
									world.setBlock(thisX, y+ry, thisZ, block);
									if(ry+1 > 0 && block == ACBlocks.limestone){
										int flag = random.nextInt(6);
										if(flag < 3){
											world.setBlock(thisX, y+ry+1, thisZ, ACBlocks.coral, 8+random.nextInt(8), 2);
											if(random.nextInt(64)==42){
												world.setBlock(thisX, y+ry, thisZ, ACBlocks.aerosPlantae, 0, 2);
											}
										}else if(flag == 3){
											world.setBlock(thisX, y+ry+1, thisZ, random.nextBoolean() ? ACBlocks.spongeSpore : ACBlocks.spongeRedSpore, random.nextInt(3), 2);
										}
										else{
											world.setBlock(thisX, y+ry+1, thisZ, Blocks.water);
										}
									}
								}
							}
						}
					}
					
					if(random.nextInt(20)==0){
						world.setBlock(x, y+1, z,Blocks.water);
						world.setBlock(x, y, z, Blocks.mob_spawner, 0, 3);
						TileEntityMobSpawner tems = (TileEntityMobSpawner) world.getTileEntity(x, y, z);
						if(tems != null){
							tems.func_145881_a().setEntityName("ac_fish");
			            }
			            else
			            {
			                System.err.println("Failed to fetch mob spawner entity at (" + x + ", " + y + ", " + z + ")");
			            }
					}
					
				}
			}
		}
	}
}
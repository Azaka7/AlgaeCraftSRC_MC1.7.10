package azaka7.algaecraft.common.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.ACConfiguration;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.world.ACBiomes;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;

//Obfuscated names: C:>Users>[session name]>.gradle>caches>minecraft>net>minecraftforge>forge>[forge version]>unpacked>conf

public class ACEntities {

	public static final String fish_tag = "ac_fish";
	public static final String lobster_tag = "ac_lobster";
	public static final String gf_tag = "ac_greekFire";
	public static final String gfb_tag = "ac_greekFireBomb";

	public static void registerEntities(){
		ACConfiguration.startSection("Entities");
		
		EntityRegistry.registerGlobalEntityID(EntityLobster.class, lobster_tag, EntityRegistry.findGlobalUniqueEntityId(), 0xCC2C18, 0x0C0101);
		EntityRegistry.registerGlobalEntityID(EntityFish.class, fish_tag, EntityRegistry.findGlobalUniqueEntityId(), 0xCCCCFF, 0x8C8C8F);
		
		//EntityRegistry.registerGlobalEntityID(EntityGreekFireBomb.class, gfb_tag, EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityGreekFireBomb.class, gfb_tag, EntityRegistry.findGlobalUniqueEntityId(), AlgaeCraft.MODID, 64, 10, true);
		EntityRegistry.registerModEntity(EntityGreekFire.class, gf_tag, EntityRegistry.findGlobalUniqueEntityId(), AlgaeCraft.MODID, 64, 10, true);
		
		EntityRegistry.addSpawn(EntityLobster.class, 20, 4, 10, EnumCreatureType.waterCreature, ACGameData.getOceanBiomes());
		List<BiomeGenBase> fishBiomes = new ArrayList<BiomeGenBase>();
		for(BiomeGenBase gen : ACGameData.getOceanBiomes())
			fishBiomes.add(gen);
		for(int gen : ACGameData.biomeIDSwampList)
			fishBiomes.add(BiomeGenBase.getBiome(gen));
		fishBiomes.add(BiomeGenBase.river); fishBiomes.add(BiomeGenBase.beach); fishBiomes.add(BiomeGenBase.frozenRiver); fishBiomes.add(BiomeGenBase.coldBeach); fishBiomes.add(BiomeGenBase.mushroomIslandShore); fishBiomes.add(ACBiomes.greatLake);
		BiomeGenBase[] fishBiomeArray = new BiomeGenBase[fishBiomes.size()];
		for(int i = 0; i < fishBiomes.size(); i++){
			fishBiomeArray[i] = fishBiomes.get(i);
		}
		EntityRegistry.addSpawn(EntityFish.class, 55, 12, 20, EnumCreatureType.waterCreature, fishBiomeArray);
		
		Field f = ReflectionHelper.findField(EnumCreatureType.class, "maxNumberOfCreature", "field_75606_e");
		f.setAccessible(true);
		try {
			f.setInt(EnumCreatureType.waterCreature, ACGameData.maxFishSpawn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ACConfiguration.endSection();
	}

}

package azaka7.algaecraft.common.entity;

import java.lang.reflect.Field;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import azaka7.algaecraft.common.ACConfiguration;
import azaka7.algaecraft.common.handlers.ACReflectionHelper;
import azaka7.algaecraft.common.world.ACBiomes;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ACEntities {

	public static final String fish_tag = "ac_fish";
	public static final String lobster_tag = "ac_lobster";

	public static void registerEntities(){
		ACConfiguration.startSection("Entities");
		
		EntityRegistry.registerGlobalEntityID(EntityLobster.class, lobster_tag,EntityRegistry.findGlobalUniqueEntityId(), 0xCC2C18, 0x0C0101);
		EntityRegistry.registerGlobalEntityID(EntityFish.class, fish_tag,EntityRegistry.findGlobalUniqueEntityId(), 0xCCCCFF, 0x8C8C8F);
		EntityRegistry.addSpawn(EntityLobster.class, 20, 4, 10, EnumCreatureType.waterCreature, new BiomeGenBase[]{BiomeGenBase.ocean,BiomeGenBase.beach});
		EntityRegistry.addSpawn(EntityFish.class, 55, 12, 20, EnumCreatureType.waterCreature, new BiomeGenBase[]{BiomeGenBase.ocean, BiomeGenBase.beach, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.swampland, BiomeGenBase.deepOcean, BiomeGenBase.mushroomIslandShore, BiomeGenBase.coldBeach, ACBiomes.greatLake/*, ACBiomes.greatLakeFlowered*/});
		
		Field f = ReflectionHelper.findField(EnumCreatureType.class, "maxNumberOfCreature", "field_75606_e");
		f.setAccessible(true);
		try {
			f.setInt(EnumCreatureType.waterCreature, 128);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ACConfiguration.endSection();
	}

}

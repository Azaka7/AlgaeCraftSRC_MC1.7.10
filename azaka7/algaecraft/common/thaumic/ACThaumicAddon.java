package azaka7.algaecraft.common.thaumic;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.blocks.ACBlocks;
import azaka7.algaecraft.common.items.ACItems;
import azaka7.algaecraft.common.items.ItemBCD;
import azaka7.algaecraft.common.items.ItemDiveMask;
import azaka7.algaecraft.common.items.ItemFlippers;
import azaka7.algaecraft.common.items.ItemWetsuit;
import azaka7.algaecraft.common.entity.ACEntities;
import cpw.mods.fml.common.event.FMLInterModComms;

public class ACThaumicAddon {
	
	private static final ResourceLocation abyssumIcon = new ResourceLocation(AlgaeCraft.MODID+":textures/thaum/abyssum.png");
	public static final Aspect ABYSS = new Aspect("abyssum", 0x080864, new Aspect[]{Aspect.WATER,Aspect.DARKNESS}, abyssumIcon, 771);

	public static void register(){
		
		//Plant Harvesting
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockGuayule, 1, 3));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockCoral, 1, 0));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockCoral, 1, 1));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockCoral, 1, 2));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockCoral, 1, 3));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockCoral, 1, 8));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockCoral, 1, 9));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockCoral, 1, 10));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockCoral, 1, 11));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockLobsterCage, 1, 1));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockAerosPlantae, 1, 2));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(ACBlocks.blockAerosPlantae, 1, 11));
		FMLInterModComms.sendMessage("Thaumcraft", "harvestStackedCrop", new ItemStack(ACBlocks.blockSeaweed, 1));
		
		//Portable Hole Blacklist
		ThaumcraftApi.portableHoleBlackList.add(ACBlocks.blockAirCompressor);
		ThaumcraftApi.portableHoleBlackList.add(ACBlocks.blockFilter);
		ThaumcraftApi.portableHoleBlackList.add(ACBlocks.blockLobsterCage);

		//Entity Aspects
		ThaumcraftApi.registerEntityTag(ACEntities.lobster_tag, (new AspectList()).add(Aspect.BEAST, 2).add(Aspect.WATER, 2).add(Aspect.FLESH, 2).add(ABYSS, 1));
		ThaumcraftApi.registerEntityTag(ACEntities.fish_tag, (new AspectList()).add(Aspect.BEAST, 1).add(Aspect.WATER, 1).add(Aspect.SENSES, 1));
		
		//Smelting Bonus
		ThaumcraftApi.addSmeltingBonus(new ItemStack(ACBlocks.blockAlgae), new ItemStack(ACItems.itemAlgaeCooked, 0, 0));
		ThaumcraftApi.addSmeltingBonus(new ItemStack(ACBlocks.blockLimestone, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.glowstone_dust, 0, 0));
		
		//Block Aspects
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockAlgae), (new AspectList()).add(Aspect.PLANT, 1).add(Aspect.SLIME, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockSpongeSpore), (new AspectList()).add(Aspect.CROP, 1).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockSpongeRedSpore), (new AspectList()).add(Aspect.CROP, 1).add(Aspect.WATER, 1).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.sponge, 1, 0),(new AspectList()).add(Aspect.PLANT, 1).add(Aspect.WATER, 2).add(Aspect.VOID, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.sponge, 1, 1),(new AspectList()).add(Aspect.PLANT, 1).add(Aspect.WATER, 10));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockSpongeRed, 1, 0),(new AspectList()).add(Aspect.PLANT, 1).add(Aspect.SENSES, 1).add(Aspect.WATER, 2).add(Aspect.VOID, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockSpongeRed, 1, 1),(new AspectList()).add(Aspect.PLANT, 1).add(Aspect.SENSES, 1).add(Aspect.WATER, 10));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockSeaweed),(new AspectList()).add(Aspect.PLANT, 2).add(Aspect.WATER, 1).add(ABYSS, 1));
		for(int n = 0; n < 4; n++){
			ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockCoral, 1, n), (new AspectList()).add(Aspect.WATER, 2).add(Aspect.PLANT, 1).add(Aspect.BEAST, 1));
			ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockCoral, 1, n+8), (new AspectList()).add(Aspect.WATER, 2).add(Aspect.PLANT, 1).add(Aspect.BEAST, 1));
		}
		for(int n = 4; n < 8; n++){
			ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockCoral, 1, n), (new AspectList()).add(Aspect.LIFE, 1).add(Aspect.WATER, 1));
			ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockCoral, 1, n+8), (new AspectList()).add(Aspect.LIFE, 1).add(Aspect.WATER, 1));
		}
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockLobsterCage),(new AspectList()).add(Aspect.BEAST, 1).add(Aspect.WATER, 1).add(Aspect.TRAP, 2).add(Aspect.TREE, 2).add(Aspect.METAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockAerosPlantae, 1, OreDictionary.WILDCARD_VALUE),(new AspectList()).add(Aspect.WATER, 2).add(Aspect.AIR, 1).add(Aspect.PLANT, 1).add(Aspect.LIGHT, 1).add(ABYSS, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockFilter, 1, 0), (new AspectList()).add(Aspect.METAL, 15).add(Aspect.MECHANISM, 5).add(Aspect.ENERGY, 5).add(Aspect.WATER, 5).add(Aspect.MOTION, 4).add(Aspect.ELDRITCH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockFilter, 1, 1), (new AspectList()).add(Aspect.METAL, 15).add(Aspect.MECHANISM, 5).add(Aspect.ENERGY, 5).add(Aspect.WATER, 7).add(Aspect.MOTION, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockFilter, 1, 2), (new AspectList()).add(Aspect.METAL, 15).add(Aspect.MECHANISM, 5).add(Aspect.ENERGY, 5).add(Aspect.WATER, 5).add(Aspect.MOTION, 4).add(Aspect.PLANT, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockFilter, 1, 3), (new AspectList()).add(Aspect.METAL, 15).add(Aspect.MECHANISM, 5).add(Aspect.ENERGY, 5).add(Aspect.WATER, 5).add(Aspect.MOTION, 4).add(Aspect.SLIME, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockSediment), (new AspectList()).add(Aspect.EARTH, 2).add(Aspect.WATER, 2).add(Aspect.GREED, 1).add(Aspect.SLIME, 1).add(ABYSS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockLimestone, 1, 0), (new AspectList()).add(Aspect.EARTH, 2).add(Aspect.WATER, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockLimestone, 1, 1), (new AspectList()).add(Aspect.EARTH, 2).add(Aspect.WATER, 2).add(Aspect.ORDER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockLimestone, 1, 2), (new AspectList()).add(Aspect.EARTH, 2).add(Aspect.WATER, 2).add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockLimestone, 1, 3), (new AspectList()).add(Aspect.EARTH, 2).add(Aspect.WATER, 2).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockLimestoneStairs, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.EARTH, 3).add(Aspect.WATER, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockLimestoneStairsBrick, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.EARTH, 3).add(Aspect.WATER, 2).add(Aspect.ORDER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockLimestoneSlab, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.EARTH, 1).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockLimestoneSlabDouble, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.EARTH, 2).add(Aspect.WATER, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockWoodTreated), (new AspectList()).add(Aspect.TREE, 2).add(Aspect.EARTH, 1).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockTWoodSlab, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockTWoodSlabDouble, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 2).add(Aspect.WATER, 1).add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockGlassSealed), (new AspectList()).add(Aspect.TREE, 1).add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockGuayule, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACBlocks.blockAirCompressor, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.METAL, 20).add(Aspect.MECHANISM, 15).add(Aspect.AIR, 5).add(Aspect.EXCHANGE, 3).add(Aspect.MOTION, 10).add(Aspect.ENERGY, 5));
		
		//ItemAspects
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemSponge, 1, 0), (new AspectList()).add(Aspect.WATER, 1).add(Aspect.VOID, 2).add(Aspect.AIR, 1).add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemSponge, 1, 1), (new AspectList()).add(Aspect.WATER, 3).add(Aspect.VOID, 1).add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemSpongeRed, 1, 0), (new AspectList()).add(Aspect.WATER, 1).add(Aspect.VOID, 2).add(Aspect.AIR, 1).add(Aspect.PLANT, 1).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemSpongeRed, 1, 1), (new AspectList()).add(Aspect.WATER, 3).add(Aspect.VOID, 1).add(Aspect.PLANT, 1).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemAlgaeCooked), (new AspectList()).add(Aspect.PLANT, 1).add(Aspect.HUNGER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemSeaweedCooked), (new AspectList()).add(Aspect.PLANT, 1).add(Aspect.WATER, 1).add(Aspect.HUNGER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemSquidRaw), (new AspectList()).add(Aspect.BEAST, 1).add(Aspect.WATER, 1).add(Aspect.FLESH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemSquidCooked), (new AspectList()).add(Aspect.HUNGER, 1).add(Aspect.WATER, 1).add(Aspect.FLESH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemSquidFried), (new AspectList()).add(Aspect.PLANT, 1).add(Aspect.WATER, 1).add(Aspect.FLESH, 1).add(Aspect.HUNGER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemSushiRaw), (new AspectList()).add(Aspect.WATER, 1).add(Aspect.PLANT, 1).add(Aspect.FLESH, 1).add(Aspect.HUNGER, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemSushiCooked), (new AspectList()).add(Aspect.WATER, 1).add(Aspect.PLANT, 1).add(Aspect.FLESH, 1).add(Aspect.HUNGER, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 1).add(Aspect.METAL, 3).add(Aspect.WEAPON, 1).add(Aspect.CRAFT, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 1).add(Aspect.METAL, 3).add(Aspect.WEAPON, 1).add(Aspect.CRAFT, 3).add(Aspect.GREED, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemLobster), (new AspectList()).add(Aspect.BEAST, 2).add(Aspect.WATER, 2).add(Aspect.FLESH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemLobsterRaw), (new AspectList()).add(Aspect.BEAST, 1).add(Aspect.WATER, 1).add(Aspect.FLESH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemLobsterCooked), (new AspectList()).add(Aspect.BEAST, 1).add(Aspect.WATER, 1).add(Aspect.FLESH, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemAerosBulb, 1, 0), (new AspectList()).add(Aspect.PLANT, 1).add(Aspect.AIR, 1).add(Aspect.HARVEST, 1).add(ABYSS, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemAerosBulb, 1, 1), (new AspectList()).add(Aspect.PLANT, 1).add(Aspect.AIR, 3).add(ABYSS, 5));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemScubaMask), (new AspectList()).add(Aspect.ARMOR, 3).add(Aspect.MOTION, 3).add(Aspect.CLOTH, 3).add(Aspect.CRYSTAL, 2).add(Aspect.SENSES, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemScubaBCD), (new AspectList()).add(Aspect.CLOTH, 4).add(Aspect.MOTION, 5).add(Aspect.ARMOR, 5).add(Aspect.METAL, 3).add(Aspect.FLIGHT, 1).add(Aspect.WATER, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemAirTankSmall, 1, 0), (new AspectList()).add(Aspect.METAL, 8).add(Aspect.MOTION, 1).add(Aspect.TOOL, 1).add(Aspect.VOID, 4).add(Aspect.AIR, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemAirTankSmall, 1, ACItems.itemAirTankSmall.getMaxDamage()), (new AspectList()).add(Aspect.METAL, 8).add(Aspect.MOTION, 1).add(Aspect.TOOL, 1).add(Aspect.VOID, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemAirTankLarge, 1, 0), (new AspectList()).add(Aspect.METAL, 24).add(Aspect.MOTION, 1).add(Aspect.TOOL, 2).add(Aspect.VOID, 8).add(Aspect.AIR, 32));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemAirTankLarge, 1, ACItems.itemAirTankLarge.getMaxDamage()), (new AspectList()).add(Aspect.METAL, 24).add(Aspect.MOTION, 1).add(Aspect.TOOL, 2).add(Aspect.VOID, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemAirTankCreative), (new AspectList()).add(Aspect.METAL, 1000).add(Aspect.MOTION, 1).add(Aspect.TOOL, 10).add(Aspect.VOID, 400).add(Aspect.AIR, 32000).add(Aspect.MAGIC, 10));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemWetsuit, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.ARMOR, 5).add(Aspect.CLOTH, 5).add(Aspect.MOTION, 6).add(Aspect.WATER, 4).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemFlippers, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.MOTION, 5).add(Aspect.ARMOR, 3).add(Aspect.CLOTH, 3));
		ThaumcraftApi.registerObjectTag(ACItems.itemStackFlaskEmpty.copy(), (new AspectList()).add(Aspect.CRYSTAL, 2).add(Aspect.VOID, 2));
		ThaumcraftApi.registerObjectTag(ACItems.itemStackFlaskWater.copy(), (new AspectList()).add(Aspect.CRYSTAL, 2).add(Aspect.WATER, 2));
		ThaumcraftApi.registerObjectTag(ACItems.itemStackFlaskNaCl.copy(), (new AspectList()).add(Aspect.CRYSTAL, 2).add(Aspect.WATER, 1).add(Aspect.GREED, 1));
		ThaumcraftApi.registerObjectTag(ACItems.itemStackFlaskNaOH.copy(), (new AspectList()).add(Aspect.CRYSTAL, 2).add(Aspect.WATER, 1).add(Aspect.POISON, 1));
		ThaumcraftApi.registerObjectTag(ACItems.itemStackFlaskCaOH.copy(), (new AspectList()).add(Aspect.CRYSTAL, 2).add(Aspect.WATER, 1).add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(ACItems.itemStackFlaskH2CO3.copy(), (new AspectList()).add(Aspect.CRYSTAL, 2).add(Aspect.WATER, 1).add(Aspect.SENSES, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemRedironElectrolyzer, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.WATER, 1).add(Aspect.METAL, 6).add(Aspect.MECHANISM, 6).add(Aspect.ENERGY, 7).add(Aspect.EXCHANGE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.itemQuicklime), (new AspectList()).add(Aspect.EARTH, 1).add(Aspect.ENERGY, 1).add(Aspect.ENTROPY, 1));
		ThaumcraftApi.registerObjectTag(ACItems.itemLobsterBoiled.copy(), (new AspectList()).add(Aspect.HUNGER, 2).add(Aspect.CRAFT, 1).add(Aspect.WATER, 1).add(Aspect.FLESH, 2));
		ThaumcraftApi.registerObjectTag(ACItems.itemChipRediron.copy(), (new AspectList()).add(Aspect.MECHANISM, 5).add(Aspect.METAL, 1).add(Aspect.ENERGY, 5));
		ThaumcraftApi.registerObjectTag(ACItems.itemGuayuleBranch.copy(), (new AspectList()).add(Aspect.PLANT, 1).add(Aspect.TOOL, 1).add(Aspect.HARVEST, 1));
		ThaumcraftApi.registerObjectTag(ACItems.itemRubberRaw.copy(), (new AspectList()).add(Aspect.MOTION, 1).add(Aspect.TOOL, 1).add(Aspect.POISON, 1));
		ThaumcraftApi.registerObjectTag(ACItems.itemRubberBall.copy(), (new AspectList()).add(Aspect.TOOL, 2).add(Aspect.MOTION, 1));
		ThaumcraftApi.registerObjectTag(ACItems.itemNeopreneTextile.copy(), (new AspectList()).add(Aspect.CLOTH, 1).add(Aspect.TOOL, 1).add(Aspect.MOTION, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.tridentWood, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 5).add(Aspect.WEAPON, 1).add(Aspect.TOOL, 1).add(ABYSS, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.tridentBone, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 2).add(Aspect.WEAPON, 2).add(Aspect.TOOL, 1).add(ABYSS, 2).add(Aspect.EARTH, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.tridentStone, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 2).add(Aspect.WEAPON, 2).add(Aspect.TOOL, 1).add(ABYSS, 2).add(Aspect.DEATH, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.tridentIron, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 2).add(Aspect.WEAPON, 3).add(Aspect.TOOL, 1).add(ABYSS, 3).add(Aspect.METAL, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.tridentNetherbrick, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 2).add(Aspect.WEAPON, 2).add(Aspect.TOOL, 1).add(ABYSS, 2).add(Aspect.FIRE, 1).add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.tridentGold, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 2).add(Aspect.WEAPON, 1).add(Aspect.TOOL, 1).add(ABYSS, 2).add(Aspect.GREED, 1).add(Aspect.METAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.tridentDiamond, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 2).add(Aspect.WEAPON, 4).add(Aspect.TOOL, 1).add(ABYSS, 2).add(Aspect.GREED, 1).add(Aspect.CRYSTAL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ACItems.tridentEmerald, 1, OreDictionary.WILDCARD_VALUE), (new AspectList()).add(Aspect.TREE, 2).add(Aspect.WEAPON, 5).add(Aspect.TOOL, 1).add(ABYSS, 2).add(Aspect.GREED, 2).add(Aspect.CRYSTAL, 1));
		
	}
	
	public static Item itemScubaMask_Thaum = new ItemDiveMask_Thaum(ACItems.materialThin, 0, "scubaEssentials")
		.setTextureName(AlgaeCraft.MODID+":scubaItemMask")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemDiveMask"));
	public static Item itemScubaBCD_Thaum = new ItemBCD_Thaum(ACItems.materialThin, 0, "scubaBcdArmor")
		.setTextureName(AlgaeCraft.MODID+":itemBCD")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemBCD"));
	public static Item itemWetsuit_Thaum = new ItemWetsuit_Thaum(ACItems.materialThin, 0)
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemWetsuit"));
	public static Item itemFlippers_Thaum = new ItemFlippers_Thaum(ACItems.materialThin, 0, "flippersArmor")
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":flippersItem")
		.setUnlocalizedName(name("itemFlippers"));

	public static Item getItem_Thaum(Item item) {
		if(item == ACItems.itemScubaMask){ return itemScubaMask_Thaum; }
		if(item == ACItems.itemScubaBCD){ return itemScubaBCD_Thaum; }
		if(item == ACItems.itemWetsuit){ return itemWetsuit_Thaum; }
		if(item == ACItems.itemFlippers){ return itemFlippers_Thaum; }
		return item;
	}
	
	private static String name(String s){
		return AlgaeCraft.MODID+"_"+s;
	}
}
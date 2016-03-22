package azaka7.algaecraft.common.blocks;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ObjectArrays;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.items.ACItems;
import azaka7.algaecraft.common.items.ItemBlockBrazier;
import azaka7.algaecraft.common.items.ItemBlockItem;
import azaka7.algaecraft.common.items.ItemBlockItemMetadata;
import azaka7.algaecraft.common.items.ItemBlockItemSeaweed;
import azaka7.algaecraft.common.items.ItemBlockMetadata;
import azaka7.algaecraft.common.items.ItemSlabLimestone;
import azaka7.algaecraft.common.items.ItemBlockNoDrops;
import azaka7.algaecraft.common.entity.EntityGreekFireBomb;
import azaka7.algaecraft.common.handlers.*;
import cpw.mods.fml.common.registry.ExistingSubstitutionException;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.Type;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistrySimple;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ACBlocks {
	public static Block algae = new BlockAlgae()
		.setBlockTextureName(AlgaeCraft.MODID+":algae")
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockName(name("algae"));
	public static Block spongeSpore;
	public static Block spongeYellow = new BlockSpongeAC("spongeBlockYellowDry","spongeBlockYellowWet")
		.setHardness(0.6F)
		.setStepSound(Block.soundTypeGrass)
		.setBlockTextureName(AlgaeCraft.MODID+":spongeBlockYellowDry")
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockName(name("spongeYellow"));
	public static Block spongeRed = new BlockSpongeAC("spongeBlockRedDry","spongeBlockRedWet")
		.setHardness(0.6F)
		.setStepSound(Block.soundTypeGrass)
		.setBlockTextureName(AlgaeCraft.MODID+":spongeBlockRedDry")
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockName(name("spongeRed"));
	public static Block spongeRedSpore = new BlockSpongeSpore(spongeRed, ACItems.itemSpongeRed)
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockTextureName(AlgaeCraft.MODID+":spongeBlockRedDry")
		.setBlockName(name("spongeSporeRed"));
	public static Block seaweed = new BlockSeaweed()
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockTextureName(AlgaeCraft.MODID+":seaweed")
		.setBlockName(name("seaweed"));
	public static Block coral = new BlockCoral()
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockName(name("coral"));
	public static Block lobsterCage = new BlockLobsterCage()
		.setBlockTextureName(AlgaeCraft.MODID+":lobsterCage")
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockName(name("lobsterCage"));
	public static Block aerosPlantae = new BlockAerosPlantae()
		.setBlockTextureName(AlgaeCraft.MODID+":waterBreathPlantSeeds")
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockName(name("aerosPlantae"));
	public static Block waterFilter = new BlockWaterFilter()
		.setCreativeTab(AlgaeCraft.modTab).setHardness(5.0F).setResistance(5.0F).setStepSound(Block.soundTypeMetal)
		.setBlockName(name("waterFilter"));
	public static Block sediment = new BlockGenericMultidrop(Material.clay,
			new ItemStack[]{
				new ItemStack(Blocks.dirt), 
				new ItemStack(Items.clay_ball, 4),
				new ItemStack(Blocks.sand),
				new ItemStack(Blocks.gravel)},
			new ItemStack(Blocks.dirt), 
			new ItemStack(Items.clay_ball, 4),
			new ItemStack(Blocks.sand),
			new ItemStack(Blocks.gravel),
			new ItemStack(Blocks.dirt), 
			new ItemStack(Items.clay_ball, 4),
			new ItemStack(Blocks.sand),
			new ItemStack(Blocks.gravel),
			new ItemStack(Blocks.dirt), 
			new ItemStack(Items.clay_ball, 4),
			new ItemStack(Blocks.sand),
			new ItemStack(Blocks.gravel),
			new ItemStack(ACBlocks.aerosPlantae, 3, 0),
			new ItemStack(ACBlocks.aerosPlantae, 2, 0),
			new ItemStack(ACBlocks.aerosPlantae, 1, 0),
			ACItems.itemValuableDust.copy(),
			ACItems.itemValuableDust.copy(),
			ACItems.itemValuableDust.copy(),
			ACItems.itemValuableDust.copy(),
			new ItemStack(Items.gold_ingot, 1),
			new ItemStack(Items.gold_ingot, 2),
			new ItemStack(Items.gold_nugget, 4),
			new ItemStack(Items.gold_nugget, 4),
			new ItemStack(Items.gold_nugget, 4),
			new ItemStack(Items.gold_nugget, 3),
			new ItemStack(Items.gold_nugget, 3),
			new ItemStack(Items.gold_nugget, 3),
			new ItemStack(Items.gold_nugget, 3),
			new ItemStack(Items.gold_nugget, 2),
			new ItemStack(Items.gold_nugget, 2),
			new ItemStack(Items.gold_nugget, 2),
			new ItemStack(Items.gold_nugget, 2),
			new ItemStack(Items.gold_nugget, 2),
			new ItemStack(Items.arrow, 16),
			new ItemStack(Items.arrow, 8),
			new ItemStack(Items.arrow, 4),
			new ItemStack(Items.arrow, 4),
			new ItemStack(Items.arrow, 2),
			new ItemStack(Items.arrow, 2),
			new ItemStack(Items.arrow, 2),
			new ItemStack(Items.boat),
			new ItemStack(Items.bone, 4),
			new ItemStack(Items.bone, 4),
			new ItemStack(Items.bone, 2),
			new ItemStack(Items.bone, 2),
			new ItemStack(Items.bone, 1),
			new ItemStack(Items.bone, 1),
			new ItemStack(Items.bowl, 3),
			new ItemStack(Items.bowl, 2),
			new ItemStack(Items.brick, 8),
			new ItemStack(Items.brick, 4),
			new ItemStack(Items.cauldron),
			new ItemStack(Items.water_bucket),
			new ItemStack(Items.water_bucket),
			new ItemStack(Items.compass),
			new ItemStack(Items.book, 3),
			new ItemStack(Items.clock),
			new ItemStack(Items.diamond),
			new ItemStack(Items.experience_bottle, 4),
			new ItemStack(Items.emerald),
			new ItemStack(Items.emerald),
			new ItemStack(Items.iron_ingot, 4),
			new ItemStack(Items.iron_ingot, 4),
			new ItemStack(Items.iron_ingot, 4),
			new ItemStack(Items.ender_pearl),
			new ItemStack(Items.ender_pearl),
			new ItemStack(Items.rotten_flesh, 8),
			new ItemStack(Items.rotten_flesh, 4),
			new ItemStack(Items.rotten_flesh, 4),
			new ItemStack(Items.rotten_flesh, 2),
			new ItemStack(Items.rotten_flesh, 1),
			new ItemStack(Items.wooden_sword, 1, Items.wooden_sword.getMaxDamage()-1),
			new ItemStack(Items.iron_sword, 1, Items.iron_sword.getMaxDamage()-1),
			new ItemStack(Items.stone_shovel, 1, Items.stone_shovel.getMaxDamage()-1),
			new ItemStack(Items.golden_hoe, 1, Items.golden_hoe.getMaxDamage()-1),
			new ItemStack(Items.iron_pickaxe, 1, Items.iron_pickaxe.getMaxDamage()-1),
			new ItemStack(Items.name_tag),
			new ItemStack(Items.poisonous_potato, 1),
			new ItemStack(Items.saddle),
			new ItemStack(Items.bow, 1, Items.bow.getMaxDamage()),
			new ItemStack(Items.potionitem),
			new ItemStack(Items.golden_horse_armor),
			new ItemStack(Items.quartz,3),
			new ItemStack(Items.string, 4),
			new ItemStack(Items.string, 4),
			new ItemStack(Items.string, 4),
			new ItemStack(Items.string, 4),
			new ItemStack(Items.slime_ball),
			new ItemStack(Items.slime_ball),
			new ItemStack(Items.slime_ball),
			new ItemStack(Items.gunpowder, 4),
			new ItemStack(Items.gunpowder, 4),
			new ItemStack(ACItems.itemKnifeIron, ACItems.itemKnifeIron.getMaxDamage()),
			new ItemStack(ACItems.itemKnifeIron, ACItems.itemKnifeIron.getMaxDamage()),
			new ItemStack(ACItems.itemKnifeGold, ACItems.itemKnifeGold.getMaxDamage())
		).setBlockName(name("sediment"))
		.setBlockTextureName(AlgaeCraft.MODID+":sediment")
		.setCreativeTab(AlgaeCraft.modTab)
		.setHardness(0.5F).setStepSound(Block.soundTypeSand);
	public static Block limestone = new BlockGenericMetadata(Material.rock,new String[]{"limestone","limestoneBrick","limestoneChiseled","limestoneTile"},new int[]{2})
		.setCreativeTab(AlgaeCraft.modTab).setStepSound(Block.soundTypePiston).setHardness(0.8F)
		.setBlockName(name("limestone"));
	public static Block limestoneStairs = new BlockStairsAC(limestone, 0)
		.setCreativeTab(AlgaeCraft.modTab).setStepSound(Block.soundTypePiston).setHardness(0.8F)
		.setBlockName(name("limestoneStairs"));
	public static Block limestoneStairsBrick = new BlockStairsAC(limestone, 1)
		.setCreativeTab(AlgaeCraft.modTab).setStepSound(Block.soundTypePiston).setHardness(0.8F)
		.setBlockName(name("limestoneBrickStairs"));
	public static BlockSlab limestoneSlab = (BlockSlab) new BlockSlabLimestone(false)
		.setCreativeTab(AlgaeCraft.modTab).setStepSound(Block.soundTypePiston).setHardness(0.8F)
		.setBlockTextureName(AlgaeCraft.MODID+":limestone")
		.setBlockName(name("limestoneSlab"));
	public static BlockSlab limestoneSlabDouble = (BlockSlab) new BlockSlabLimestone(true)
		.setStepSound(Block.soundTypePiston).setHardness(0.8F)
		.setBlockTextureName(AlgaeCraft.MODID+":limestone")
		.setBlockName(name("limestoneSlabDouble"));
	public static BlockGeneric treatedWood = (BlockGeneric) new BlockGeneric(Material.glass)
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockTextureName(AlgaeCraft.MODID+":woodTreated")
		.setStepSound(Block.soundTypeLadder)
		.setHardness(0.3F).setResistance(1.0F)
		.setBlockName(name("treatedWood"));
	public static BlockSlab treatedWoodSlab = (BlockSlab) new BlockSlabTWood(false)
		.setCreativeTab(AlgaeCraft.modTab).setStepSound(Block.soundTypeLadder).setHardness(1.0F).setResistance(1.0F)
		.setBlockTextureName(AlgaeCraft.MODID+":woodTreated")
		.setBlockName(name("treatedWoodSlab"));
	public static BlockSlab treatedWoodSlabDouble = (BlockSlab) new BlockSlabTWood(true)
		.setStepSound(Block.soundTypeLadder).setHardness(1.0F).setResistance(1.0F)
		.setBlockTextureName(AlgaeCraft.MODID+":woodTreated")
		.setBlockName(name("treatedWoodSlabDouble"));
	public static BlockGlassAC sealedGlass = (BlockGlassAC) new BlockGlassAC(true)
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockTextureName(AlgaeCraft.MODID+":glassSealed")
		.setStepSound(Block.soundTypeGlass)
		.setHardness(1.0F).setResistance(1.0F)
		.setBlockName(name("sealedGlass"));
	
	public static Block guayule = new BlockGuayule()
		.setCreativeTab(AlgaeCraft.modTab)
		.setHardness(0.5F)
		.setStepSound(Block.soundTypeGrass)
		.setBlockName(name("guayule"))
		.setBlockTextureName(name("guayuleSmall"));

	public static Block airCompressor = new BlockAirCompressor()
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockTextureName(AlgaeCraft.MODID+":airCompressor")
		.setStepSound(Block.soundTypeLadder)
		.setHardness(1.0F).setResistance(1.0F)
		.setBlockName(name("airCompressor"));
	
	public static Block greekFire = (new BlockGreekFire())
			.setHardness(0.0F)
			.setLightLevel(1.0F)
			.setStepSound(Block.soundTypeWood)
			.setBlockName(name("greek_fire"))
			.setBlockTextureName("algaecraft:greek_fire");
	
	public static Block brazier = (new BlockBrazier(false))
			.setHardness(0.0F)
			.setResistance(10.0F)
			.setLightLevel(0.8F)
			.setStepSound(Block.soundTypeMetal)
			.setCreativeTab(AlgaeCraft.modTab)
			.setBlockName(name("greek_brazier"))
			.setBlockTextureName(AlgaeCraft.MODID+":absentComponent");
	public static Block brazier_wet = (new BlockBrazier(true))
			.setHardness(0.0F)
			.setResistance(10.0F)
			.setLightLevel(1.0F)
			.setStepSound(Block.soundTypeMetal)
			.setBlockName(name("greek_brazier_inWater"))
			.setBlockTextureName(AlgaeCraft.MODID+":absentComponent");
	
	public static void registerBlocks(){
		spongeSpore = new BlockSpongeSpore(Blocks.sponge, ACItems.itemSponge)
		.setCreativeTab(AlgaeCraft.modTab)
		.setBlockTextureName(AlgaeCraft.MODID+":spongeBlockYellowDry")
		.setBlockName(name("spongeSpore"));
		
		ArrayList<Block> canPlaceInWater = new ArrayList<Block>();
		canPlaceInWater.add(Blocks.water);
		ArrayList<Block> canPlaceInAir = new ArrayList<Block>();
		canPlaceInAir.add(Blocks.air);
		canPlaceInAir.add(Blocks.snow_layer);
		
		registerBlock(limestone, "limestone", ItemBlockMetadata.class, new String[]{"limestone","limestoneBrick","limestoneChiseled","limestoneTile"});
		registerBlockStack(new ItemStack(limestone, 1, 1), "limestoneBrick");
		registerBlockStack(new ItemStack(limestone, 1, 2), "limestoneChiseled");
		registerBlockStack(new ItemStack(limestone, 1, 3), "limestoneTile");
		
		String[] filterNames = new String[16];
		for(int i = 0; i < 16 && i < BlockWaterFilter.EnumWaterType.values().length; i++){
			filterNames[i] = BlockWaterFilter.EnumWaterType.values()[i].name;
		}
		registerBlock(waterFilter, "waterFilter", ItemBlockMetadata.class, filterNames);
		registerBlockStack(new ItemStack(waterFilter, 1, 0), "filterEnder");
		registerBlockStack(new ItemStack(waterFilter, 1, 1), "filterFresh");
		registerBlockStack(new ItemStack(waterFilter, 1, 1), "filterOcean");
		registerBlockStack(new ItemStack(waterFilter, 1, 1), "filterSpororus");
		
		registerBlock(lobsterCage,"lobsterCage");
		registerBlockStack(new ItemStack(lobsterCage,1,1),"lobsterCageLobster");
		
		registerBlock(spongeRed, "spongeRed", ItemBlockItemMetadata.class, Boolean.FALSE, new String[]{"spongeBlockRedDry","spongeBlockRedWet"});
		registerBlock(sediment, "sediment");
		
		registerBlock(limestoneStairs,"limestoneStairs");
		registerBlock(limestoneStairsBrick, "limestoneBrickStairs");
		
		registerBlock(seaweed, "seaweed", ItemBlockItemSeaweed.class, Boolean.FALSE, new String("seaweedItem"));
		registerBlock(spongeSpore, "spongeSpore", ItemBlockItem.class, Boolean.FALSE, canPlaceInWater, new String("spongeSeed"));
		BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(spongeSpore), dispenserBehavior_SpongeSpore);
		registerBlock(spongeRedSpore, "spongeRedSpore", ItemBlockItem.class, Boolean.FALSE, canPlaceInWater, new String("spongeRedSeed"));
		BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(spongeRedSpore), dispenserBehavior_SpongeSpore);
		registerBlock(coral, "coral", ItemBlockItemMetadata.class, Boolean.FALSE, new String[]{"coral_orange","coral_purple","coral_brainI","coral_blue","coral_orange_small","coral_purple_small","coral_brain_smallI","coral_blue_small"});
		registerBlock(algae, "algae", ItemBlockItem.class, Boolean.TRUE, canPlaceInAir, new String("algaeBall"));
		OreDictionary.registerOre("slimeball", Item.getItemFromBlock(algae));
		
		registerBlock(aerosPlantae,"aerosPlantae",ItemBlockItem.class, Boolean.FALSE, canPlaceInWater, new String("waterBreathPlantSeeds"));
		
		registerBlock(limestoneSlab, "limestoneSlab", ItemSlabLimestone.class, (BlockSlab) limestoneSlab, (BlockSlab) limestoneSlabDouble, false);
		registerBlock(limestoneSlabDouble, "limestoneSlabDouble", ItemSlabLimestone.class, (BlockSlab) limestoneSlab, (BlockSlab) limestoneSlabDouble, true);
		registerBlockStack(new ItemStack(limestoneSlab, 1, 1), "limestoneSlabBrick");
		registerBlockStack(new ItemStack(limestoneSlab, 1, 3), "limestoneSlabTile");
		
		registerBlock(treatedWood, "treatedWood");
		registerBlock(treatedWoodSlab, "treatedWoodSlab", ItemSlabLimestone.class, (BlockSlab) treatedWoodSlab, (BlockSlab) treatedWoodSlabDouble, false);
		registerBlock(treatedWoodSlabDouble, "treatedWoodSlabDouble", ItemSlabLimestone.class, (BlockSlab) treatedWoodSlab, (BlockSlab) treatedWoodSlabDouble, true);
		
		registerBlock(sealedGlass, "sealedGlass");
		
		registerBlock(guayule, "guayule",ItemBlockItem.class, Boolean.FALSE, canPlaceInAir, new String("guayuleSmall"));
		
		registerBlock(airCompressor, "airCompressor",ItemBlockItem.class, Boolean.FALSE, canPlaceInAir, new String("airCompressorIcon"));
		
		registerBlock(greekFire, "greekFire", ItemBlockNoDrops.class);
		BlockGreekFire.registerFlamables();
		BlockDispenser.dispenseBehaviorRegistry.putObject(ACItems.greekFireFlask, dispenserBehavior_GreekFire);
		BlockDispenser.dispenseBehaviorRegistry.putObject(ACItems.greekFireBomb, new BehaviorProjectileDispense(){
			@Override
			protected IProjectile getProjectileEntity(World world, IPosition pos){
				return new EntityGreekFireBomb(world, pos.getX(), pos.getY(), pos.getZ());
			}
		});
		
		registerBlock(brazier, "brazier", ItemBlockBrazier.class);
		registerBlock(brazier_wet, "brazier_inWater", ItemBlockBrazier.class);
	}
	
	//used to register blocks that are required by items
	public static void registerPreItemBlocks(){
		ItemBlock itemSpongeBlock = new ItemBlockItemMetadata(spongeYellow, Boolean.FALSE,  new String[]{"spongeBlockYellowDry","spongeBlockYellowWet"});
		OreDictionary.registerOre("blockSponge", itemSpongeBlock);
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
		//System.out.println(Item.getItemById(19).getUnlocalizedName());
		//System.out.println(Block.getIdFromBlock(blockSpongeYellow));
		//System.out.println(Block.getIdFromBlock(Blocks.sponge));
		//System.out.println(Item.getItemFromBlock(blockSpongeYellow));
		//System.out.println(Item.getItemFromBlock(Blocks.sponge));
		/*try {
			System.out.println("overriding sponge");
			GameRegistry.addSubstitutionAlias("minecraft:sponge", GameRegistry.Type.BLOCK, blockSpongeYellow);
			GameRegistry.addSubstitutionAlias("minecraft:sponge", GameRegistry.Type.ITEM, itemSpongeBlock);
		} catch (Exception e) {
			System.out.println("AlgaeCraft cannot override vanilla sponge, because another mod already does. Here's Minecraft's complaint:");
			e.printStackTrace();
		}*/
		try {
			String registryObjects = "field_82596_a";
			String underlyingIntegerMap = "field_148759_a";
			//String iBlockRegistry = "";
			//String iItemRegistry = "";
			//String mainData = "";
			((Map) ReflectionHelper.findField(RegistrySimple.class, "registryObjects", registryObjects).get(ReflectionHelper.findField(GameData.class, "iBlockRegistry").get(ReflectionHelper.findField(GameData.class, "mainData").get(null)))).put("minecraft:sponge", spongeYellow);
			((Map) ReflectionHelper.findField(RegistryNamespaced.class, "field_148758_b").get(ReflectionHelper.findField(GameData.class, "iBlockRegistry").get(ReflectionHelper.findField(GameData.class, "mainData").get(null)))).put(spongeYellow, "minecraft:sponge");
			((ObjectIntIdentityMap) ReflectionHelper.findField(RegistryNamespaced.class, "underlyingIntegerMap", underlyingIntegerMap).get(ReflectionHelper.findField(GameData.class, "iBlockRegistry").get(ReflectionHelper.findField(GameData.class, "mainData").get(null)))).func_148746_a(spongeYellow, 19);
			((Map) ReflectionHelper.findField(RegistrySimple.class, "registryObjects", registryObjects).get(ReflectionHelper.findField(GameData.class, "iItemRegistry").get(ReflectionHelper.findField(GameData.class, "mainData").get(null)))).put("minecraft:sponge", itemSpongeBlock);
			((Map) ReflectionHelper.findField(RegistryNamespaced.class, "field_148758_b").get(ReflectionHelper.findField(GameData.class, "iItemRegistry").get(ReflectionHelper.findField(GameData.class, "mainData").get(null)))).put(itemSpongeBlock, "minecraft:sponge");
			((ObjectIntIdentityMap) ReflectionHelper.findField(RegistryNamespaced.class, "underlyingIntegerMap", underlyingIntegerMap).get(ReflectionHelper.findField(GameData.class, "iItemRegistry").get(ReflectionHelper.findField(GameData.class, "mainData").get(null)))).func_148746_a(itemSpongeBlock, Block.getIdFromBlock(Blocks.sponge));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Item.itemRegistry.putObject("sponge", itemSpongeBlock);
		//System.out.println(Item.getItemFromBlock(Blocks.sponge));
		try {
			ACReflectionHelper.setFinalStatic(ReflectionHelper.findField(Blocks.class, "sponge"), ACBlocks.spongeYellow);
		} catch (Exception e) {
			//e.printStackTrace();
			//field_150360_v
			try{
				ACReflectionHelper.setFinalStatic(ReflectionHelper.findField(Blocks.class, "field_150360_v"), ACBlocks.spongeYellow);
			}
			catch(Exception e2){
				System.out.println("AlgaeCraft ran into a problem overriding the vanilla sponge reference.");
				System.out.println("Other mods may have problems referring to the sponge block.");
				System.out.println("Please report this issue to Azaka7 on the Minecraft Forums.");
				e.printStackTrace();
				e2.printStackTrace();
			}
		}
		//Block.blockRegistry.addObject(19, "sponge", blockSpongeYellow);
		//Item.itemRegistry.addObject(Block.getIdFromBlock(Blocks.sponge), "sponge", itemSpongeBlock);
		
	}
	
	
	
	///////////////////////////////////////////////////////////////////////
	
	private static void registerBlock(Block block, String name){
		registerBlock(block, name, ItemBlock.class);
	}
	
	private static void registerBlock(Block block, String name, Class<? extends ItemBlock> item){
		GameRegistry.registerBlock(block, item, name);
	}
	
	private static void registerBlockStack(ItemStack block, String name){
		GameRegistry.registerCustomItemStack(name, block);
	}
	
	private static void registerBlock(Block block, String name, Class<? extends ItemBlock> item, Object arg1){
		GameRegistry.registerBlock(block, item, name, arg1);
	}
	
	private static void registerBlock(Block block, String name, Class<? extends ItemBlock> item, Object arg1, Object arg2){
		GameRegistry.registerBlock(block, item, name, arg1, arg2);
	}
	
	private static void registerBlock(Block block, String name, Class<? extends ItemBlock> item, Object arg1, Object arg2, Object arg3){
		GameRegistry.registerBlock(block, item, name, arg1, arg2, arg3);
	}
	
	public static int renderPass(){
		return Minecraft.getMinecraft().isFancyGraphicsEnabled() ? 1 : 0;
	}
	
	private static final IBehaviorDispenseItem dispenserBehavior_GreekFire = new BehaviorDefaultDispenseItem()
    {
		/**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        protected ItemStack dispenseStack(IBlockSource blocksource, ItemStack stack)
        {
        	World world = blocksource.getWorld();
        	EnumFacing enumfacing = BlockDispenser.func_149937_b(blocksource.getBlockMetadata());
            int i = blocksource.getXInt() + enumfacing.getFrontOffsetX();
            int j = blocksource.getYInt() + enumfacing.getFrontOffsetY();
            int k = blocksource.getZInt() + enumfacing.getFrontOffsetZ();
        	
            if(world.getBlock(i, j, k).isReplaceable(world, i, j, k) && Block.getBlockFromItem(stack.getItem()).canBlockStay(world, i, j, k)){
        		world.setBlock(i, j, k, greekFire, 0, 3);
        		return ACItems.itemStackFlaskEmpty.copy();
        	}
        	else{
        		return super.dispenseStack(blocksource, stack);
        	}
        }
    };
	
	private static final IBehaviorDispenseItem dispenserBehavior_SpongeSpore = new BehaviorDefaultDispenseItem()
    {
        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        protected ItemStack dispenseStack(IBlockSource blocksource, ItemStack stack)
        {
        	World world = blocksource.getWorld();
        	EnumFacing enumfacing = BlockDispenser.func_149937_b(blocksource.getBlockMetadata());
            int i = blocksource.getXInt() + enumfacing.getFrontOffsetX();
            int j = blocksource.getYInt() + enumfacing.getFrontOffsetY();
            int k = blocksource.getZInt() + enumfacing.getFrontOffsetZ();
        	
            if((world.getBlock(i, j, k) == Blocks.water || world.getBlock(i, j, k) == Blocks.flowing_water) && Block.getBlockFromItem(stack.getItem()).canBlockStay(world, i, j, k)){
        		world.setBlock(i, j, k, Block.getBlockFromItem(stack.getItem()), 0, 3);
        		--stack.stackSize;
        		return stack;
        	}
        	else{
        		return super.dispenseStack(blocksource, stack);
        	}
        }
    };
	
	private static String name(String s){
		return AlgaeCraft.MODID+"_"+s;
	}
	
}

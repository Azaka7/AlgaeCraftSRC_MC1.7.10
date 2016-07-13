package azaka7.algaecraft.common.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import azaka7.algaecraft.common.blocks.ACBlocks;
import azaka7.algaecraft.common.items.ACItems;
import azaka7.algaecraft.common.items.ItemFlask;
import azaka7.algaecraft.common.items.ItemFlaskGreekFire;
import azaka7.algaecraft.common.items.ItemWetsuit;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ACCraftingHandler implements IFuelHandler {
	
	public static final ACCraftingHandler INSTANCE = new ACCraftingHandler();
	
	public ACCraftingHandler(){
		RecipeSorter.register("algaecraft:coralrecipe", CoralRecipes.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
		FMLCommonHandler.instance().bus().register(this);
	}
	
	public void registerRecipes(){
		OreDictionary.registerOre("dustOrichalcum", ACItems.itemValuableDust);
		OreDictionary.registerOre("dustGold", ACItems.itemValuableDust);
		OreDictionary.registerOre("dustRedstone", ACItems.itemValuableDust);
		OreDictionary.registerOre("dustIron", ACItems.itemValuableDust);
		OreDictionary.registerOre("dustEmerald", ACItems.itemValuableDust);
		OreDictionary.registerOre("dustRuby", ACItems.itemValuableDust);
		OreDictionary.registerOre("dustPlatinum", ACItems.itemValuableDust);
		OreDictionary.registerOre("dustSilver", ACItems.itemValuableDust);
		OreDictionary.registerOre("dustCopper", ACItems.itemValuableDust);
		OreDictionary.registerOre("dustTin", ACItems.itemValuableDust);
		OreDictionary.registerOre("dustAluminum", ACItems.itemValuableDust);
		
		/*
		ItemStack copperIngot = null;
		ArrayList<ItemStack> copper_ingots = OreDictionary.getOres("ingotCopper");
		if(!copper_ingots.isEmpty()){
			copperIngot = copper_ingots.get(0);
			//add all copper-based recipes
		}
		*/
		
		//GameRegistry.registerFuelHandler(handler);
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sticky_piston), new Object[]{Blocks.piston, ACBlocks.algae});
		
		GameRegistry.addRecipe(new CoralRecipes());
		for(int i = 0; i < 4; i++){
			GameRegistry.addShapelessRecipe(new ItemStack(ACBlocks.coral, 2, i+4), new Object[]{new ItemStack(ACBlocks.coral, 1, i)});
			GameRegistry.addShapelessRecipe(new ItemStack(ACBlocks.coral, 2, i+8+4), new Object[]{new ItemStack(ACBlocks.coral, 1, i+8)});
			OreDictionary.registerOre("blockCoralSmall", new ItemStack(ACBlocks.coral,1,i+4));
			OreDictionary.registerOre("blockCoralSmall", new ItemStack(ACBlocks.coral,1,i+8+4));
			OreDictionary.registerOre("blockCoral", new ItemStack(ACBlocks.coral,1,i));
			OreDictionary.registerOre("blockCoral", new ItemStack(ACBlocks.coral,1,i+8));
			/*GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.blockLimestone),"cc","cc", 'c', new ItemStack(ACBlocks.blockCoral, 1, i));
			GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.blockLimestone),"ccc","ccc","ccc", 'c', new ItemStack(ACBlocks.blockCoral, 1, i+4));
			GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.blockLimestone),"cc","cc", 'c', new ItemStack(ACBlocks.blockCoral, 1, i+8));
			GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.blockLimestone),"ccc","ccc","ccc", 'c', new ItemStack(ACBlocks.blockCoral, 1, i+8+4));*/
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ACBlocks.limestone), "ccc", "ccc", "ccc", 'c', "blockCoralSmall"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ACBlocks.limestone), "cc", "cc", 'c', "blockCoral"));

		//GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSponge, 2), new Object[]{new ItemStack(Blocks.sponge, 1, OreDictionary.WILDCARD_VALUE)});
		OreDictionary.registerOre("blockSponge", ACBlocks.spongeYellow);
		Block spongeAC = (Block)Block.blockRegistry.getObject("minecraft:sponge");
		OreDictionary.registerOre("blockSponge", spongeAC);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ACItems.itemSponge, 2), new Object[]{"blockSponge"}));
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSponge, 2), new Object[]{Blocks.sponge});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSpongeRed, 2), new Object[]{ACBlocks.spongeRed});
		GameRegistry.addShapelessRecipe(new ItemStack(ACBlocks.spongeSpore, 2), new Object[]{ACItems.itemSponge});
		GameRegistry.addShapelessRecipe(new ItemStack(ACBlocks.spongeRedSpore, 2), new Object[]{ACItems.itemSpongeRed});
		
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.limestone, 4, 1), new Object[]{
			"xx","xx", Character.valueOf('x'), new ItemStack(ACBlocks.limestone, 1, 0)});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.limestone, 4, 3), new Object[]{
			"xx","xx", Character.valueOf('x'), new ItemStack(ACBlocks.limestone, 1, 1)});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.limestoneSlab, 6, 0), new Object[]{
			"xxx", Character.valueOf('x'), new ItemStack(ACBlocks.limestone, 1, 0)});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.limestoneSlab, 6, 1), new Object[]{
			"xxx", Character.valueOf('x'), new ItemStack(ACBlocks.limestone, 1, 1)});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.limestoneSlab, 6, 3), new Object[]{
			"xxx", Character.valueOf('x'), new ItemStack(ACBlocks.limestone, 1, 3)});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.limestone, 1, 2), new Object[]{
			"x","x", Character.valueOf('x'), new ItemStack(ACBlocks.limestoneSlab, 1, 0)});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.limestone, 1, 3), new Object[]{
			"x","x", Character.valueOf('x'), new ItemStack(ACBlocks.limestoneSlab, 1, 1)});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.limestoneStairs, 8, 0), new Object[]{
			"x  ","xx ", "xxx", Character.valueOf('x'), new ItemStack(ACBlocks.limestone, 1, 0)});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.limestoneStairsBrick, 8, 0), new Object[]{
			"x  ","xx ", "xxx", Character.valueOf('x'), new ItemStack(ACBlocks.limestone, 1, 1)});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.waterFilter, 1, 0), new Object[]{
			"beb","pwp","cxc",
			Character.valueOf('b'), new ItemStack(Blocks.iron_bars),
			Character.valueOf('e'), new ItemStack(Items.ender_pearl),
			Character.valueOf('p'), new ItemStack(Blocks.heavy_weighted_pressure_plate),
			Character.valueOf('w'), new ItemStack(Items.water_bucket),
			Character.valueOf('c'), ACItems.itemChipRediron.copy(),
			Character.valueOf('x'), new ItemStack(Blocks.piston)
		});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.waterFilter, 1, 1), new Object[]{
			"b b","pwp","cxc",
			Character.valueOf('b'), new ItemStack(Blocks.iron_bars),
			Character.valueOf('p'), new ItemStack(Blocks.heavy_weighted_pressure_plate),
			Character.valueOf('w'), new ItemStack(Items.water_bucket),
			Character.valueOf('c'), ACItems.itemChipRediron.copy(),
			Character.valueOf('x'), new ItemStack(Blocks.piston)
		});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.waterFilter, 1, 2), new Object[]{
			"bsb","pwp","cxc",
			Character.valueOf('b'), new ItemStack(Blocks.iron_bars),
			Character.valueOf('s'), new ItemStack(ACBlocks.seaweed),
			Character.valueOf('p'), new ItemStack(Blocks.heavy_weighted_pressure_plate),
			Character.valueOf('w'), new ItemStack(Items.water_bucket),
			Character.valueOf('c'), ACItems.itemChipRediron.copy(),
			Character.valueOf('x'), new ItemStack(Blocks.piston)
		});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.waterFilter, 1, 3), new Object[]{
			"bab","pwp","cxc",
			Character.valueOf('b'), new ItemStack(Blocks.iron_bars),
			Character.valueOf('a'), new ItemStack(ACBlocks.algae),
			Character.valueOf('p'), new ItemStack(Blocks.heavy_weighted_pressure_plate),
			Character.valueOf('w'), new ItemStack(Items.water_bucket),
			Character.valueOf('c'), ACItems.itemChipRediron.copy(),
			Character.valueOf('x'), new ItemStack(Blocks.piston)
		});
		GameRegistry.addShapedRecipe(ACItems.itemChipRediron.copy(), new Object[]{
			"rqr","iii",
			Character.valueOf('r'), new ItemStack(Items.redstone),
			Character.valueOf('q'), new ItemStack(Items.quartz),
			Character.valueOf('i'), new ItemStack(Items.iron_ingot)
		});
		OreDictionary.registerOre("blockSeaweed", ACBlocks.seaweed);
		OreDictionary.registerOre("materialString", Items.string);
		OreDictionary.registerOre("blockVine", Blocks.vine);
		for(String bind : new String[]{"blockSeaweed","materialString","blockVine"}){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ACItems.itemKnifeIron),
					"bk","s ",'b',bind,'k',"ingotIron",'s',"stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ACItems.itemKnifeGold),
					"bk","s ",'b',bind,'k',"ingotGold",'s',"stickWood"));
		}
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemKnifeIron), new Object[]{
			"bi","s ",
			Character.valueOf('b'), new ItemStack(ACBlocks.seaweed),
			Character.valueOf('i'), new ItemStack(Items.iron_ingot),
			Character.valueOf('s'), new ItemStack(Items.stick)
		});
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemKnifeGold), new Object[]{
			"bi","s ",
			Character.valueOf('b'), new ItemStack(ACBlocks.seaweed),
			Character.valueOf('i'), new ItemStack(Items.gold_ingot),
			Character.valueOf('s'), new ItemStack(Items.stick)
		});
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemKnifeIron), new Object[]{
			"bi","s ",
			Character.valueOf('b'), new ItemStack(Items.string),
			Character.valueOf('i'), new ItemStack(Items.iron_ingot),
			Character.valueOf('s'), new ItemStack(Items.stick)
		});
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemKnifeGold), new Object[]{
			"bi","s ",
			Character.valueOf('b'), new ItemStack(Items.string),
			Character.valueOf('i'), new ItemStack(Items.gold_ingot),
			Character.valueOf('s'), new ItemStack(Items.stick)
		});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSquidFried), new Object[]{
			new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemSquidCooked),
			new ItemStack(Items.bowl), new ItemStack(Blocks.brown_mushroom)});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemLobsterRaw, 3), new Object[]{
			new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemLobster)});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemLobsterCooked, 3), new Object[]{
			new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE), ACItems.itemLobsterBoiled});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSquidFried), new Object[]{
			new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemSquidCooked),
			new ItemStack(Items.bowl), new ItemStack(Blocks.brown_mushroom)});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemLobsterRaw, 4), new Object[]{
			new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemLobster)});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemLobsterCooked, 4), new Object[]{
			new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE), ACItems.itemLobsterBoiled});
		GameRegistry.addRecipe(new ShapedOreRecipe(ACBlocks.lobsterCage, "wbw", "b b", "wbw", 'w', "plankWood", 'b', Blocks.iron_bars));
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.lobsterCage), new Object[]{
			"wbw", "b b", "wbw",
			Character.valueOf('w'), new ItemStack(Blocks.planks),
			Character.valueOf('b'), new ItemStack(Blocks.iron_bars)
		});
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemKnifeGold), new Object[]{
			"wbw", "b b", "wbw",
			Character.valueOf('w'), new ItemStack(ACBlocks.treatedWood),
			Character.valueOf('b'), new ItemStack(Blocks.iron_bars)
		});
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ACBlocks.treatedWood, 12), "plankWood", "plankWood", "plankWood", "plankWood", "plankWood", "plankWood", "dye", Items.clay_ball));
		GameRegistry.addShapelessRecipe(new ItemStack(ACBlocks.treatedWood), new Object[]{
			Blocks.planks,Blocks.planks,Blocks.planks,Blocks.planks,Blocks.planks,Blocks.planks,new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE), Items.clay_ball
		});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.treatedWoodSlab, 6), "www", 'w', ACBlocks.treatedWood);
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.sealedGlass, 6), "wgw","grg","wgw",'w',ACBlocks.treatedWood,'g',Blocks.glass,'r',ACItems.itemRubberBall);
		
		Object[] nr = new Object[]{'n', ACItems.itemNeopreneTextile,'r', ACItems.itemRubberBall};
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemScubaMask), new Object[]{
			"n n","rgr",nr[0],nr[1],nr[2],nr[3],'g', Blocks.glass_pane});
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemScubaBCD), new Object[]{
			"nrn","rbr","iri",nr[0],nr[1],nr[2],nr[3],'b',Items.bucket,'i',Items.iron_ingot});
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemWetsuit), 
				new Object[]{"nnn","nnn","n n",nr[0],nr[1]});
		String[] dyes =
	        {	"Black","Red","Green","Brown",
				"Blue","Purple","Cyan","LightGray",
	            "Gray","Pink","Lime","Yellow",
		        "LightBlue","Magenta","Orange","White"	};
		for(int i = 0; i < 16; i++){
			ItemStack stack = ItemWetsuit.colorize(new ItemStack(ACItems.itemWetsuit), i);
			GameRegistry.addRecipe(new ShapelessOreRecipe(
					stack, new ItemStack(ACItems.itemWetsuit, 1, OreDictionary.WILDCARD_VALUE), "dye"+dyes[i]
			));
			GameRegistry.addShapelessRecipe(stack, new Object[]{
				new ItemStack(ACItems.itemWetsuit, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.dye, 1, i)
			});
		}
		this.registerCustomWetsuits();
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemFlippers), new Object[]{
			"n n","r r",nr[0],nr[1],nr[2],nr[3]});
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemRedironElectrolyzer), new Object[]{
			" l ","gcg","g g",'l',Blocks.lever,'g',Items.gold_nugget,'c',ACItems.itemChipRediron});
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemFlask, 2, 0), new Object[]{
			" g ","gng",'g', Blocks.glass,'n', Items.gold_nugget
		});
		GameRegistry.addShapelessRecipe(ACItems.itemStackFlaskNaOH.copy(), new Object[]{
			ACItems.itemStackFlaskNaCl.copy(),
			new ItemStack(ACItems.itemRedironElectrolyzer, 1, OreDictionary.WILDCARD_VALUE)});
		ItemStack rubberRaw = ACItems.itemRubberRaw.copy(); rubberRaw.stackSize = 2;
		GameRegistry.addShapelessRecipe(rubberRaw.copy(), new Object[]{
			ACItems.itemStackFlaskNaOH.copy(),ACItems.itemGuayuleBranch.copy(), ACItems.itemGuayuleBranch.copy()});
		GameRegistry.addShapelessRecipe(new ItemStack(ACBlocks.limestone, 1, 0), new Object[]{
			ACItems.itemStackFlaskCaOH.copy(), ACItems.itemStackFlaskH2CO3.copy()
		});
		GameRegistry.addShapelessRecipe(ACItems.itemStackFlaskCaOH.copy(), new Object[]{
			ACItems.itemStackFlaskWater.copy(), ACItems.itemQuicklime
		});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.glowstone_dust, 4), ACItems.itemQuicklime, ACItems.itemQuicklime, ACItems.itemQuicklime, ACItems.itemQuicklime, Items.blaze_powder);
		OreDictionary.registerOre("blockWool", new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE));
		for(int i = 0; i < 16; i++){
			String s = Character.toUpperCase(ItemDye.field_150921_b[i].charAt(0)) + ItemDye.field_150921_b[i].substring(1);
			OreDictionary.registerOre("blockWool"+s, new ItemStack(Blocks.wool, 1, i));
		}
		OreDictionary.registerOre("materialRubber", ACItems.itemRubberBall.copy());
		GameRegistry.addRecipe(new ShapelessOreRecipe(ACItems.itemNeopreneTextile.copy(), "blockWool", "materialRubber"));
		GameRegistry.addShapelessRecipe(ACItems.itemNeopreneTextile.copy(), new Object[]{
			new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), ACItems.itemRubberBall.copy()
		});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ACItems.itemAirTankSmall),
				" r ","i i"," i ",'i',"ingotIron",'r',"materialRubber"));
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemAirTankSmall), new Object[]{
			" r ","i i"," i ",'i',Items.iron_ingot,'r',ACItems.itemRubberBall});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ACItems.itemAirTankLarge),
				" r ","ptp"," p ",'t',new ItemStack(ACItems.itemAirTankSmall, 1, OreDictionary.WILDCARD_VALUE),'r',"materialRubber", 'p', Blocks.heavy_weighted_pressure_plate));
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.itemAirTankLarge),
				new Object[]{" r ","ptp"," p ",
			't',new ItemStack(ACItems.itemAirTankSmall, 1, OreDictionary.WILDCARD_VALUE),
			'r', ACItems.itemRubberBall.copy(), 
			'p', Blocks.heavy_weighted_pressure_plate});
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.airCompressor), new Object[]{
				"chc","ptp","iri",
				't',new ItemStack(ACItems.itemAirTankSmall, 1, OreDictionary.WILDCARD_VALUE),
				'r',Items.repeater,
				'i',Blocks.heavy_weighted_pressure_plate,
				'c',ACItems.itemChipRediron,
				'h', Blocks.hopper,
				'p',Blocks.piston});
		OreDictionary.registerOre("foodFishRaw",new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("foodFishRaw",new ItemStack(ACItems.itemLobsterRaw));
		OreDictionary.registerOre("foodFishRaw",new ItemStack(ACItems.itemSquidRaw));
		OreDictionary.registerOre("foodFishCooked",new ItemStack(Items.cooked_fished,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("foodFishCooked",new ItemStack(ACItems.itemLobsterCooked));
		OreDictionary.registerOre("foodFishCooked",new ItemStack(ACItems.itemSquidCooked));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(ACItems.itemSushiRaw, ACBlocks.seaweed,"foodFishRaw", new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ACItems.itemSushiRaw, ACBlocks.seaweed,"foodFishRaw", new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ACItems.itemSushiCooked, ACBlocks.seaweed,"foodFishCooked", new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ACItems.itemSushiCooked, ACBlocks.seaweed,"foodFishCooked", new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ACItems.itemSushiRawPumpkin, ACBlocks.seaweed,"foodFishRaw", Blocks.pumpkin, new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ACItems.itemSushiRawPumpkin, ACBlocks.seaweed,"foodFishRaw", Blocks.pumpkin, new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ACItems.itemSushiCookedPumpkin, ACBlocks.seaweed,"foodFishCooked", Blocks.pumpkin, new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ACItems.itemSushiCookedPumpkin, ACBlocks.seaweed,"foodFishCooked", Blocks.pumpkin, new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE)));

		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSushiRaw), new Object[]{ACBlocks.seaweed,new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSushiRaw), new Object[]{ACBlocks.seaweed,new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSushiCooked), new Object[]{ACBlocks.seaweed,new ItemStack(Items.cooked_fished, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSushiCooked), new Object[]{ACBlocks.seaweed,new ItemStack(Items.cooked_fished, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSushiRawPumpkin), new Object[]{ACBlocks.seaweed,new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE), Blocks.pumpkin});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSushiRawPumpkin), new Object[]{ACBlocks.seaweed,new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE), Blocks.pumpkin});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSushiCookedPumpkin), new Object[]{ACBlocks.seaweed,new ItemStack(Items.cooked_fished, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemKnifeIron, 1, OreDictionary.WILDCARD_VALUE), Blocks.pumpkin});
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemSushiCookedPumpkin), new Object[]{ACBlocks.seaweed,new ItemStack(Items.cooked_fished, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemKnifeGold, 1, OreDictionary.WILDCARD_VALUE), Blocks.pumpkin});
		
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.tridentWood), "mbm"," sb","s m",
				'm', Blocks.planks, 's', Items.stick, 'b', ACBlocks.seaweed);
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.tridentBone), "mbm"," sb","s m",
				'm', Items.bone, 's', Items.stick, 'b', ACBlocks.seaweed);
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.tridentDiamond), "mbm"," sb","s m",
				'm', Items.diamond, 's', Items.stick, 'b', ACBlocks.seaweed);
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.tridentEmerald), "mbm"," sb","s m",
				'm', Items.emerald, 's', Items.stick, 'b', ACBlocks.seaweed);
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.tridentGold), "mbm"," sb","s m",
				'm', Items.gold_ingot, 's', Items.stick, 'b', ACBlocks.seaweed);
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.tridentIron), "mbm"," sb","s m",
				'm', Items.iron_ingot, 's', Items.stick, 'b', ACBlocks.seaweed);
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.tridentNetherbrick), "mbm"," sb","s m",
				'm', Items.netherbrick, 's', Items.stick, 'b', ACBlocks.seaweed);
		GameRegistry.addShapedRecipe(new ItemStack(ACItems.tridentStone), "mbm"," sb","s m",
				'm', Blocks.cobblestone, 's', Items.stick, 'b', ACBlocks.seaweed);
		GameRegistry.addShapelessRecipe(ACItems.itemStackFlaskNaCl, new ItemStack(ACItems.itemSponge, 1, 1), ACItems.itemStackFlaskEmpty);
		GameRegistry.addShapelessRecipe(ACItems.itemStackFlaskNaCl, new ItemStack(ACItems.itemSpongeRed, 1, 1), ACItems.itemStackFlaskEmpty);
		GameRegistry.addShapelessRecipe(ACItems.itemRubberRaw.copy(), ACItems.itemStackFlaskCaOH.copy(), ACItems.itemGuayuleBranch.copy());
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), new ItemStack(ACBlocks.coral, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), new ItemStack(ACBlocks.coral, 1, 12));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 5), new ItemStack(ACBlocks.coral, 1, 5));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 5), new ItemStack(ACBlocks.coral, 1, 13));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 9), new ItemStack(ACBlocks.coral, 1, 6));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 9), new ItemStack(ACBlocks.coral, 1, 14));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 12), new ItemStack(ACBlocks.coral, 1, 7));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 12), new ItemStack(ACBlocks.coral, 1, 15));
		OreDictionary.registerOre("dyeGreen", new ItemStack(ACItems.itemAlgaeCooked));
		
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.greekFireFlask), ACItems.itemStackFlaskTar.copy(), ACItems.itemQuicklime, Items.gunpowder);
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.greekFireBomb), ACItems.greekFireFlask, ACItems.itemQuicklime, Items.gunpowder, Items.flower_pot);
		GameRegistry.addShapedRecipe(new ItemStack(ACBlocks.brazier, 2), "ifi"," i ","sss",
				'i', Items.iron_ingot, 'f', ACItems.greekFireFlask, 's', Blocks.stone_slab);
		
		GameRegistry.addShapelessRecipe(ACItems.itemTarBall.copy(), ACItems.itemStackFlaskTar.copy(), new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(ACItems.itemTarBall.copy(), ACItems.itemStackFlaskTar.copy(), new ItemStack(Blocks.tallgrass, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(ACItems.itemTarBall.copy(), ACItems.itemStackFlaskTar.copy(), new ItemStack(Blocks.leaves, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(ACItems.itemTarBall.copy(), ACItems.itemStackFlaskTar.copy(), new ItemStack(Blocks.leaves2, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(ACItems.itemTarBall.copy(), ACItems.itemStackFlaskTar.copy(), Blocks.vine);
		GameRegistry.addShapelessRecipe(ACItems.itemTarBall.copy(), ACItems.itemStackFlaskTar.copy(), Blocks.web);
		GameRegistry.addShapelessRecipe(ACItems.itemTarBall.copy(), ACItems.itemStackFlaskTar.copy(), Items.wheat);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ACItems.itemTanDye.getItem(), 2, ACItems.itemTanDye.getItemDamage()),"dyeOrange", "dyeLightGray"));
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemTanDye.getItem(), 2, ACItems.itemTanDye.getItemDamage()), new ItemStack(Items.dye, 1, 14), new ItemStack(Items.dye, 1, 7));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ACItems.itemTanDye.getItem(), 2, ACItems.itemTanDye.getItemDamage()),"dyeBrown", "dyeWhite"));
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemTanDye.getItem(), 2, ACItems.itemTanDye.getItemDamage()), new ItemStack(Items.dye, 1, 3), new ItemStack(Items.dye, 1, 15));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ACItems.itemTanDye.getItem(), 2, ACItems.itemTanDye.getItemDamage()),"dyeLime", "dyeRed"));
		GameRegistry.addShapelessRecipe(new ItemStack(ACItems.itemTanDye.getItem(), 2, ACItems.itemTanDye.getItemDamage()), new ItemStack(Items.dye, 1, 10), new ItemStack(Items.dye, 1, 1));

		GameRegistry.addShapelessRecipe(new ItemStack(ACBlocks.sail_wool, 8), Blocks.wool, Blocks.wool, Blocks.wool, Blocks.wool, Blocks.wool, Blocks.wool, Blocks.wool, Blocks.wool, ACItems.itemTanDye);
		GameRegistry.addShapelessRecipe(new ItemStack(ACBlocks.sail_wool, 3), Blocks.wool, Blocks.wool, Blocks.wool, ACItems.itemTanDye);
		GameRegistry.addShapelessRecipe(new ItemStack(ACBlocks.sail_wool, 3), Blocks.wool, Blocks.wool, Blocks.wool, Blocks.sand);
		GameRegistry.addShapelessRecipe(new ItemStack(ACBlocks.sail_wool, 1), Blocks.wool, ACItems.itemTanDye);
	}
	
	private void registerCustomWetsuits() {
		ItemStack wetsuit = new ItemStack(ACItems.itemWetsuit);
		NBTTagCompound tags = new NBTTagCompound();
		tags.setString("skin", "spiderman");
		tags.setInteger("dyecolor", 1);
		wetsuit.setTagCompound(tags);
		GameRegistry.addShapelessRecipe(wetsuit.copy(), new ItemStack(ACItems.itemWetsuit), new ItemStack(Items.dye, 1, 1), new ItemStack(Items.string));
		
		wetsuit = new ItemStack(ACItems.itemWetsuit);
		tags = new NBTTagCompound();
		tags.setString("skin", "squid");
		tags.setInteger("dyecolor", 4);
		wetsuit.setTagCompound(tags);
		GameRegistry.addShapelessRecipe(wetsuit.copy(), new ItemStack(ACItems.itemWetsuit), new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 0));
		
		wetsuit = new ItemStack(ACItems.itemWetsuit);
		tags = new NBTTagCompound();
		tags.setString("skin", "clownfish");
		tags.setInteger("dyecolor", 14);
		wetsuit.setTagCompound(tags);
		GameRegistry.addShapelessRecipe(wetsuit.copy(), new ItemStack(ACItems.itemWetsuit), new ItemStack(Items.dye, 1, 14), new ItemStack(Items.fish, 1, 2));
		
		wetsuit = new ItemStack(ACItems.itemWetsuit);
		tags = new NBTTagCompound();
		tags.setString("skin", "treky_com");
		tags.setInteger("dyecolor",1);
		wetsuit.setTagCompound(tags);
		GameRegistry.addShapelessRecipe(wetsuit.copy(), new ItemStack(ACItems.itemWetsuit), new ItemStack(Items.dye, 1, 1), new ItemStack(Blocks.redstone_torch));
		
		wetsuit = new ItemStack(ACItems.itemWetsuit);
		tags = new NBTTagCompound();
		tags.setString("skin", "treky_eng");
		tags.setInteger("dyecolor",11);
		wetsuit.setTagCompound(tags);
		GameRegistry.addShapelessRecipe(wetsuit.copy(), new ItemStack(ACItems.itemWetsuit), new ItemStack(Items.dye, 1, 11), new ItemStack(Blocks.redstone_torch));
		
		wetsuit = new ItemStack(ACItems.itemWetsuit);
		tags = new NBTTagCompound();
		tags.setString("skin", "treky_med");
		tags.setInteger("dyecolor",4);
		wetsuit.setTagCompound(tags);
		GameRegistry.addShapelessRecipe(wetsuit.copy(), new ItemStack(ACItems.itemWetsuit), new ItemStack(Items.dye, 1, 4), new ItemStack(Blocks.redstone_torch));
		
		wetsuit = new ItemStack(ACItems.itemWetsuit);
		tags = new NBTTagCompound();
		tags.setString("skin", "batman");
		tags.setInteger("dyecolor",0);
		wetsuit.setTagCompound(tags);
		GameRegistry.addShapelessRecipe(wetsuit.copy(), new ItemStack(ACItems.itemWetsuit), new ItemStack(Items.dye, 1, 0), new ItemStack(Items.feather));
		
		wetsuit = new ItemStack(ACItems.itemWetsuit);
		tags = new NBTTagCompound();
		tags.setString("skin", "creeper");
		tags.setInteger("dyecolor",10);
		wetsuit.setTagCompound(tags);
		GameRegistry.addShapelessRecipe(wetsuit.copy(), new ItemStack(ACItems.itemWetsuit), new ItemStack(Items.dye, 1, 10), new ItemStack(Items.gunpowder));
		
		
	}

	public void registerSmelting(){
		GameRegistry.addSmelting(ACBlocks.seaweed, new ItemStack(ACItems.itemSeaweedCooked), 0.2F);
		GameRegistry.addSmelting(ACBlocks.algae, new ItemStack(ACItems.itemAlgaeCooked), 0.2F);
		GameRegistry.addSmelting(new ItemStack(Blocks.sponge, 1, 1), new ItemStack(Blocks.sponge, 1, 0), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.spongeRed, 1, 1), new ItemStack(ACBlocks.spongeRed, 1, 0), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.limestone, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.itemQuicklime), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ACItems.itemSquidRaw), new ItemStack(ACItems.itemSquidCooked), 0.35F);
		GameRegistry.addSmelting(new ItemStack(ACItems.itemLobsterRaw), new ItemStack(ACItems.itemLobsterCooked), 0.35F);
		GameRegistry.addSmelting(new ItemStack(ACItems.itemLobster), ACItems.itemLobsterBoiled, 1.0F);
		GameRegistry.addSmelting(ACItems.itemRubberRaw, ACItems.itemRubberBall, 0.4F);
		
		GameRegistry.registerFuelHandler(this);
	}
	
	@SubscribeEvent
	public void handleSpecialCrafting(PlayerEvent.ItemCraftedEvent event){
		EntityPlayer player = event.player;
		ItemStack result = event.crafting;
		IInventory matrix = event.craftMatrix;
		
		for(int i = 0; i < matrix.getSizeInventory(); i++){
			ItemStack stack = matrix.getStackInSlot(i);
			if(stack == null){continue;}
			if(stack.getItem() instanceof ItemFlask){
				ItemStack rubberRaw = ACItems.itemRubberRaw.copy(); rubberRaw.stackSize = 2;
				if(ItemStack.areItemStacksEqual(result, rubberRaw.copy())){
					if(ItemStack.areItemStacksEqual(stack, ACItems.itemStackFlaskNaOH.copy())){
						matrix.setInventorySlotContents(i, ItemFlask.fake(ACItems.itemStackFlaskEmpty));
					}
				}
				if(result.getItem() instanceof ItemFlask && result.getItemDamage() == ACItems.itemStackFlaskNaOH.getItemDamage()){
					if(result.getItem() instanceof ItemFlask && stack.getItemDamage() == ACItems.itemStackFlaskNaCl.getItemDamage()){
						matrix.setInventorySlotContents(i, null);
					}
				}
				if(ItemStack.areItemStacksEqual(result, new ItemStack(ACBlocks.limestone, 1, 0))){
					if(ItemStack.areItemStacksEqual(stack, ACItems.itemStackFlaskCaOH.copy()) || ItemStack.areItemStacksEqual(stack, ACItems.itemStackFlaskH2CO3.copy())){
						matrix.setInventorySlotContents(i, ItemFlask.fake(ACItems.itemStackFlaskEmpty));
					}
				}
				if(ItemStack.areItemStacksEqual(result, ACItems.itemStackFlaskCaOH)){
					if(ItemStack.areItemStacksEqual(stack, ACItems.itemStackFlaskWater)){
						matrix.setInventorySlotContents(i, null);
					}
				}
				if(ItemStack.areItemStacksEqual(result, new ItemStack(ACItems.greekFireFlask))){
					if(ItemStack.areItemStacksEqual(stack, ACItems.itemStackFlaskTar)){
						matrix.setInventorySlotContents(i, null);
					}
				}
				if(ItemStack.areItemStacksEqual(result, ACItems.itemTarBall)){
					if(ItemStack.areItemStacksEqual(stack, ACItems.itemStackFlaskTar)){
						matrix.setInventorySlotContents(i, ItemFlask.fake(ACItems.itemStackFlaskEmpty));
					}
				}
			}
			if(stack.getItem() instanceof ItemFlaskGreekFire){
				if(ItemStack.areItemStacksEqual(result, new ItemStack(ACItems.greekFireBomb))){
					matrix.setInventorySlotContents(i, ItemFlask.fake(ACItems.itemStackFlaskEmpty));
				}
				if(ItemStack.areItemStacksEqual(result, new ItemStack(ACBlocks.brazier, 2))){
					matrix.setInventorySlotContents(i, ItemFlask.fake(ACItems.itemStackFlaskEmpty));
				}
			}
		}
	}
	
	public class CoralRecipes implements IRecipe{
		@Override
		public boolean matches(InventoryCrafting inventory, World world) {
			boolean flag = false;
			int count = 0;
			for(int i = 0; i < inventory.getSizeInventory(); i++){
				ItemStack stack = inventory.getStackInSlot(i);
				if(stack != null && stack.getItem() != Item.getItemFromBlock(ACBlocks.coral)){
					return false;
				}
				if(stack != null && stack.getItem() == Item.getItemFromBlock(ACBlocks.coral) && stack.getItemDamage()%8 > 3){
					count += 1;
				}
			}
			if(count == 9){return true;}
			
			count = 0;
			for(int i = 0; i < inventory.getSizeInventory(); i++){
				ItemStack stack = inventory.getStackInSlot(i);
				if(stack != null && stack.getItem() != Item.getItemFromBlock(ACBlocks.coral)){
					return false;
				}
				if(stack != null && stack.getItem() == Item.getItemFromBlock(ACBlocks.coral) && stack.getItemDamage()%8 < 4){
					count += 1;
				}
			}
			if(count == 4){return true;}
			return false;
		}
		@Override
		public ItemStack getCraftingResult(InventoryCrafting inventory) {
			return new ItemStack(ACBlocks.limestone);
		}
		@Override
		public int getRecipeSize() {
	        return 9;
		}
		@Override
		public ItemStack getRecipeOutput() {
			return new ItemStack(ACBlocks.limestone);
		}
	}
	
	@Override
	public int getBurnTime(ItemStack fuel) {
		if(fuel.getItem() == ACItems.itemGeneric && fuel.getItemDamage() == ACItems.itemTarBall.getItemDamage()){
			return 1600;
		}
		return (fuel.getItem() == ACItems.itemGuayuleBranch.getItem() && fuel.getItemDamage() == ACItems.itemGuayuleBranch.getItemDamage()) ? 100 : 0;
	}
}

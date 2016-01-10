package azaka7.algaecraft.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.handlers.ACInterModHandler;
import azaka7.algaecraft.common.items.ItemAirTank.TankType;

public class ACItems {
	public static final ArmorMaterial materialThin = EnumHelper.addArmorMaterial("thin", 4, new int[]{1,2,1,1}, 10);
	public static final ArmorMaterial materialDryIron = EnumHelper.addArmorMaterial("dryIron", 14, new int[]{2,5,4,2}, 9);
	public static final ArmorMaterial materialDryGold = EnumHelper.addArmorMaterial("dryGold", 6, new int[]{2,4,2,1}, 20);
	public static final ArmorMaterial materialDryDiamond = EnumHelper.addArmorMaterial("dryDiamond", 30, new int[]{3,7,5,3}, 12);
	
	public static Item itemSponge = new ItemSponge("")
		.setUnlocalizedName(name("drySponge"))
		.setCreativeTab(AlgaeCraft.modTab);;
	public static Item itemSpongeRed = new ItemSponge("Red")
		.setUnlocalizedName(name("drySpongeRed"))
		.setCreativeTab(AlgaeCraft.modTab);;
	public static Item itemAlgaeCooked = new ItemFood(2, 0.2F, false)
		.setPotionEffect(Potion.hunger.getId(), 5, 0, 0.2F)
		.setUnlocalizedName(name("itemAlgaeCooked"))
		.setTextureName(AlgaeCraft.MODID+":algaeChips")
		.setCreativeTab(AlgaeCraft.modTab);
	public static Item itemSeaweedCooked = new ItemFood(3, 0.3F, false)
		.setUnlocalizedName(name("itemSeaweedCooked"))
		.setTextureName(AlgaeCraft.MODID+":seaweedCooked")
		.setCreativeTab(AlgaeCraft.modTab);
	public static Item itemSquidRaw = new ItemFood(4, 0.5F, false)
		.setPotionEffect(Potion.hunger.getId(), 6, 0, 0.3F)
		.setUnlocalizedName(name("itemSquidRaw"))
		.setTextureName(AlgaeCraft.MODID+":rawSquid")
		.setCreativeTab(AlgaeCraft.modTab);
	public static Item itemSquidCooked = new ItemFood(7, 0.7F, true)
		.setUnlocalizedName(name("itemSquidCooked"))
		.setTextureName(AlgaeCraft.MODID+":calamariSeared")
		.setCreativeTab(AlgaeCraft.modTab);
	public static Item itemSquidFried = new ItemSoup(9)
		.setTextureName(AlgaeCraft.MODID+":calamari")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemSquidFried"));
	
	public static Item itemSushiRaw = new ItemFood(6, 0.6F, false)
		.setUnlocalizedName(name("sushiRaw"))
		.setTextureName(AlgaeCraft.MODID+":sushiRaw")
		.setCreativeTab(AlgaeCraft.modTab);
	public static Item itemSushiCooked = new ItemFood(9, 0.9F, true)
		.setUnlocalizedName(name("sushiCooked"))
		.setTextureName(AlgaeCraft.MODID+":sushiCooked")
		.setCreativeTab(AlgaeCraft.modTab);
	public static Item itemSushiRawPumpkin = new ItemFood(13, 5.5F, false)
		.setUnlocalizedName(name("sushiRawPumpkin"))
		.setTextureName(AlgaeCraft.MODID+":sushiRawPumpkin")
		.setCreativeTab(AlgaeCraft.modTab);
	public static Item itemSushiCookedPumpkin = new ItemFood(16, 11.1F, false)
		.setUnlocalizedName(name("sushiCookedPumpkin"))
		.setTextureName(AlgaeCraft.MODID+":sushiCookedPumpkin")
		.setCreativeTab(AlgaeCraft.modTab);
	
	public static Item itemKnifeIron = new ItemKnife(4.0D, 150)
		.setTextureName(AlgaeCraft.MODID+":knife")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemKnife"));
	public static Item itemKnifeGold = new ItemKnife(3.0D, 70)
		.setTextureName(AlgaeCraft.MODID+":knifeGold")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemKnifeGold"));
	public static Item itemLobster = new ItemLobster()
		.setTextureName(AlgaeCraft.MODID+":lobsterItem")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemLobster"));
	public static Item itemLobsterRaw = new ItemFood(2, 0.1F, false)
		.setTextureName(AlgaeCraft.MODID+":lobsterRaw")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemLobsterRaw"));
	public static Item itemLobsterCooked = new ItemFood(8, 0.4F, true)
		.setTextureName(AlgaeCraft.MODID+":lobsterCooked")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemLobsterCooked"));
	public static Item itemAerosBulb = new ItemAerosBulb()
		.setTextureName(AlgaeCraft.MODID+":waterBreathPearl")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemAerosBulb"));
	public static Item itemScubaMask = new ItemDiveMask(materialThin, 0, "scubaEssentials")
		.setTextureName(AlgaeCraft.MODID+":scubaItemMask")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemDiveMask"));
	public static Item itemScubaBCD = new ItemBCD(materialThin, 0, "scubaBcdArmor")
		.setTextureName(AlgaeCraft.MODID+":itemBCD")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemBCD"));
	public static Item itemAirTankSmall = new ItemAirTank(200, TankType.SMALL)
		.setTextureName(AlgaeCraft.MODID+":itemAirtankSmall")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemAirTankSmall"));
	public static Item itemAirTankLarge = new ItemAirTank(800, TankType.LARGE)
		.setTextureName(AlgaeCraft.MODID+":itemAirtankLarge")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemAirTankLarge"));
	public static Item itemAirTankCreative = new ItemAirTank(Integer.MAX_VALUE, TankType.LARGE)
	{
		@Override
		public void onCreated(ItemStack stack, World p_77622_2_, EntityPlayer p_77622_3_){
			stack.stackSize = 0;
		}
		
		@Override
		public int getDamage(ItemStack stack)
	    {
			stack.setItemDamage(0);
	        return super.getDamage(stack);
	    }
		@SideOnly(Side.CLIENT)
	    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
			super.addInformation(stack, player, list, bool);
			list.add(EnumChatFormatting.DARK_GRAY+"Infinite Air");
		}
	}
		.setTextureName(AlgaeCraft.MODID+":itemAirtankCreative")
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemAirTankCreative"));
	public static Item itemWetsuit = new ItemWetsuit(materialThin, 0)
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemWetsuit"));
	public static Item itemFlippers = new ItemFlippers(materialThin, 0, "flippersArmor")
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":flippersItem")
		.setUnlocalizedName(name("itemFlippers"));
	//private static final PotionEffect greekFire = constructMultiEffect(new PotionEffect(Potion.poison.id, 60, 4), new PotionEffect(Potion.blindness.id, 60), new PotionEffect(Potion.nightVision.id, 60), new PotionEffect(Potion.moveSpeed.id, 60, 2));
	public static Item itemFlask = new ItemFlask((Item) null,
			new       String[]{"Empty","Water",                                           "Salt",                                    "NaOH",                                     "CaOH", "H2CO3"},
			new PotionEffect[]{null,    new PotionEffect(Potion.fireResistance.id, 50, 1), new PotionEffect(Potion.hunger.id, 300, 2), new PotionEffect(Potion.wither.id, 40, 2), null,   null  })
		.setCreativeTab(AlgaeCraft.modTab)
		.setUnlocalizedName(name("itemFlask"));
	public static Item itemFlaskFake = new ItemFlask(itemFlask,
			new       String[]{"Empty","Water",                                           "Salt",                                    "NaOH",                                     "CaOH", "H2CO3"},
			new PotionEffect[]{null,    new PotionEffect(Potion.fireResistance.id, 50, 1), new PotionEffect(Potion.hunger.id, 300, 2), new PotionEffect(Potion.wither.id, 40, 2), null,   null  })
		.setUnlocalizedName(name("itemFlaskFake"));
	public static Item itemRedironElectrolyzer= new ItemDamageableCrafting(200, false, true)
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":redstoneElectrolyzer")
		.setUnlocalizedName(name("itemRedironElectrolyzer"));
	public static Item itemQuicklime = new ItemHydrophile(0.03125F,0)
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":quicklime")
		.setUnlocalizedName(name("itemQuicklime"));
	public static Item tridentWood = new ItemTrident(3.0F, ToolMaterial.WOOD)
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":trident_wood")
		.setUnlocalizedName(name("itemTridentWood"));
	public static Item tridentStone = new ItemTrident(3.0F, ToolMaterial.STONE)
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":trident_stone")
		.setUnlocalizedName(name("itemTridentStone"));
	public static Item tridentBone = new ItemTrident(3.0F, ItemTrident.Materials.bone)
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":trident_bone")
		.setUnlocalizedName(name("itemTridentBone"));
	public static Item tridentIron = new ItemTrident(3.0F, ToolMaterial.IRON)
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":trident_iron")
		.setUnlocalizedName(name("itemTridentIron"));
	public static Item tridentNetherbrick = new ItemTrident(3.0F, ItemTrident.Materials.netherbrick)
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":trident_netherbrick")
		.setUnlocalizedName(name("itemTridentNetherbrick"));
	public static Item tridentGold = new ItemTrident(3.0F, ToolMaterial.GOLD)
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":trident_gold")
		.setUnlocalizedName(name("itemTridentGold"));
	public static Item tridentDiamond = new ItemTrident(3.0F, ToolMaterial.EMERALD)
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":trident_diamond")
		.setUnlocalizedName(name("itemTridentDiamond"));
	public static Item tridentEmerald = new ItemTrident(3.0F, ItemTrident.Materials.emerald)
		.setCreativeTab(AlgaeCraft.modTab)
		.setTextureName(AlgaeCraft.MODID+":trident_emerald")
		.setUnlocalizedName(name("itemTridentEmerald"));

	/*/ Make the files of these items force-update themselves to new items.
	public static Item itemLobsterBoiled;
	public static Item itemChipRediron;
	public static Item itemGuayuleBranch;
	public static Item itemRubberRaw;
	public static Item itemRubberBall;
	public static Item itemNeopreneTextile;
	////////////////////////////////////////////////////////*/
	
	public static ItemGenericItems itemGeneric = (ItemGenericItems) new ItemGenericItems().setUnlocalizedName(name("genericItems")).setCreativeTab(AlgaeCraft.modTab);;
	public static ItemStack itemLobsterBoiled = itemGeneric.addGenericItem(0, "lobsterBoiled", "lobsterBoiledItem");
	public static ItemStack itemChipRediron = itemGeneric.addGenericItem(1, "redironChip", "chipRediron");
	public static ItemStack itemGuayuleBranch = itemGeneric.addGenericItem(2, "guayuleBranch", "guayuleBranches");
	public static ItemStack itemRubberRaw = itemGeneric.addGenericItem(3, "rubberRaw", "rubberRaw");
	public static ItemStack itemRubberBall = itemGeneric.addGenericItem(4, "rubberBall", "rubberBall");
	public static ItemStack itemNeopreneTextile = itemGeneric.addGenericItem(5, "neopreneTextile", "neopreneTextile");
	//public static ItemStack itemObdurateChunk = itemGeneric.addGenericItem(6, "obdurateChunk", "obdurate_chunk");
	//public static ItemStack itemObdurateSteelIngot = itemGeneric.addGenericItem(7, "ingotObdurateSteel", "ingot_obdurateSteel");
	public static ItemStack itemValuableDust = itemGeneric.addGenericItem(6, "valuableDust", "valuableDust");
	
	public static ItemStack itemStackFlaskEmpty = new ItemStack(itemFlask, 1, 0);
	public static ItemStack itemStackFlaskWater = new ItemStack(itemFlask, 1, 1);
	public static ItemStack itemStackFlaskNaCl = new ItemStack(itemFlask, 1, 2);
	public static ItemStack itemStackFlaskNaOH = new ItemStack(itemFlask, 1, 3);
	public static ItemStack itemStackFlaskCaOH = new ItemStack(itemFlask, 1, 4);
	public static ItemStack itemStackFlaskH2CO3 = new ItemStack(itemFlask, 1, 5);
	
	public static void registerItems(){
		//FMLInterModComms.
		String modId = AlgaeCraft.MODID;
		//Items
		registerItem(itemSponge, "drySponge", modId,"materialSponge");
		FluidContainerRegistry.registerFluidContainer(new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(itemSponge, 1, 1), new ItemStack(itemSponge, 1, 0));
		registerItem(itemSpongeRed, "drySpongeRed", modId,"materialSpongeRed");
		FluidContainerRegistry.registerFluidContainer(new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(itemSpongeRed, 1, 1), new ItemStack(itemSpongeRed, 1, 0));
		registerItem(itemGeneric, "genericItems", modId,"materialAlgaecraftGenericItems");
		registerItem(itemAlgaeCooked, "algaeCooked", modId,"foodAlgaeCooked", "foodCookedAlgae");
		registerItem(itemSeaweedCooked, "seaweedCooked", modId,"foodSeaweedCooked", "foodCookedSeaweed");
		registerItem(itemSquidRaw, "squidRaw", modId,"foodRawSquid", "foodSquidRaw", "foodRawCalamari", "foodCalamariRaw");
		registerItem(itemSquidCooked, "squidCooked", modId,"foodCookedSquid","foodSquidCooked","foodCalamariCooked","foodCookedCalamari");
		registerItem(itemSquidFried, "squidFried", modId,"foodCalamariRingsCooked", "foodCookedCalamariRings", "foodFriedCalamariRings");
		
		registerItem(itemSushiRaw, "sushiRaw", modId,"foodSushiRaw","foodRawSushi");
		registerItem(itemSushiCooked, "sushiCooked", modId,"foodSushiCooked","foodCookedSushi");
		registerItem(itemSushiRawPumpkin, "sushiRawPumpkin", modId,"foodSushiRawPumpkin","foodRawSushiPumpkin");
		registerItem(itemSushiCookedPumpkin, "sushiCookedPumpkin", modId,"foodSushiCookedPumpkin","foodCookedSushiPumpkin");
		
		registerItem(itemKnifeIron, "knifeIron", modId,"weaponKnifeIron","toolKnifeIron","itemKnifeIron","weaponIronKnife","toolIronKnife","itemIronKnife");
		registerItem(itemKnifeGold, "knifeGold", modId,"weaponKnifeGold","toolKnifeGold","itemKnifeGold","weaponGoldKnife","toolGoldKnife","itemGoldKnife");
		registerItem(itemLobster, "lobster", modId,"mobLobster","itemLobster","entityLobster","materialLobster");
		registerItem(itemLobsterRaw, "lobsterRaw", modId,"foodLobsterRaw","foodRawLobster");
		registerItem(itemLobsterCooked, "lobsterCooked", modId, "foodLobsterCooked","foodCookedLobster","foodBoiledLobster","foodLobsterBoiled");
		registerItem(itemAerosBulb, "aerosBulb", modId, "ingredientAerosBulb", "itemAerosBulb");
		itemAerosBulb.setPotionEffect(PotionHelper.field_151423_m);
		
		if(AlgaeCraft.thaumcraft()){
			itemScubaMask = ACInterModHandler.getItem_Thaum(itemScubaMask);
			itemScubaBCD = ACInterModHandler.getItem_Thaum(itemScubaBCD);
			itemWetsuit = ACInterModHandler.getItem_Thaum(itemWetsuit);
			itemFlippers = ACInterModHandler.getItem_Thaum(itemFlippers);
		}
		registerItem(itemScubaMask, "diveMask", modId, "diveMask", "helmetDiveMask", "itemDiveMask");
		registerItem(itemScubaBCD, "bcd", modId, "diveBCD", "armorBCD", "bcd");
		registerItem(itemWetsuit, "wetsuit", modId, "armorWetsuit", "diveWetsuit", "itemWetsuit");
		registerItem(itemFlippers, "flippers", modId, "diveFlippers", "flippers", "armorFlippers", "itemFlippers");
		registerItem(itemAirTankSmall,"airTankSmall",modId,"itemAirTankSmall");
		registerItem(itemAirTankLarge,"airTankLarge",modId,"itemAirTankLarge");
		registerItem(itemAirTankCreative,"airTankCreative",modId);
		
		registerItem(itemFlask, "flask", modId);
		registerItemStack(new ItemStack(itemFlask, 1, 0), "flaskEmpty", "flaskEmpty", "itemFlaskEmpty", "itemEmptyFlask");
		registerItemStack(new ItemStack(itemFlask, 1, 1), "flaskWater", "flaskWater", "itemFlaskWater", "itemWaterFlask");
		registerItemStack(new ItemStack(itemFlask, 1, 2), "flaskSaltWater", "flaskSaltWater", "flaskWaterSalt", "itemFlaskSaltWater", "itemSaltWaterFlask");
		registerItemStack(new ItemStack(itemFlask, 1, 3), "flaskNaOH", "flaskSodiumHydroxide", "flaskNaOH", "itemFlaskSodiumHydroxide", "itemFlaskNaOH", "itemSodiumHydroxideFlask", "itemNaOHFlask");
		registerItem(itemFlaskFake, "CRAFTING-FLASK-DO-NOT-OBTAIN", modId);
		
		registerItem(itemRedironElectrolyzer, "electrolyzer", modId, "itemElectrolyzer", "toolElectrolyzer");
		registerItem(itemQuicklime, "quicklime", modId, "itemQuicklime", "itemCaO", "dustQuicklime", "dustCaO");
		
		registerItem(tridentWood, "tridentWood", modId, "toolTridentWood", "tridentWood");
		registerItem(tridentStone, "tridentStone", modId, "toolTridentStone", "tridentStone");
		registerItem(tridentBone, "tridentBone", modId, "toolTridentBone", "tridentBone");
		registerItem(tridentIron, "tridentIron", modId, "toolTridentIron", "tridentIron");
		registerItem(tridentNetherbrick, "tridentNetherbrick", modId, "toolTridentNetherbrick", "tridentNetherbrick");
		registerItem(tridentGold, "tridentGold", modId, "toolTridentGold", "tridentGold");
		registerItem(tridentDiamond, "tridentDiamond", modId, "toolTridentDiamond", "tridentDiamond");
		registerItem(tridentEmerald, "tridentEmerald", modId, "toolTridentEmerald", "tridentEmerald");
		
		registerItemStack(ACItems.itemChipRediron,"redironChip", "chipRediron","chipRedironBasic");
		registerItemStack(ACItems.itemLobsterBoiled,"lobsterBoiled", "materialLobsterCooked", "materialCookedLobster");
	}

	private static void registerItem(Item item, String name, String modId, String... oreDict){
		GameRegistry.registerItem(item, name);
		for(int i = 0; i<oreDict.length; i++){
			OreDictionary.registerOre(oreDict[i], item);
		}
	}
	
	private static void registerItemStack(ItemStack stack, String name, String... oreDict){
		GameRegistry.registerCustomItemStack(name, stack);
		for(int i = 0; i<oreDict.length; i++){
			OreDictionary.registerOre(oreDict[0], stack);
		}
		OreDictionary.registerOre("item"+Character.toUpperCase(name.charAt(0))+name.substring(1),stack);
	}
	
	public static class ArmorMaterials{
		public static final ItemArmor.ArmorMaterial materialThin = EnumHelper.addArmorMaterial("thin", 4, new int[]{1,2,1,1}, 10);
		public static final ItemArmor.ArmorMaterial materialDryIron = EnumHelper.addArmorMaterial("dryIron", 14, new int[]{2,5,4,2}, 9);
		public static final ItemArmor.ArmorMaterial materialDryGold = EnumHelper.addArmorMaterial("dryGold", 6, new int[]{2,4,2,1}, 20);
		public static final ItemArmor.ArmorMaterial materialDryDiamond = EnumHelper.addArmorMaterial("dryDiamond", 30, new int[]{3,7,5,3}, 12);
	}
	
	private static String name(String s){
		return AlgaeCraft.MODID+"_"+s;
	}
}

package azaka7.algaecraft.common.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.ISwimGear;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.MoveKey;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.SwimFactor;
import cpw.mods.fml.client.config.GuiConfigEntries.ChatColorEntry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBCD extends ItemArmor implements ISwimGear{

	public static final SwimFactor H_FACTOR_F = new SwimFactor("AlgaeCraft:bcd_horizontal_f", 1.2, 0.1, MoveKey.FOREWARD);
	public static final SwimFactor H_FACTOR_B = new SwimFactor("AlgaeCraft:bcd_horizontal_b", 1.2, 0.1, MoveKey.BACKWARD);
	public static final SwimFactor H_FACTOR_L = new SwimFactor("AlgaeCraft:bcd_horizontal_l", 1.2, 0.1, MoveKey.LEFT);
	public static final SwimFactor H_FACTOR_R = new SwimFactor("AlgaeCraft:bcd_horizontal_r", 1.2, 0.1, MoveKey.RIGHT);
	public static final SwimFactor V_FACTOR_U = new SwimFactor("AlgaeCraft:bcd_vertical_u", 1.2, 0.105, MoveKey.JUMP);
	public static final SwimFactor V_FACTOR_D = new SwimFactor("AlgaeCraft:bcd_vertical_d", 1.2, 0.105, MoveKey.SNEAK);
	
	private String armorImg;
	
	public static final String tankDamage = "tankDamage";
	public static final String tankID = "tankID";
	public static final String tankName = "tankName";
	public static final String bcdState = "bcdState";
	public static final String bcdFlight = "bcdFlight";
	
	public static List<String> flyingPlayers = new ArrayList<String>();
	
	public ItemBCD(ItemArmor.ArmorMaterial par2EnumArmorMaterial, int par3, String armorName) {
		super(par2EnumArmorMaterial, par3, 1);
		armorImg = armorName;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		confirmTagCompound(par1ItemStack);
		if(par3EntityPlayer.isSneaking()){
			ItemStack bcd = par1ItemStack.copy();
			ItemStack tank = createTankStack(bcd);
			
			if(tank != null){
				if(!par3EntityPlayer.inventory.addItemStackToInventory(tank)){
					par3EntityPlayer.dropPlayerItemWithRandomChoice(tank, false);
				}
				bcd.getTagCompound().setString(tankName, "null");
				bcd.getTagCompound().setInteger(tankDamage, 0);
				
				return bcd;
			}
		}
		
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
    }
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
		confirmTagCompound(itemStack);
		if(!player.isInWater()){
			if(player.getEntityData().getBoolean(bcdFlight)){
				player.capabilities.allowFlying = player.capabilities.isCreativeMode;
				player.capabilities.isFlying = false;
				if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
					player.capabilities.setFlySpeed(0.05F);
				}
				player.getEntityData().setBoolean(bcdFlight, false);
			}
			return;}
		
		ItemStack tank = createTankStack(itemStack);
		if(tank !=null && tank.getItemDamage() < tank.getMaxDamage()){
			if(player.getAir() + 60 <= 300){
				player.setAir(player.getAir() + 60);
				tank.damageItem(1, player);
			}
			setTankReturnOld(itemStack, tank);
		}
		//TODO Rewrite flight system... again.
		if(getState(itemStack) == 1){
			if(!player.capabilities.isCreativeMode){
				//boolean flightOverride = player.capabilities.allowFlying;
				player.capabilities.allowFlying = player.capabilities.isCreativeMode;
				//if(!defaultFlight){
					player.capabilities.isFlying = true;
					if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
						player.capabilities.setFlySpeed(0.0085F);
					}
					player.motionY *= 0.6;
					player.getEntityData().setBoolean(bcdFlight, true);
				//}
				
				//player.capabilities.allowFlying = flightOverride;
			}
		}
		else{
			if(player.getEntityData().getBoolean(bcdFlight)){
				player.capabilities.allowFlying = player.capabilities.isCreativeMode;
				player.getEntityData().removeTag(bcdFlight);
				player.capabilities.isFlying = false;
				if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
					player.capabilities.setFlySpeed(0.05F);
				}
				player.getEntityData().setBoolean(bcdFlight, false);
			}
		}
		if(getState(itemStack) == 2){
			player.motionY += 0.03F;
		}
    }
	
	public void changeDo(ItemStack par1ItemStack, EntityPlayer player, boolean increase, Side side)
	{
		ItemStack tank = createTankStack(par1ItemStack);
		if(tank == null || tank.getItem() == null){return;}
		confirmTagCompound(par1ItemStack);
		
		int reqAir = 0;
		if(increase){
			if(getState(par1ItemStack) == 2){reqAir = 0;}
			else{reqAir = 1;}
			
			if(tank.getItemDamage() + reqAir > tank.getMaxDamage()){
				if(side == Side.CLIENT){
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY+"Not enough air to change state"));
				}
				return;
			}
			
			tank.setItemDamage(tank.getItemDamage()+reqAir);
			setTankReturnOld(par1ItemStack, tank);
			setState(par1ItemStack, getState(par1ItemStack)+1);
			if(side == Side.CLIENT){
				String s = "";
				switch(getState(par1ItemStack)){
					case 0: s = "Negative"; break;
					case 1: s = "Neutral"; break;
					case 2: s = "Positive"; break;
					default: s = ""; break;
				}
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY+"BCD State: "+s+" Buoyancy"));
			}
		}
		else{
			if(getState(par1ItemStack) == 0){reqAir = 2;}
			else{reqAir = 0;}
			
			if(tank.getItemDamage() + reqAir > tank.getMaxDamage()){
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY+"Not enough air to change state"));
				return;}
			
			tank.setItemDamage(tank.getItemDamage()+reqAir);
			setTankReturnOld(par1ItemStack, tank);
			setState(par1ItemStack, getState(par1ItemStack)-1);
			if(side == Side.CLIENT){
				String s = "";
				switch(getState(par1ItemStack)){
					case 0: s = "Negative"; break;
					case 1: s = "Neutral"; break;
					case 2: s = "Positive"; break;
					default: s = ""; break;
				}
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY+"BCD State: "+s+" Buoyancy"));
			}
		}
	}
	
	public static boolean setTank(ItemStack bcd, ItemStack newTank){
		//System.out.println("setting tank");
		//confirmTagCompound(bcd);
		if(createTankStack(bcd) != null){return false;}
		bcd.getTagCompound().setInteger(tankDamage, newTank.getItemDamage());
		//newTank.writeToNBT(bcd.getTagCompound().getCompoundTag(tankTag));
		setTankType(bcd, newTank.getItem());
		return true;
	}
	
	public static ItemStack setTankReturnOld(ItemStack bcd, ItemStack newTank){
		confirmTagCompound(bcd);
		ItemStack oldTank = createTankStack(bcd);

		bcd.getTagCompound().setInteger(tankDamage, newTank.getItemDamage());
		//newTank.writeToNBT(bcd.getTagCompound().getCompoundTag(tankTag));
		setTankType(bcd, newTank.getItem());
		
		return oldTank;
	}
	
	private static ItemStack createTankStack(ItemStack bcd){
		ItemStack stack = bcd.copy();
		//int tankType = bcd.getTagCompound().getInteger(tankID);
		String tankname = bcd.getTagCompound().getString(tankName);
		int tankDam = bcd.getTagCompound().getInteger(tankDamage); 
		
		if(tankname != "null" && tankname != "" && tankname != null){
			ItemStack tank = new ItemStack(getTankType(bcd), 1, tankDam);
			//tank.setTagCompound(bcd.getTagCompound().getCompoundTag(tankTag));
			//tank.loadItemStackFromNBT(tankTags);
			return tank;
		}
		else{return null;}
	}
	
	//tankType: 0 - none; 1 - small; 2 - mid; 3 - large; 4 - creative
	private static Item getTankType(ItemStack stack){
		/*switch(stack.getTagCompound().getInteger(tankID)){
		case 1: return ACItems.itemAirTankSmall;
		case 2: return ACItems.itemAirTankLarge;
		case -1: return ACItems.itemAirTankCreative;
		default: return null;
		}*/
		return (Item) Item.itemRegistry.getObject(stack.getTagCompound().getString("tankName"));
	}
	private static void setTankType(ItemStack stack, Item item){
		//int i = item == null ? 0 : (item == ACItems.itemAirTankSmall ? 1 : 0);
		//stack.getTagCompound().setInteger(tankID, i);
		stack.getTagCompound().setString("tankName",Item.itemRegistry.getNameForObject(item));
	}
	
	//getState: 0 - off; 1 - neutral; 2 - float;
	private static int getState(ItemStack stack){
		return stack.getTagCompound().getInteger(bcdState);
	}
	private static void setState(ItemStack stack, int i){
		stack.getTagCompound().setInteger(bcdState, i);
		if(stack.getTagCompound().getInteger(bcdState) > 2){
			stack.getTagCompound().setInteger(bcdState, 0);
		}
		if(stack.getTagCompound().getInteger(bcdState) < 0){
			stack.getTagCompound().setInteger(bcdState, 2);
		}
	}
	
	private static void confirmTagCompound(ItemStack stack){
		if(stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		if(ACItems.itemScubaBCD == par1ItemStack.getItem()){
			return (ACItems.itemRubberBall.getItem() == par2ItemStack.getItem() && ACItems.itemRubberBall.getItemDamage() == par2ItemStack.getItemDamage()) || super.getIsRepairable(par1ItemStack,par2ItemStack) ;
		}
		else{
			return super.getIsRepairable(par1ItemStack, par2ItemStack);
		}
	//return AlgaeCraftMain.itemScubaBCD.itemID == par2ItemStack.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
	
	@SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
		confirmTagCompound(itemStack);
		//if(armorSlot == 2){
		float f = this.getState(itemStack)*0.45F;
		ModelBiped model = new ModelBiped(0.51F+f);
		model.isSneak = entityLiving.isSneaking();
        return model;
		//}
		//return null;
    }
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		confirmTagCompound(par1ItemStack);
		String s = "Off, but something went wrong.";
		switch(this.getState(par1ItemStack)){
		case 0: s = "Off"; break;
		case 1: s = "Neutral"; break;
		case 2: s = "Float"; break;
		}
		par3List.add("BCD State: "+s);
		ItemStack atank = this.createTankStack(par1ItemStack);
		boolean notNull = atank != null && atank.getItem() != null;
		if(notNull && atank.getItem() == ACItems.itemAirTankCreative){
			par3List.add("Air Tank: \u221E");
			return;
		}
		//if(atank == null){System.out.println("could not construct tank stack");}
		int max = notNull ? atank.getMaxDamage() : 0;
		int amount = notNull ? atank.getItemDamage() : 0;
		amount = max - amount;
		s="No Air Tank!";
		if(amount - max > max && max>0){s = "Too Much Air! ("+(amount-max)+"/"+max+")";}
		else if(max>0){
			s = amount+"/"+max+" Breaths";
		}
		par3List.add("Air Tank: " + amount + "/" + max);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer)
    {
        return AlgaeCraft.MODID+":textures/armor/"+armorImg+".png";
    }
	
	@Override
	public ArrayList<SwimFactor> getValidFactorsForItem(EntityPlayer player,
			ItemStack stack, int slot) {
		ArrayList<SwimFactor> ret = new ArrayList<SwimFactor>();
			ret.add(H_FACTOR_B);
			ret.add(H_FACTOR_F);
			ret.add(H_FACTOR_L);
			ret.add(H_FACTOR_R);
			ret.add(V_FACTOR_D);
			ret.add(V_FACTOR_U);
		return ret;
	}
	
	@Override
	public void onPlayerSwimTick(EntityPlayer player, ItemStack stack, int slot) {}

}
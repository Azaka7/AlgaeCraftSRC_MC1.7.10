package azaka7.algaecraft.common.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import azaka7.algaecraft.common.handlers.ACSwimmingHandler.MoveKey;
import azaka7.algaecraft.common.items.ItemFlippers;
import azaka7.algaecraft.common.items.ItemWetsuit;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ACSwimmingHandler {
	
	/**
	 * The normal walking speed of a player
	 */
	public static final double DEFAULT_WALK_SPEED = 0.1;
	
	private static final HashMap<String,SwimFactor> factorMap = new HashMap<String,SwimFactor>();
	
	public static final String SWIM_TAG = "ac_swimming_tags";
	
	//public static final SwimFactor YOUR_FACTOR = new SwimFactor(getOpenID(), 1, true);
	
	public static void registerKnownFactors(){
		registerFactor(ItemFlippers.H_FACTOR_F);
		registerFactor(ItemFlippers.H_FACTOR_B);
		registerFactor(ItemFlippers.H_FACTOR_L);
		registerFactor(ItemFlippers.H_FACTOR_R);
		registerFactor(ItemFlippers.V_FACTOR_U);
		registerFactor(ItemFlippers.V_FACTOR_D);
		

		registerFactor(ItemFlippers.H_FACTOR_F_W);
		registerFactor(ItemFlippers.H_FACTOR_B_W);
		registerFactor(ItemFlippers.H_FACTOR_L_W);
		registerFactor(ItemFlippers.H_FACTOR_R_W);
		registerFactor(ItemFlippers.V_FACTOR_U_W);
		registerFactor(ItemFlippers.V_FACTOR_D_W);
		
		registerFactor(ItemWetsuit.H_FACTOR_B);
		registerFactor(ItemWetsuit.H_FACTOR_F);
		registerFactor(ItemWetsuit.H_FACTOR_L);
		registerFactor(ItemWetsuit.H_FACTOR_R);
		registerFactor(ItemWetsuit.V_FACTOR_D);
		registerFactor(ItemWetsuit.V_FACTOR_U);
	}
	
	public static ArrayList<SwimFactor> getValidFactorsForKeys(EntityPlayer player, MoveKey... keys){
		ArrayList<SwimFactor> ret = new ArrayList<SwimFactor>();
		NBTTagCompound tags = player.getEntityData();
		
		for(int slot = 0; slot < player.inventory.getSizeInventory(); slot++){
			ItemStack stack = player.inventory.getStackInSlot(slot);
			if(stack != null && stack.getItem() != null && stack.getItem() instanceof ISwimGear){
				//System.out.println("Getting factors for "+stack.getDisplayName());
				ArrayList<SwimFactor> factors = ((ISwimGear) stack.getItem()).getValidFactorsForItem(player, stack, slot);
				//System.out.println("iterating through "+factors.size()+" factors");
				for(SwimFactor factor : factors){
					//System.out.println("Trying factor "+factor.toString()+" for "+stack.getDisplayName());
					//System.out.println("factor key: "+factor.getMoveKey());
					if(Arrays.asList(keys).contains(factor.getMoveKey())){
						//System.out.println("valid key");
						//System.out.println("Adding factor: "+factor.getID());
						ret.add(factor);
					}
				}
			}
		}
		
		return ret;
	}
	
	public static void setFactorsToPlayerNBT(ArrayList<SwimFactor> factors,EntityPlayer player){
		NBTTagCompound tags = player.getEntityData();
		NBTTagCompound newTags = new NBTTagCompound();
		//System.out.println("writing " + factors.size() + " factors: ");
		for(int i = 0; i < factors.size(); i++){
			SwimFactor sf = factors.get(i);
			newTags.setString("f"+i, sf.getID());
			//System.out.println("f"+i+":"+sf.getID());
		}
		newTags.setInteger("size", factors.size());
		tags.setTag(SWIM_TAG, newTags);
	}
	
	public static ArrayList<SwimFactor> getFactorsFromPlayerNBT(EntityPlayer player){
		ArrayList<SwimFactor> ret = new ArrayList<SwimFactor>();
		NBTTagCompound tags = player.getEntityData();
		NBTTagCompound factorTags = tags.getCompoundTag(SWIM_TAG);
		//System.out.println("Getting factors from NBT: ");
		for(int i = 0; i < factorTags.getInteger("size"); i++){
			String id = factorTags.getString("f"+i);
			//System.out.println(id);
			ret.add(getFactor(id));
		}
		//System.out.println("get factors from nbt: "+ret.size()+"/"+factorTags.getInteger("size")+"|"+factorTags.toString());
		return ret;
	}
	
	public static SwimFactor getFactor(String id){
		try{
			SwimFactor factor = factorMap.get(id);
			return factor;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static SwimFactor registerFactor(SwimFactor sf){
		factorMap.put(sf.getID(), sf);
		return sf;
	}
	
	public static class SwimFactor{
		private String type;
		private double factor;
		private MoveKey key;
		private double[] limits;
		
		public SwimFactor(String t, double f, double lowerLimit, double upperLimit, MoveKey movekey){
			type = t;
			factor = f;
			limits = new double[]{lowerLimit,upperLimit};
			key = movekey;
		}
		
		public SwimFactor(String t, double f, double upperLimit, MoveKey movekey){
			this(t,f,-0.2, upperLimit,movekey);
		}
		
		public SwimFactor(String t, double f, MoveKey movekey){
			this(t,f,Double.MAX_VALUE-2047,movekey);
		}
		
		public String getID(){
			return type;
		}
		
		public double getFactor(){
			return factor;
		}
		
		public double getUpperLimit(){
			return limits[1];
		}
		
		public double getLowerLimit(){
			return limits[0];
		}
		
		/**
		 * Adds the given SwimFactor to this
		 * @param sf The given SwimFactor to add to this SwimFactor
		 * @return Returns the resulting SwimFactor after addition
		 */
		public SwimFactor addTo(SwimFactor sf){
			if(this.type == sf.getID()){
				this.factor += sf.getFactor();
				return this;
			}
			System.out.println("[AlgaeCraft] Cannot add swimfactors of different types.");
			return this;
		}
		
		/**
		 * Applies the given SwimFactor to the factor of this SwimFactor.
		 * @param sf The given SwimFactor to apply to this SwimFactor
		 * @return Returns the resulting SwimFactor after application
		 */
		public SwimFactor applyTo(SwimFactor sf){
			if(this.type == sf.getID()){
				factor = sf.apply(factor);
				return this;
			}
			System.out.println("[AlgaeCraft] Cannot add swimfactors of different types.");
			return this;
		}
		
		/**
		 * Applies this SwimFactor to the given value.
		 * @param value
		 * @return Returns the altered value
		 */
		public double apply(double value){
			return value*factor;
		}
		
		public byte[] encode(){
			((Double) factor).toString();
			return null;
		}
		
		public SwimFactor copy(){
			return new SwimFactor(type, factor, limits[0], limits[1], key);
		}
		
		@Override
		public String toString(){
			return "SwimFactor[type="+type+";factor="+factor+";key="+key.name()+";]";
		}
		
		@Override
		public boolean equals(Object obj){
			if(obj instanceof ACSwimmingHandler.SwimFactor){
				SwimFactor sf = (SwimFactor) obj;
				return this.getID() == sf.getID() && this.getFactor() == sf.getFactor() && getMoveKey() == sf.getMoveKey();
			}
			return false;
		}

		public MoveKey getMoveKey() {
			return key;
		}
	}
	
	public static enum MoveKey{
		
		FOREWARD, BACKWARD, LEFT, RIGHT, JUMP, SNEAK;
		
		public short getShortOrdinal(){
			return (short) this.ordinal();
		}

		@SideOnly(Side.CLIENT)
		public boolean isPressed(){
			switch(this){
			case FOREWARD: return Minecraft.getMinecraft().gameSettings.keyBindForward.getIsKeyPressed();
			case BACKWARD: return Minecraft.getMinecraft().gameSettings.keyBindBack.getIsKeyPressed();
			case LEFT: return Minecraft.getMinecraft().gameSettings.keyBindLeft.getIsKeyPressed();
			case RIGHT: return Minecraft.getMinecraft().gameSettings.keyBindRight.getIsKeyPressed();
			case JUMP: return Minecraft.getMinecraft().gameSettings.keyBindJump.getIsKeyPressed();
			case SNEAK: return Minecraft.getMinecraft().gameSettings.keyBindSneak.getIsKeyPressed();
			
			default: return false;
			}
		}
	}
	
	public static interface ISwimGear{
		
		/**
		 * Get a list of SwimFactors that can be applied to the given player by the given itemstack
		 * @param player The given player
		 * @param stack The given itemstack
		 * @param slot the slot of the player's inventory that the itemstack is from
		 * @return Returns a list of SwimFactors to be applied to the player.
		 */
		public ArrayList<SwimFactor> getValidFactorsForItem(EntityPlayer player, ItemStack stack, int slot);
		
		/**
		 * Cause an update to the player or itemstack each tick the itemstack is applying
		 * a SwimFactor to the player.
		 * @param player The player
		 * @param stack The itemstack
		 * @param slot the slot of the player's inventory that the itemstack is from
		 */
		public void onPlayerSwimTick(EntityPlayer player, ItemStack stack, int slot);
	}
	
	private static double abs(double d){
		if(d < 0){
			return -1*d;
		}
		return d;
	}

}

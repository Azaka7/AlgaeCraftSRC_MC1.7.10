package azaka7.algaecraft.common.items;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.ISwimGear;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.MoveKey;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.SwimFactor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemWetsuit extends ItemArmor implements ISwimGear{

	public static final SwimFactor H_FACTOR_F = new SwimFactor("AlgaeCraft:wetsuit_horizontal_f", 1.1, 0.11, MoveKey.FOREWARD);
	public static final SwimFactor H_FACTOR_B = new SwimFactor("AlgaeCraft:wetsuit_horizontal_b", 1.1, 0.11, MoveKey.BACKWARD);
	public static final SwimFactor H_FACTOR_L = new SwimFactor("AlgaeCraft:wetsuit_horizontal_l", 1.1, 0.11, MoveKey.LEFT);
	public static final SwimFactor H_FACTOR_R = new SwimFactor("AlgaeCraft:wetsuit_horizontal_r", 1.1, 0.11, MoveKey.RIGHT);
	public static final SwimFactor V_FACTOR_U = new SwimFactor("AlgaeCraft:wetsuit_vertical_u", 1.1, 0.11, MoveKey.JUMP);
	public static final SwimFactor V_FACTOR_D = new SwimFactor("AlgaeCraft:wetsuit_vertical_d", 1.1, 0.11, MoveKey.SNEAK);
	
	private static final String[] colors = new String[]{"black","red","green","brown","blue","purple","cyan","lightgray","gray","pink","lime","yellow","lightblue","magenta","orange","white"};
	private static IIcon[] icons = new IIcon[16];
	
	public ItemWetsuit(ItemArmor.ArmorMaterial par2EnumArmorMaterial,
			int par3) {
		super(par2EnumArmorMaterial, par3, 2);
		this.setCreativeTab(AlgaeCraft.modTab);
	}
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
		/*int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack);
		if(i == 0){
			i = EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, itemStack);
			if(i == 0){
				i = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, itemStack);
				if(i == 0){
					i = EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, itemStack);
				}
			}
		}
		if(player.isInWater()){
			player.motionX *= (Math.pow(1.003,(i+2)/2));
			player.motionZ *= (Math.pow(1.003,(i+2)/2));
		}*/
    }
	@SideOnly(Side.CLIENT)
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
		NBTTagCompound tags = stack.getTagCompound();
		if(tags.hasKey("skin")){
			String skin = tags.getString("skin");
			if(skin != ""){
				return AlgaeCraft.MODID+":textures/armor/wetsuit_"+skin+".png";
			}
		}
        return AlgaeCraft.MODID+":textures/armor/wetsuit_"+colors[this.getSuitColor(stack)]+".png";
    }
	
	@SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
		ModelBiped model = new ModelBiped(0.005F);
		model.isSneak = entityLiving.isSneaking();
		model.heldItemRight = entityLiving.getHeldItem() != null ? 1 : 0;
        return model;
    }
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		for(int i = 0; i<16;i++){
		icons[i] = par1IconRegister.registerIcon(AlgaeCraft.MODID+":wetsuit_"+colors[i]+"_item");
		}
    }

	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
    	return icons[this.getSuitColor(stack)];
    }
	
	public IIcon getIconFromDamage(int par1)
    {
		return icons[0];
    }
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		int color = this.getSuitColor(par1ItemStack);
		if(color > 0){
			par3List.add("Color: "+colors[this.getSuitColor(par1ItemStack)]);
		}
		NBTTagCompound tags = par1ItemStack.getTagCompound();
		if(tags.hasKey("skin")){
			String skin = tags.getString("skin");
			if(skin != ""){
				par3List.add("Skin: "+skin);
			}
		}
	}
	
	public static ItemStack colorize(ItemStack suit, int color){
		if(suit.stackTagCompound==null){
			suit.stackTagCompound = new NBTTagCompound();
		}
		suit.stackTagCompound.setInteger("dyecolor", color);
		return suit;
	}
	
	public int getSuitColor(ItemStack suit){
		if(suit.stackTagCompound==null){
			suit.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		if(!suit.stackTagCompound.hasKey("dyecolor")){
			suit.stackTagCompound.setInteger("dyecolor", 0);
		}
		return suit.stackTagCompound.getInteger("dyecolor");
	}
	
	/**
     * Return whether the specified armor ItemStack has a color.
     */
    public boolean hasColor(ItemStack par1ItemStack)
    {
        return true;
    }

    /**
     * Return the color for the specified armor ItemStack.
     */
    public int getColor(ItemStack par1ItemStack)
    {
        switch(getSuitColor(par1ItemStack)){
        case 1: return 0x993333;
        case 2: return 0x667F33;
        case 3: return 0x664C33;
        case 4: return 0x334CB2;
        case 5: return 0x7F3FB2;
        case 6: return 0x4C7F99;
        case 7: return 0x999999;
        case 8: return 0x4C4C4C;
        case 9: return 0xF27FA5;
        case 10: return 0x7FCC19;
        case 11: return 0xE5E533;
        case 12: return 0x6699D8;
        case 13: return 0xB24CD8;
        case 14: return 0xD87F33;
        case 15: return 0xFFFFFF;
        default: return -1;
        }
    }
    
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		if(ACItems.itemWetsuit == par1ItemStack.getItem()){
			return (ACItems.itemNeopreneTextile.getItem() == par2ItemStack.getItem() && ACItems.itemNeopreneTextile.getItemDamage() == par2ItemStack.getItemDamage()) || super.getIsRepairable(par1ItemStack,par2ItemStack) ;
		}
		else{
			return super.getIsRepairable(par1ItemStack, par2ItemStack);
		}
	}
	@Override
	public ArrayList<SwimFactor> getValidFactorsForItem(EntityPlayer player,
			ItemStack stack, int slot) {
		ArrayList<SwimFactor> ret = new ArrayList<SwimFactor>();
		if(slot == player.inventory.getSizeInventory() + 1){
			ItemStack boots = player.inventory.armorItemInSlot(0);
			if(!(boots == null || boots.getItem() == null || boots.getItem() instanceof ItemWetsuit)){
				ret.add(H_FACTOR_B);
				ret.add(H_FACTOR_F);
				ret.add(H_FACTOR_L);
				ret.add(H_FACTOR_R);
				ret.add(V_FACTOR_D);
				ret.add(V_FACTOR_U);
			}
		}
		return ret;
	}
	@Override
	public void onPlayerSwimTick(EntityPlayer player, ItemStack stack, int slot) {}

}
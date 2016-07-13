package azaka7.algaecraft.common.items;

import java.util.ArrayList;

import javax.swing.Icon;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.client.model.ModelFlippers;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.ISwimGear;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.MoveKey;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.SwimFactor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFlippers extends ItemArmor implements ISwimGear {

	public static final SwimFactor H_FACTOR_F = new SwimFactor("AlgaeCraft:flippers_horizontal_f", 1.20, 0.15, MoveKey.FOREWARD);
	public static final SwimFactor H_FACTOR_B = new SwimFactor("AlgaeCraft:flippers_horizontal_b", 1.20, 0.15, MoveKey.BACKWARD);
	public static final SwimFactor H_FACTOR_L = new SwimFactor("AlgaeCraft:flippers_horizontal_l", 1.15, 0.15, MoveKey.LEFT);
	public static final SwimFactor H_FACTOR_R = new SwimFactor("AlgaeCraft:flippers_horizontal_r", 1.15, 0.15, MoveKey.RIGHT);
	public static final SwimFactor V_FACTOR_U = new SwimFactor("AlgaeCraft:flippers_vertical_u", 1.30, 0.15, MoveKey.JUMP);
	public static final SwimFactor V_FACTOR_D = new SwimFactor("AlgaeCraft:flippers_vertical_d", 1.30, 0.15, MoveKey.SNEAK);

	public static final SwimFactor H_FACTOR_F_W = new SwimFactor("AlgaeCraft:flippers_horizontal_f_w", 1.21, 0.17, MoveKey.FOREWARD);
	public static final SwimFactor H_FACTOR_B_W = new SwimFactor("AlgaeCraft:flippers_horizontal_b_w", 1.21, 0.17, MoveKey.BACKWARD);
	public static final SwimFactor H_FACTOR_L_W = new SwimFactor("AlgaeCraft:flippers_horizontal_l_w", 1.16, 0.17, MoveKey.LEFT);
	public static final SwimFactor H_FACTOR_R_W = new SwimFactor("AlgaeCraft:flippers_horizontal_r_w", 1.16, 0.17, MoveKey.RIGHT);
	public static final SwimFactor V_FACTOR_U_W = new SwimFactor("AlgaeCraft:flippers_vertical_u_w", 1.31, 0.17, MoveKey.JUMP);
	public static final SwimFactor V_FACTOR_D_W = new SwimFactor("AlgaeCraft:flippers_vertical_d_w", 1.31, 0.17, MoveKey.SNEAK);
	
	private String armorImg;

	public ItemFlippers(ItemArmor.ArmorMaterial par2EnumArmorMaterial, int par3, String armorName) {
		super(par2EnumArmorMaterial, par3, 3);
		armorImg = armorName;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
		if(player.capabilities.isCreativeMode && player.capabilities.isFlying){
			return;
		}
		/*if(player.isInWater()){
			player.motionX *= 1.08D;
			player.motionZ *= 1.08D;
			if(player.motionY >0){
				player.motionY *= 1.12F;
			}
		}*/
		if(!player.isInWater()){
			player.motionX *= 0.649F;
			player.motionZ *= 0.649F;
		}
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
		//if(armorSlot == 2){
		ModelBiped model = new ModelFlippers(0.05F);
		model.isSneak = entityLiving.isSneaking();
        return model;
		//}
		//return null;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer)
    {
        return AlgaeCraft.MODID+":textures/armor/"+armorImg+".png";
    }
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		if(ACItems.itemFlippers == par1ItemStack.getItem()){
			return (ACItems.itemRubberBall.getItem() == par2ItemStack.getItem() && ACItems.itemRubberBall.getItemDamage() == par2ItemStack.getItemDamage()) || super.getIsRepairable(par1ItemStack,par2ItemStack) ;
		}
		else{
			return super.getIsRepairable(par1ItemStack, par2ItemStack);
		}
	}

	@Override
	public ArrayList<SwimFactor> getValidFactorsForItem(EntityPlayer player, ItemStack stack, int slot) {

		ArrayList<SwimFactor> factors = new ArrayList<SwimFactor>();
		ItemStack boots = player.inventory.armorItemInSlot(0);
		if(slot == player.inventory.mainInventory.length + 0){
			ItemStack legs = player.inventory.armorItemInSlot(1);
			if(legs != null && legs.getItem() != null && legs.getItem() instanceof ItemWetsuit){
				factors.add(H_FACTOR_F_W);
				factors.add(H_FACTOR_B_W);
				factors.add(H_FACTOR_L_W);
				factors.add(H_FACTOR_R_W);
				factors.add(V_FACTOR_U_W);
				factors.add(V_FACTOR_D_W);
			} else {
				factors.add(H_FACTOR_F);
				factors.add(H_FACTOR_B);
				factors.add(H_FACTOR_L);
				factors.add(H_FACTOR_R);
				factors.add(V_FACTOR_U);
				factors.add(V_FACTOR_D);
			}
		}
		
		return factors;
	}

	@Override
	public void onPlayerSwimTick(EntityPlayer player, ItemStack stack, int slot) {
		/*stack.damageItem(1, player);
		if(stack.getItemDamage() >= stack.getMaxDamage()){
			stack.stackSize--;
			player.inventory.setInventorySlotContents(slot, null);
			//player.inventory.inventoryChanged = true;
		}
		System.out.println("Flippers in use: "+stack.getItemDamage()+"/"+stack.getMaxDamage()+" : "+stack.getItemDamage()%stack.getMaxDamage());*/
	}

}
package azaka7.algaecraft.common.items;

import javax.swing.Icon;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.client.model.ModelFlippers;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFlippers extends ItemArmor {
	
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
		if(player.isInWater()){
			player.motionX *= 1.08D;
			player.motionZ *= 1.08D;
			if(player.motionY >0){
				player.motionY *= 1.12F;
			}
		}
		else{
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

}
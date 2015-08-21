package azaka7.algaecraft.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemAerosBulb extends Item {
	
	public ItemAerosBulb(){
		super();
		this.hasSubtypes = true;
	}
	
	/*public String getPotionEffect(ItemStack stack)
    {
        return stack.getItemDamage() > 0 ? ACPotions.aerosEffect : "";
    }*/
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par1ItemStack.getItemDamage() != 1){
			if(par3EntityPlayer.getAir() < 300 && !par2World.isRemote){
				if(par3EntityPlayer.getAir() + 120 <= 300){
					par3EntityPlayer.setAir(par3EntityPlayer.getAir() + 120);
				}
				else{
					par3EntityPlayer.setAir(300);
				}
				
				if(!par3EntityPlayer.capabilities.isCreativeMode){
					par1ItemStack.stackSize--;
				}
			}
		}
		else{
			if(!par2World.isRemote){
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20*10, 0, true));
				par3EntityPlayer.setAir(300);
				
				if(!par3EntityPlayer.capabilities.isCreativeMode){
					par1ItemStack.stackSize--;
				}
			}
		}
        return par1ItemStack;
    }
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		return this.getUnlocalizedName()+(par1ItemStack.getItemDamage()==0 ? ".simple" : ".ancient");
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
    }
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
		return (pass == 0 && par1ItemStack.getItemDamage() != 0);
    }
	
	public boolean hasColor(ItemStack par1ItemStack)
    {
        return true;
    }
	
    public int getColor(ItemStack par1ItemStack)
    {
    	return par1ItemStack.getItemDamage() == 1 ? 0x880088 : -1;
    }
	
}

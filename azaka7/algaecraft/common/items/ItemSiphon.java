package azaka7.algaecraft.common.items;

import azaka7.algaecraft.AlgaeCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSiphon extends ItemBow{
	
	public IIcon itemIconFull;
	public ItemSiphon(){
        this.maxStackSize = 1;
        this.setMaxDamage(30 * 64);
        this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int duration)
    {
		if(stack.stackTagCompound == null){ stack.stackTagCompound = new NBTTagCompound(); }
		
		if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0){
			stack.stackTagCompound.setInteger("fuel", stack.stackTagCompound.getInteger("fuel") - 1);
		}
		
    }
	
	/**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
    	if(stack.stackTagCompound == null){ stack.stackTagCompound = new NBTTagCompound(); }
		
    	if(stack.stackTagCompound.getInteger("fuel") > 0){
    		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
    	}
    	return stack;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg)
    {
        this.itemIcon = iconReg.registerIcon(AlgaeCraft.MODID+":siphon_empty");
        this.itemIconFull = iconReg.registerIcon(AlgaeCraft.MODID+":siphon_full");
    }
    
    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
    	if(stack.stackTagCompound == null){
    		stack.stackTagCompound = new NBTTagCompound();
    	}
    	int fuel = stack.stackTagCompound.getInteger("fuel");
    	
    	if(fuel > 0){
    		return itemIconFull;
    	} else {
    		return itemIcon;
    	}
    }
	
}

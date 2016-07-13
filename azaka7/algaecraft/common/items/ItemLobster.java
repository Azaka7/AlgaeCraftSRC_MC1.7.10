package azaka7.algaecraft.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.entity.EntityLobster;
import azaka7.algaecraft.common.blocks.ACBlocks;

public class ItemLobster extends Item {
	
	private IIcon[] itemIcons = new IIcon[2];
	
	public ItemLobster() {
		super();
		this.setMaxStackSize(1);
		this.setCreativeTab(AlgaeCraft.modTab);
		this.setHasSubtypes(true);
	}
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
		if (!par3World.isRemote)
        {
			EntityLobster lob = new EntityLobster(par3World,par1ItemStack.getItemDamage() % 2 == 1);
			lob.setLocationAndAngles(par4+0.5, par5+1, par6+0.5, this.wrapAngleTo180_float(par3World.rand.nextFloat() * 360.0F), 0.0F);
			lob.rotationYawHead = lob.rotationYaw;
			lob.renderYawOffset = lob.rotationYaw;
			par3World.spawnEntityInWorld(lob);
			lob.playLivingSound();
			
			if(!par2EntityPlayer.capabilities.isCreativeMode){
				par1ItemStack.stackSize--;
			}
			return true;
        }
		return false;
    }
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		itemIcons[0] = par1IconRegister.registerIcon(AlgaeCraft.MODID+":lobsterItem");
		itemIcons[1] = par1IconRegister.registerIcon(AlgaeCraft.MODID+":lobsterItemBlue");
	}
	
	@Override
	public IIcon getIconFromDamage(int par1)
    {
		return par1 == 1 ? itemIcons[1] : itemIcons[0];
    }
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2, List par3List)
    {
		par3List.add(new ItemStack(this, 1, 0));
		par3List.add(new ItemStack(this, 1, 1));
    }
	
	public static float wrapAngleTo180_float(float par0)
    {
        par0 %= 360.0F;

        if (par0 >= 180.0F)
        {
            par0 -= 360.0F;
        }

        if (par0 < -180.0F)
        {
            par0 += 360.0F;
        }

        return par0;
    }


}
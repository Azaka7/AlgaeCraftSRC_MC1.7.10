package azaka7.algaecraft.common.items;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.entity.EntityLobster;
import azaka7.algaecraft.common.blocks.ACBlocks;

public class ItemLobster extends Item {
	
	public ItemLobster() {
		super();
		this.setMaxStackSize(1);
		this.setCreativeTab(AlgaeCraft.modTab);
	}
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
		if (!par3World.isRemote)
        {
			EntityLobster lob = new EntityLobster(par3World);
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
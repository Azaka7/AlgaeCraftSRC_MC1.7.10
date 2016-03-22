package azaka7.algaecraft.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.entity.EntityGreekFireBomb;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGreekFireBomb extends Item{
	
	public ItemGreekFireBomb(){
		this.maxStackSize = 4;
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b0)
	{
		list.add(StatCollector.translateToLocal(AlgaeCraft.MODID+".gfb.throw")); //lang: Hold Use and release to throw
	}
	
	/**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.bow;
    }
    
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }
    
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 72000;
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useCount)
    {
    	int j = this.getMaxItemUseDuration(stack) - useCount;
    	float f = (float)j / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        
        if(f < 0.5F){return;}
        if(f > 1.0F){f = 1.0F;}
        
        if (!player.capabilities.isCreativeMode)
        {
            --stack.stackSize;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new EntityGreekFireBomb(world, player));
        }
    }
	
}

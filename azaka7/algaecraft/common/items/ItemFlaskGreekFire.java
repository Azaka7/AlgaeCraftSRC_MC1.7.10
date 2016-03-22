package azaka7.algaecraft.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.blocks.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class ItemFlaskGreekFire extends Item{
	
	private IIcon raw_flame;
	
	public ItemFlaskGreekFire(){
		super();
		this.setMaxStackSize(1); 
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        this.itemIcon = p_94581_1_.registerIcon(this.getIconString());
        this.raw_flame = p_94581_1_.registerIcon(AlgaeCraft.MODID+":greekFireFlame");
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
		if(damage == 1){
			return this.raw_flame;
		}
        return this.itemIcon;
    }
	
	public boolean onItemUse(ItemStack stack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (side == 0)
        {
            --y;
        }

        if (side == 1)
        {
            ++y;
        }

        if (side == 2)
        {
            --z;
        }

        if (side == 3)
        {
            ++z;
        }

        if (side == 4)
        {
            --x;
        }

        if (side == 5)
        {
            ++x;
        }

        if (!entityPlayer.canPlayerEdit(x, y, z, side, stack))
        {
            return false;
        }
        else
        {
            if (world.getBlock(x, y, z).isReplaceable(world, x, y, z))
            {
                world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                world.setBlock(x, y, z, ACBlocks.greekFire);
            }

            if (!entityPlayer.capabilities.isCreativeMode)
            {
                entityPlayer.setCurrentItemOrArmor(0, ACItems.itemStackFlaskEmpty.copy());
            }
            return true;
        }
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer entityPlayer)
    {
        MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, entityPlayer, true);

        if (mop == null)
        {
            return stack;
        }
        else
        {
        	
            if (mop.typeOfHit == MovingObjectType.BLOCK)
            {
                int x = mop.blockX;
                int y = mop.blockY;
                int z = mop.blockZ;

                if (!world.canMineBlock(entityPlayer, x, y, z))
                {
                    return stack;
                }

                if (!entityPlayer.canPlayerEdit(x, y, z, mop.sideHit, stack))
                {
                    return stack;
                }

                if (world.getBlock(x, y, z).getMaterial() == Material.water && world.getBlockMetadata(x, y, z) == 0 && world.isAirBlock(x, y + 1, z)&& ACBlocks.greekFire.canBlockStay(world, x, y+1, z))
                {
                    if(world.getBlock(x, y+1, z)!=Blocks.air){return stack;}
                	
                	world.setBlock(x, y + 1, z, ACBlocks.greekFire, 0, 2);

                    if (!entityPlayer.capabilities.isCreativeMode)
                    {
                        entityPlayer.setCurrentItemOrArmor(0, ACItems.itemStackFlaskEmpty.copy());
                        return stack;
                    }
                    
                }
                if (mop.sideHit == 0)
                {
                    --y;
                }

                if (mop.sideHit == 1)
                {
                    ++y;
                }

                if (mop.sideHit == 2)
                {
                    --z;
                }

                if (mop.sideHit == 3)
                {
                    ++z;
                }

                if (mop.sideHit == 4)
                {
                    --x;
                }

                if (mop.sideHit == 5)
                {
                    ++x;
                }

                if (!entityPlayer.capabilities.allowEdit)
                {
                    return stack;
                }

                if (world.setBlock(x, y, z, ACBlocks.greekFire) && !entityPlayer.capabilities.isCreativeMode)
                {
                	entityPlayer.setCurrentItemOrArmor(0, ACItems.itemStackFlaskEmpty.copy());
                    return stack;
                }
            }

            return stack;
        }
    }
}

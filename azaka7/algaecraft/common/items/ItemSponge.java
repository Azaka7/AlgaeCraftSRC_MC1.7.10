package azaka7.algaecraft.common.items;

import java.util.List;

import azaka7.algaecraft.AlgaeCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemSponge extends Item {
	private IIcon[] spongeImg = new IIcon[3];
	private String baseName;
	
	private boolean isWet;
	
	public ItemSponge(String name) {
		super();
		this.setHasSubtypes(true);
        this.setMaxStackSize(16);
        baseName=name;
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack)
    {
        return stack.getItemDamage() > 0;
    }
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
    {
		return new ItemStack(this, 1, 0);
    }
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		String metaName = "dry";
		if(par1ItemStack.getItemDamage()>0){metaName = "wet";}
		return this.getUnlocalizedName() + "." + metaName;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
		spongeImg[0] = par1IconRegister.registerIcon(AlgaeCraft.MODID+":sponge"+this.baseName+"Dry");
		spongeImg[1] = par1IconRegister.registerIcon(AlgaeCraft.MODID+":sponge"+this.baseName+"Wet");
		this.itemIcon = spongeImg[0];
    }
	
	@Override
	public IIcon getIconFromDamage(int par1)
    {
		if(par1 == 0){
		return spongeImg[0];
		}
		return spongeImg[1];
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        float var4 = 1.0F;
        double var5 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)var4;
        double var7 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)var4 + 1.62D - (double)par3EntityPlayer.yOffset;
        double var9 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)var4;
        this.isWet = (par1ItemStack.getItemDamage() == 1);
        boolean isNotWet = !this.isWet;
        MovingObjectPosition var12 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, isNotWet);

        if (var12 == null)
        {
            return par1ItemStack;
        }
        else
        {
            if (var12.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int var13 = var12.blockX;
                int var14 = var12.blockY;
                int var15 = var12.blockZ;

                if (!par2World.canMineBlock(par3EntityPlayer, var13, var14, var15))
                {
                    return par1ItemStack;
                }

                if (isNotWet)
                {
                    if (!par3EntityPlayer.canPlayerEdit((int)(Math.round(var5)), (int)Math.round(var7), (int)Math.round(var9), var12.sideHit, par1ItemStack))
                    {
                        return par1ItemStack;
                    }

                    if (par2World.getBlock(var13, var14, var15).getMaterial() == Material.water && par2World.getBlockMetadata(var13, var14, var15) == 0)
                    {
                        par2World.setBlock(var13, var14, var15, Blocks.air, 0, 3);

                        if (par3EntityPlayer.capabilities.isCreativeMode)
                        {
                            return par1ItemStack;
                        }

                        if (--par1ItemStack.stackSize <= 0)
                        {
                            //return new ItemStack(this, 1, 1);
                        }

                        if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(this,1,1)))
                        {
                            par3EntityPlayer.entityDropItem(new ItemStack(this, 1, 1), 0F);
                        }

                        return par1ItemStack;
                    }
                }
                else
                {
                    if (var12.sideHit == 0)
                    {
                        --var14;
                    }

                    if (var12.sideHit == 1)
                    {
                        ++var14;
                    }

                    if (var12.sideHit == 2)
                    {
                        --var15;
                    }

                    if (var12.sideHit == 3)
                    {
                        ++var15;
                    }

                    if (var12.sideHit == 4)
                    {
                        --var13;
                    }

                    if (var12.sideHit == 5)
                    {
                        ++var13;
                    }

                    if (!par3EntityPlayer.capabilities.allowEdit)
                    {
                        return par1ItemStack;
                    }

                    if (this.cauldronInteraction(par1ItemStack, par2World, par3EntityPlayer)
                    		|| this.func_77875_a(par2World, var5, var7, var9, var13, var14, var15) && !par3EntityPlayer.capabilities.isCreativeMode)
                    {
                    	if (--par1ItemStack.stackSize <= 0)
                        {
                            //return new ItemStack(this, 1, 1);
                        }

                        if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(this,1,0)))
                        {
                            par3EntityPlayer.entityDropItem(new ItemStack(this, 1, 0),0F);
                        }
                    }
                }
            }

            return par1ItemStack;
        }
    }
	
	public boolean cauldronInteraction(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		MovingObjectPosition var12 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, !this.isWet);
		int x = var12.blockX;
        int y = var12.blockY;
        int z = var12.blockZ;
        
        if(par2World.getBlock(x, y, z)==Blocks.cauldron && par2World.getBlockMetadata(x, y, z) < 3){
        	par2World.setBlockMetadataWithNotify(x, y, z, 3, 2);
        	return true;
        }
        return false;
	}
	
	public boolean func_77875_a(World par1World, double par2, double par4, double par6, int par8, int par9, int par10)
    {
        if (!this.isWet)
        {
            return false;
        }
        else if(par1World.getBlock(par8, par9, par10).isReplaceable(par1World, par8, par9, par10));
        {
            if (par1World.provider.isHellWorld && this.isWet)
            {
                par1World.playSoundEffect(par2 + 0.5D, par4 + 0.5D, par6 + 0.5D, "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);

                for (int var11 = 0; var11 < 8; ++var11)
                {
                    par1World.spawnParticle("largesmoke", (double)par8 + Math.random(), (double)par9 + Math.random(), (double)par10 + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            }
            else
            {
                par1World.setBlock(par8, par9, par10, Blocks.flowing_water, 0, 3);
            }

            return true;
        }
    }
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(this, 1, 0));
        par3List.add(new ItemStack(this, 1, 1));
    }
}

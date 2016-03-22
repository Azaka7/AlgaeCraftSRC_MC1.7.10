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
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer entityPlayer)
    {
        float var4 = 1.0F; 
        double x = entityPlayer.prevPosX + (entityPlayer.posX - entityPlayer.prevPosX) * (double)var4;
        double y = entityPlayer.prevPosY + (entityPlayer.posY - entityPlayer.prevPosY) * (double)var4 + 1.62D - (double)entityPlayer.yOffset;
        double z = entityPlayer.prevPosZ + (entityPlayer.posZ - entityPlayer.prevPosZ) * (double)var4;
        this.isWet = (stack.getItemDamage() == 1);
        boolean isNotWet = !this.isWet;
        MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, entityPlayer, isNotWet);

        if (mop == null)
        {
            return stack;
        }
        else
        {
            if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int x1 = mop.blockX;
                int y1 = mop.blockY;
                int z1 = mop.blockZ;

                if (!world.canMineBlock(entityPlayer, x1, y1, z1))
                {
                    return stack;
                }

                if (isNotWet)
                {
                    if (!entityPlayer.canPlayerEdit((int)(Math.round(x)), (int)Math.round(y), (int)Math.round(z), mop.sideHit, stack))
                    {
                        return stack;
                    }

                    if (world.getBlock(x1, y1, z1).getMaterial() == Material.water && world.getBlockMetadata(x1, y1, z1) == 0)
                    {
                        world.setBlock(x1, y1, z1, Blocks.air, 0, 3);

                        if (entityPlayer.capabilities.isCreativeMode)
                        {
                            return stack;
                        }

                        if (--stack.stackSize <= 0)
                        {
                            //return new ItemStack(this, 1, 1);
                        }

                        if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(this,1,1)))
                        {
                            entityPlayer.entityDropItem(new ItemStack(this, 1, 1), 0F);
                        }

                        return stack;
                    }
                }
                else
                {
                    if (mop.sideHit == 0)
                    {
                        --y1;
                    }

                    if (mop.sideHit == 1)
                    {
                        ++y1;
                    }

                    if (mop.sideHit == 2)
                    {
                        --z1;
                    }

                    if (mop.sideHit == 3)
                    {
                        ++z1;
                    }

                    if (mop.sideHit == 4)
                    {
                        --x1;
                    }

                    if (mop.sideHit == 5)
                    {
                        ++x1;
                    }

                    if (!entityPlayer.capabilities.allowEdit)
                    {
                        return stack;
                    }

                    if (this.cauldronInteraction(stack, world, entityPlayer)
                    		|| this.func_77875_a(world, x, y, z, x1, y1, z1) && !entityPlayer.capabilities.isCreativeMode)
                    {
                    	if (--stack.stackSize <= 0)
                        {
                            //return new ItemStack(this, 1, 1);
                        }

                        if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(this,1,0)))
                        {
                            entityPlayer.entityDropItem(new ItemStack(this, 1, 0),0F);
                        }
                    }
                }
            }

            return stack;
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

package azaka7.algaecraft.common.items;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import azaka7.algaecraft.AlgaeCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class ItemBlockItem extends ItemBlock {
	
	public final boolean placeOnWater;
	public final ArrayList<Block> canPlaceIn;
	
	public ItemBlockItem(Block block, Boolean floats, ArrayList<Block> replaces, String img) {
		super(block);
		placeOnWater = floats.booleanValue();
		canPlaceIn = replaces;
		this.setTextureName(AlgaeCraft.MODID+":"+img);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        String s = this.getIconString();

        if (s != null)
        {
            this.itemIcon = par1IconRegister.registerIcon(s);
        }
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int par1)
	{
		return this.itemIcon;
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
		if(!this.field_150939_a.canBlockStay(world, x, y, z) || !canPlaceIn.contains(world.getBlock(x, y, z))){return false;}
		return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(!placeOnWater){return par1ItemStack;}
		
        MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);

        if (var4 == null)
        {
            return par1ItemStack;
        }
        else
        {
            if (var4.typeOfHit == MovingObjectType.BLOCK)
            {
                int var5 = var4.blockX;
                int var6 = var4.blockY;
                int var7 = var4.blockZ;

                if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7))
                {
                    return par1ItemStack;
                }

                if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var4.sideHit, par1ItemStack))
                {
                    return par1ItemStack;
                }

                if (par2World.getBlock(var5, var6, var7).getMaterial() == Material.water && par2World.getBlockMetadata(var5, var6, var7) == 0 && par2World.isAirBlock(var5, var6 + 1, var7)&& this.field_150939_a.canBlockStay(par2World, var5, var6+1, var7))
                {
                    if(par2World.getBlock(var5, var6+1, var7)!=Blocks.air){return par1ItemStack;}
                	
                	par2World.setBlock(var5, var6 + 1, var7, this.field_150939_a, 0, 2);

                    if (!par3EntityPlayer.capabilities.isCreativeMode)
                    {
                        --par1ItemStack.stackSize;
                    }
                }
            }

            return par1ItemStack;
        }
    }

}

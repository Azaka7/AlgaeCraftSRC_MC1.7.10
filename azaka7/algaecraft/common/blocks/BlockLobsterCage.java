package azaka7.algaecraft.common.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.tileentity.TileEntityCage;
import azaka7.algaecraft.common.entity.EntityLobster;
import azaka7.algaecraft.common.items.ACItems;
import azaka7.algaecraft.common.items.ItemLobster;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLobsterCage extends BlockContainer {
	
	public BlockLobsterCage() {
		super(Material.water);
	}
	
	@Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            ItemStack itemstack = par5EntityPlayer.inventory.getCurrentItem();

            if (itemstack == null || (itemstack !=null && itemstack.getItem() == null))
            {
                int i1 = par1World.getBlockMetadata(par2, par3, par4);
                    if (i1 == 1 || i1 == 2)
                    {
                    	ItemStack itemstack1 = new ItemStack(ACItems.itemLobster, 1, i1 - 1);

                    	if (!par5EntityPlayer.inventory.addItemStackToInventory(itemstack1))
                    	{
                    		par1World.spawnEntityInWorld(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 1.5D, (double)par4 + 0.5D, itemstack1));
                    	}
                    	else if (par5EntityPlayer instanceof EntityPlayerMP)
                    	{
                    		((EntityPlayerMP)par5EntityPlayer).sendContainerToPlayer(par5EntityPlayer.inventoryContainer);
                    	}
                    
                    	par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);
                    }

                    return true;
            }
        }
		return true;
    }
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public int getRenderType()
    {
        return 0;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
		return false;
    }
	
	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
	public int getRenderBlockPass()
    {
        return 0;
    }
	
	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta){
		return true;
	}
	
	@Override
	public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer) {
		if(par5 == 1){
			EntityLobster lob = new EntityLobster(par1World);
			lob.setLocationAndAngles(par2+0.5, par3+1, par4+0.5, ItemLobster.wrapAngleTo180_float(par1World.rand.nextFloat() * 360.0F), 0.0F);
			lob.rotationYawHead = lob.rotationYaw;
			lob.renderYawOffset = lob.rotationYaw;
			par1World.spawnEntityInWorld(lob);
			lob.playLivingSound();
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		TileEntityCage tile = new TileEntityCage();
		tile.setWorld(var1);
		return tile;
	}
	
	@Override
	public MapColor getMapColor(int p_149728_1_)
    {
        return MapColor.woodColor;
    }
	
}

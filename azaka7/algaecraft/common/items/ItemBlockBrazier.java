package azaka7.algaecraft.common.items;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.blocks.ACBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemBlockBrazier extends ItemBlock{

	public ItemBlockBrazier(Block p_i45328_1_) {
		super(p_i45328_1_);
		this.setTextureName(AlgaeCraft.MODID+":brazier");
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
		if(!this.field_150939_a.canBlockStay(world, x, y, z) || !world.getBlock(x, y, z).isReplaceable(world, x, y, z) || !world.getBlock(x, y, z).isReplaceable(world, x, y+1, z)){return false;}
		boolean ret = false;
		if(world.getBlock(x, y, z).getMaterial() == Material.water){
			ret |= this.placeBlock(stack, player, world, x, y, z, side, hitX, hitY, hitZ, ACBlocks.brazier_wet, 8);
		} else {
			ret |= this.placeBlock(stack, player, world, x, y, z, side, hitX, hitY, hitZ, ACBlocks.brazier, 8);
		}
		
		if(ret && world.getBlock(x, y+1, z).getMaterial() == Material.water){
			ret |= this.placeBlock(stack, player, world, x, y+1, z, side, hitX, hitY, hitZ, ACBlocks.brazier_wet, 9);
		} else if(ret){
			ret |= this.placeBlock(stack, player, world, x, y+1, z, side, hitX, hitY, hitZ, ACBlocks.brazier, 9);
		}
		return ret;
    }
	
	private boolean placeBlock(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, Block block, int metadata)
    {
       if (!world.setBlock(x, y, z, block, metadata, 3))
       {
           return false;
       }

       if (world.getBlock(x, y, z) == field_150939_a)
       {
           block.onBlockPlacedBy(world, x, y, z, player, stack);
           block.onPostBlockPlaced(world, x, y, z, metadata);
       }

       return true;
    }
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entity){
		if(this.field_150939_a == ACBlocks.brazier_wet){
			ItemStack stack = new ItemStack(ACBlocks.brazier, 1, entity.getEntityItem().stackSize);
			stack.setTagCompound(entity.getEntityItem().getTagCompound());
			entity.setEntityItemStack(stack);
		}
		return false;
	}

}

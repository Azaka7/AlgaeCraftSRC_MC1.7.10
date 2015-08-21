package azaka7.algaecraft.common.blocks;

import java.util.List;
import java.util.Random;

import azaka7.algaecraft.AlgaeCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
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

public class BlockSlabTWood extends BlockSlab {
	
	@SideOnly(Side.CLIENT)
    private IIcon field_150007_M;
    
    private boolean full;

    public BlockSlabTWood(boolean p_i45431_1_)
    {
        super(p_i45431_1_, Material.wood);
        full = p_i45431_1_;
        this.useNeighborBrightness = true;
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
    	
    	if(p_149691_2_ > 7){p_149691_2_ = p_149691_2_-8;}
    	if(p_149691_2_ > 3){p_149691_2_ = 0;}
    	
    	return ACBlocks.blockWoodTreated.getIcon(p_149691_1_, p_149691_2_);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(this);
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int p_149644_1_)
    {
        return new ItemStack(Item.getItemFromBlock(this), 2, p_149644_1_ & 7);
    }

    public String func_150002_b(int p_150002_1_)
    {
        return super.getUnlocalizedName() + ".treatedWood";
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));       
    }
    
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
    {
        return new ItemStack(ACBlocks.blockTWoodSlab, 1, world.getBlockMetadata(x, y, z)%4);
    }

}
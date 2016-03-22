package azaka7.algaecraft.common.blocks;

import java.util.Random;

import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.tileentity.TileEntityBrazier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBrazier extends BlockContainer{
	
	public final boolean inWater;
	
	public BlockBrazier(boolean water) {
		super(water ? Material.water : Material.iron);
		inWater = water;
		this.setLightLevel(1.0F);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
    {
		if(world.getBlockMetadata(x, y, z) % 2 == 0){
			return World.doesBlockHaveSolidTopSurface(world, x, y-1, z);
		} else {
			return (world.getBlock(x, y-1, z) instanceof BlockBrazier);
		}
    }
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
		if(!this.canBlockStay(world, x, y, z)){
			if(this.inWater){
				world.setBlock(x, y, z, Blocks.flowing_water, 7, 2);
			} else {
				world.setBlock(x, y, z, Blocks.air);
			}
			//this.dropBlockAsItem(world, x, y, z, new ItemStack(this));
			return;
		}
    }
	
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
		int meta = world.getBlockMetadata(x, y, z);
		if(this.inWater){
			world.setBlock(x, y, z, Blocks.flowing_water, 7, 3);
		} else {
	        world.setBlockToAir(x, y, z);
		}
		if(meta % 2 == 0){
			Block above = world.getBlock(x, y+1, z);
			if(above instanceof BlockBrazier && world.getBlockMetadata(x, y+1, z) % 2 == 1){
				world.setBlock(x, y+1, z, ((BlockBrazier) above).inWater ? Blocks.flowing_water : Blocks.air, 7, 3);
			}
		} else {
			Block below = world.getBlock(x, y-1, z);
			if(below instanceof BlockBrazier && world.getBlockMetadata(x, y-1, z) % 2 == 0){
				world.setBlock(x, y-1, z, ((BlockBrazier) below).inWater ? Blocks.flowing_water : Blocks.air, 7, 3);
			}
		}
		if(!player.capabilities.isCreativeMode)
		{
			this.dropBlockAsItem(world, x, y, z, new ItemStack(this));
		}
		
		return true;
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
		super.onNeighborBlockChange(world, x, y, z, block);
		int where = world.getBlockMetadata(x, y, z) % 2;
		
		if(where == 0){
			if(!(world.getBlock(x, y+1, z) instanceof BlockBrazier)){
				if(this.inWater){
					world.setBlock(x, y, z, Blocks.flowing_water, 7, 2);
				} else {
					world.setBlock(x, y, z, Blocks.air);
				}
				//this.dropBlockAsItem(world, x, y, z, new ItemStack(this));
				return;
			}
		} else {
			if(!(world.getBlock(x, y-1, z) instanceof BlockBrazier)){
				if(this.inWater){
					world.setBlock(x, y, z, Blocks.flowing_water, 7, 2);
				} else {
					world.setBlock(x, y, z, Blocks.air);
				}
				//this.dropBlockAsItem(world, x, y, z, new ItemStack(this));
				return;
			}
		}
		
		if(!this.canBlockStay(world, x, y, z)){
			if(this.inWater){
				world.setBlock(x, y, z, Blocks.flowing_water, 7, 2);
			} else {
				world.setBlock(x, y, z, Blocks.air);
			}
			this.dropBlockAsItem(world, x, y, z, new ItemStack(this));
			return;
		}
		
		int count=0, fullCount=0;
		if(world.getBlock(x, y+1, z).getMaterial() == Material.water){
			world.setBlock(x, y, z, ACBlocks.brazier_wet, 8 + where, 3);
			return;
		}
		if(world.getBlock(x+1, y, z).getMaterial() == Material.water){
			int meta = world.getBlockMetadata(x+1, y, z);
			if(meta != 6 && meta != 7)
				count += 1;
			if(meta == 0 || meta >= 8){
				fullCount += 1;
			}
		}
		if(world.getBlock(x-1, y, z).getMaterial() == Material.water){
			int meta = world.getBlockMetadata(x-1, y, z);
			if(meta != 6 && meta != 7)
				count += 1;
			if(meta == 0 || meta >= 8){
				fullCount += 1;
			}
		}
		if(world.getBlock(x, y, z+1).getMaterial() == Material.water){
			int meta = world.getBlockMetadata(x, y, z+1);
			if(meta != 6 && meta != 7)
				count += 1;
			if(meta == 0 || meta >= 8){
				fullCount += 1;
			}
		}
		if(world.getBlock(x, y, z-1).getMaterial() == Material.water){
			int meta = world.getBlockMetadata(x, y, z-1);
			if(meta != 6 && meta != 7)
				count += 1;
			if(meta == 0 || meta >= 8){
				fullCount += 1;
			}
		}
		
		if(fullCount >= 2){
			world.setBlock(x, y, z, ACBlocks.brazier_wet, 8 + where, 3);
		} else if(count > 0){
			world.setBlock(x, y, z, ACBlocks.brazier_wet, 6 + where, 3);
		} else {
			world.setBlock(x, y, z, ACBlocks.brazier, 0 + where, 3);
		}
		
    }
	
	@Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public int getRenderType()
    {
        return ACGameData.brazierModelID;
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
        return 1;
    }
	
	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta){
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBrazier();
	}
	
	/**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
    	int m = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

		float p = 1.0F/16.0F;
		
    	if (m%2 == 0){
    		this.setBlockBounds(5*p, 0*p, 5*p, 1 - 5*p, 32*p, 1 - 5*p);
    	}
    	else{
    		this.setBlockBounds(5*p, -16*p, 5*p, 1 - 5*p, 16*p, 1 - 5*p);
    	}
    }

    /**
     * Gets the light value of the specified block coords. Args: x, y, z
     */
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block != this)
        {
            return block.getLightValue(world, x, y, z);
        }
        
        return (world.getBlockMetadata(x, y, z) % 2 == 0) ? 0 : (this.inWater ? 15 : 7);
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
    	super.randomDisplayTick(world, x, y, z, rand);
    	if(world.getBlockMetadata(x, y, z) % 2 == 0){return;}
    	
    	int max = rand.nextInt(2)+1;
    	for(int i = 0; i < max; i++){
    		world.spawnParticle("happyVillager", x + 0.6 + calcShift(0.1,rand), y + 0.75 + calcShift(0.1,rand), z  + 0.6 + calcShift(0.1,rand), 0.0D, 0.0D, 0.0D);
    	}
    	max = rand.nextInt(3)+1;
    	for(int i = 0; i < max; i++){
    		world.spawnParticle("smoke",  x + 0.5 + calcShift(0.1,rand), y + 0.75 + calcShift(0.1,rand), z  + 0.5 + calcShift(0.1,rand), 0.0D, 0.0D, 0.0D);
    	}
    	
    }
    
    private double calcShift(double size, Random rand){
    	return rand.nextDouble()*size - rand.nextDouble()*size;
    }
    
	
}

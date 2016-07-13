package azaka7.algaecraft.common.blocks;

import java.util.Random;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.tileentity.TileEntityWaterFilter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockSeaweed extends Block {
	
	public Material[] canPlantOn = new Material[]{Material.clay, Material.grass, Material.ground, Material.rock, Material.sand};
	
	protected BlockSeaweed() {
		super(Material.water);
		this.setStepSound(soundTypeGrass);
        this.setTickRandomly(true);
	}
	
	public boolean isBlockReplaceable(World world, int x, int y, int z)
    {
    	return false;
    }
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
    {
		Block blockBelow = world.getBlock(x, y-1, z);
		//boolean belowIsHard = (blockIDBelow == Block.cobblestone.blockID || blockIDBelow == Block.stone.blockID || blockIDBelow == Block.cobblestoneMossy.blockID || blockIDBelow == Block.gravel.blockID || blockIDBelow == Block.sandStone.blockID);
		boolean aboveIsWater = (world.getBlock(x, y+1, z).getMaterial() == Material.water);
		boolean belowIsSeaweed = (world.getBlock(x, y-1, z)==this);
		//boolean aboveIsSeaweed = (world.getBlock(x, y+1, z)==this);

        boolean belowIsHard = false;
        for(int i = 0; i < canPlantOn.length; i++){
        	if(blockBelow.getMaterial() == canPlantOn[i]){
        		belowIsHard = true;
        		break;
        	}
        }
        if(!((belowIsHard||belowIsSeaweed) && aboveIsWater)){return false;}
        if(belowIsSeaweed){return true;}
		
		boolean biome = false;
        for(int i = 0; i < ACGameData.biomeIDOceanList.length; i++){
        	if(world.getBiomeGenForCoords(x, z).biomeID == ACGameData.biomeIDOceanList[i]){
        		biome = true;
        		break;
        	}
        }
        boolean filter = false;
        boolean badfilter = false;
        for(int x1 = -6; x1 <= 6; x1++){
        	for(int y1 = -6; y1 <= 6; y1++){
        		for(int z1 = -6; z1 <= 6; z1++){
        			if(world.getTileEntity(x+x1, y+y1, z+z1) instanceof TileEntityWaterFilter){
        				if(!biome && BlockWaterFilter.isLocationValid(world, x, y, z, BlockWaterFilter.EnumWaterType.OCEAN, x+x1, y+y1, z+z1)){
                    		return true;
                    	}
                    	else if(biome && BlockWaterFilter.isLocationInvalid(world, x, y, z, BlockWaterFilter.EnumWaterType.OCEAN, x+x1, y+y1, z+z1)){
                    		return false;
                    	}
        			}
                }
            }
        }
        return biome;
    }
	
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return this.canBlockStay(par1World, par2, par3, par4);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
		if(!this.canBlockStay(world, x, y, z)){
			this.dropBlockAsItem(world, x, y, z, new ItemStack(this));
			world.setBlock(x, y, z, Blocks.water, 0, 3);
		}
		if(world.getBlock(x+1, y, z) == Blocks.air){
        	world.setBlock(x+1, y, z, Blocks.flowing_water, 1, 3);
        }
        if(world.getBlock(x-1, y, z) == Blocks.air){
        	world.setBlock(x-1, y, z, Blocks.flowing_water, 1, 3);
        }
        if(world.getBlock(x, y, z+1) == Blocks.air){
        	world.setBlock(x, y, z+1, Blocks.flowing_water, 1, 3);
        }
        if(world.getBlock(x, y, z-1) == Blocks.air){
        	world.setBlock(x, y, z-1, Blocks.flowing_water, 1, 3);
        }
    }
	
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random rand)
    {
        if (!par1World.isRemote)
        {
        	if(!this.canBlockStay(par1World, par2, par3, par4)){
        		this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(this));
    			par1World.setBlock(par2, par3, par4, Blocks.water, 0, 3);
    		}
        	if(par1World.getBlock(par2+1, par3, par4) == Blocks.air){
            	par1World.setBlock(par2+1, par3, par4, Blocks.flowing_water, 1, 3);
            }
            if(par1World.getBlock(par2-1, par3, par4) == Blocks.air){
            	par1World.setBlock(par2-1, par3, par4, Blocks.flowing_water, 1, 3);
            }
            if(par1World.getBlock(par2, par3, par4+1) == Blocks.air){
            	par1World.setBlock(par2, par3, par4+1, Blocks.flowing_water, 1, 3);
            }
            if(par1World.getBlock(par2, par3, par4-1) == Blocks.air){
            	par1World.setBlock(par2, par3, par4-1, Blocks.flowing_water, 1, 3);
            }
        	if(rand.nextDouble() < ACGameData.seaweedGrowthChance){
        		if(par1World.getBlock(par2, par3+1, par4).isReplaceable(par1World, par2, par3, par4) && this.canPlaceBlockAt(par1World, par2, par3+1, par4)){
        			par1World.setBlock(par2, par3+1, par4, this, 0, 3);
        		}
        	}
        }
    }
	
	@Override
	public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer) {
		if (!par1World.isRemote){
			if(!par6EntityPlayer.capabilities.isCreativeMode){
			super.dropBlockAsItem(par1World, par2, par3, par4, par5, 1);}
		}
	}
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block block, int par6){
		par1World.setBlock(par2, par3, par4, Blocks.water, 0, 3);
	}
	
	@Override
	public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
	public boolean isOpaqueCube()
    {
        return false;
    }
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	public int getRenderType()
    {
        //return 6;
		return ACGameData.seaweedModelID;
    }
	
	public int getRenderBlockPass()
    {
        return 1;
    }

}

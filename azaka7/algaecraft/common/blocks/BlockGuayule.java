package azaka7.algaecraft.common.blocks;

import java.util.List;
import java.util.Random;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.items.ACItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGuayule extends BlockBush implements IGrowable{
	
	private static IIcon iconSmall;
	private static IIcon iconMid;
	private static IIcon iconFull;

	protected BlockGuayule() {
		super(Material.vine);
        float f = 0.4F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
	}
	
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
		//if(player.inventory.getCurrentItem()==null){
			int meta = world.getBlockMetadata(x, y, z);
			
			if(meta >= 3){
				world.setBlockMetadataWithNotify(x, y, z, 0, 3);
				ItemStack ret = ACItems.itemGuayuleBranch.copy();
				ret.stackSize = world.rand.nextInt(2)+1;
				this.dropBlockAsItem(world, x, y, z, ret);
				if(world.rand.nextInt(12)==0){
					this.dropBlockAsItem(world, x, y, z, new ItemStack(this));
				}
				return true;
			}
			return false;
		//}
		/*if(player.inventory.getCurrentItem().getItem() == Items.dye && player.inventory.getCurrentItem().getItemDamage() == 15){
			Random rand = new Random();
			if(this.tryToGrow(world, world.getBlockMetadata(x, y, z), x, y, z, rand)
			&&!player.capabilities.isCreativeMode){
				player.inventory.getCurrentItem().stackSize--;
			}
		}
		return true;*/
    }
	
	@Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	if(!this.canBlockStay(world, x, y, z)){
    		this.dropBlockAsItem(world, x, y, z, new ItemStack(this,1,world.getBlockMetadata(x, y, z)));
    		world.setBlockToAir(x, y, z);
    	}
    	else if(world.getBlock(x, y-1, z) == Blocks.air){
    		this.dropBlockAsItem(world, x, y, z, new ItemStack(this,1,world.getBlockMetadata(x, y, z)));
    		world.setBlockToAir(x, y, z);
    	}
    	else if(rand.nextDouble() < ACGameData.guayuleGrowthChance){
    		int metadata = world.getBlockMetadata(x, y, z);
    		this.tryToGrow(world,metadata,x,y,z, rand);
    	}
        super.updateTick(world, x, y, z, rand);
    }
    
    private boolean tryToGrow(World world, int meta, int x, int y, int z, Random rand){
    	if(this.canBlockStay(world, x, y, z) && meta <= 2 && meta >= 0){
    		world.setBlock(x, y, z, this, meta+1, 2);
    		return true;
    	}
    	else if(this.canBlockStay(world, x, y, z) && meta == 3){
    		if(world.rand.nextInt(20) > 0){
    			world.setBlock(x, y, z, this, 4, 2);
    		}
    		else{
    			world.setBlock(x, y, z, Blocks.deadbush, 0, 2);
    		}
    		return true;
    	}
    	return false;
    }
    
    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        Block below = world.getBlock(x, y-1, z);
        boolean block = canPlaceBlockOn(below);
        return block;
    }
    
    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        if(super.canPlaceBlockAt(par1World, par2, par3, par4)){
        	return this.canBlockStay(par1World, par2, par3, par4);
        }
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	iconSmall = par1IconRegister.registerIcon(AlgaeCraft.MODID+":guayuleSmall");
    	iconMid = par1IconRegister.registerIcon(AlgaeCraft.MODID+":guayuleMid");
    	iconFull = par1IconRegister.registerIcon(AlgaeCraft.MODID+":guayuleFull");
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return meta > 2 ? iconFull : (meta > 0 ? iconMid : iconSmall);
    }
    
    @Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float f1, int t)
    {
    	this.dropBlockAsItem(world, x, y, z, new ItemStack(this));
    	if(meta > 0){
    		if(world.rand.nextInt(12)==0){
    	    	this.dropBlockAsItem(world, x, y, z, new ItemStack(this));
    		}
    		if(meta >=3){
    			ItemStack ret = ACItems.itemGuayuleBranch.copy();
    			ret.stackSize = 1+world.rand.nextInt(2);
    	    	this.dropBlockAsItem(world, x, y, z, ret);
    		}
    	}
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int par1)
    {
        return 0;
    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
    	return 1;
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
    	if(!this.canBlockStay(world, x, y, z)){
    		this.dropBlockAsItem(world, x, y, z, new ItemStack(this,world.rand.nextInt(3) == 0 ? 2 : 1,0));
    		world.setBlockToAir(x, y, z);
    	}
    }
    
    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }
	
	protected boolean canPlaceBlockOn(Block block)
    {
        return block == Blocks.sand || block == Blocks.hardened_clay || block == Blocks.stained_hardened_clay;
    }

	//Should consume bonemeal
	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean isRemote) {
		int meta = world.getBlockMetadata(x, y, z);
		return (meta%8 <= 3 && meta >= 0);
	}
	
	//Should grow
	@Override
	public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
		return true;
	}
		
	//Do growing
	@Override
	public void func_149853_b(World world, Random rand, int x, int y, int z) {
		
		int meta = world.getBlockMetadata(x, y, z);
		if(meta <= 2 && meta >= 0){
    		world.setBlock(x, y, z, this, meta+1, 2);
    	}
    	else if(meta == 3){
    		if(rand.nextInt(30) > 0){
    			world.setBlock(x, y, z, this, 4, 2);
    		}
    		else{
    			world.setBlock(x, y, z, Blocks.deadbush, 0, 2);
    		}
    	}
	}
	
	

}

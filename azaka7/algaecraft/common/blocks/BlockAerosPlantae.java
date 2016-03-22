package azaka7.algaecraft.common.blocks;

import java.util.Random;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.items.ACItems;
import azaka7.algaecraft.common.tileentity.TileEntityWaterFilter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockAerosPlantae extends BlockBush implements IGrowable{

	private static final Material[] canPlantOn = new Material[]{Material.sand,Material.rock,Material.ground,Material.clay};
	private String[] imgList = {"Seedling","Empty","Full"};
	public IIcon[] iconList = new IIcon[3];
	
	public BlockAerosPlantae() {
		super(Material.water);
		this.setStepSound(this.soundTypeGrass);
		this.setLightLevel(0.4F);
	}
	
	@Override
	protected void checkAndDropBlock(World p_149855_1_, int p_149855_2_, int p_149855_3_, int p_149855_4_)
    {
        if (!this.canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_))
        {
            this.dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_, p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_), 0);
            p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, Blocks.water, 0, 2);
        }
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
		iconList = new IIcon[imgList.length];
    	for (int i = 0; i < this.imgList.length; ++i)
        {
            iconList[i] = par1IconRegister.registerIcon(AlgaeCraft.MODID+":waterBreathPlant"+imgList[i]);
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        return iconList[par2%iconList.length];
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int par1)
    {
        return 0;
    }
    
    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		//if(par5EntityPlayer.inventory.getCurrentItem()==null || par5EntityPlayer.inventory.getCurrentItem().getItem() == null){
			int meta = world.getBlockMetadata(x, y, z);
			if(meta%9 >= 2){
				world.setBlockMetadataWithNotify(x, y, z, 1+9, 3);
				if(world.rand.nextInt(50) == 0){
					this.dropBlockAsItem(world, x, y, z, new ItemStack(this,1,0));
				}
				if(world.rand.nextInt(60) == 0){
					this.dropBlockAsItem(world, x, y, z, new ItemStack(ACItems.itemAerosBulb,1,1));
				}
				else
				{
					this.dropBlockAsItem(world, x, y, z, new ItemStack(ACItems.itemAerosBulb,1,0));
				}
				return true;
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
			
			return false;
		//}
		/*else if(par5EntityPlayer.inventory.getCurrentItem().getItem() == Items.dye && par5EntityPlayer.inventory.getCurrentItem().getItemDamage() == 15){
			Random rand = new Random();
			if(this.tryToGrow(par1World, par1World.getBlockMetadata(par2, par3, par4), par2, par3, par4, rand)
			&&!par5EntityPlayer.capabilities.isCreativeMode){
				par5EntityPlayer.inventory.getCurrentItem().stackSize--;
			}
		}*/
    }
	
    @Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random rand)
    {
    	if(!this.canBlockStay(par1World, par2, par3, par4)){
    		this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(this,1,par1World.getBlockMetadata(par2, par3, par4)));
    		par1World.setBlock(par2, par3, par4, Blocks.water, 0, 3);
    	}
    	else if(par1World.getBlock(par2, par3-1, par4) == Blocks.air){
    		this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(this,1,par1World.getBlockMetadata(par2, par3, par4)));
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
    	if(rand.nextDouble() < ACGameData.aerosGrowthChance){
    		int metadata = par1World.getBlockMetadata(par2, par3, par4);
    		this.tryToGrow(par1World,metadata,par2,par3,par4, rand);
    	}
        super.updateTick(par1World, par2, par3, par4, rand);
    }
    
    private boolean tryToGrow(World par1World, int par2Metadata, int x, int y, int z, Random r){
    	if(this.canBlockStay(par1World, x, y, z) && par2Metadata%9 < 2 && par2Metadata%9 >= 0){
    		par1World.setBlock(x, y, z, this, (par2Metadata%9)+1+9, 2);
    		return true;
    	}
    	return false;
    }
	
    @Override
	public boolean canBlockStay(World world, int x, int y, int z)
    {
        Block var6 = world.getBlock(x, y-1, z);
        Block var7 = world.getBlock(x, y+1, z);

        boolean block = false;
        for(int i = 0; i < this.canPlantOn .length; i++){
        	if(var6.getMaterial() == this.canPlantOn[i] && var6.isSideSolid(world, x, y, z, ForgeDirection.UP)){
        		block = true;
        		break;
        	}
        }
        if(!block){return false;}
        
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
        if(super.canPlaceBlockAt(par1World, par2, par3, par4)){
        	return this.canBlockStay(par1World, par2, par3, par4);
        }
        return false;
    }
	
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
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
    	return ACGameData.coralModelID;
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
    	if(!this.canBlockStay(world, x, y, z)){
    		this.dropBlockAsItem(world, x, y, z, new ItemStack(this,1,world.getBlockMetadata(x, y, z)));
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
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }

  //Should consume bonemeal
  	@Override
  	public boolean func_149851_a(World world, int x, int y, int z, boolean isRemote) {
  		int meta = world.getBlockMetadata(x, y, z);
  		return (meta%9 < 2 && meta%9 >= 0);
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
  		world.setBlock(x, y, z, this, (meta%9)+1+9, 2);
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
}

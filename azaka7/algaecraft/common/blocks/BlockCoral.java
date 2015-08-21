package azaka7.algaecraft.common.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.tileentity.TileEntityWaterFilter;
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
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCoral extends BlockBush implements IGrowable{
	
	public final String[] coralList = new String[]{"orange", "purple", "brain", "blue"};
	public IIcon[] iconList = new IIcon[16];
	
	public Material[] canPlantOn = new Material[]{Material.clay, Material.grass, Material.ground, Material.rock, Material.sand};
	
	public BlockCoral() {
		super(Material.water);
		this.setStepSound(soundTypeStone);
		this.setLightLevel(0.4F);
	}
	
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		//if(par5EntityPlayer.inventory.getCurrentItem()==null){
			int meta = this.getDamageValue(world, x, y, z);
			if(meta%8 >= 0 &&  meta%8 <= 3){
				world.setBlockMetadataWithNotify(x, y, z, meta+4, 3);
				this.dropBlockAsItem(world, x, y, z, new ItemStack(this,1,meta+4));
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
			
		//}
		/*if(par5EntityPlayer.inventory.getCurrentItem().getItem() == Items.dye && par5EntityPlayer.inventory.getCurrentItem().getItemDamage() == 15){
			Random rand = new Random();
			if(this.tryToGrow(par1World, par1World.getBlockMetadata(par2, par3, par4), par2, par3, par4, rand)
			&&!par5EntityPlayer.capabilities.isCreativeMode){
				par5EntityPlayer.inventory.getCurrentItem().stackSize--;
			}
		}*/
		return false;
    }
	@Override
	public int getDamageValue(World par1World, int par2, int par3, int par4)
    {
        return par1World.getBlockMetadata(par2, par3, par4);
    }
    
	@Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
    	if(!this.canBlockStay(par1World, par2, par3, par4)){
    		this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(this,1,par1World.getBlockMetadata(par2, par3, par4)));
    		par1World.setBlock(par2, par3, par4, Blocks.water, 0, 3);
    	}
    	else if(par1World.getBlock(par2, par3-1, par4) == Blocks.air){
    		this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(this,1,par1World.getBlockMetadata(par2, par3, par4)));
    		par1World.setBlock(par2, par3, par4, Blocks.water, 0, 3);
    	}
    	if(par5Random.nextInt(10)==0 && par5Random.nextBoolean()){
    		int metadata = par1World.getBlockMetadata(par2, par3, par4);
    		this.tryToGrow(par1World,metadata,par2,par3,par4, par5Random);
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
        super.updateTick(par1World, par2, par3, par4, par5Random);
    }
    
    private boolean tryToGrow(World par1World, int par2Metadata, int x, int y, int z, Random r){
    	if(this.canBlockStay(par1World, x, y, z) && par2Metadata%8 <= 7 && par2Metadata%8 >= 4){
    		par1World.setBlock(x, y, z, this, par2Metadata-4, 2);
    		return true;
    	}
    	else if(this.canBlockStay(par1World, x, y, z) && par2Metadata%8 <= 3 && par2Metadata%8 >= 0 && r.nextBoolean()){
    		//System.out.println("Secrets await....");
    		return false;
    	}
    	return false;
    }
    
    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
    	//System.out.println("canStay?");
        Block var6 = world.getBlock(x, y-1, z);
        Block var7 = world.getBlock(x, y+1, z);
        boolean biome = false;
        for(int i = 0; i < ACGameData.biomeIDOceanList.length; i++){
        	if(world.getBiomeGenForCoords(x, z).biomeID == ACGameData.biomeIDOceanList[i]){
        		biome = true;
        		break;
        	}
        }
        boolean block = false;
        for(int i = 0; i < this.canPlantOn.length; i++){
        	if(var6.getMaterial() == this.canPlantOn[i] && var6.isSideSolid(world, x, y, z, ForgeDirection.UP)){
        		block = true;
        		break;
        	}
        }
        boolean filter = false;
        boolean badfilter = false;
        for(int x1 = -6; x1 <= 6; x1++){
        	for(int y1 = -6; y1 <= 6; y1++){
        		for(int z1 = -6; z1 <= 6; z1++){
        			if(!(world.getTileEntity(x+x1, y+y1, z+z1) instanceof TileEntityWaterFilter)){
        				//System.out.println("not a filter");
        				//break;
        			}
        			else if(BlockWaterFilter.isLocationValid(world, x, y, z, BlockWaterFilter.EnumWaterType.OCEAN, x+x1, y+y1, z+z1)){
                		//System.out.println("is valid");
        				filter = true;
                		break;
                	}
                	else if(BlockWaterFilter.isLocationInvalid(world, x, y, z, BlockWaterFilter.EnumWaterType.OCEAN, x+x1, y+y1, z+z1)){
                		badfilter = true;
                		break;
                	}
                }
            }
        }
        return block && (filter || (biome && !badfilter));
        /*if(block && (var7 == Blocks.water||var7 == Blocks.flowing_water)){
        	if(biome && !(BlockFilter.isBlockFiltered(par1World, par2, par3, par4, 1)||BlockFilter.isBlockFiltered(par1World, par2, par3, par4, 2))){
        		return true;
        	}
        	if(BlockFilter.isBlockFiltered(par1World, par2, par3, par4, 0)||BlockFilter.isBlockFiltered(par1World, par2, par3, par4, 3)){
        		return true;
        	}
        }*/
        //return false;
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
    	for (int i = 0; i < this.coralList.length; ++i)
        {
            iconList[i] = par1IconRegister.registerIcon(AlgaeCraft.MODID+":coral_"+coralList[i]);
        }
    	for (int i = coralList.length; i < 2*coralList.length; ++i)
        {
            iconList[i] = par1IconRegister.registerIcon(AlgaeCraft.MODID+":coral_"+coralList[i-coralList.length]+"_small");
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        return iconList[par2%8];
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int par1)
    {
        return (par1%8)+8;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 8; var4 < 16; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
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
    	float par5 = 0;
    	float par6 = 0;
    	float par7 = 1;
    	float parH = 1;
    	float par8 = 1;
    	
    	if (m%8 == 6){//0.375F, 0.0F, 0.375F, 0.625F, 0.25F, 0.625F
    		par5 = 0.375F;
        	par6 = 0.375F;
        	par7 = 0.625F;
        	parH = 0.25F;
        	par8 = 0.625F;
    	}
    	else if (m%8 == 2){//0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F
    		par5 = 0.25F;
        	par6 = 0.25F;
        	par7 = 0.75F;
        	parH = 0.5F;
        	par8 = 0.75F;
    	}
    	else{//0.125F, 0.0F, 0.125F, 0.875F, 0.75F, 0.875F
    		par5 = 0.25F;
        	par6 = 0.25F;
        	par7 = 0.75F;
        	parH = 0.75F;
        	par8 = 0.75F;
    	}
    	
    	this.setBlockBounds(par5, 0.0F, par6, par7, parH, par8);
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
		return (meta%8 <= 7 && meta%8 >= 4);
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
		world.setBlock(x, y, z, this, meta-4, 3);
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

package azaka7.algaecraft.common.blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import azaka7.algaecraft.AlgaeCraft;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.util.Tuple;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSpongeAC extends BlockSponge{
	
	private final String imgNameDry;
	private IIcon iconDry;
	private final String imgNameWet;
	private IIcon iconWet;

	protected BlockSpongeAC(String dry, String wet) {
		super();
		imgNameDry = dry;
		imgNameWet = wet;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
		if(this == ACBlocks.spongeRed){
			ACBlocks.spongeYellow.registerBlockIcons(par1IconRegister);
		}
    	iconDry = par1IconRegister.registerIcon(AlgaeCraft.MODID+":"+imgNameDry);
    	iconWet = par1IconRegister.registerIcon(AlgaeCraft.MODID+":"+imgNameWet);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1, int par2)
    {
    	return par2 == 1 ? iconWet : iconDry;
    }
	
	/**
     * Get the damage value that this Block should drop
     */
	@Override
    public int damageDropped(int damage)
    {
        return damage == 1 ? 1 : 0;
    }
	
	@Override
    public void onBlockAdded(World worldIn, int x, int y, int z)
    {
        this.tryAbsorb(worldIn, new BlockPos(x, y, z));
    }

    /**
     * Called when a neighboring block changes.
     */
	@Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighborBlock)
    {
        this.tryAbsorb(worldIn, new BlockPos(x, y, z));
        super.onNeighborBlockChange(worldIn, x, y, z, neighborBlock);
    }

    protected void tryAbsorb(World worldIn, BlockPos pos)
    {
        //System.out.println("tryAbsorb()");
        if (!(worldIn.getBlockMetadata(pos.getX(), pos.getY(), pos.getZ()) == 1) && this.absorb(worldIn, pos))
        {
            //System.out.println("success");
            worldIn.setBlockMetadataWithNotify(pos.getX(), pos.getY(), pos.getZ(), 1, 2);
            worldIn.playAuxSFX(2001, pos.getX(), pos.getY(), pos.getZ(), Block.getIdFromBlock(Blocks.water));
        }
    }

    private boolean absorb(World worldIn, BlockPos pos)
    {
        LinkedList linkedlist = Lists.newLinkedList();
        ArrayList arraylist = Lists.newArrayList();
        linkedlist.add(new Tuple(pos, Integer.valueOf(0)));
        int i = 0;
        BlockPos blockpos1;

        while (!linkedlist.isEmpty())
        {
            Tuple tuple = (Tuple)linkedlist.poll();
            blockpos1 = (BlockPos)tuple.getFirst();
            int j = ((Integer)tuple.getSecond()).intValue();
            EnumFacing[] aenumfacing = EnumFacing.values();
            int k = aenumfacing.length;

            for (int l = 0; l < k; ++l)
            {
                EnumFacing enumfacing = aenumfacing[l];
                BlockPos blockpos2 = blockpos1.offset(enumfacing);
                Block blockAtPos2=worldIn.getBlock(blockpos2.getX(),  blockpos2.getY(),  blockpos2.getZ());
                if (blockAtPos2.getMaterial() == Material.water/* && blockAtPos2 instanceof BlockLiquid*/)
                {
                	if(!(blockAtPos2 instanceof BlockLiquid)){
                		blockAtPos2.dropBlockAsItem(worldIn, blockpos2.getX(),  blockpos2.getY(),  blockpos2.getZ(), worldIn.getBlockMetadata(blockpos2.getX(),  blockpos2.getY(),  blockpos2.getZ()), 0);
                	}
                	worldIn.setBlock(blockpos2.getX(), blockpos2.getY(), blockpos2.getZ(), Blocks.air, 0, 2);
                    arraylist.add(blockpos2);
                    ++i;

                    if (j < 6)
                    {
                        linkedlist.add(new Tuple(blockpos2, Integer.valueOf(j + 1)));
                    }
                }
            }

            if (i > 64)
            {
                break;
            }
        }

        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext())
        {
            blockpos1 = (BlockPos)iterator.next();
            worldIn.notifyBlocksOfNeighborChange(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ(), Blocks.air);
            
        }

        return i > 0;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, int x, int y, int z, Random rand)
    {
		BlockPos pos = new BlockPos(x,y,z);
        if (worldIn.getBlockMetadata(pos.getX(), pos.getY(), pos.getZ()) == 1)
        {
            EnumFacing enumfacing = EnumFacing.values()[rand.nextInt(EnumFacing.values().length)];
            BlockPos pos1 = pos.offset(enumfacing);
            if (enumfacing != EnumFacing.UP && !World.doesBlockHaveSolidTopSurface(worldIn, pos1.getX(), pos1.getY(), pos1.getZ()))
            {
                double d0 = (double)pos1.getX();
                double d1 = (double)pos1.getY()+1.0D;
                double d2 = (double)pos1.getZ();

                if (enumfacing == EnumFacing.DOWN)
                {
                	d0 += rand.nextDouble();
                	d2 += rand.nextDouble();
                }
                else
                {
                    if (enumfacing == EnumFacing.EAST || enumfacing == EnumFacing.WEST)
                    {
                        d2 += rand.nextDouble();
                        d1 -= rand.nextDouble()*0.9D;

                        if (enumfacing == EnumFacing.EAST)
                        {
                            d0+=1.05D;
                        }
                        else
                        {
                            d0 += 0.15D;
                        }
                    }
                    else
                    {
                        d0 += rand.nextDouble();
                        d1 -= rand.nextDouble()*0.9D;

                        if (enumfacing == EnumFacing.SOUTH)
                        {
                        	d2+=0.15D;
                        }
                        else
                        {
                        	d2+=1.05D;
                        }
                    }
                }

                worldIn.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}

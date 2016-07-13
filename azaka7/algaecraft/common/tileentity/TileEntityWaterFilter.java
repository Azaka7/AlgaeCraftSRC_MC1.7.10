package azaka7.algaecraft.common.tileentity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;

import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.handlers.ACPathingHandler;
import azaka7.algaecraft.common.handlers.ACPathingHandler.Pos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;

public class TileEntityWaterFilter extends TileEntity {
	
	private List<ACPathingHandler.Pos> pathmap = new ArrayList<ACPathingHandler.Pos>();
	
	private List<BlockPos> blockmap = new ArrayList<BlockPos>();
	
	private boolean worldloadInit = true;
	
	@Override
	public void updateEntity()
    {
		long updateRate = ACGameData.filterUpdateRate;
		long tickShift = Math.abs((this.xCoord+this.yCoord+this.zCoord) % updateRate);
		
		if (this.worldObj.getWorldTime() % updateRate == tickShift)
        {
			this.blockmap.clear();
			this.filter(this.worldObj, new BlockPos(this.xCoord,this.yCoord,this.zCoord));
		}else if(worldloadInit){
			this.blockmap.clear();
			this.filter(this.worldObj, new BlockPos(this.xCoord,this.yCoord,this.zCoord));
			worldloadInit = false;
		}
		
		if(ACGameData.sporousMossGen && this.getBlockMetadata() == 3 && this.worldObj.getWorldTime() % 100 == 0){
			int x = this.worldObj.rand.nextInt(5)-2, y = this.worldObj.rand.nextInt(5)-2, z = this.worldObj.rand.nextInt(5)-2;
			if(this.isPosNearPathmap(this.xCoord+x, this.yCoord+y, this.zCoord+z)){
				if(worldObj.getBlock(this.xCoord+x, this.yCoord+y, this.zCoord+z) == Blocks.cobblestone){
					worldObj.setBlock(this.xCoord+x, this.yCoord+y, this.zCoord+z, Blocks.mossy_cobblestone);
				} else if(worldObj.getBlock(this.xCoord+x, this.yCoord+y, this.zCoord+z) == Blocks.stonebrick && worldObj.getBlockMetadata(x, y, z) == 0){
					worldObj.setBlock(this.xCoord+x, this.yCoord+y, this.zCoord+z, Blocks.stonebrick, 1, 3);
				}
			}
		}
	}
	
	public boolean isPosNearPathmap(int x, int y, int z){
		return isPosInPathmap(x,y,z)
				|| isPosInPathmap(x+1,y,z) || isPosInPathmap(x,y+1,z) || isPosInPathmap(x,y,z+1)
				|| isPosInPathmap(x-1,y,z) || isPosInPathmap(x,y-1,z) || isPosInPathmap(x,y,z-1);
	}
	
	public boolean isPosInPathmap(int x, int y, int z){
		for(BlockPos pos : this.blockmap){
			if(pos.getX() == x && pos.getZ() == z && pos.getY() == y){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<ACPathingHandler.Pos> getPathmap(){
		return (ArrayList<Pos>) this.pathmap;
	}
	
	public ArrayList<BlockPos> getBlockmap(){
		return (ArrayList<BlockPos>) this.blockmap;
	}
	
	private boolean filter(World worldIn, BlockPos pos)
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
                if (blockAtPos2.getMaterial() == Material.water)
                {
                    this.blockmap.add(blockpos2);
                	//worldIn.setBlock(blockpos2.getX(), blockpos2.getY(), blockpos2.getZ(), Blocks.air, 0, 2);
                    arraylist.add(blockpos2);
                    ++i;

                    if (j < 6)
                    {
                        linkedlist.add(new Tuple(blockpos2, Integer.valueOf(j + 1)));
                    }
                }
            }

            if (i > 1024)
            {
                break;
            }
        }

        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext())
        {
            blockpos1 = (BlockPos)iterator.next();
            //worldIn.notifyBlocksOfNeighborChange(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ(), Blocks.air);
        }

        return i > 0;
    }
	
}

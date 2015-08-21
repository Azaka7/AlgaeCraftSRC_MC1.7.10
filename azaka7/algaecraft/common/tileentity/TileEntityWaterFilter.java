package azaka7.algaecraft.common.tileentity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;

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
	
	public void updateEntity()
    {
		int tickShift = (this.xCoord+this.yCoord+this.zCoord) % 40;
		if (this.worldObj.getWorldTime() % 40L == (long)(tickShift))
        {
			this.blockmap.clear();
			this.filter(this.worldObj, new BlockPos(this.xCoord,this.yCoord,this.zCoord));
			//this.pathmap.clear();
			//ACPathingHandler.Pos pos = new ACPathingHandler.Pos(this.xCoord,this.yCoord,this.zCoord);
			//this.pathmap.addAll(ACPathingHandler.INSTANCE.getRangedConduitMap(this.worldObj, pos, Material.water, 5));
        }
		if(worldloadInit){
			this.blockmap.clear();
			this.filter(this.worldObj, new BlockPos(this.xCoord,this.yCoord,this.zCoord));
			//this.pathmap.clear();
			//ACPathingHandler.Pos pos = new ACPathingHandler.Pos(this.xCoord,this.yCoord,this.zCoord);
			//this.pathmap.addAll(ACPathingHandler.INSTANCE.getRangedConduitMap(this.worldObj, pos, Material.water, 5));
			worldloadInit = false;
		}
    }
	
	public boolean isPosInPathmap(int x, int y, int z){
		//return ACPathingHandler.findAStarPath(this.worldObj, new Pos(this.xCoord,this.yCoord,this.zCoord), new Pos(x,y,z), Material.water, 5);
		/*for(int i = 0; i < this.pathmap.size(); i++){
			if(this.pathmap.get(i).isSame(new Pos(x,y,z))){
				return true;
			}
		}*/
		for(int i = 0; i < this.blockmap.size(); i++){
			BlockPos pos = this.blockmap.get(i);
			if(pos.getX() == x && pos.getY() == y && pos.getZ() == z){
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

            if (i > 2048)
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

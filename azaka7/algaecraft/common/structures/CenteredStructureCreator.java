package azaka7.algaecraft.common.structures;

import net.minecraft.block.Block;
import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.structures.Structure.BlockData;

public class CenteredStructureCreator extends StructureCreator{
	
	private final int thisX,thisY,thisZ;
	public CenteredStructureCreator(int x, int y, int z){
		this.thisX = x;
		this.thisY = y;
		this.thisZ = z;
	}
	
	public StructureCreator setBlock(int x, int y, int z, Block block)
	{
		return super.setBlock(x-thisX, y-thisY, z-thisZ, block);
	}
	
	public StructureCreator setBlock(int x, int y, int z, Block block, int meta)
	{
		return super.setBlock(x-thisX,  y-thisY, z-thisZ, block, meta);
	}
	
	public StructureCreator setBlock(int x, int y, int z, BlockData data){
		return super.setBlock(x-thisX, y-thisY, z-thisZ, data);
	}
	
	public void fill(int x1, int y1, int z1, int x2, int y2, int z2, BlockData data){
		super.fill(x1-thisX, y1-thisY, z1-thisZ, x2-thisX, y2-thisY, z2-thisZ, data);
	}
}

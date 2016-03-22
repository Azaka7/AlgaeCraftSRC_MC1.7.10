package azaka7.algaecraft.common.structures;

import net.minecraft.init.Blocks;
import azaka7.algaecraft.common.blocks.ACBlocks;
import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.structures.Structure.BlockData;

public class StructureTarKiln{
	
	public static Structure createStructure(){
		StructureCreator creator = new StructureCreator();
		creator.setBlock(0, 0, 0, ACBlocks.limestoneSlab)
		.setBlock(pos(0, 1, 0), Structure.BlockType.FIRE.toBlockData())
		.setBlock(pos(0, 1, -1), Structure.BlockType.LOG.toBlockData())
		.setBlock(1, 0, 0, ACBlocks.limestone, -1)
		.setBlock(-1, 0, 0, ACBlocks.limestone, -1)
		.setBlock(pos(0,0,-1), new BlockData(Structure.BlockType.SOLID))
		.setBlock(1, 1, -1, ACBlocks.limestone, -1)
		.setBlock(-1, 1, -1, ACBlocks.limestone, -1)
		.setBlock(0, 1, -2, ACBlocks.limestone, -1); 
		
		return creator.createStructure();
	}
	
	private static BlockPos pos(int x, int y, int z){return new BlockPos(x,y,z);}
	
}

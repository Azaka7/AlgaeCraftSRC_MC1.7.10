package azaka7.algaecraft.common.structures;

import net.minecraft.init.Blocks;
import azaka7.algaecraft.common.blocks.ACBlocks;
import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.structures.Structure.BlockData;
import azaka7.algaecraft.common.structures.Structure.BlockType;

public class StructureShip1{
	
	public static Structure createStructure(){
		CenteredStructureCreator creator = new CenteredStructureCreator(751, 44, -62);
		creator.fill(751, 49, -64, 751, 70, -64, new BlockData(Blocks.log, 0));
		
		
		return creator.createStructure();
	}
	
	private static BlockPos pos(int x, int y, int z){return new BlockPos(x,y,z);}
	
}

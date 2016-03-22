package azaka7.algaecraft.common.structures;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.structures.Structure.BlockData;
import azaka7.algaecraft.common.structures.Structure.TileData;

public class StructureHandler {
	
	private static final StructureHandler INSTANCE = new StructureHandler();
	
	public StructureHandler(){}
	
	public static boolean isPosAtStructureOrigin(World world, BlockPos pos, Structure structure){
		boolean flag = false;
		for(Structure theStructure : structure.getAllTypes()){
			flag = false;
			StructureAccess access = StructureAccess.read(theStructure);
			for(BlockPos apos : access.getPosSet()){
				BlockPos thePos = pos.add(apos);
				BlockData data = new BlockData(world.getBlock(thePos.getX(), thePos.getY(), thePos.getZ()), world.getBlockMetadata(thePos.getX(), thePos.getY(), thePos.getZ()));
				if(!access.getBlockData(apos).equals(data)){
					flag = false;
					break;
				}
				flag = true;
			}
			if(flag){return true;}
		}
		return false;
	}
	
	public static void generateStructure(BlockPos pos, Structure structure){
		StructureAccess access = StructureAccess.read(structure);
		
	}
}

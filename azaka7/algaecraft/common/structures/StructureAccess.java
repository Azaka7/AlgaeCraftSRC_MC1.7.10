package azaka7.algaecraft.common.structures;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.structures.Structure.BlockData;
import azaka7.algaecraft.common.structures.Structure.TileData;

public class StructureAccess{
	private Map<BlockPos,BlockData> blockMap;
	private Map<BlockPos,TileData> tileMap;
	
	private StructureAccess(){}
	
	public static StructureAccess read(Structure structure){
		return structure.access(new StructureAccess());
	}
	
	public void initialize(Map<BlockPos,BlockData> blocks, Map<BlockPos,TileData> tileentities){
		this.blockMap = blocks;
		this.tileMap = tileentities;
	}
	
	public Set<BlockPos> getPosSet()
	{
		Set<BlockPos> posList = new HashSet<BlockPos>();
		for(BlockPos pos : this.blockMap.keySet()){
			posList.add(pos);
		}
		return posList;
	}
	public BlockData getBlockData(BlockPos pos){
		for(BlockPos data : blockMap.keySet()){
			if(data.equals(pos)){
				return blockMap.get(data);
			}
		}
		return null;
	}
	
	public Block getBlock(BlockPos pos){
		return this.getBlockData(pos).getBlock();
	}
}

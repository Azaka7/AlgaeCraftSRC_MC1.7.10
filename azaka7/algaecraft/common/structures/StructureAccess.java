package azaka7.algaecraft.common.structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.structures.Structure.BlockData;
import azaka7.algaecraft.common.structures.Structure.TileData;
import azaka7.algaecraft.common.structures.Structure.WeightedChildList;

public class StructureAccess{
	private Map<BlockPos,BlockData> blockMap;
	private Map<BlockPos,TileData> tileMap;
	private ArrayList<WeightedChildList> variants = new ArrayList<WeightedChildList>();
	
	private StructureAccess(){}
	
	public static StructureAccess read(Structure structure){
		return structure.access(new StructureAccess());
	}
	
	public void initialize(Map<BlockPos,BlockData> blocks, Map<BlockPos,TileData> tileentities, ArrayList<WeightedChildList> vars){
		this.blockMap = blocks;
		this.tileMap = tileentities;
		this.variants = vars;
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

	public int getBlockMetadata(BlockPos pos) {
		return this.getBlockData(pos).getMetadata();
	}

	public TileData getTileData(BlockPos pos){
		for(BlockPos data : tileMap.keySet()){
			if(data.equals(pos)){
				TileData td = tileMap.get(data);
				return new TileData(td.getNewTileEntity(), td.getChestGen());
			}
		}
		return null;
	}
	
	public ArrayList<Structure> getRandomVariation(Random rand){
		//System.out.println(variants);
		//System.out.println(variants.size());
		ArrayList<Structure> ret = new ArrayList<Structure>();
		for(int i = 0; i < variants.size(); i++){
			//System.out.println(variants.get(i));
			ret.add(variants.get(i).pick(rand));
		}
		return ret;
	}
}

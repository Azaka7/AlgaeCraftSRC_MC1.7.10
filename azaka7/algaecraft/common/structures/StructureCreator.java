package azaka7.algaecraft.common.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.ChestGenHooks;
import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.structures.Structure.BlockData;
import azaka7.algaecraft.common.structures.Structure.TileData;

public class StructureCreator {
	
	private final Map<BlockPos,BlockData> blockMap = new HashMap<BlockPos,BlockData>();
	private final Map<BlockPos,TileData> tileMap = new HashMap<BlockPos,TileData>();
	
	public StructureCreator(){}
	
	public Structure createStructure(){
		List list = new ArrayList();
		
		for(Entry<BlockPos,BlockData> entry : blockMap.entrySet()){
			list.add(entry.getKey());
			list.add(entry.getValue());
		}
		
		for(Entry<BlockPos,TileData> entry : tileMap.entrySet()){
			list.add(entry.getKey());
			list.add(entry.getValue());
		}
		
		Structure structure = new Structure(list.toArray());
		return structure;
	}
	
	public StructureCreator setBlock(int x, int y, int z, Block block)
	{
		return this.setBlock(new BlockPos(x,y,z), new BlockData(block, 0));
	}
	
	public StructureCreator setBlock(int x, int y, int z, Block block, int meta)
	{
		return this.setBlock(new BlockPos(x,y,z), new BlockData(block, meta));
	}
	
	public StructureCreator setBlock(BlockPos pos, BlockData data){
		this.blockMap.put(pos, data);
		return this;
	}
	
	public void fill(BlockPos pos1, BlockPos pos2, BlockData data){
		for(Object pos : BlockPos.getAllInBox(pos1, pos2)){
			blockMap.put((BlockPos) pos, data);
		}
		/*for(int x = pos1.getX(); (pos2.getX()+(pos2.getX() >= pos1.getX() ? 1 : -1)) - x != 0; x += (pos2.getX() >= pos1.getX() ? 1 : -1)){
			for(int y = pos1.getY(); (pos2.getY()+(pos2.getY() >= pos1.getY() ? 1 : -1)) - y != 0; y += (pos2.getY() >= pos1.getY() ? 1 : -1)){
				for(int z = pos1.getZ(); (pos2.getZ()+(pos2.getZ() >= pos1.getZ() ? 1 : -1)) - z != 0; z += (pos2.getZ() >= pos1.getZ() ? 1 : -1)){
					blockMap.put(new BlockPos(x,y,z), data);
				}
			}
		}*/
	}
	
	public void setChestWithChance(BlockPos pos, ChestGenHooks cgh){
		blockMap.put(pos, new BlockData(Blocks.chest));
		tileMap.put(pos, new TileData(new TileEntityChest(), cgh));
	}
	
	public void setChestWithContents(BlockPos pos, ItemStack[] stacks){
		blockMap.put(pos, new BlockData(Blocks.chest));
		TileEntityChest te = new TileEntityChest();
		for(int i = 0; i < stacks.length; i++){
			if(stacks[i] != null && i < te.getSizeInventory()){
				te.setInventorySlotContents(i, stacks[i]);
			}
		}
		tileMap.put(pos, new TileData(te));
	}
	
}

package azaka7.algaecraft.common.structures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.oredict.OreDictionary;
import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.structures.StructureCreator;

public class Structure {

	private final Map<BlockPos,BlockData> blockMap = new HashMap<BlockPos,BlockData>();
	private final Map<BlockPos,TileData> tileMap = new HashMap<BlockPos,TileData>();

	private final Structure altMaps90;
	private final Structure altMaps180;
	private final Structure altMaps270;
	
	public Structure(Object...objects){
		this.constructBlockMap(objects);
		altMaps90 = this.rotate90();
		altMaps180 = this.rotate180();
		altMaps270 = this.rotate270();
	}
	
	public Structure(){
		altMaps90 = this;
		altMaps180 = this;
		altMaps270 = this;
	}
	
	public Set<Structure> getAllTypes(){
		Set<Structure> types = new HashSet<Structure>();
			types.add(this);
			types.add(altMaps90);
			types.add(altMaps180);
			types.add(altMaps270);
		
		return types;
	}
	
	public Structure getRotate90(){
		return this.altMaps90;
	}
	public Structure getRotate180(){
		return this.altMaps180;
	}
	public Structure getRotate270(){
		return this.altMaps270;
	}

	private void constructBlockMap(Object[] objects) {
		if(objects.length % 2 > 0){
			System.out.println("[AlgaeCraft] WARNING: A structure definition is invalid.");
			return;
		}
		for(int i = 0; i < objects.length; i+=2){
			if(!(objects[i] instanceof BlockPos)){
				System.out.println("[AlgaeCraft] WARNING: A structure definition location is invalid. Skipping block.");
			} else if(objects[i+1] instanceof BlockData){
				blockMap.put((BlockPos) objects[i], (BlockData) objects[i+1]);
			} else if(objects[i+1] instanceof TileEntity) {
				tileMap.put((BlockPos) objects[i], (TileData) objects[i+1]);
			}
		}
	}

	private Structure rotate90() {
		Structure ret = new Structure();
		for(BlockPos pos : this.blockMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getZ(), pos.getY(), pos.getX()*-1);
			ret.blockMap.put(pos1, this.blockMap.get(pos));
		}
		for(BlockPos pos : this.tileMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getZ(), pos.getY(), pos.getX()*-1);
			ret.tileMap.put(pos1, this.tileMap.get(pos));
		}
		return ret;
	}

	private Structure rotate180() {
		Structure ret = new Structure();
		for(BlockPos pos : this.blockMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getX()*-1, pos.getY(), pos.getZ()*-1);
			ret.blockMap.put(pos1, this.blockMap.get(pos));
		}
		for(BlockPos pos : this.tileMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getX()*-1, pos.getY(), pos.getZ()*-1);
			ret.tileMap.put(pos1, this.tileMap.get(pos));
		}
		return ret;
	}

	private Structure rotate270() {
		Structure ret = new Structure();
		for(BlockPos pos : this.blockMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getZ()*-1, pos.getY(), pos.getX());
			ret.blockMap.put(pos1, this.blockMap.get(pos));
		}
		for(BlockPos pos : this.tileMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getZ()*-1, pos.getY(), pos.getX());
			ret.tileMap.put(pos1, this.tileMap.get(pos));
		}
		return ret;
	}
	
	public StructureAccess access(StructureAccess access){
		access.initialize(blockMap, tileMap);
		return access;
	}
	
	public static class TileData{
		private final TileEntity tile;
		private final ChestGenHooks table;
		
		public TileData(TileEntity te, ChestGenHooks cgh){
			tile = te;
			table = cgh;
		}
		
		public TileData(TileEntity te) {
			this(te, null);
		}

		public TileEntity getTileEntity(){
			return tile;
		}
		
		public ChestGenHooks getChestGen(){
			return table;
		}
		
		public void lootTable(Random rand){
			WeightedRandomChestContent[] wrcc = table.getItems(rand);
			if(tile instanceof IInventory){
				IInventory inv =((IInventory) tile);
				WeightedRandomChestContent.generateChestContents(rand, wrcc, inv, 0);
			}
		}
	}
	
	public static class BlockData{
		private final Block block;
		private final int metadata;
		
		private final BlockType type;

		public BlockData(Block b, int i){
			block = b;
			metadata = i;
			type = null;
		}
		
		public BlockData(Block b) {
			this(b, 0);
		}
		
		public BlockData(BlockType t){
			block = null;
			metadata = 0;
			type = t;
		}

		public boolean compare(Block testBlock, int meta){
			if(block != null){
				return block == testBlock && meta == metadata;
			} else {
				return type != null ? (type.isBlockOfType(testBlock)) : (false);
			}
		}
		
		public Block getBlock(){
			return block != null ? block : (type != null ? type.getDefaultBlock() : Blocks.air);
		}
		
		public int getMetadata(){return metadata;}
		
		public BlockType getBlockType(){
			return type != null ? type : null;
		}
		
		@Override
		public boolean equals(Object object){
			if(object == null || !(object instanceof BlockData)){
				return false;
			}
			BlockData data = ((BlockData) object);
			
			return (data.getBlock() == this.getBlock() && (this.getMetadata() == -1 || data.getMetadata() == this.getMetadata()))  || (this.getBlockType() != null ? this.getBlockType().isBlockOfType(data.getBlock()) : false);
		}
		
		@Override
		public String toString(){
			return "BlockData["+"Block="+(block != null ? block.getUnlocalizedName() : "null")+",Metadata="+metadata+",Type="+(type != null ? type.toString() : "null")+"]";
		}
	}
	
	public static enum BlockType{
		AIR(Material.air, Blocks.air),
		AIR_LIKE(Material.vine, Blocks.tripwire),
		SOLID(Material.rock, Blocks.stone),
		WATER(Material.water, Blocks.water),
		LAVA(Material.lava, Blocks.lava),
		FIRE(Material.fire, Blocks.fire),
		LOG(Material.wood, Blocks.log);
		
		private final Material material_definition;
		private final Block default_block;
		
		BlockType(Material material, Block block){
			this.material_definition = material;
			this.default_block = block;
		}
		
		public BlockData toBlockData(){
			return new BlockData(this);
		}
		
		public Material getMaterialDefinition(){
			return this.material_definition;
		}
		
		public Block getDefaultBlock(){
			return this.default_block;
		}
		
		public boolean isBlockOfType(Block block){
			if(this == AIR_LIKE && !block.isOpaqueCube()){
				return true;
			} else if(this == SOLID && block.isOpaqueCube()){
				return true;
			} else if(this == LOG && (block.getClass() == this.getDefaultBlock().getClass() || OreDictionary.getOreName(OreDictionary.getOreID(new ItemStack(block))).startsWith("log"))){
				return true;
			}
			return block.getMaterial() == this.getMaterialDefinition();
		}
		
		public static boolean isBlockOfType(Block block, BlockType type){
			if(type == AIR_LIKE && !block.isOpaqueCube()){
				return true;
			} else if(type == SOLID && block.isOpaqueCube()){
				return true;
			} else if(type == LOG && block.getClass() == type.getDefaultBlock().getClass()){
				return true;
			}
			return block.getMaterial() == type.getMaterialDefinition();
		}
	}
	
}

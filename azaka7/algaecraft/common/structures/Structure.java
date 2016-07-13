package azaka7.algaecraft.common.structures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
	private final ArrayList<WeightedChildList> variants = new ArrayList<WeightedChildList>();
	
	private Structure altMaps90;
	private Structure altMaps180;
	private Structure altMaps270;
	
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
			types.add(this.rotate90());
			types.add(this.rotate180());
			types.add(this.rotate270());
		
		return types;
	}
	
	public Structure getRotate90(){
		if(this.altMaps90 == this || this.altMaps90 == null){
			altMaps90 = this.rotate90();
		}
		return this.rotate90();
	}
	public Structure getRotate180(){
		if(this.altMaps180 == this || this.altMaps180 == null){
			altMaps180 = this.rotate180();
		}
		return this.rotate180();
	}
	public Structure getRotate270(){
		if(this.altMaps270 == this || this.altMaps270 == null){
			altMaps270 = this.rotate270();
		}
		return this.rotate270();
	}

	private void constructBlockMap(Object[] objects) {
		for(int i = 0; i < objects.length; i+=1){
			if(objects[i] instanceof WeightedChildList){
				//System.out.println("adding variant: "+objects[i]);
				//System.out.println("contains: "+((WeightedChildList) objects[i]).children);
				variants.add((WeightedChildList) objects[i]);
			} else if(!(objects[i] instanceof BlockPos && objects.length > i+1)){
				System.out.println("[AlgaeCraft] WARNING: A structure definition location is invalid. Skipping block ("+i+":"+objects[i]+").");
				continue;
			} else if(objects.length > i+1 && objects[i+1] instanceof BlockData){
				blockMap.put((BlockPos) objects[i], (BlockData) objects[i+1]);
				i+=1;
			} else if(objects.length > i+1 && objects[i+1] instanceof TileData) {
				tileMap.put((BlockPos) objects[i], (TileData) objects[i+1]);
				i+=1;
			}
		}
	}

	private Structure rotate90() {
		Structure ret = new Structure();
		for(BlockPos pos : this.blockMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getZ()*-1, pos.getY(), pos.getX());
			BlockData bd1 = this.blockMap.get(pos);
			BlockData bd2 = bd1;
			if(bd1.getBlock() != null){
				if(bd1.getBlockType() == null && bd1.getBlock() instanceof BlockLog){
					int meta = bd1.getMetadata();
					boolean b4 = (meta & 4) == 4;
					boolean b8 = (meta & 8) == 8;
					if(b4 && !b8){
						meta = 8 + meta % 4;
					} else if(b8 && !b4){
						meta = 4 + meta % 4;
					}
					bd2 = new BlockData(bd1.getBlock(), meta);
				} else if(bd1.getBlock() instanceof BlockStairs){
					int meta = bd1.getMetadata();
					boolean b = ((meta & 4) == 4);
					switch(meta % 4){
					case 0: meta = 2; break;
					case 1: meta = 3; break;
					case 2: meta = 1; break;
					case 3: meta = 0; break;
					}
					meta += b ? 4 : 0;
					bd2 = new BlockData(bd1.getBlock(), meta);
				} else if(bd1.getBlock() instanceof BlockPistonBase || bd1.getBlock() instanceof BlockPistonExtension
						|| bd1.getBlock() instanceof BlockLadder || bd1.getBlock() instanceof BlockFurnace || bd1.getBlock() instanceof BlockChest){
					int meta = bd1.getMetadata();
					switch(meta % 8){
					case 2: meta = 5; break;
					case 3: meta = 4; break;
					case 4: meta = 2; break;
					case 5: meta = 3; break;
					}
					bd2 = new BlockData(bd1.getBlock(), meta);
				} else if(bd1.getBlock() instanceof BlockDispenser || bd1.getBlock() instanceof BlockDropper
						|| bd1.getBlock() instanceof BlockHopper){
					int meta = bd1.getMetadata();
					boolean b = (meta & 8) == 8;
					switch(meta % 8){
					case 2: meta = 5; break;
					case 3: meta = 4; break;
					case 4: meta = 2; break;
					case 5: meta = 3; break;
					}
					meta += b ? 8 : 0;
					bd2 = new BlockData(bd1.getBlock(), meta);
				} else if(bd1.getBlock() instanceof BlockButton){
					int meta = bd1.getMetadata();
					boolean b = (meta & 8) == 8;
					switch(meta % 8){
					case 1: meta = 3; break;
					case 2: meta = 4; break;
					case 3: meta = 2; break;
					case 4: meta = 1; break;
					}
					meta += b ? 8 : 0;
					bd2 = new BlockData(bd1.getBlock(), meta);
				} else if(bd1.getBlock() instanceof BlockDoor || bd1.getBlock() instanceof BlockBed){
					int meta = bd1.getMetadata();
					boolean b1 = (meta & 4) == 4;
					boolean b2 = (meta & 8) == 8;
					switch(meta % 4){
					case 0: meta = 1; break;
					case 1: meta = 2; break;
					case 2: meta = 3; break;
					case 3: meta = 0; break;
					}
					meta += (b1 ? 4 : 0) + (b2 ? 8 : 0);
					bd2 = new BlockData(bd1.getBlock(), meta);
				} else if(bd1.getBlock() instanceof BlockTorch || bd1.getBlock() instanceof BlockRedstoneTorch){
					int meta = bd1.getMetadata();
					boolean b = (meta & 8) == 8;
					switch(meta % 8){
					case 1: meta = 3; break;
					case 2: meta = 4; break;
					case 3: meta = 2; break;
					case 4: meta = 1; break;
					}
					meta += b ? 8 : 0;
					bd2 = new BlockData(bd1.getBlock(), meta);
				} else if(bd1.getBlock() instanceof BlockTrapDoor){
					int meta = bd1.getMetadata();
					boolean b1 = ((meta & 4) == 4);
					boolean b2 = ((meta & 8) == 8);
					switch(meta % 4){
					case 0: meta = 3; break;
					case 1: meta = 2; break;
					case 2: meta = 0; break;
					case 3: meta = 1; break;
					}
					meta += (b1 ? 4 : 0) + (b2 ? 8 : 0);
					bd2 = new BlockData(bd1.getBlock(), meta);
				} else if(bd1.getBlock() instanceof BlockLever){
					int meta = bd1.getMetadata();
					boolean b2 = ((meta & 8) == 8);
					switch(meta % 8){
					case 1: meta = 3; break;
					case 3: meta = 2; break;
					case 2: meta = 4; break;
					case 4: meta = 1; break;
					}
					meta += (b2 ? 8 : 0);
					bd2 = new BlockData(bd1.getBlock(), meta);
				} else if(bd1.getBlock() instanceof BlockFenceGate){
					int meta = bd1.getMetadata();
					boolean b2 = ((meta & 4) == 4);
					switch(meta % 8){
					case 0: meta = 1; break;
					case 1: meta = 2; break;
					case 2: meta = 3; break;
					case 3: meta = 0; break;
					}
					meta += (b2 ? 4 : 0);
					bd2 = new BlockData(bd1.getBlock(), meta);
				}
				ret.blockMap.put(pos1, bd2);
			}
			
		}
		for(BlockPos pos : this.tileMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getZ()*-1, pos.getY(), pos.getX());
			ret.tileMap.put(pos1, this.tileMap.get(pos));
		}
		for(WeightedChildList wcl : variants){
			ret.variants.add(wcl.copy());
		}
		return ret;
	}

	private Structure rotate180() {
		/*Structure ret = new Structure();
		for(BlockPos pos : this.blockMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getX()*-1, pos.getY(), pos.getZ()*-1);
			ret.blockMap.put(pos1, this.blockMap.get(pos));
		}
		for(BlockPos pos : this.tileMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getX()*-1, pos.getY(), pos.getZ()*-1);
			ret.tileMap.put(pos1, this.tileMap.get(pos));
		}*/
		return rotate90().rotate90();//ret;
	}

	private Structure rotate270() {
		/*Structure ret = new Structure();
		for(BlockPos pos : this.blockMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getZ()*1, pos.getY(), pos.getX()*-1);
			ret.blockMap.put(pos1, this.blockMap.get(pos));
		}
		for(BlockPos pos : this.tileMap.keySet()){
			BlockPos pos1 = new BlockPos(pos.getZ()*-1, pos.getY(), pos.getX());
			ret.tileMap.put(pos1, this.tileMap.get(pos));
		}*/
		return rotate180().rotate90();//ret;
	}
	
	public StructureAccess access(StructureAccess access){
		access.initialize(blockMap, tileMap, variants);
		return access;
	}
	
	public static class WeightedChildList{
		private final ArrayList<Structure> children;
		private boolean isFinal;
		
		public WeightedChildList(){
			children = new ArrayList<Structure>();
			isFinal = false;
		}
		
		public WeightedChildList addWeightedChild(Structure child, int weight){
			if(!isFinal){
				for(int i = 0; i < weight; i++){
					children.add(child);
				}
			}
			return this;
		}
		
		public void finalize(){
			isFinal = true;
		}
		
		public Structure pick(Random rand){
			return children.get(rand.nextInt(children.size()));
		}
		
		public WeightedChildList copy(){
			WeightedChildList ret = new WeightedChildList();
			for(Structure struc : children){
				ret.addWeightedChild(struc, 1);
			}
			return ret;
		}
	}
	
	public static class TileData{
		private final TileEntity tile;
		private final ChestGenHooks table;
		
		public TileData(TileEntity te, ChestGenHooks cgh){
			tile = te;
			table = cgh;
		}
		
		public TileData(TileEntity te, String cgh){
			tile = te;
			table = ChestGenHooks.getInfo(cgh);
		}
		
		public TileData(TileEntity te) {
			tile = te;
			table = null;
		}
		
		public TileEntity getTileEntity(){
			return tile;
		}

		public TileEntity getNewTileEntity(){
			if(tile == null){
				return null;
			}
			NBTTagCompound nbt = new NBTTagCompound();
			tile.writeToNBT(nbt);
			//System.out.println(nbt.getString("id"));
			return TileEntity.createAndLoadEntity(nbt);
		}
		
		public ChestGenHooks getChestGen(){
			return table;
		}
		
		public void lootTable(Random rand, TileEntity te){
			if(tile == null || te == null){
				FMLLog.fine("[AlgaeCraft] Unable to generate contents for null TileEntity: "+this.toString());
				return;
			} else if(te.getClass() != tile.getClass()){
				FMLLog.fine("[AlgaeCraft] Incompatable TileEntity type.");
			}
			WeightedRandomChestContent[] wrcc = table.getItems(rand);
			if(te instanceof IInventory){
				IInventory inv =((IInventory) te);
				WeightedRandomChestContent.generateChestContents(rand, wrcc, inv, table.getCount(rand));
			}
		}
		
		@Override
		public String toString(){
			return "TileData[tile:"+tile+"|table:"+table+"]";
		}
	}
	
	public static class BlockData{
		private final Block block;
		private final int metadata;
		
		private final BlockType type;
		
		public BlockData(){
			this(BlockType.AIR);
		}

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
		
		public static BlockType getTypeFromString(String name){
			for(BlockType type : BlockType.values()){
				if(type.toString().equals(name)){
					return type;
				}
			}
			return BlockType.AIR;
		}
	}
	
	public void exportAsText(String file){
		try {
			File targetFile = new File("config/algaecraft/structures/"+file+".acs");
			File parent = targetFile.getParentFile();
			if(!parent.exists() && !parent.mkdirs()){
				throw new IllegalStateException("Couldn't create dir: "+parent);
			}
			FileWriter writer = new FileWriter(targetFile);
			BlockPos[] positions = blockMap.keySet().toArray(new BlockPos[1]);
			BlockData[] blocks = blockMap.values().toArray(new BlockData[1]);
			for(int i = 0; i < blockMap.size(); i++){
				BlockPos pos = positions[i];
				BlockData block = blocks[i];
				String line = "";
				line += ("b "+pos.getX()+" "+pos.getY()+" "+pos.getZ()+" ");
				if(block.getBlockType() != null){
					line += ("\""+block.getBlockType().toString()+"\"");
				} else if(block.getBlock() != null){
					line += (""+Block.blockRegistry.getNameForObject(block.getBlock())+" "+block.getMetadata());
				} else {
					line = "a"+line.substring(1);
				}
				writer.append(line);
				if(i < blockMap.size()-1){
					writer.append("\n");}
			}
			
			writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

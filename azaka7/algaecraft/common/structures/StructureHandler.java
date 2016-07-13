package azaka7.algaecraft.common.structures;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
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
	
	public static void generateStructure(World world, BlockPos pos, Structure structure, int rotations){
		generateStructure(world, pos, structure, rotations, world.rand);
	}
	
	public static void generateStructure(World world, BlockPos pos, Structure structure, int rotations, Random rand){
		Structure struc = structure;
		for(int r = 0; r < rotations; r++){
			struc = struc.getRotate90();
		}
		StructureAccess access = StructureAccess.read(struc);
		Set<BlockPos> posSet = access.getPosSet();
		int x1 = pos.getX(), y1 = pos.getY(), z1 = pos.getZ();
		for(BlockPos apos : posSet){
			world.setBlock(apos.getX()+x1, apos.getY()+y1, apos.getZ()+z1, access.getBlock(apos), access.getBlockMetadata(apos), 2);
			world.setBlockMetadataWithNotify(apos.getX()+x1, apos.getY()+y1, apos.getZ()+z1, access.getBlockMetadata(apos), 2);
			TileData tiledata = access.getTileData(apos);
			if(tiledata != null && tiledata.getChestGen() != null){
				try{
					tiledata.lootTable(world.rand, tiledata.getTileEntity());
				} catch(Exception e){
					e.printStackTrace();
				}
				world.setTileEntity(apos.getX()+x1, apos.getY()+y1, apos.getZ()+z1, tiledata.getTileEntity());
			}
		}
		ArrayList<Structure> variation = access.getRandomVariation(rand);
		for(Structure part : variation){
			//System.out.println("a part: "+part);
			generateStructure(world, pos, part, rotations, rand);
		}
	}
}

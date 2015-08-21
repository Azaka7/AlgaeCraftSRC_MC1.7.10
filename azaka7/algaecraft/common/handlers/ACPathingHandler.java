package azaka7.algaecraft.common.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ACPathingHandler {
	
	public static ACPathingHandler INSTANCE = new ACPathingHandler();
	
	public static boolean findAStarPath(World world, Pos start, Pos goal, Material material, int pathSize)
	{
		//System.out.println("called astar");
		List<Node> open = new ArrayList<Node>();
		List<Node> closed = new ArrayList<Node>();
		
		Map<Pos,Material> preCheckedMaterials = new HashMap<Pos,Material>();
		
		open.add(new Node(start));
		
		int timer = (int) Math.pow(pathSize, 3);
		
		while(open.size() > 0 && timer >= 0){

			//System.out.println("execute while");
			Node lowF = findLowestFNode(open);
			//System.out.println("Current pos: "+lowF.toString());
			
			open.remove(lowF);
			
			List<Node> adjacents = new ArrayList<Node>();
			Node toTest = new Node(lowF.getPos().inDirection(ForgeDirection.NORTH)).setParent(lowF);
			if(getMaterialForPos(world, toTest.getPos(), preCheckedMaterials) == material){adjacents.add(toTest);}
			toTest = (new Node(lowF.getPos().inDirection(ForgeDirection.SOUTH)).setParent(lowF));
			if(getMaterialForPos(world, toTest.getPos(), preCheckedMaterials) == material){adjacents.add(toTest);}
			toTest = (new Node(lowF.getPos().inDirection(ForgeDirection.EAST)).setParent(lowF));
			if(getMaterialForPos(world, toTest.getPos(), preCheckedMaterials) == material){adjacents.add(toTest);}
			toTest = (new Node(lowF.getPos().inDirection(ForgeDirection.WEST)).setParent(lowF));
			if(getMaterialForPos(world, toTest.getPos(), preCheckedMaterials) == material){adjacents.add(toTest);}
			toTest = (new Node(lowF.getPos().inDirection(ForgeDirection.UP)).setParent(lowF));
			if(getMaterialForPos(world, toTest.getPos(), preCheckedMaterials) == material){adjacents.add(toTest);}
			toTest =(new Node(lowF.getPos().inDirection(ForgeDirection.DOWN)).setParent(lowF));
			if(getMaterialForPos(world, toTest.getPos(), preCheckedMaterials) == material){adjacents.add(toTest);}
			
			for(int i = 0; i < adjacents.size(); i++){
				//System.out.println("check adjacents");
				Node successor = adjacents.get(i);
				//System.out.println("adjacent: "+successor.getPos().toString());
				if(successor.getPos().isSame(goal)){
					//System.out.println("isGoal");
					return getDistance(successor.getPos(), start) <= pathSize;
				}
				//System.out.println("notGoal");
				
				successor.g = (float) (lowF.g + getDistance(lowF.getPos(),successor.getPos()));
				successor.h = getDistance(goal, successor.getPos());
				successor.f = successor.g + successor.h;
				
				if(!isLowestNodeInList(successor, open) || 
						!isLowestNodeInList(successor, closed)){
					//System.out.println("Skipping adjacent");
					//Skipping successor
				}
				else{
					//System.out.println("added adjacent to check");
					open.add(successor);
				}
			}
			
			closed.add(lowF);
			timer--;
			//System.out.println("close current pos");
		}
		//System.out.println("path blocked");
		
		return false;
	}
	
	private static Material getMaterialForPos(World world, Pos pos, Map<Pos,Material> map){
		if(map.containsKey(pos) && map.get(pos)!=null){
			return map.get(pos);
		}
		else{
			Material material = world.getBlock(pos.x, pos.y, pos.z).getMaterial();
			map.put(pos, material);
			return material;
		}
	}
	
	private static boolean isLowestNodeInList(Node node, List<Node> list){
		boolean flag = true;
		
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getPos().isSame(node.getPos())){
				if(list.get(i).f < node.f){
					return false;
				}
			}
		}
		
		return flag;
	}
	
	private static Node findLowestFNode(List<Node> nodes){

		List<Float> flist = new ArrayList<Float>();
		
		for(int i = 0; i < nodes.size(); i++){
			flist.add((nodes.get(i).f*10000)+i);
		}
		
		return nodes.get(flist.indexOf(Collections.min(flist)));
	}
	
	/*public int[][][] getTrinomeConduitMap(World world, Pos start, Material material, int maxPathSize){
		
		int length = 1 + (maxPathSize*2);
		
		int[][][] ret = new int[length][length][length];
		
		Pos mapCenter = new Pos(maxPathSize+1,maxPathSize+1,maxPathSize+1);
		
		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				for(int k = 0; k < length; k++){
					ret[i][j][k] = maxPathSize+2;
				}
			}
		}
		
		ret[maxPathSize+1][maxPathSize+1][maxPathSize+1] = -1;
		
		List<Pos> currentCheck = new ArrayList<Pos>();
		currentCheck.add(mapCenter);
		
		List<Pos> finalizedChecks = new ArrayList<Pos>();
		finalizedChecks.add(mapCenter);
		
		for(int i = -1; i < 5; i++){
			for(int j = 0; j < currentCheck.size(); j++){
				Pos cur = relativePosToReal(currentCheck.get(j), start, mapCenter);
				Pos alt = cur.inDirection(ForgeDirection.UP);
				if(world.getBlock(alt.x, alt.y, alt.z).getMaterial() == material){
					Pos rel = realPosToRelative(alt, start, mapCenter);
					if(ret[rel.x][rel.y][rel.z] > ret[cur.x][cur.y][cur.z]+1){
						ret[rel.x][rel.y][rel.z]
					}
				}
				alt = cur.inDirection(ForgeDirection.DOWN);
				alt = cur.inDirection(ForgeDirection.NORTH);
				alt = cur.inDirection(ForgeDirection.SOUTH);
				alt = cur.inDirection(ForgeDirection.EAST);
				alt = cur.inDirection(ForgeDirection.WEST);
			}
		}
		
		return ret;
	}
	
	private Pos realPosToRelative(Pos real, Pos realCenter, Pos relativeCenter){
		return relativeCenter.addPos(realCenter.subtractPos(real));
	}
	
	private Pos relativePosToReal(Pos relative, Pos realCenter, Pos relativeCenter){
		return realCenter.addPos(relativeCenter.subtractPos(relative));
	}*/
	
	public List<Pos> getRangedConduitMap(World world, Pos start, Material material, int maxPathSize){
		List<Pos> map = new ArrayList<Pos>();
		
		List<Pos> checked = new ArrayList<Pos>();
		List<Pos> checking = new ArrayList<Pos>();
		List<Pos> tocheck = new ArrayList<Pos>();
		int checkingIterations = 0;
		
		checking.add(start);
		
		for(checkingIterations = 0; checkingIterations < maxPathSize; checkingIterations++){
			for(int i = 0; i < checking.size(); i++){
				Pos pos = checking.get(i);
				for(int x = -1; x <= 1; x++){
					for(int y = -1; y <= 1; y++){
						for(int z = -1; z <= 1; z++){
							//if((Math.abs(x) != Math.abs(y)) && (Math.abs(x) != Math.abs(z)) && (Math.abs(y)!=Math.abs(z))){
							//if(Math.abs(x)+Math.abs(y)+Math.abs(z) == 1){
								if(pos.addPos(x, y, z).getBlock(world).getMaterial() == material){
									boolean flag = true;
									for(int c = 0; c <checked.size(); c++){
										if(checked.get(c).isSame(pos.addPos(x, y, z))){
											flag = false;
										}
									}
									if(flag){
										boolean flag1 = true;
										for(int n = 0; n < tocheck.size(); n++){
											if(tocheck.get(n).isSame(pos)){
												flag1 = false;
												break;
											}
										}
										if(flag1) tocheck.add(pos.addPos(x, y, z));
										map.add(pos.addPos(x, y, z));
									}
								}
							//}
						}
					}
				}
				checked.add(checking.get(i));
				checking.remove(i);
			}
			if(checking.size() > 0){
				for(int c = 0; c < checking.size(); c++){
					checked.add(checking.get(c));
					checking.remove(c);
				}
			}
			if(tocheck.size()>0){
				for(int c = 0; c < tocheck.size(); c++){
					checking.add(tocheck.get(c));
					tocheck.remove(c);
				}
			}
		}
		
		return map;
	}
	
	public static float getDistance(Pos pos1, Pos pos2){
		float ret = (Math.abs(pos2.x-pos1.x)^2)+(Math.abs(pos2.y-pos1.y)^2)+(Math.abs(pos2.z-pos1.z)^2);
		ret = (float) Math.sqrt(ret);
		return ret;
	}
	
	public static class Node{
		public Node parent;
		
		public int x; public int y; public int z;
		public float f; public float g; public float h;
		
		public Node(int i, int j, int k, float a, float b, float c){
			x = i; y = j; z = k; f = a; g = b; h = c;
		}
		
		public Node(Pos pos, float a, float b, float c){
			x = pos.x; y = pos.y; z = pos.z; f = a; g = b; h = c;
		}
		
		public Node(Pos pos){
			x = pos.x; y = pos.y; z = pos.z; f = 0; g = 0; h = 0;
		}
		
		public Pos getPos(){
			return new Pos(x,y,z);
		}
		
		public Node setParent(Node par){
			this.parent = par;
			return this;
		}
		
		public int getPathLength(){
			Node cur = this;
			int ret = 0;
			
			while(cur != null){
				cur = cur.parent;
				ret++;
			}
			
			return ret;
		}
	}
	
	public static class Pos{
		public int x;
		public int y;
		public int z;
		
		public Pos(int i, int j, int k){
			x = i;
			y = j;
			z = k;
		}
		
		public Pos addPos(int i, int j, int k){
			return new Pos(x+i,y+j,z+k);
		}
		
		public Pos addPos(Pos pos){
			return new Pos(x+pos.x,y+pos.y,z+pos.z);
		}
		
		public Pos subtractPos(int i, int j, int k){
			return new Pos(x-i,y-j,z-k);
		}
		
		public Pos subtractPos(Pos pos){
			return new Pos(x-pos.x,y-pos.y,z-pos.z);
		}
		
		public Pos inDirection(ForgeDirection dir){
			return new Pos(x+dir.offsetX,y+dir.offsetY,z+dir.offsetZ);
		}
		
		public Block getBlock(World world){
			return world.getBlock(x, y, z);
		}
		
		public boolean isSame(Pos pos){
			return ((this.x==pos.x)&&(this.y==pos.y)&&(this.z==pos.z));
		}
		
		public Pos copy(){
			return new Pos(this.x,this.y,this.z);
		}
		
		@Override
		public String toString(){
			return "Pos:"+"x"+x+"y"+y+"z"+z;
		}
	}
	
}

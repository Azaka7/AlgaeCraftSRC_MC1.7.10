package azaka7.algaecraft.common.structures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.structures.Structure.BlockData;
import azaka7.algaecraft.common.structures.Structure.BlockType;
import azaka7.algaecraft.common.structures.Structure.TileData;
import azaka7.algaecraft.common.structures.Structure.WeightedChildList;

public class StructureLoader {
	
	public static final StructureLoader INSTANCE = new StructureLoader();
	private static Integer nextNewCGH = 0;
	private ArrayList<Object> thisObjects;
	private static boolean isReadingParent = false;
	
	public Structure loadStructure(String file){
		if(ACStructures.isStructureLoaded(file)){
			return ACStructures.loadStructure(file);
		}
		FMLLog.info("[AlgaeCraft] Loading Structure: " +file);
		thisObjects = new ArrayList<Object>();
		try {
			//BufferedReader readIn = new BufferedReader(new InputStreamReader(getClass().getClassLoader()
			            //.getResourceAsStream("assets/algaecraft/structures/"+file+".acs"), "UTF-8"));
			File targetFile = new File("config/algaecraft/structures/"+file+".acs");
			if(!targetFile.exists()){
				//System.out.println("attempting to load default");
				Structure ret =  loadStructureInit(file);
				this.loadDefault(file);
				return ret;
			}
			FileReader readIn = new FileReader(targetFile);
			Scanner reader = new Scanner(readIn);
			int l = 1;
			//System.out.println("start lines for "+file);
			while(reader.hasNextLine()){
				readLine(file, reader.nextLine(), l);
				l++;
			}
			
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Structure ret = new Structure(thisObjects.toArray());
		//System.out.println("made: "+ret);
		ACStructures.saveStructure(file, ret);
		return ret;
	}
	
	public Structure loadStructureInit(String file){
		FMLLog.info("[AlgaeCraft] Initializing Structure: " +file);
		thisObjects = new ArrayList<Object>();
		try {
			BufferedReader readIn = new BufferedReader(new InputStreamReader(getClass().getClassLoader()
			            .getResourceAsStream("assets/algaecraft/structures/"+file+".acs"), "UTF-8"));
			Scanner reader = new Scanner(readIn);
			int l = 1;
			while(reader.hasNextLine()){
				readLine(file, reader.nextLine(), l);
				l++;
			}
			
			reader.close();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new Structure(thisObjects.toArray());
	}
	
	public void readLine(String file, String line, int l){
		char type = line.charAt(0);
		if(file.contains("store")){
			//System.out.println("type: "+type);
		}
		switch(type){
		case 'a': handleAir(file, line, l); break;
		case 'b': handleBlock(file, line, l); break;
		case 'c': handleContainer(file, line, l); break;
		case 't': handleEnsureChestGen(file, line, l); break;
		case 'p': handleReadParent(file, line, l); break;
		case 'r': handleRandomSubtype(file, line, l); break;
		default: return;
		}
	}
	
	private void handleRandomSubtype(String file, String line, int l) {
		Scanner rLine = new Scanner(line);
		WeightedChildList children = new WeightedChildList();
		Structure structure = null;
		int p = 0;
		BlockPos pos = new BlockPos(0,0,0);
		//System.out.println("Loading subtypes: "+file+" \""+line+"\" "+l);
		while(rLine.hasNext()){
			String arg = rLine.next();
			//System.out.println(arg);
			if(p == 0){
				if(!arg.equals("r")){rLine.close();
				return;}
			} else {
				if(this.isReadingParent){
					try{
						throw new InvalidStructureFormatException("Loading of grand child structures is unsupported.", file, l, line, p, arg);
					} catch(InvalidStructureFormatException isfe){
						isfe.printStackTrace();
						rLine.close();
						return;
					}
				}
				int weight = 0;
				if(p % 2 == 1){
					//System.out.println("subtype = "+arg);
					String afile = arg;
					this.isReadingParent = true;
					structure = (new StructureLoader()).loadStructure(afile);
					this.isReadingParent = false;
				} else {
					try{
						//System.out.println("structure to be weighted: "+structure);
						weight = Integer.parseInt(arg);
						children.addWeightedChild(structure, weight);
					} catch(Exception e) {
						try{
							throw new InvalidStructureFormatException("Structure child weight value must be an integer.", file, l, line, p, arg);
						} catch(InvalidStructureFormatException isfe){
							isfe.printStackTrace();
						}
					}
				}
			}
			p++;
		}
		children.finalize();
		thisObjects.add(children);
		rLine.close();
	}

	private void handleAir(String file, String line, int l){
		Scanner rLine = new Scanner(line);
		int p = 0;
		BlockPos pos = new BlockPos(0,0,0);
		while(rLine.hasNext()){
			String arg = rLine.next();
			switch(p){
			case 0: {
				if(!arg.equals("a")){rLine.close();
					return;} break;
			}
			case 1:
			case 2:
			case 3: {
				try{
					int c = Integer.parseInt(arg);
					if(p==1){
						pos = pos.add(c, 0, 0);
					} else if(p==2) {
						pos = pos.add(0, c, 0);
					} else if(p==3) {
						pos = pos.add(0, 0, c);
					}
				} catch(Exception e){
					try{
						throw new InvalidStructureFormatException("Coords must be an integer. File: "+file+"Line "+l+": \""+line+"\" Arg "+p+": "+arg);
					} catch (InvalidStructureFormatException isfe){
						isfe.printStackTrace();
					}
				}
				break;
			}
			default: {
				try{
					throw new InvalidStructureFormatException("Too many args for adding air. File: "+file+"Line "+l+": \""+line+"\" Arg "+p+": "+arg);
				} catch(Exception e){
					e.printStackTrace();
				}
				
			}
				
			}
			p++;
		}
		this.addBlock(pos, new BlockData());
		rLine.close();
	}
	
	private void handleBlock(String file, String line, int l){
		Scanner rLine = new Scanner(line);
		int p = 0;
		BlockPos pos = new BlockPos(0,0,0);
		Block block = Blocks.air;
		int meta = 0;
		while(rLine.hasNext()){
			String arg = rLine.next();
			switch(p){
			case 0: {
				if(!arg.equals("b")){rLine.close();
					return;} break;
			}
			case 1:
			case 2:
			case 3: {
				try{
					int c = Integer.parseInt(arg);
					if(p==1){
						pos = pos.add(c, 0, 0);
					} else if(p==2) {
						pos = pos.add(0, c, 0);
					} else if(p==3) {
						pos = pos.add(0, 0, c);
					}
				} catch(Exception e){
					try{
						throw new InvalidStructureFormatException("Coords must be an integer. File: "+file+"Line "+l+": \""+line+"\" Arg "+p+": "+arg);
					} catch (InvalidStructureFormatException isfe){
						isfe.printStackTrace();
					}
				}
				break;
			}
			case 4: {
				if(arg.startsWith("\"")){
					BlockType type = BlockType.getTypeFromString(arg.substring(1, arg.length()-1));
					this.addBlock(pos, new BlockData(type));
					rLine.close();
					return;
				} else {
					Block thisBlock = Block.getBlockFromName(arg);
					if(thisBlock == null){
						try{
							throw new InvalidStructureFormatException("Invalid block ID. File: "+file+"Line "+l+": \""+line+"\" Arg "+p+": "+arg);
						} catch(Exception e){
							e.printStackTrace();
							rLine.close();
							return;
						}
					}
					block = thisBlock;
				}
				break;
			}
			case 5: {
				try{
					meta = Integer.parseInt(arg);
				} catch(Exception e){
					try{
						throw new InvalidStructureFormatException("Metadata must be an integer. File: "+file+"Line "+l+": \""+line+"\" Arg "+p+": "+arg);
					} catch (InvalidStructureFormatException isfe){
						isfe.printStackTrace();
					}
					meta = 0;
				}
				break;
			}
			default: {
				try{
					throw new InvalidStructureFormatException("Too many args for adding a block. File: "+file+"Line "+l+": \""+line+"\" Arg "+p+": "+arg);
				} catch(Exception e){
					e.printStackTrace();
				}
				
			}
				
			}
			p++;
		}
		this.addBlock(pos, new BlockData(block, meta));
		rLine.close();
	}
	
	private void handleContainer(String file, String line, int l){
		Scanner rLine = new Scanner(line);
		int lpos = 0;
		BlockPos pos = new BlockPos(0,0,0);
		NBTTagCompound nbt = null;
		TileEntity te = null;
		Block block = Blocks.air;
		int meta = 0;
		String id = "null";
		boolean flag = false;
		ChestGenHooks cgh = null;
		while(rLine.hasNext()){
			String arg = rLine.next();
			switch(lpos){
			case 0: {
				if(!arg.equals("c")){rLine.close();
					return;} break;
			}
			case 1:
			case 2:
			case 3: {
				try{
					int c = Integer.parseInt(arg);
					if(lpos==1){
						pos = pos.add(c, 0, 0);
					} else if(lpos==2) {
						pos = pos.add(0, c, 0);
					} else if(lpos==3) {
						pos = pos.add(0, 0, c);
					}
				} catch(Exception e){
					try{
						throw new InvalidStructureFormatException("Coords must be an integer. File: "+file+"Line "+l+": \""+line+"\" Arg "+lpos+": "+arg);
					} catch (InvalidStructureFormatException isfe){
						isfe.printStackTrace();
					}
				}
				break;
			}
			case 4:{
					id = arg;
					//System.out.println("id: "+id);
					nbt = new NBTTagCompound();
					nbt.setString("id", id);
					nbt.setInteger("x", pos.getX());
					nbt.setInteger("y", pos.getY());
					nbt.setInteger("z", pos.getZ());
						try{
							te = TileEntity.createAndLoadEntity(nbt);
						} catch (Exception e){
							try{
								throw new InvalidStructureFormatException("Unable to identify TileEntity. File: "+file+"Line "+l+": \""+line+"\" Arg "+lpos+": "+arg);
							} catch (InvalidStructureFormatException isfe){
								isfe.printStackTrace();
								return;
							}
						}
				break;
			}
			case 5:{
				String cghI = arg;
				//System.out.print("inv: "+cghI);
				if(arg == "{"){
					flag = true;
					cghI = "acs"+nextNewCGH;
					nextNewCGH++;
				}
				cgh = ChestGenHooks.getInfo(cghI);
				//System.out.println(" | "+cgh.getItems(new Random()).length);
				break;
			}
			default:{
				if(!flag){break;}
				byte stackPart = (byte) ((lpos - 6) % 4);
				Item item = null;
				int min = 0, max = 0;
				int metadata = 0;
				if(stackPart == 0){
					item = (Item) Item.itemRegistry.getObject(arg);
				} else if(stackPart == 1){
					if(arg.contains("|")){
						Scanner lims = new Scanner(arg);
						lims.useDelimiter("|");
						min = lims.nextInt();
						max = lims.nextInt();
						lims.close();
					} else {
						min = Integer.parseInt(arg);
						max = min;
					}
				} else if(stackPart == 2){
					metadata = Integer.parseInt(arg);
				} else {
					int weight = Integer.parseInt(arg);
					boolean sFlag = min == max;
					//System.out.println("adding item");
					cgh.addItem(new WeightedRandomChestContent(new ItemStack(item, metadata, sFlag ? min : 1), sFlag ? 1 : min, sFlag ? 1 : max, weight));
				}
				
			}
			}
			lpos++;
		}
		Structure.TileData tileData = new Structure.TileData(te, cgh);
		this.addTile(pos, tileData);
		rLine.close();
	}
	
	private void handleReadParent(String file, String line, int l){
		//System.out.println("handle parent?");
		Scanner rLine = new Scanner(line);
		int p = 0;
		while(rLine.hasNext()){
			String arg = rLine.next();
			switch(p){
				case 0: {
					if(!arg.equals("p")){rLine.close();
					return;} 
					//System.out.println("handle parent.");
					break;
				}
				case 1: {
					if(isReadingParent){
						try{
							throw new InvalidStructureFormatException("Unsafe to attempt grandparent files. Skipping. File: "+file+"Line "+l+": \""+line+"\" Arg "+p+": "+arg);
						} catch (InvalidStructureFormatException isfe){
							isfe.printStackTrace();
							return;
						}
					} else if(!(arg.equals(file))){
						//System.out.println("start parent");
						isReadingParent = true;
						this.loadStructure(arg);
						isReadingParent = false;
						//System.out.println("end parent");
					} else {
						try{
							throw new InvalidStructureFormatException("Cannot refer to self as parent. File: "+file+"Line "+l+": \""+line+"\" Arg "+p+": "+arg);
						} catch (InvalidStructureFormatException isfe){
							isfe.printStackTrace();
							return;
						}
					}
					return;
				}
				default: break;
			}
			p++;
		}
		rLine.close();
	}
	
	private void handleEnsureChestGen(String file, String line, int l){
		//System.out.println("ensure chest gen?");
		Scanner rLine = new Scanner(line);
		int lpos = 0;
		Item item = null;
		int min = 0, max = 0;
		int size = 0;
		int metadata = 0;
		boolean flag = false;
		ChestGenHooks cgh = null;
		while(rLine.hasNext()){
			String arg = rLine.next();
			switch(lpos){
			case 0: {
				//System.out.println("ensure chest gen");
				if(!arg.equals("t")){rLine.close();
					return;}
				break;
			}
			case 1:{
				String cghI = arg;
				//System.out.println(cghI);
				cgh = ChestGenHooks.getInfo(cghI);
				try{
					if(cgh.getOneItem(new Random()) == null){
						flag = true;
					} 
				}catch(IllegalArgumentException e){
					//Intentional error
					flag = true;
				}
				break;
			}
			case 2:{
				int trueMin = 1;
				try{
					trueMin = Integer.parseInt(arg);
					} catch(NumberFormatException e){
						try{
							throw new InvalidStructureFormatException("Minimum must be an integer. File: "+file+"Line "+l+": \""+line+"\" Arg "+lpos+": "+arg);
						} catch(InvalidStructureFormatException isfe) {
							isfe.printStackTrace();
						}
					}
				cgh.setMin(trueMin);
				break;
			}
			case 3:{
				int trueMax = 1;
				try{
					trueMax = Integer.parseInt(arg);
					} catch(NumberFormatException e){
						try{
							throw new InvalidStructureFormatException("Maximum must be an integer. File: "+file+"Line "+l+": \""+line+"\" Arg "+lpos+": "+arg);
						} catch(InvalidStructureFormatException isfe) {
							isfe.printStackTrace();
						}
					}
				cgh.setMin(trueMax);
				break;
			}
			default:{
				if(!flag){break;}
				int stackPart = ((lpos - 4) % 6);
				if(stackPart == 0){
					//System.out.println(arg);
					item = (Item) Item.itemRegistry.getObject(arg);
					if(item == null){
						try{
							throw new InvalidStructureFormatException("Invalid item. File: "+file+" Line "+l+": \""+line+"\" Arg "+lpos+": "+arg);
						} catch (InvalidStructureFormatException isfe){
							isfe.printStackTrace();
						}
					}
				} else if(stackPart == 1){
					try{
					size = Integer.parseInt(arg);
					} catch(NumberFormatException e){
						try{
							throw new InvalidStructureFormatException("Size must be an integer. File: "+file+"Line "+l+": \""+line+"\" Arg "+lpos+": "+arg);
						} catch(InvalidStructureFormatException isfe) {
							isfe.printStackTrace();
						}
					}
					//System.out.println("size: "+size);
				} else if(stackPart == 2){
					try{
					metadata = Integer.parseInt(arg);
					} catch(NumberFormatException e){
						try{
							throw new InvalidStructureFormatException("Metadata must be an integer. File: "+file+"Line "+l+": \""+line+"\" Arg "+lpos+": "+arg);
						} catch(InvalidStructureFormatException isfe) {
							isfe.printStackTrace();
						}
					}
					//System.out.println("meta: "+metadata);
				} else if(stackPart == 3){
					try{
					min = Integer.parseInt(arg);
					} catch(NumberFormatException e){
						try{
							throw new InvalidStructureFormatException("Stack Minimum must be an integer. File: "+file+"Line "+l+": \""+line+"\" Arg "+lpos+": "+arg);
						} catch(InvalidStructureFormatException isfe) {
							isfe.printStackTrace();
						}
					}
				} else if(stackPart == 4){
					try{
					max = Integer.parseInt(arg);
					} catch(NumberFormatException e){
						try{
							throw new InvalidStructureFormatException("Stack Maximum must be an integer. File: "+file+"Line "+l+": \""+line+"\" Arg "+lpos+": "+arg);
						} catch(InvalidStructureFormatException isfe) {
							isfe.printStackTrace();
						}
					}
				} else {
					int weight = 0;
					try{
						weight = Integer.parseInt(arg);
					} catch(Exception e){
						try{
							throw new InvalidStructureFormatException("Weight must be an integer. File: "+file+"Line "+l+": \""+line+"\" Arg "+lpos+": "+arg);
						} catch(InvalidStructureFormatException isfe) {
							isfe.printStackTrace();
						}
					}
					//System.out.println("adding item");
					ItemStack theStack = new ItemStack(item, size, metadata);
					WeightedRandomChestContent wrcc = new WeightedRandomChestContent(theStack , min, max, weight);
					cgh.addItem(wrcc);
					//System.out.println(""+lpos+") "+stackPart+") "+item);
					//System.out.println(theStack);
					//System.out.println(wrcc.toString());
					//System.out.println(cgh.getItems(new Random()).length);
					//System.out.println(cgh.getOneItem(new Random()));
				}
			}
			}
			lpos++;
		}
		//System.out.println(cgh.getItems(new Random()).length);
		//System.out.println(cgh.getOneItem(new Random()));
		rLine.close();
	}
	
	private void addBlock(BlockPos pos, BlockData block){
		thisObjects.add(pos);
		thisObjects.add(block);
	}
	
	private void addTile(BlockPos pos, TileData tile){
		thisObjects.add(pos);
		thisObjects.add(tile);
	}
	
	private void loadDefault(String file){
		try {
			BufferedReader readIn = new BufferedReader(new InputStreamReader(getClass().getClassLoader()
			        .getResourceAsStream("assets/algaecraft/structures/"+file+".acs"), "UTF-8"));
			Scanner reader = new Scanner(readIn);

			File targetFile = new File("config/algaecraft/structures/"+file+".acs");
			File parent = targetFile.getParentFile();
			if(!parent.exists() && !parent.mkdirs()){
				reader.close();
				throw new IllegalStateException("Couldn't create dir: "+parent);
			}
			FileWriter writer = new FileWriter(targetFile);
			while(reader.hasNextLine()){
				writer.append(reader.nextLine());
				if(reader.hasNextLine()){
					writer.append('\n');
				}
			}
			
			writer.close();
			reader.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class InvalidStructureFormatException extends Exception{
		public InvalidStructureFormatException(){
			super();
		}
		
		public InvalidStructureFormatException(String s){
			super(s);
		}
		
		public InvalidStructureFormatException(String message, String file, int l, String line, int p, String arg){
			this(message+" File: "+file+"Line "+l+": \""+line+"\" Arg "+p+": "+arg);
		}
	}
}

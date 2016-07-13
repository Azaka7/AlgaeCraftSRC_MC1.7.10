package azaka7.algaecraft.common.commands;

import java.util.ArrayList;

import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.structures.CenteredStructureCreator;
import azaka7.algaecraft.common.structures.Structure;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandExportStructure extends CommandBase{

	@Override
	public String getCommandName() {
		return "acexport";
	}
	
	public int getRequiredPermissionLevel()
    {
        return 2;
    }

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/acexport <x1> <y1> <z1> <x2> <y2> <z2> <xC> <yC> <zC> <name>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(sender.getEntityWorld() == null){
			sender.addChatMessage(new ChatComponentText("Command could not be processed."));
			return;
		}
		try{
			int x1 = Integer.parseInt(args[0]);
			int y1 = Integer.parseInt(args[1]);
			int z1 = Integer.parseInt(args[2]);
			
			int x2 = Integer.parseInt(args[3]);
			int y2 = Integer.parseInt(args[4]);
			int z2 = Integer.parseInt(args[5]);
			
			int xC = Integer.parseInt(args[6]);
			int yC = Integer.parseInt(args[7]);
			int zC = Integer.parseInt(args[8]);
			
			String fileName = args[9];
			
			ArrayList<Block> blocks = new ArrayList<Block>();
			for(int i = 10; i < args.length; i++){
				try{
					blocks.add((Block) Block.blockRegistry.getObject(args[i]));
				} catch(Exception e){}
			}
			
			CenteredStructureCreator csc = new CenteredStructureCreator(xC,yC,zC);

			int xi = Math.min(x1, x2), xf = Math.max(x1, x2);
			int yi = Math.min(y1, y2), yf = Math.max(y1, y2);
			int zi = Math.min(z1, z2), zf = Math.max(z1, z2);
			int count = 0;
			for(int x = xi; x <= xf; x++){
				for(int y = yi; y <= yf; y++){
					for(int z = zi; z <= zf; z++){
						count ++;
						Block block = sender.getEntityWorld().getBlock(x, y, z);
						if(blocks.contains(block)){
						} else {
							Structure.BlockData data = new Structure.BlockData(block, sender.getEntityWorld().getBlockMetadata(x, y, z));
							csc.setBlock(x,y,z, data);
						}
					}
				}
			}
			System.out.println(count);
			Structure structure = csc.createStructure();
			structure.exportAsText(fileName);
			sender.addChatMessage(new ChatComponentText("Exported selected region as "+fileName+".acs"));
			
		}catch(Exception e){
			FMLLog.info("[AlgaeCraft] Failed to export structure.");
			e.printStackTrace();
		}
		
	}
	
	private static int abs(int i){
		return i < 0 ? i * -1 : i;
	}

}

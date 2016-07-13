package azaka7.algaecraft.common.commands;

import java.util.ArrayList;
import java.util.Set;

import azaka7.algaecraft.common.blocks.BlockPos;
import azaka7.algaecraft.common.structures.CenteredStructureCreator;
import azaka7.algaecraft.common.structures.Structure;
import azaka7.algaecraft.common.structures.StructureHandler;
import azaka7.algaecraft.common.structures.Structure.TileData;
import azaka7.algaecraft.common.structures.StructureAccess;
import azaka7.algaecraft.common.structures.StructureLoader;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandImportStructure extends CommandBase{

	@Override
	public String getCommandName() {
		return "acimport";
	}
	
	public int getRequiredPermissionLevel()
    {
        return 2;
    }

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/acimport <x1> <y1> <z1> <name>";
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
			
			String fileName = args[3];
			
			int rotations = 0;
			if(args.length > 4){
				rotations = Integer.parseInt(args[4]) % 4;
			}
			
			Structure structure = StructureLoader.INSTANCE.loadStructure(fileName);
			
			StructureHandler.generateStructure(sender.getEntityWorld(), new BlockPos(x1, y1, z1), structure, rotations);
			
			sender.addChatMessage(new ChatComponentText("Imported selected region as "+fileName+".acs"));
			
		}catch(Exception e){
			FMLLog.info("[AlgaeCraft] Failed to export structure.");
			e.printStackTrace();
		}
		
	}
	
	private static int abs(int i){
		return i < 0 ? i * -1 : i;
	}

}

package azaka7.algaecraft.common;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.handlers.ACEventHandler;
import azaka7.algaecraft.common.handlers.ACInterModHandler;
import azaka7.algaecraft.common.tileentity.TileEntityAirCompressor;
import azaka7.algaecraft.common.tileentity.TileEntityBrazier;
import azaka7.algaecraft.common.tileentity.TileEntityCage;
import azaka7.algaecraft.common.tileentity.TileEntityWaterFilter;

public class CommonProxy {

	public void registerCommon(){
		GameRegistry.registerTileEntity(TileEntityAirCompressor.class, "AirCompressor_AC");
		GameRegistry.registerTileEntity(TileEntityCage.class, "LobsterTrap_AC");
		GameRegistry.registerTileEntity(TileEntityWaterFilter.class, "WaterFilter_AC");
		GameRegistry.registerTileEntity(TileEntityBrazier.class, "Brazier_AC");
		
		if(AlgaeCraft.thermalExpansion() && ACGameData.enableRFDevices){
			ACInterModHandler.registerRFTileEntities();
		}
		
		ACEventHandler.INSTANCE.initialize();
	}
	
	public void registerRenders(){}

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
	 return ctx.getServerHandler().playerEntity;
	}

}

package azaka7.algaecraft.common;

import cpw.mods.fml.common.registry.GameRegistry;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.handlers.ACEventHandler;
import azaka7.algaecraft.common.handlers.ACInterModHandler;
import azaka7.algaecraft.common.tileentity.TileEntityAirCompressor;
import azaka7.algaecraft.common.tileentity.TileEntityCage;
import azaka7.algaecraft.common.tileentity.TileEntityWaterFilter;

public class CommonProxy {

	public void registerCommon(){
		GameRegistry.registerTileEntity(TileEntityAirCompressor.class, "AirCompressor_AC");
		GameRegistry.registerTileEntity(TileEntityCage.class, "LobsterTrap_AC");
		GameRegistry.registerTileEntity(TileEntityWaterFilter.class, "WaterFilter_AC");
		
		if(AlgaeCraft.thermalExpansion() && ACGameData.enableRFDevices){
			ACInterModHandler.registerRFTileEntities();
		}
		
		ACEventHandler.INSTANCE.initialize();
	}
	
	public void registerRenders(){}

}

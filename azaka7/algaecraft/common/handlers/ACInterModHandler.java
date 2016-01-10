package azaka7.algaecraft.common.handlers;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.rf.ACThermalExpansionHandler;
import azaka7.algaecraft.common.thaumic.ACThaumicHandler;
import azaka7.algaecraft.common.tileentity.TileEntityAirCompressor;
import azaka7.algaecraft.common.tileentity.TileEntityAirCompressor_RF;
import azaka7.algaecraft.common.tileentity.TileEntityWaterFilter;

public class ACInterModHandler {
	public static TileEntity getRFAirComporessor(){
		if(AlgaeCraft.thermalExpansion() && ACGameData.enableRFDevices){
			return ACThermalExpansionHandler.getRFAirComporessor();
		}
		return new TileEntityAirCompressor();
	}
	
	public static void registerRFItems(){
		if(AlgaeCraft.thermalExpansion() && ACGameData.enableRFItems){
			ACThermalExpansionHandler.registerRFItems();
		}
	}
	
	public static void registerThumcraftCompatability(){
		if(AlgaeCraft.thaumcraft()){
			ACThaumicHandler.register();
		}
	}
	
	public static void registerThaumicItems(){
		if(AlgaeCraft.thaumcraft() && ACGameData.enableThaumicItems){
			ACThaumicHandler.registerThaumicItems();
		}
	}
	
	public static Item getItem_Thaum(Item item0){
		if(!AlgaeCraft.thaumcraft()){
			return item0;
		}
		else{
			return ACThaumicHandler.getItem_Thaum(item0);
		}
	}

	public static void registerRFTileEntities() {
		if(AlgaeCraft.thermalExpansion() && ACGameData.enableRFDevices){
			GameRegistry.registerTileEntity(TileEntityAirCompressor_RF.class, "AirCompressorRF_AC");
		}
		
	}
}

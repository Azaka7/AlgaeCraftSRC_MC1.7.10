package azaka7.algaecraft.common.rf;

import net.minecraft.tileentity.TileEntity;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.tileentity.TileEntityAirCompressor;
import azaka7.algaecraft.common.tileentity.TileEntityAirCompressor_RF;

public class ACThermalExpansionHandler {
	
	public static TileEntity getRFAirComporessor(){
		if(AlgaeCraft.thermalExpansion()){
			return new TileEntityAirCompressor_RF();
		}
		return new TileEntityAirCompressor();
	}

	public static void registerRFItems() {
		
	}
	
}

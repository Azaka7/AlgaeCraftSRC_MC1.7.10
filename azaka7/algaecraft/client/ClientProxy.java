package azaka7.algaecraft.client;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.client.model.ModelFish;
import azaka7.algaecraft.client.model.ModelLobster;
import azaka7.algaecraft.client.renderer.entity.RenderFish;
import azaka7.algaecraft.client.renderer.entity.RenderLobster;
import azaka7.algaecraft.client.renderer.tileentity.TileEntityRendererAirCompressor;
import azaka7.algaecraft.client.renderer.tileentity.TileEntityRendererLobsterCage;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.CommonProxy;
import azaka7.algaecraft.common.entity.EntityFish;
import azaka7.algaecraft.common.entity.EntityLobster;
import azaka7.algaecraft.common.handlers.ACInterModHandler;
import azaka7.algaecraft.common.tileentity.TileEntityAirCompressor;
import azaka7.algaecraft.common.tileentity.TileEntityAirCompressor_RF;
import azaka7.algaecraft.common.tileentity.TileEntityCage;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenders(){
		RenderingRegistry.registerEntityRenderingHandler(EntityLobster.class, new RenderLobster(new ModelLobster(), 0.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFish.class, new RenderFish(new ModelFish(), 0.5F));
		ClientRegistry.registerTileEntity(TileEntityCage.class, "lobsterCageRender", (TileEntitySpecialRenderer) new TileEntityRendererLobsterCage());
		ClientRegistry.registerTileEntity(TileEntityAirCompressor.class, "airCompressorRender", (TileEntitySpecialRenderer) new TileEntityRendererAirCompressor());
		if(AlgaeCraft.thermalExpansion()){
			ClientRegistry.registerTileEntity(ACInterModHandler.getRFAirComporessor().getClass(), "airCompressorRender_RF", (TileEntitySpecialRenderer) new TileEntityRendererAirCompressor());
		}
		RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.algaeModelID,false,"algae"));
		RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.coralModelID,false,"coral"));
		RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.spongeModelID,false,"sponge"));
		RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.seaweedModelID, false,"seaweed"));
		//RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.waterBlockModelID, false,"water"));
	}
}

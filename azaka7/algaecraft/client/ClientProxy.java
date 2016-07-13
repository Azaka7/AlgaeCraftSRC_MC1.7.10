package azaka7.algaecraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.client.model.ModelFish;
import azaka7.algaecraft.client.model.ModelLobster;
import azaka7.algaecraft.client.renderer.entity.RenderFish;
import azaka7.algaecraft.client.renderer.entity.RenderLobster;
import azaka7.algaecraft.client.renderer.tileentity.TileEntityRendererAirCompressor;
import azaka7.algaecraft.client.renderer.tileentity.TileEntityRendererBrazier;
import azaka7.algaecraft.client.renderer.tileentity.TileEntityRendererLobsterCage;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.CommonProxy;
import azaka7.algaecraft.common.entity.EntityFish;
import azaka7.algaecraft.common.entity.EntityGreekFire;
import azaka7.algaecraft.common.entity.EntityGreekFireBomb;
import azaka7.algaecraft.common.entity.EntityLobster;
import azaka7.algaecraft.common.handlers.ACInterModHandler;
import azaka7.algaecraft.common.items.ACItems;
import azaka7.algaecraft.common.tileentity.TileEntityAirCompressor;
import azaka7.algaecraft.common.tileentity.TileEntityAirCompressor_RF;
import azaka7.algaecraft.common.tileentity.TileEntityBrazier;
import azaka7.algaecraft.common.tileentity.TileEntityCage;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenders(){
		RenderingRegistry.registerEntityRenderingHandler(EntityLobster.class, new RenderLobster(new ModelLobster(), 0.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFish.class, new RenderFish(new ModelFish(), 0.5F));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityGreekFireBomb.class, new RenderSnowball(ACItems.greekFireBomb));
		RenderingRegistry.registerEntityRenderingHandler(EntityGreekFire.class, new RenderSnowball(ACItems.greekFireFlask, 1));
		
		ClientRegistry.registerTileEntity(TileEntityCage.class, "lobsterCageRender", (TileEntitySpecialRenderer) new TileEntityRendererLobsterCage());
		ClientRegistry.registerTileEntity(TileEntityAirCompressor.class, "airCompressorRender", (TileEntitySpecialRenderer) new TileEntityRendererAirCompressor());
		ClientRegistry.registerTileEntity(TileEntityBrazier.class, "brazierRender", (TileEntitySpecialRenderer) new TileEntityRendererBrazier());
		if(AlgaeCraft.thermalExpansion()){
			ClientRegistry.registerTileEntity(ACInterModHandler.getRFAirComporessor().getClass(), "airCompressorRender_RF", (TileEntitySpecialRenderer) new TileEntityRendererAirCompressor());
		}
		RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.algaeModelID,false,"algae"));
		RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.coralModelID,false,"coral"));
		RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.spongeModelID,false,"sponge"));
		RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.seaweedModelID, false,"seaweed"));
		RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.greekFireModelID, false,"greekfire"));
		RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.brazierModelID, false,"brazier"));
		//RenderingRegistry.registerBlockHandler(new RenderBlockSimpleHandler(ACGameData.waterBlockModelID, false,"water"));
	}
	
	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
	 // Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
	 // your packets will not work because you will be getting a client
	 // player even when you are on the server! Sounds absurd, but it's true.

	 // Solution is to double-check side before returning the player:
	 return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}
}

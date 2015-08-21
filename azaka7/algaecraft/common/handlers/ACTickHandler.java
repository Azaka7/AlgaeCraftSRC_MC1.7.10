package azaka7.algaecraft.common.handlers;

import azaka7.algaecraft.common.items.ItemBCD;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class ACTickHandler {
	
	public static ACTickHandler INSTANCE = new ACTickHandler();
	
	public void initialize(){
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if(event.player.capabilities.isFlying && 
				!(event.player.inventory.armorItemInSlot(2) !=null && event.player.inventory.armorItemInSlot(2).getItem() instanceof ItemBCD) && 
					event.player.getEntityData().getBoolean(ItemBCD.bcdFlight)){
			event.player.capabilities.allowFlying = event.player.capabilities.isCreativeMode;
			event.player.capabilities.isFlying = false;
			event.player.capabilities.setFlySpeed(0.05F);
			event.player.getEntityData().setBoolean(ItemBCD.bcdFlight, false);
		}
	}
}
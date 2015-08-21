package azaka7.algaecraft.common.handlers;

import org.lwjgl.input.Keyboard;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.KeyBindingPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class ACKeyBindingHandler {
	
	public static final ACKeyBindingHandler INSTANCE = new ACKeyBindingHandler();
	
	public static KeyBinding bcdControlUp;
	 public static KeyBinding bcdControlDown;
	
	public void initialize(){
		bcdControlUp = new KeyBinding("key.bcdControl.up",Keyboard.KEY_X,"key.categories.gameplay");
		ClientRegistry.registerKeyBinding(bcdControlUp);
		bcdControlDown = new KeyBinding("key.bcdControl.down",Keyboard.KEY_Z,"key.categories.gameplay");
		ClientRegistry.registerKeyBinding(bcdControlDown);
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
			if(bcdControlUp.getIsKeyPressed()){
				KeyBindingPacket packet = new KeyBindingPacket(bcdControlUp);
				AlgaeCraft.NETWORK.sendToServer(packet);
				packet.handleClientSide(Minecraft.getMinecraft().thePlayer);
			}
			
			else if(bcdControlDown.getIsKeyPressed()){
				KeyBindingPacket packet = new KeyBindingPacket(bcdControlDown);
				AlgaeCraft.NETWORK.sendToServer(packet);
				packet.handleClientSide(Minecraft.getMinecraft().thePlayer);
			}
        
    }
	
}
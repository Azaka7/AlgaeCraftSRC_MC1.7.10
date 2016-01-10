package azaka7.algaecraft.common.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.KeyBindingPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;

public class ACKeyBindingHandler {
	
	public static final ACKeyBindingHandler INSTANCE = new ACKeyBindingHandler();
	
	public static KeyBinding bcdControlUp;
	 public static KeyBinding bcdControlDown;
	 
	public Map<String, Integer> keyCommands = new HashMap<String, Integer>();
	
	public void initialize(FMLPreInitializationEvent event){
		keyCommands.put("", 0);
		bcdControlUp = new KeyBinding("key.bcdControl.up",Keyboard.KEY_X,"key.categories.gameplay");
		ClientRegistry.registerKeyBinding(bcdControlUp);
		keyCommands.put("bcdUp", 1);
		bcdControlDown = new KeyBinding("key.bcdControl.down",Keyboard.KEY_Z,"key.categories.gameplay");
		ClientRegistry.registerKeyBinding(bcdControlDown);
		keyCommands.put("bcdDown", 2);
	}
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
			if(bcdControlUp.getIsKeyPressed()){
				KeyBindingPacket packet = new KeyBindingPacket(EnumKeyCommands.BCD_UP.ordinal());
				AlgaeCraft.NETWORK.sendToServer(packet);
				packet.handleClientSide(Minecraft.getMinecraft().thePlayer);
			}
			
			else if(bcdControlDown.getIsKeyPressed()){
				KeyBindingPacket packet = new KeyBindingPacket(EnumKeyCommands.BCD_DOWN.ordinal());
				AlgaeCraft.NETWORK.sendToServer(packet);
				packet.handleClientSide(Minecraft.getMinecraft().thePlayer);
			}
        
    }
	
	public enum EnumKeyCommands{
		NULL,
		BCD_UP,
		BCD_DOWN;
	}
	
}
package azaka7.algaecraft.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import azaka7.algaecraft.common.handlers.ACKeyBindingHandler;
import azaka7.algaecraft.common.items.ItemBCD;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class KeyBindingPacket extends AbstractPacket {
	
	//private static Map<Byte,Integer> bytMap = new HashMap();
	
	private int keyCode;
	
	public KeyBindingPacket() {
		keyCode = 0;
	}

	public KeyBindingPacket(int key) {
		keyCode = key;
	}
	/*
	public static void registerKeyBindToByte(KeyBinding key, byte byt){
		if(bytMap.containsValue(key.getKeyCode()) || 
				bytMap.containsKey(byt)){return;}
		
		bytMap.put(byt, key.getKeyCode());
	}
	
	private int decodeByteToKeyCode(byte byt){
		return (Integer) bytMap.get(byt);
	}
	
	private byte encodeKeyCodeToByte(int key){
		for(byte i = 0; i < bytMap.size(); i ++){
			if(bytMap.get(i) == key){
				return i;
			}
		}
		return 0;
	}*/

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		// TODO Auto-generated method stub
		buffer.writeInt(keyCode);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		// TODO Auto-generated method stub
		keyCode = buffer.readInt();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClientSide(EntityPlayer player) {
		if(player.inventory.armorItemInSlot(2) == null){return;}
		if(player.inventory.armorItemInSlot(2).getItem() != null &&  !(player.inventory.armorItemInSlot(2).getItem() instanceof ItemBCD)){return;}
		// TODO Auto-generated method stub
		if(keyCode==ACKeyBindingHandler.EnumKeyCommands.BCD_UP.ordinal()){
			ItemBCD bcdItem = (ItemBCD) player.inventory.armorItemInSlot(2).getItem();
			bcdItem.changeDo(player.inventory.armorItemInSlot(2), player, true, Side.CLIENT);
		}
		else if(keyCode==ACKeyBindingHandler.EnumKeyCommands.BCD_DOWN.ordinal()){
			ItemBCD bcdItem = (ItemBCD) player.inventory.armorItemInSlot(2).getItem();
			bcdItem.changeDo(player.inventory.armorItemInSlot(2), player, false, Side.CLIENT);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		// TODO Auto-generated method stub
		if(player.inventory.armorItemInSlot(2)!= null && (player.inventory.armorItemInSlot(2).getItem() instanceof ItemBCD))
		{
			if(keyCode==1){
				ItemBCD bcdItem = (ItemBCD) player.inventory.armorItemInSlot(2).getItem();
				bcdItem.changeDo(player.inventory.armorItemInSlot(2), player, true, Side.SERVER);
			}
			else if(keyCode==2){
				ItemBCD bcdItem = (ItemBCD) player.inventory.armorItemInSlot(2).getItem();
				bcdItem.changeDo(player.inventory.armorItemInSlot(2), player, false, Side.SERVER);
			}
		}
	}

}
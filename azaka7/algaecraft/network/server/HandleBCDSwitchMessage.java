package azaka7.algaecraft.network.server;

import azaka7.algaecraft.common.items.ItemBCD;
import azaka7.algaecraft.network.BCDSwitchMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HandleBCDSwitchMessage extends AbstractServerMessageHandler{

	@Override
	public IMessage handleServerMessage(EntityPlayer player, IMessage message,
			MessageContext ctx) {
		if(message instanceof BCDSwitchMessage){
			BCDSwitchMessage msg = (BCDSwitchMessage) message;
			ItemStack stack = player.inventory.armorItemInSlot(2);
			if(stack != null && stack.getItem() instanceof ItemBCD){
				ItemBCD bcd = (ItemBCD) stack.getItem();
				bcd.changeDo(stack, player, msg.getSwitchUp(), ctx.side);
			}
		}
		
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public static void handleClientAction(EntityPlayer player, boolean switchUp){
		ItemStack stack = player.inventory.armorItemInSlot(2);
		if(stack != null && stack.getItem() instanceof ItemBCD){
			ItemBCD bcd = (ItemBCD) stack.getItem();
			bcd.changeDo(stack, player, switchUp, Side.CLIENT);
		}
	}
	
}

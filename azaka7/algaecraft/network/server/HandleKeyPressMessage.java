package azaka7.algaecraft.network.server;

import java.util.ArrayList;

import azaka7.algaecraft.common.handlers.ACSwimmingHandler;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.MoveKey;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.SwimFactor;
import azaka7.algaecraft.network.KeyPressMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandleKeyPressMessage extends AbstractServerMessageHandler{

	@Override
	public IMessage handleServerMessage(EntityPlayer player, IMessage message,
			MessageContext ctx) {
		
		if(message instanceof KeyPressMessage){
			//System.out.println("recieved keypress message for: "+player.getDisplayName());
			//System.out.println("server player motion: x="+player.motionX+"|y="+player.motionY+"|z="+player.motionZ);
			KeyPressMessage kpm = (KeyPressMessage) message;
			MoveKey[] keys = kpm.getKeys();
			
			/*NBTTagCompound tags = player.getEntityData();
			tags.removeTag(ACSwimmingHandler.SWIM_TAG);*/
			ArrayList<SwimFactor> factors = ACSwimmingHandler.getValidFactorsForKeys(player, keys);
			
			ACSwimmingHandler.setFactorsToPlayerNBT(factors, player);
		}
		
		return null;
	}

}

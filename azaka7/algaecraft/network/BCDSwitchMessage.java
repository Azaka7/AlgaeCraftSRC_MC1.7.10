package azaka7.algaecraft.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class BCDSwitchMessage implements IMessage{
	
	private boolean switchUp = false;

	public BCDSwitchMessage(){
		switchUp = false;
	}
	
	public BCDSwitchMessage(boolean up){
		switchUp = up;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		switchUp = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(switchUp);
	}
	
	public boolean getSwitchUp(){
		return switchUp;
	}

}

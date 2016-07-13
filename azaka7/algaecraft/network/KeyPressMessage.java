package azaka7.algaecraft.network;

import azaka7.algaecraft.common.handlers.ACSwimmingHandler;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.MoveKey;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.SwimFactor;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class KeyPressMessage implements IMessage{
	
	private short[] keys;
	
	public KeyPressMessage(){
		keys = null;
	}
	
	public KeyPressMessage(MoveKey[] k){
		keys = new short[k.length];
		for(int i = 0; i < k.length; i++){
			keys[i] = k[i].getShortOrdinal();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int size = buf.readInt();
		keys = new short[size];
		for(int i = 0; i < size; i++){
			keys[i] = buf.readShort();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(keys.length);
		for(short s : keys){
			buf.writeShort(s);
		}
	}
	
	public MoveKey[] getKeys(){
		MoveKey[] ret = new MoveKey[keys.length];
		for(int i = 0; i < keys.length; i++){
			ret[i] = MoveKey.values()[keys[i]];
		}
		return ret;
	}

}

package azaka7.algaecraft.common;

import azaka7.algaecraft.common.handlers.ACEventHandler;

public class CommonProxy {

	public void registerEventHandler(){
		ACEventHandler.INSTANCE.initialize();
	}
	
	public void registerRenders(){}

}

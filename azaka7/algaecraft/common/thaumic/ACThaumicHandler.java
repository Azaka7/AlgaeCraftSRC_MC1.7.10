package azaka7.algaecraft.common.thaumic;

import net.minecraft.item.Item;
import azaka7.algaecraft.AlgaeCraft;

public class ACThaumicHandler {
	
	public static void register(){
		if(AlgaeCraft.thaumcraft()){
			ACThaumicAddon.register();
		}
	}

	public static Item getItem_Thaum(Item item0) {
		if(AlgaeCraft.thaumcraft()){
			return ACThaumicAddon.getItem_Thaum(item0);
		}
		else{
			return item0;
		}
	}

	public static void registerThaumicItems() {
		
		
		
	}
}

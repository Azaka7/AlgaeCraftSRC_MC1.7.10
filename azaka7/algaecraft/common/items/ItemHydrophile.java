package azaka7.algaecraft.common.items;

import azaka7.algaecraft.common.handlers.ACEventHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.item.ItemEvent;

public class ItemHydrophile extends Item{
	
	public float explosion;
	public int time;
	
	public ItemHydrophile(float f, int t){
		explosion = f;
		time =t;
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entity){
		ACEventHandler.INSTANCE.onItemEntityUpdate(new ItemEvent(entity));
		return false;
		
	}
	
}

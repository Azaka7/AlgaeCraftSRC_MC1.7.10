package azaka7.algaecraft.common.items;

import azaka7.algaecraft.common.handlers.ACEventHandler;
import net.minecraft.entity.Entity;
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
	public boolean onEntityItemUpdate(EntityItem entityItem){
		if(entityItem.getEntityItem().getItem() instanceof ItemHydrophile){
			if(entityItem.handleWaterMovement()){
				if (((ItemHydrophile) entityItem.getEntityItem().getItem()).time  > entityItem.age){return false;}
				float i = ((ItemHydrophile) entityItem.getEntityItem().getItem()).explosion;
				entityItem.worldObj.createExplosion((Entity) entityItem, entityItem.posX, entityItem.posY, entityItem.posZ,  i*entityItem.getEntityItem().stackSize, false);
				entityItem.setDead();
			}
		}
		
		return false;
		
	}
	
}

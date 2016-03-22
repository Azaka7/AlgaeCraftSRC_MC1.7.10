package azaka7.algaecraft.common.items;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;

public class ItemBlockNoDrops extends ItemBlock{

	public ItemBlockNoDrops(Block p_i45328_1_) {
		super(p_i45328_1_);
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entity){
		entity.setDead();
		return false;
	}
}

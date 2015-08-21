package azaka7.algaecraft.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDamageableCrafting extends Item {
	private boolean leavesCrafting;
	
	ItemDamageableCrafting(int md, boolean leave, boolean reparable){
		this.setMaxDamage(md);
		leavesCrafting = leave;
		this.hasSubtypes = false;
		this.maxStackSize = 1;
		this.canRepair = reparable;
	}
	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
    {
		return leavesCrafting;
    }
	
	@Override
	public boolean hasContainerItem()
    {
        return true;
    }
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
    {
		itemStack.setItemDamage(itemStack.getItemDamage()+1);
		return itemStack;
    }
}

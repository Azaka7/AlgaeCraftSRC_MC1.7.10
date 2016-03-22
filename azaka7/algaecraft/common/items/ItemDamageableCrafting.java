package azaka7.algaecraft.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDamageableCrafting extends Item {
	private boolean leavesCrafting;
	private ItemStack[] repairItems;
	
	ItemDamageableCrafting(int md, boolean leave, boolean reparable, ItemStack... anvilRepair){
		this.setMaxDamage(md);
		leavesCrafting = leave;
		this.hasSubtypes = false;
		this.maxStackSize = 1;
		this.canRepair = reparable;
		repairItems = anvilRepair;
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
	
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2)
    {
		for(ItemStack stack : repairItems){
			if(stack == stack2 || net.minecraftforge.oredict.OreDictionary.itemMatches(stack, stack2, false)){
				return true;
			}
		}
		return super.getIsRepairable(stack1, stack2);
    }
}

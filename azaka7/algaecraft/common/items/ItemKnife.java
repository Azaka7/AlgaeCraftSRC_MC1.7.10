package azaka7.algaecraft.common.items;

import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemKnife extends Item {
	
	private final double damage;
	
	public ItemKnife(double dmg, int dur) {
		super();
		this.maxStackSize = 1;
		this.setMaxDamage(dur);
		this.setNoRepair();
		damage = dmg;
	}

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
	@Override
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)damage, 0));
        return multimap;
    }
    
    @Override
    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_)
    {
        p_77644_1_.damageItem(1, p_77644_3_);
        return true;
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
	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
    {
		return false;
    }
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return false;
	}

}

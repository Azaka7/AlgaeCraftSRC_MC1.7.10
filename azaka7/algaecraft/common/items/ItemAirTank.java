package azaka7.algaecraft.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import azaka7.algaecraft.client.model.ModelAirCompressor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAirTank extends Item {
	
	public final TankType type;
	
	public ItemAirTank(int i, TankType t){
		super();
		this.setMaxDamage(i);
		this.setMaxStackSize(1);
		this.setHasSubtypes(false);
		this.type = t;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem() instanceof ItemBCD){
			//System.out.println("found bcd");
			ItemStack bcd = player.inventory.armorItemInSlot(2);
			if(!ItemBCD.setTank(bcd, stack)){
				//System.out.println("replacing tank");
				ItemStack newStack = ItemBCD.setTankReturnOld(bcd, stack);
				stack = (newStack != null && newStack.getItem() != null) ? newStack : useStack(stack);
			}
			else{
				stack.stackSize = 0;
			}
		}
		return stack;
    }
	
	private ItemStack useStack(ItemStack stack){
		stack.stackSize--;
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if(this == ACItems.itemAirTankCreative){
			par3List.add("Breaths: \u221E");
		}
		else{
			par3List.add("Breaths: "+((getMaxDamage(par1ItemStack)-getDamage(par1ItemStack)))+"/"+(getMaxDamage(par1ItemStack)));
		}
	}

	//Used for rendering of tank
	public enum TankType{
		SMALL((new ModelAirCompressor()).TankSmall),
		LARGE((new ModelAirCompressor()).TankLarge);
		
		public final ModelRenderer model;
		TankType(ModelRenderer mod){
			model = mod;
		}
	}
	
	
}

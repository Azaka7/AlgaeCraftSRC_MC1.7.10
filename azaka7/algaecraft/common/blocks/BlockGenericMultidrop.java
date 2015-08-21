package azaka7.algaecraft.common.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockGenericMultidrop extends Block {
	
	private ItemStack[] drops;
	private ItemStack[] baseDrops;
	
	protected BlockGenericMultidrop(Material material, ItemStack[] base, ItemStack... stacks) {
		super(material);
		baseDrops = base;
		drops = stacks;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
		//System.out.println("Fortune: "+fortune);
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		if(world.rand.nextInt(100)<=(fortune*25)){
			ItemStack stack = drops[world.rand.nextInt(drops.length)].copy();
			if(stack.getItem().isItemTool(stack)){
				stack.setItemDamage(world.rand.nextInt(stack.getItemDamage()+1));
			}
			else{
				stack.stackSize = (world.rand.nextInt(stack.stackSize)+1);
			}
			ret.add(stack);
		}
		ret.add(baseDrops[world.rand.nextInt(baseDrops.length)].copy());
		return ret;
    }

}

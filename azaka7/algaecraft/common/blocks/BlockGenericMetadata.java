package azaka7.algaecraft.common.blocks;

import java.util.List;

import azaka7.algaecraft.AlgaeCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.Face;

public class BlockGenericMetadata extends Block {
	
	private String[] iconNames;
	private IIcon[] icons;
	private int[] metaWith0Top;
	
	protected BlockGenericMetadata(Material material, String[] icoms, int[] tops) {
		super(material);
		iconNames = icoms;
		icons = new IIcon[iconNames.length];
		metaWith0Top = tops;
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		for(int i = 0; i < metaWith0Top.length; i++){
			if(meta == metaWith0Top[i]){
				if(side == 1 || side == 0){
					return icons[0];
				}
			}
		}
		return icons[meta%(icons.length + 1)];
    }
	
	@Override
	public String getUnlocalizedName(){
		return super.getUnlocalizedName();
	}
	
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
		for(int i = 0; i < icons.length; i++){
			icons[i]=register.registerIcon(AlgaeCraft.MODID+":"+iconNames[i]);
		}
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tabs, List list)
    {
		for(int i = 0; i < icons.length; i++){
			list.add(new ItemStack(this, 1, i));
		}
    }
	
	/**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }

}

package azaka7.algaecraft.common.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockLogAC extends BlockLog{
	
	public static String[] blockIcons;
    
    public BlockLogAC(String[] icons){
    	int i = (4 <= icons.length ? icons.length : 4);
    	blockIcons = new String[i];
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	for(int i = 0; i < blockIcons.length; i++){
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
    	}
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.field_150167_a = new IIcon[blockIcons.length];
        this.field_150166_b = new IIcon[blockIcons.length];

        for (int i = 0; i < this.field_150167_a.length; ++i)
        {
            this.field_150167_a[i] = p_149651_1_.registerIcon(this.getTextureName() + "_" + blockIcons[i]);
            this.field_150166_b[i] = p_149651_1_.registerIcon(this.getTextureName() + "_" + blockIcons[i] + "_top");
        }
    }
	
}

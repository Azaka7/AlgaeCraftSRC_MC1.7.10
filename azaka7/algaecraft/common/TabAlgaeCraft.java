package azaka7.algaecraft.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class TabAlgaeCraft extends CreativeTabs {

	public TabAlgaeCraft() {
		super(CreativeTabs.getNextID(),"algaecraft");
	}
	
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
    {
        return "AlgaeCraft";
    }
	
	@SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
        return Item.getItemFromBlock(Blocks.sponge);
    }

}
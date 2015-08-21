package azaka7.algaecraft.common.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import azaka7.algaecraft.AlgaeCraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemGenericItems extends Item {
	private static String[] unlocalizedList = new String[128];
	private static String[] iconNameList = new String[128];
	private static IIcon[] iconList = new IIcon[128];
	//private static Map<String,String> unlocalizedToIcon = new HashMap<String,String>();
	
	public ItemGenericItems(){
        this.setHasSubtypes(true);
    }
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		for(int i = 0; i <iconNameList.length; i++){
			iconList[i]=par1IconRegister.registerIcon("algaecraft:"+iconNameList[i]);
		}
    }
	
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		return this.getUnlocalizedName() + "." +unlocalizedList[par1ItemStack.getItemDamage() < unlocalizedList.length ? par1ItemStack.getItemDamage() : 0];
    }
	
	public IIcon getIconFromDamage(int par1)
    {
		return iconList[par1];
    }
	
	public ItemStack addGenericItem(int metaID, String unlocalized, String iconName){
		unlocalizedList[metaID] = unlocalized;
		iconNameList[metaID] = iconName;
		return new ItemStack(this,1,metaID);
	}
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		for(int i = 0; i < unlocalizedList.length; i++){
			if(unlocalizedList[i]!=null){
				par3List.add(new ItemStack(this, 1, i));
			}
		}
    }
}

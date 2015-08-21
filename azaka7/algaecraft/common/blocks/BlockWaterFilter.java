package azaka7.algaecraft.common.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.blocks.BlockWaterFilter.EnumWaterType;
import azaka7.algaecraft.common.tileentity.TileEntityCage;
import azaka7.algaecraft.common.tileentity.TileEntityWaterFilter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockWaterFilter extends BlockContainer {
	
	IIcon[] icons = new IIcon[16];
	IIcon bottomIcon = null;
	IIcon sideIcon = null;
	
	public BlockWaterFilter() {
		super(Material.anvil);
		this.setStepSound(soundTypeAnvil);
		TileEntity.addMapping(TileEntityWaterFilter.class, "tileEntityWaterFilter");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(side == 0){return bottomIcon;}
		if(side == 1){return icons[meta];}
		return sideIcon;
    }
	
	@Override
	public String getUnlocalizedName(){
		return super.getUnlocalizedName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
		for(int i = 0; i < icons.length; i++){
			icons[i]=register.registerIcon(AlgaeCraft.MODID+":"+getWaterForDamage(i).icon);
		}
		bottomIcon = register.registerIcon(AlgaeCraft.MODID+":filterBottom");
		sideIcon = register.registerIcon(AlgaeCraft.MODID+":filterSide");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tabs, List list)
    {
		for(int i = 0; i < icons.length && i < EnumWaterType.values().length; i++){
			list.add(new ItemStack(this, 1, i));
		}
    }
	
	private static int getDamageForWater(EnumWaterType type){
		if(type.values().length > 15){
			System.out.println("[AlgaeCraft] There are too many water types for this filter instance. Setting large values to 0 for ender-style handling.");
		}
		return type.ordinal() > 15 ? 0 : type.ordinal();
	}
	
	private static EnumWaterType getWaterForDamage(int damage){
		if(damage < EnumWaterType.values().length) return EnumWaterType.values()[damage];
		return EnumWaterType.values()[0];
	}
	
	public static boolean isLocationValid(World world, int x, int y, int z, EnumWaterType water, int i, int j, int k){
		//System.out.println("checking location");
		if(world.getBlock(i, j, k) instanceof BlockWaterFilter){
			if(world.getBlockMetadata(i, j, k) == getDamageForWater(water) || world.getBlockMetadata(i, j, k) == 0){
				TileEntityWaterFilter tileentity = (TileEntityWaterFilter) world.getTileEntity(i, j, k);
				//if(tileentity.getPathmap().size() > 0){
				//System.out.println("pre-isposinpathmap");
				return tileentity.isPosInPathmap(x,y,z);
				//}
				
			}
		}
		return false;
	}

	public static boolean isLocationInvalid(World world, int x, int y, int z, EnumWaterType water, int i, int j, int k){
		if(world.getBlock(i, j, k) instanceof BlockWaterFilter){
			if(world.getBlockMetadata(i, j, k) != getDamageForWater(water) && world.getBlockMetadata(i, j, k) != 0){
				TileEntityWaterFilter tileentity = (TileEntityWaterFilter) world.getTileEntity(i, j, k);
				//if(tileentity.getPathmap().size() > 0){
					return tileentity.isPosInPathmap(x,y,z);
				//}
				
			}
		}
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		TileEntity tile = new TileEntityWaterFilter();
		tile.setWorldObj(var1);
		tile.getBlockType();
		tile.getBlockMetadata();
		return tile;
	}
	
	@Override
	public int damageDropped(int p_149692_1_)
    {
        return p_149692_1_;
    }
	
	public static enum EnumWaterType{
		ENDER("Ender"),
		DEFAULT("Fresh"),
		OCEAN("Ocean"),
		SWAMP("Sporous");
		
		public final String icon;
		public final String name;
		EnumWaterType(String s){icon = "filter"+s; name = s;}
	}

}

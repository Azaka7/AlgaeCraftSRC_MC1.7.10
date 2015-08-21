package azaka7.algaecraft.common.items;

import java.util.Iterator;
import java.util.List;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.handlers.ACEventHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.fluids.FluidRegistry;

public class ItemFlask extends Item {
	
	//public static String[] itemNames = new String[]{"Empty","Water","Salt Water","Sodium Hydroxide","CraftingNaOH","CraftingSaltWater"};
	public static String[] iconNames = new String[]{"Empty","Fresh","Salt","NaOH","NaOH","Salt"};
	public static IIcon[] itemIcons = new IIcon[3];
	public static PotionEffect[] itemEffects = new PotionEffect[]{};
	
	Item parent = null;
	
	public ItemFlask(Item real, String[] icons, PotionEffect[] efects) {
		super();
		this.maxStackSize = 1;
		this.setHasSubtypes(true);
		iconNames = icons;
		parent = real;
		itemEffects = efects;
		//this.setContainerItem(this);
        //this.setMaxDamage(3);
	}

	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		itemIcons = new IIcon[iconNames.length];
		for(int i = 0; i < itemIcons.length; i ++){
			itemIcons[i] = par1IconRegister.registerIcon(AlgaeCraft.MODID+":flask"+iconNames[i]);
		}
	}
	
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		int damage = par1ItemStack.getItemDamage();
		if(damage > iconNames.length){return "item.algaecraft_itemFlask";}
		return "item.algaecraft_itemFlask."+iconNames[damage];
    }
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2, List par3List)
    {
		for(int i = 0; i < itemIcons.length; i++){
			par3List.add(new ItemStack(par1, 1, i));
		}
    }
	
	@Override
	public boolean hasContainerItem(ItemStack stack)
    {
        return stack.getItemDamage() > 0 || this.parent != null;
    }
	
	public static ItemStack fake(ItemStack stack){
		return new ItemStack(ACItems.itemFlaskFake, 1, stack.getItemDamage());
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
    {
		if(this.parent != null){
			return new ItemStack(parent, 1, itemStack.getItemDamage());
		}
		ItemStack ret = itemStack.copy(); ret.setItemDamage(0);
		return ret;
    }
	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
    {
		return false;
    }
	
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
    	if(this.parent != null){return EnumAction.none;}
        return EnumAction.drink;
    }
    
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        if (!par2World.isRemote)
        {
        	PotionEffect potioneffect = getEffectFromContained(par1ItemStack.getItemDamage());
        	if(potioneffect != null){
        		par3EntityPlayer.addPotionEffect(new PotionEffect(potioneffect));
        	}
        }
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return new ItemStack(this, 1, 0);
            }

            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(this, 1, 0));
        }

        return par1ItemStack;
    }
    
    private PotionEffect getEffectFromContained(int damage){
    	try{
    		PotionEffect effect = itemEffects[damage];
    		return effect;
    	}
    	catch(Exception e){
    		return null; 
    	}
    }
    
    @Override
	public IIcon getIconFromDamage(int par1)
    {
    	try{
    		IIcon icon = itemIcons[par1];
    		return icon;
    	}
    	catch(Exception e){
    		return itemIcons[0];
    	}
    }
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par1ItemStack.getItemDamage() >= 1){
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
			return par1ItemStack;
		}
		
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);

        if (movingobjectposition == null)
        {
            return par1ItemStack;
        }
        else
        {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!par2World.canMineBlock(par3EntityPlayer, i, j, k))
                {
                    return par1ItemStack;
                }

                if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, par1ItemStack))
                {
                    return par1ItemStack;
                }

                if (par2World.getBlock(i, j, k).getMaterial() == Material.water)
                {
                    --par1ItemStack.stackSize;
                    
                    int aspect = 1;
                    
                    int[] idList = ACGameData.biomeIDOceanList.clone();
                    for(int n = 0; n < idList.length; n++){
                    	if(idList[n] == par2World.getBiomeGenForCoords(i, k).biomeID){
                    		aspect = 2;
                    	}
                    }

                    if (par1ItemStack.stackSize <= 0)
                    {
                        return new ItemStack(this,1,aspect);
                    }

                    if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(this, 1, aspect)))
                    {
                        par3EntityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(this, 1, aspect), false);
                    }
                }
            }

            return par1ItemStack;
        }
    }
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entity){
		ItemStack stack = entity.getEntityItem();
		if(stack.getItemDamage() != ACItems.itemStackFlaskWater.getItemDamage()){return false;}
		if(stack.stackTagCompound == null){stack.stackTagCompound = new NBTTagCompound();}
		if(!stack.stackTagCompound.hasKey("timer")){
			stack.stackTagCompound.setInteger("timer", 0);
		}
		if(entity.worldObj.getBlock(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY)-1, MathHelper.floor_double(entity.posZ)) == Blocks.lit_furnace){
			stack.stackTagCompound.setInteger("timer", stack.stackTagCompound.getInteger("timer") + 1);
		}
		if(stack.stackTagCompound.getInteger("timer") > 395){
			ItemStack ret = ACItems.itemStackFlaskH2CO3.copy();
			ret.stackSize = stack.stackSize;
			entity.setEntityItemStack(ret);
		}
		return false;
	}
}
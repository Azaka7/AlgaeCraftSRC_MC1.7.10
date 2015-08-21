package azaka7.algaecraft.common.tileentity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import azaka7.algaecraft.common.blocks.ACBlocks;
import azaka7.algaecraft.common.items.ItemAirTank;

public class TileEntityAirCompressor extends TileEntity {
	
	protected ItemStack tank;
	
	private boolean lastPowered;
	
	private byte timer;
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		this.readFromNBT(pkt.func_148857_g());
    }
	
	@Override
	public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
    }

	public ItemStack removeTank(World par1World) {
		ItemStack ret = tank != null ? tank.copy() : null;
		tank = null;
		return ret;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        NBTTagCompound stacktag = (NBTTagCompound) tagCompound.getTag("tank");
        tank = ItemStack.loadItemStackFromNBT(stacktag);
        if(tank != null && !(tank.getItem() instanceof ItemAirTank)){
        	tank = null;
        }
    }
	
	@Override
    public void writeToNBT(NBTTagCompound tagCompound)
    {
    	super.writeToNBT(tagCompound);
    	NBTTagCompound stacktag = new NBTTagCompound();
    	if(tank != null){
    		tank.writeToNBT(stacktag);
    	}
		tagCompound.setTag("tank", stacktag);
    }

	public void setTank(ItemStack currentItem) {
		if(currentItem.getItem() instanceof ItemAirTank){
			tank = currentItem.copy();
		}
	}
	
	public void setWorld(World world){
		this.worldObj = world;
	}
	
	@Override
	public void updateEntity(){
		if(tank == null || tank.getItem() == null || tank.stackSize == 0){
			return;}
		boolean thisPowered = (this.worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) != 0 || this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord));
		//System.out.println("ispowered: " + thisPowered);
		if(thisPowered != lastPowered){
			timer++;
			if(timer >= 3){
				timer = 0;

				tank.setItemDamage(tank.getItemDamage()-1);
				if(tank.getItemDamage() < 0){
					tank.setItemDamage(0);
				}
			}
			lastPowered = thisPowered;
		}
	}

	public ItemStack getTankCopy() {
		return tank != null ? tank.copy() : null;
	}

}

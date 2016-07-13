package azaka7.algaecraft.common.tileentity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import azaka7.algaecraft.common.entity.EntityLobster;

public class TileEntityCage extends TileEntity {

	public TileEntityCage(){
	}
	
	public void setWorld(World world){
		this.worldObj = world;
	}
	
	public void setCreature(EntityCreature entity){
		if(entity instanceof EntityLobster){
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, ((EntityLobster) entity).isBlue() ? 2 : 1, 3);
		}
		this.markDirty();
	}
}
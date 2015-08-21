package azaka7.algaecraft.common.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityAirCompressor_RF extends TileEntityAirCompressor implements IEnergyConnection,IEnergyReceiver {

	protected EnergyStorage storage = new EnergyStorage(1000);

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		storage.readFromNBT(nbt.getCompoundTag("energy"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);
		NBTTagCompound energyStorage = new NBTTagCompound();
		storage.writeToNBT(energyStorage);
		nbt.setTag("energy", energyStorage);
	}
	
	@Override
	public void updateEntity(){
		if(tank == null || storage == null){return;}
		//boolean thisPowered = (this.worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0);
		if(storage.getEnergyStored() >= 100 && tank.getItemDamage() > 0){
			tank.setItemDamage(tank.getItemDamage()-1);
			if(tank.getItemDamage() < 0){
				tank.setItemDamage(0);
			}
			storage.extractEnergy(100, false);
		}
	}

	/* IEnergyConnection */
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {

		return from == ForgeDirection.DOWN;
	}

	/* IEnergyReceiver */
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

		return storage.receiveEnergy(maxReceive, simulate);
	}

	/* IEnergyReceiver and IEnergyProvider */
	@Override
	public int getEnergyStored(ForgeDirection from) {

		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {

		return storage.getMaxEnergyStored();
	}
	
}

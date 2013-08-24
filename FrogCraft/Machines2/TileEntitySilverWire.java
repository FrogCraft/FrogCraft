package FrogCraft.Machines2;

import java.util.Random;

import FrogCraft.Common.BaseIC2NetTileEntity;
import ic2.api.Direction;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.network.INetworkTileEntityEventListener;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySilverWire extends BaseIC2NetTileEntity implements IEnergyConductor, INetworkTileEntityEventListener{
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		return true;
	}
	
	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) {
		return true;
	}

	@Override
	public double getConductionLoss() {
		return 0.1;
	}

	public int getInsulationEnergyAbsorption() {
		return 8200;
	}

	public int getInsulationBreakdownEnergy() {
		return 9000;
	}

	public int getConductorBreakdownEnergy() {
		return 8200;
	}

	@Override
	public void removeInsulation() {
	}

	@Override
	public void removeConductor() {		
		this.invalidate();
		worldObj.setBlock(xCoord, yCoord, zCoord, 0);
		
		ic2.api.network.NetworkHelper.initiateTileEntityEvent(this, 0, true);
	}
	
	@Override
	public void onNetworkEvent(int event){
		if(event==0){
			this.worldObj.playSoundEffect(this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.5F, "random.fizz", 0.5F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F);
			for (int l = 0; l < 8; l++) 
				this.worldObj.spawnParticle("largesmoke", this.xCoord + Math.random(), this.yCoord + 1.2D, this.zCoord + Math.random(), 0.0D, 0.0D, 0.0D);
		}
	}
}

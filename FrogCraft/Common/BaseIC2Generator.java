package FrogCraft.Common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import ic2.api.Direction;
import ic2.api.energy.event.*;

public class BaseIC2Generator extends BaseIC2NetTileEntity implements ic2.api.energy.tile.IEnergySource{
	private EnergyTileSourceEvent sourceEvent;
	public int maxEnergy,
	           energy=0;
	public BaseIC2Generator(int maxEnergy) {
		this.maxEnergy=maxEnergy;
	}

	@Override
	public void updateEntity(){
		super.updateEntity();
	}
	
    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
    	super.writeToNBT(tagCompound);
    	tagCompound.setInteger("energy", energy);  
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
    	super.readFromNBT(tagCompound);
    	energy=tagCompound.getInteger("energy");
    }
	
	/**Output amounts of energy, return energy not being emitted*/
	protected int out(int voltage,int power){
		if (voltage>power)
			return voltage-power;
		
		int num = power/voltage;
		for (int i=0;i<num;i++){
			out(voltage);
		}
		
		return num*voltage-power;
	}
	
	/**Output a packet of energy*/
	protected void out(int amount){	
		if (energy<amount)
			return;
		
		sourceEvent=new EnergyTileSourceEvent(this,amount);
		MinecraftForge.EVENT_BUS.post(sourceEvent);		
		energy-=amount-sourceEvent.amount;			
	}
	
	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) {
		return true;
	}

	@Override
	public int getMaxEnergyOutput() {
		return Integer.MAX_VALUE;
	}
}

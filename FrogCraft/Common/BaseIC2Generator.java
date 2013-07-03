package FrogCraft.Common;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import ic2.api.Direction;
import ic2.api.energy.event.*;

public class BaseIC2Generator extends BaseIC2Machine implements ic2.api.energy.tile.IEnergySource{
	private EnergyTileSourceEvent sourceEvent;
	public BaseIC2Generator(int energyMax) {
		super(0, energyMax);
	}

	@Override public void updateEntity(){
		super.updateEntity();
	}
	
	void out(int voltage,int power){
		if (voltage>power){
			out(power);
			return;
		}
		
		
		int num = power/voltage;
		for (int i=0;i<num;i++){
			out(voltage);
		}
		
		if (num*voltage<power){
			out(num*voltage-power);
		}
	}
	
	void out(int amount){
		if (amount>energy)
			amount=energy;
		
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
	
    @Override
    public boolean acceptsEnergyFrom(TileEntity var1, Direction var2)
    {
        return false;
    }
}

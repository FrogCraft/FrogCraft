package FrogCraft.Machines2.ACWindMill;

import FrogCraft.Common.SidedIC2Machine;
import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileSourceEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityACWindMillBase extends SidedIC2Machine implements ic2.api.energy.tile.IEnergySource{
	private EnergyTileSourceEvent sourceEvent;
	public int energy=0;
	public int maxEnergy=256;
		
	
    @Override
    public void updateEntity(){  	
        super.updateEntity();
        
        if (worldObj.isRemote)
            return;
        
        out(32);
        out(32); 
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
	public boolean isAddedToEnergyNet() {
		return true;
	}

	@Override
	public int getMaxEnergyOutput() {
		return 128;
	}

}

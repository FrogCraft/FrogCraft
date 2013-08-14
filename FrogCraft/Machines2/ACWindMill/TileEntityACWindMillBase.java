package FrogCraft.Machines2.ACWindMill;

import FrogCraft.Common.SidedIC2Machine;
import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileSourceEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityACWindMillBase extends SidedIC2Machine implements ic2.api.energy.tile.IEnergySource{
	private EnergyTileSourceEvent sourceEvent;
	public int energy=0;
	public int maxEnergy=256;
    public boolean addedToEnergyNet;
	
    @Override
    public void updateEntity(){  	
        super.updateEntity();
        
        if (worldObj.isRemote)
            return;
        
        if (!this.addedToEnergyNet)
        {
            //EnergyNet.getForWorld(this.worldObj).addTileEntity(this);
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }        
        
        out(32);
        out(32); 
    }
	
    @Override
    public void invalidate()
    {
        if (!worldObj.isRemote&this.addedToEnergyNet)
        {
            //EnergyNet.getForWorld(this.worldObj).removeTileEntity(this);
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }

        super.invalidate();
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
    public boolean isAddedToEnergyNet()
    {
        return this.addedToEnergyNet;
    }


	@Override
	public int getMaxEnergyOutput() {
		return 128;
	}

}

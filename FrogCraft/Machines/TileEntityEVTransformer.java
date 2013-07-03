package FrogCraft.Machines;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileSourceEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import FrogCraft.Common.BaseIC2Machine;

public class TileEntityEVTransformer extends BaseIC2Machine implements ic2.api.energy.tile.IEnergySource{

	private EnergyTileSourceEvent sourceEvent;
	
	public TileEntityEVTransformer() {
		super(8192, 8192);
	}

    @Override
    public void updateEntity(){  	
        super.updateEntity();
    
        for (int i=0;i<(maxEnergy/2048);i++)
        	out(2048);
    }
	
	void out(int amount){
		if (amount>energy)
			return;
		
		if (amount<0)
			return;
		
		sourceEvent=new EnergyTileSourceEvent(this,amount);
		MinecraftForge.EVENT_BUS.post(sourceEvent);		
		energy-=amount-sourceEvent.amount;			
	}
	
	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) {
    	if (direction.toSideValue()==facing)
    		return false;
		return true;
	}


	@Override
	public int getMaxEnergyOutput(){return Integer.MAX_VALUE;}
    
    public boolean acceptsEnergyFrom(TileEntity var1, Direction var2)
    {
    	if (var2.toSideValue()==facing)
    		return true;
        return false;
    }
    
    @Override
    public boolean wrenchCanSetFacing(EntityPlayer var1, int facingToSet)
    {
    	if (facingToSet == facing)
    		return false;
        return true;
    }
}

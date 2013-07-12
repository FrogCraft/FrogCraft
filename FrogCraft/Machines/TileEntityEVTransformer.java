package FrogCraft.Machines;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileSourceEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import FrogCraft.Common.BaseIC2Machine;

public class TileEntityEVTransformer extends BaseIC2Machine implements ic2.api.energy.tile.IEnergySource{

	private EnergyTileSourceEvent sourceEvent;
	private boolean red=false;
	
	public TileEntityEVTransformer() {
		super(8192, 8192*2);
	}

    @Override
    public void updateEntity(){  	
        super.updateEntity();
    
        if(red!=(worldObj.getBlockPowerInput(xCoord, yCoord, zCoord)>0)){
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }
        
        red=worldObj.getBlockPowerInput(xCoord, yCoord, zCoord)>0;
        
        if(!red){
        	for (int i=0;i<(maxEnergy/2048);i++)
        		out(2048);
        }
        else
        	out(8192);
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
    	if(worldObj.getBlockPowerInput(xCoord, yCoord, zCoord)==0){		
    		if (direction.toSideValue()==facing)
    			return false;
    		return true;
    	}
    	else{
    		if (direction.toSideValue()==facing)
    			return true;
    		return false;    		
    	}
	}
	
	@Override 
	public void beforeSetFacing(short newFacing,short oldFacing){
		if(this.addedToEnergyNet){
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
        	this.addedToEnergyNet = false;
		}
		return;
	}
	
	@Override 
	public void afterSetFacing(short facing){
		if(!this.addedToEnergyNet){
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
        	this.addedToEnergyNet = true;
		}
		return;
	}

	@Override
	public int getMaxEnergyOutput(){return Integer.MAX_VALUE;}
    
    public boolean acceptsEnergyFrom(TileEntity var1, Direction var2)
    {
    	if(worldObj.getBlockPowerInput(xCoord, yCoord, zCoord)==0){
    		if (var2.toSideValue()==facing)
    			return true;
    		return false;
    	}
    	else{
    		if (var2.toSideValue()==facing)
    			return false;
    		return true;    		
    	}
    }
    
    @Override
    public boolean wrenchCanSetFacing(EntityPlayer var1, int facingToSet)
    {
    	if (facingToSet == facing)
    		return false;
        return true;
    }
}

package FrogCraft.Machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import FrogCraft.*;
import FrogCraft.Common.BaseIC2Machine;

public class TileEntityAirPump extends BaseIC2Machine {
	//Variables
 
	
	public int gasamount=0;
	public int tick=0;
	public int max_gasamount=100000;
	
	public int GasPercentage=0;

	//Class Declaration
	public TileEntityAirPump(){
		super(128,10000);  
	}
	
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
    	super.readFromNBT(tagCompound);
    	gasamount =tagCompound.getInteger("gasamount");    
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
    	super.writeToNBT(tagCompound);
    	tagCompound.setInteger("gasamount", gasamount);
    }
	
    
    @Override
    public void updateEntity(){  	
        super.updateEntity();
                       
        
        if (worldObj.isRemote)
            return;

        
        if (energy>=32&gasamount<max_gasamount){
        	tick+=1;
        	if (tick==4){
        		tick=0;
        		gasamount+=50;
        	}
        	energy-=32;
        }
        
        GasPercentage=10000*getGas()/max_gasamount;
    }
    
    public int getGas(){
    	return gasamount;
    }
    
    public boolean useGas(int a){
    	if (gasamount>=a){
    		gasamount-=a;
    		return true;
    	}
    	else
    		return false;
    }
       
}
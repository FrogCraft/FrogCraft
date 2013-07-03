package FrogCraft.Common;


import ic2.api.Direction;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public abstract class BaseIC2Machine extends SidedIC2Machine implements ic2.api.energy.tile.IEnergySink{
    public boolean addedToEnergyNet;
    public int maxEnergy=0;
    public int maxInput;
    public int energy=0;
    
    public BaseIC2Machine(int saftVoltage, int maxStoredEnergy){
    	maxEnergy=maxStoredEnergy;
    	maxInput=saftVoltage;
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (!this.addedToEnergyNet)
        {
            //EnergyNet.getForWorld(this.worldObj).addTileEntity(this);
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }
    }
	
    @Override
    public void invalidate()
    {
        if (this.addedToEnergyNet)
        {
            //EnergyNet.getForWorld(this.worldObj).removeTileEntity(this);
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }

        super.invalidate();
    }
    
    @Override
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.energy = var1.getInteger("energy");
    }

    @Override
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setInteger("energy", this.energy);
    }
    
    @Override
    public boolean isAddedToEnergyNet()
    {
        return this.addedToEnergyNet;
    }

    @Override
    public int demandsEnergy()
    {
        return maxEnergy - energy;
    }

    @Override
    public int injectEnergy(Direction directionFrom, int amount)
    {
        if (amount > this.maxInput)
        {
        	this.worldObj.createExplosion(null, this.xCoord,  this.yCoord,  this.zCoord, 2F, true);
        	invalidate();
            return 0;
        }
        else
        {
            this.energy += amount;
            int var3 = 0;
            if (this.energy > this.maxEnergy)
            {
                var3 = this.energy - this.maxEnergy;
                this.energy = this.maxEnergy;
            }
            return var3;
        }
    }
    
    @Override
    public int getMaxSafeInput()
    {
        return maxInput;
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity var1, Direction var2)
    {
        return true;
    }


}

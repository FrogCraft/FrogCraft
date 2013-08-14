package FrogCraft.Machines;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileSourceEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.network.NetworkHelper;
import ic2.api.item.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.item.Item;
import FrogCraft.*;
import FrogCraft.Common.BaseIC2Machine;
import FrogCraft.Common.IC2Charger;

public class TileEntityHSU extends BaseIC2Machine implements ic2.api.energy.tile.IEnergySource,IInventory{
	//Variables
	public int energyK=0,energyM=0,energyB=0;
	private EnergyTileSourceEvent sourceEvent;
	private ItemStack[] inv;
	
	public int fuelslot=1,chargeslot=0,tier=4;
	
	//Class Declaration
	public TileEntityHSU(){
		super(2048,100000000);  
		inv = new ItemStack[2];
	}
	
	public TileEntityHSU(int v,int e){
		super(v,e);  
		inv = new ItemStack[2];
	}
    
    @Override
    public void updateEntity(){  	
        super.updateEntity();
                       
        
        if (worldObj.isRemote)
            return;

        energy=IC2Charger.discharge(inv[fuelslot], maxInput*2, tier, energy, maxEnergy);
        energy=IC2Charger.charge(inv[chargeslot], maxInput*2, tier, energy, maxEnergy);
        
        outputEu();
        
        energyB=energy/1000000;
        energyM=(energy-(energyB*1000000))/1000;
        energyK=energy-(energyB*1000000)-(energyM*1000); 	
    }
    
    public void outputEu(){
    	out(2048);
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
		
		if (amount<0)
			return;
		
		sourceEvent=new EnergyTileSourceEvent(this,amount);
		MinecraftForge.EVENT_BUS.post(sourceEvent);		
		energy-=amount-sourceEvent.amount;			
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
        if (worldObj.isRemote)
            return;
        
		if(!this.addedToEnergyNet){
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
        	this.addedToEnergyNet = true;
		}
	}
	
	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) {
    	if (direction.toSideValue()==facing)
    		return true;
		return false;
	}


	@Override
	public int getMaxEnergyOutput() {return Integer.MAX_VALUE;}
    
    @Override
    public boolean acceptsEnergyFrom(TileEntity var1, Direction var2)
    {
    	if (var2.toSideValue()==facing)
    		return false;
        return true;
    }
    
    @Override
    public boolean wrenchCanSetFacing(EntityPlayer var1, int facingToSet)
    {
    	if (facingToSet == facing)
    		return false;
        return true;
    }
      
    
    @Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}

    @Override
    public ItemStack decrStackSize(int slot, int amt) {
            ItemStack stack = getStackInSlot(slot);
            if (stack != null) {
                    if (stack.stackSize <= amt) {
                            setInventorySlotContents(slot, null);
                    } else {
                            stack = stack.splitStack(amt);
                            if (stack.stackSize == 0) {
                                    setInventorySlotContents(slot, null);
                            }
                    }
            }
            return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
    	ItemStack stack = getStackInSlot(slot);
    	if (stack != null) 
    		setInventorySlotContents(slot, null);
    	return stack;
    }

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inv[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit())
        	stack.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInvName() {return "tileentityPneumaticCompressor";}

	@Override
	public boolean isInvNameLocalized() {return false;}

    @Override
    public int getInventoryStackLimit() {return 64;}

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;}
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
            super.readFromNBT(tagCompound);
            
            NBTTagList tagList = tagCompound.getTagList("Inventory");
            for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
                    byte slot = tag.getByte("Slot");
                    if (slot >= 0 && slot < inv.length) {
                            inv[slot] = ItemStack.loadItemStackFromNBT(tag);
                    }
            }
                    
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);
                            
            NBTTagList itemList = new NBTTagList();
            for (int i = 0; i < inv.length; i++) {
                    ItemStack stack = inv[i];
                    if (stack != null) {
                            NBTTagCompound tag = new NBTTagCompound();
                            tag.setByte("Slot", (byte) i);
                            stack.writeToNBT(tag);
                            itemList.appendTag(tag);
                    }
            }
            tagCompound.setTag("Inventory", itemList);
    }

	@Override
	public void openChest() {}


	@Override
	public void closeChest() {}


	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {return false;}
}
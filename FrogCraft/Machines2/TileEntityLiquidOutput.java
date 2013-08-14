package FrogCraft.Machines2;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

import FrogCraft.Common.*;

public class TileEntityLiquidOutput extends SidedIC2Machine implements ISidedInventory,IFluidHandler{
	public int maxCapacity=10000;
	public fcFluidTank tank=new fcFluidTank(maxCapacity);
	public ItemStack inv[];	
	
	//For display
	public int fluidID,amountP;
	

	public TileEntityLiquidOutput(){
		inv=new ItemStack[2];
	}
	
	public boolean canFill(FluidStack in){
		if (tank.fluid==null)
			return true;
		if (tank.fluid.isFluidEqual(in)&&
				tank.fluid.amount<=maxCapacity-in.amount)
			return true;
		return false;		
	}
	
	public void fill(FluidStack in){
		if (in==null)
			return;
		
		if (tank.fluid==null)
			tank.setFluid(in.copy());
		else
			tank.fluid.amount+=in.amount;		
	}	
	
	
    @Override
    public void updateEntity(){  	
        super.updateEntity();
        
        if (worldObj.isRemote)
            return;
         
          
        FluidManager.fillContainer(tank.fluid,inv,0,1);
        
        if (tank.fluid!=null){
        	fluidID=tank.fluid.fluidID;
        	amountP=tank.fluid.amount*1000/maxCapacity;
        }
    }
	    
    //IFluidHandler
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {return tank.fill(resource, doFill);}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(tank.getFluid()))
        {
            return null;
        }
        return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {return true;}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {return true;}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {return new FluidTankInfo[] { tank.getInfo() };}
    
	//SidedInventory-------------------------------------------------------------------------------
	
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
	public String getInvName() {return "tileentityLiquidOutput";}

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
                    
            
            tank.readFromNBT(tagCompound);
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
            
    	tank.writeToNBT(tagCompound);
    }


	@Override
	public void openChest() {}


	@Override
	public void closeChest() {}


	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {return false;}

	//SidedInventory
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[]{0,1};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		if (slot==0)
			return true;
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		if (slot==1)
			return true;
		return false;
	}
}

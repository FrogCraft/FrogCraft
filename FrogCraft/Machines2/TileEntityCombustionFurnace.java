package FrogCraft.Machines2;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

import FrogCraft.Common.*;

public class TileEntityCombustionFurnace extends BaseIC2Generator implements ISidedInventory,IFluidHandler{
	public int maxCapacity=10000;
	
	public int progress=0,tick=0,burnTime=0;
	public ItemStack[] inv;
	public int fluidID,amountP;
	public fcFluidTank tank=new fcFluidTank(maxCapacity); 
	
	Object[] recipe=null;
	
    @Override
	public void onInventoryChanged(){
    	if(isWorking())
    		return;
    	burnTime=TileEntityFurnace.getItemBurnTime(inv[0])/4;
    	
    	//Get the recipe
    	Object[] nRec=RecipeManager.getCombustionFurnaceRecipe(inv[0]); 
    	if(nRec!=recipe){
    		tick=0;
    		progress=0;
    	}
    	recipe=nRec;
    }
	
	public void fill(FluidStack fluid){
		if (fluid==null)
			return;
		if (tank.fluid==null)
			tank.fluid=fluid.copy();
		else
			tank.fluid.amount+=fluid.amount;
	}
	
	public boolean canFill(FluidStack fluid){
		//Empty tank
		if (tank.fluid==null)
			return true;
		//No new fluid is produced
		if (fluid==null)
			return true;		
		if (tank.fluid.isFluidEqual(fluid)
				&&tank.fluid.amount<=maxCapacity-fluid.amount)
			return true;
		return false;
	}
	
    void dowork(){	
    	setWorking(true);
    	
		//Update progress
		progress=100*tick/burnTime;
		
		tick+=1;
		energy+=10;
		if (energy>maxEnergy)
			energy=maxEnergy;
		
		if (tick<burnTime)
			return;
		
		tick=0;
		progress=0;
		setWorking(false);
		onInventoryChanged();
		
		if(recipe==null)
			return;
			
		//Make products
		if(recipe[1]!=null){
			if(inv[1]==null)
				inv[1]=((ItemStack)recipe[1]).copy();
			else
				inv[1].stackSize+=((ItemStack)recipe[1]).stackSize;
		}
		
		fill((FluidStack)recipe[2]);
}
    
    boolean canwork(Object[] recipe){
    	//Validate Fuel
    	if (burnTime==0) 
    		return false; //Not a fuel
    	
    	if (recipe==null)
    		return true;
    	
		ItemStack product=(ItemStack)recipe[1];
		
		//Check product slot
    	if (inv[1]!=null&product!=null){
    		//Stack type mismatch
    		if (!product.isItemEqual(inv[1])|
    			inv[1].stackSize>inv[1].getMaxStackSize()-product.stackSize)
    			return false;
    	}
    	else if(product==null&&inv[1]!=null)
    		return false;
    	
    	
    	if (!canFill(((FluidStack)recipe[2])))
    		return false;
    	
    	return true;
    }
	
	//TileEntity-------------------------------------------------------
	public TileEntityCombustionFurnace() {
		//Can store 10000Eu
		super(10000);
		inv = new ItemStack[4];
	}

    @Override
    public void updateEntity(){  	
        super.updateEntity();
        
        if (worldObj.isRemote)
        	return;

        if(isWorking())
        	dowork();
        else if (inv[0]!=null){
        	boolean canwork=canwork(recipe);
        	
        	setWorking(canwork);
        	
    		if (canwork){
    			//Consume Input
    			inv[0].stackSize-=1;
    			if (inv[0].stackSize==0)
    				inv[0]=null;    			
    		}
    		else
    		{
            	tick=0;
            	progress=0;
    		}

        }
        else{
        	tick=0;
        	progress=0;
        	setWorking(false);
        }

        if (tank.fluid!=null&&tank.fluid.amount==0)
        	tank.fluid=null;   
        	
        if (tank.fluid!=null){
        	fluidID=tank.fluid.fluidID;
        	amountP=tank.fluid.amount*1000/maxCapacity;
        	
        	FluidManager.fillContainer(tank.fluid, inv, 2, 3);
        }
        else{
        	fluidID=0;
        	amountP=0;
        }
        
        out(10);
    }
        
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
    	onInventoryChanged();
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
	
	//IFluidHandler------------------------------------------------------------------
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

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
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] {tank.getInfo()};
	}
	
	//Inventory-------------------------------------------------------------------------------
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
	public String getInvName() {return "tileentityCombustionFurnace";}

	@Override
	public boolean isInvNameLocalized() {return false;}

    @Override
    public int getInventoryStackLimit() {return 64;}

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;}

	@Override
	public void openChest() {}


	@Override
	public void closeChest() {}


	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {return true;}

	
	//SidedInventory
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {return new int[]{0,1,2,3};}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		if(FluidContainerRegistry.isEmptyContainer(itemstack))
			return i==2;
		else if(i==0)
			return TileEntityFurnace.isItemFuel(itemstack);
		
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if(i==1|i==3)
			return true;
		return false;
	}
}

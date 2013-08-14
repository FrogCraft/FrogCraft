package FrogCraft.Machines;

import ic2.api.item.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

import FrogCraft.mod_FrogCraft;
import FrogCraft.Common.*;

public class TileEntityLiquifier extends BaseIC2Machine implements ISidedInventory,IFluidHandler{
	public static int maxCapacity=10000;
	
	public int fluidID,amountP;
	public fcFluidTank tank=new fcFluidTank(maxCapacity);
	public ItemStack inv[];
	
	public int tick=0;
	
	public TileEntityLiquifier() {
		super(128,10000);
		inv=new ItemStack[4];
	}

    @Override
    public void readFromNBT(NBTTagCompound n) {
    	super.readFromNBT(n);
    	
        NBTTagList tagList = n.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
                byte slot = tag.getByte("Slot");
                if (slot >= 0 && slot < inv.length) {
                        inv[slot] = ItemStack.loadItemStackFromNBT(tag);
                }
        }
        
        tank.readFromNBT(n);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound n) {
    	super.writeToNBT(n);
	
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
        n.setTag("Inventory", itemList);
        
        tank.writeToNBT(n);
    }
        
    @Override
    public void updateEntity(){  	
        super.updateEntity();
                       
        //Do nothing for client world
        if (worldObj.isRemote)
            return;
        
        //Variable for update the working state
        boolean didSomething=false;	
        
        if(worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord) instanceof TileEntityAirPump){
        	tick=0;
        	if (energy>=64){
        		TileEntityAirPump te=(TileEntityAirPump)worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
        		
        		if (tank.fluid==null){
        			if(te.useGas(12)){
        				tank.fluid=FluidManager.getFluid("LiquifiedAir",10);	
        				energy-=32;
        				didSomething=true;
        			}
        		}		
        		if (tank.fluid!=null)
        			if(tank.fluid.getFluid().getID()==FluidManager.getFluid("LiquifiedAir",10).fluidID
        			&tank.fluid.amount<=maxCapacity-10)
        				if(te.useGas(12)){
        					tank.fluid.amount+=10;
        					energy-=32;
        					didSomething=true;
        				}
        		
        	}
        }
        else {
        	if(inv[0]!=null&&Items.getItem("airCell").isItemEqual(inv[0])&&
        			(tank.fluid==null|(tank.fluid!=null&&tank.fluid.amount<=maxCapacity-1000))&&
        			(inv[2]==null|(inv[2]!=null&&ic2.api.item.Items.getItem("cell").isItemEqual(inv[2])&&inv[2].stackSize<inv[2].getMaxStackSize()))){
        		if (energy<32)
        			tick=0;
        		else{
        			
        			tick++;
   
        			didSomething=true;
        		
        			energy-=32;
        		
        			if(tick>100){
        				tick=0;
        				
        				if (tank.fluid==null)
            				tank.fluid=FluidManager.getFluid("LiquifiedAir",1000);	
        				else
        					tank.fluid.amount+=1000;
        	
        				
        				inv[0].stackSize-=1;
        				if(inv[0].stackSize==0)
        					inv[0]=null;
        				
        				if(inv[2]==null)
        					inv[2]=ic2.api.item.Items.getItem("cell");
        				else
        					inv[2].stackSize+=1;
        			}
        		}
        	}
        	else
        		tick=0;
        		
        }
    	
        setWorking(didSomething);
        
        if (tank.fluid!=null){
        	fluidID=tank.fluid.fluidID;
        	amountP=tank.fluid.amount*1000/maxCapacity;
        	
        	FluidManager.fillContainer(tank.fluid, inv, 1, 3);
        }
        else{
        	fluidID=0;
        	amountP=0;
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
	public String getInvName() {return "tileentityThermalCracker";}

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
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[]{0,1,2,3};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		if(FluidContainerRegistry.isEmptyContainer(itemstack)){
			if(i==1)
				return true;
		}
		else if (i==0)
			return true;
		
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if(i==2|i==3)
			return true;
		return false;
	}
}

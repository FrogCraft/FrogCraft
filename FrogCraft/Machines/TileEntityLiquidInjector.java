package FrogCraft.Machines;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

import FrogCraft.*;
import FrogCraft.Common.*;
import FrogCraft.Machines2.*;

public class TileEntityLiquidInjector extends BaseIC2Machine implements ISidedInventory,IFluidHandler{
	public int maxCapacity=10000;
	
	public int fluidID,amountP;
	public fcFluidTank tank=new fcFluidTank(maxCapacity);
	public int tick=0;
	public ItemStack inv[];
	
	public TileEntityLiquidInjector() {
		super(128, 10000);
		inv=new ItemStack[2];
	}

	boolean fullyset(int tier){
		if ((!y(1))|(!y(2)))
			return false;
		for (int i=0;i<tier;i++){
			if ((!y(3+i))&(!(worldObj.getBlockTileEntity(xCoord, yCoord+3+i, zCoord) instanceof TileEntityLiquidOutput)))
				return false;
		}
		
		return true;
	}
	
	boolean y(int level){
		return (worldObj.getBlockId(xCoord, yCoord+level, zCoord)==mod_FrogCraft.Machines2.blockID)&(worldObj.getBlockMetadata(xCoord, yCoord+level, zCoord)==1);
	}
	
	void dowork(FluidStack liquid){
		FluidStack[] recipes=RecipeManager.getCondenceTowerRecipes(liquid);
		
		if (recipes==null)
			return ;
		
		/**{amount of input consumed per tick, tickTotal, energy per tick}*/
		Integer[] info=RecipeManager.getCondenceTowerRecipeInfo(liquid);
		int tier=RecipeManager.getCondensetowerRecipesTier(recipes);
		
		if (!fullyset(tier))
			return ;		
		
		if (energy<info[1])
			return ;
		
		if (!canwork(recipes,tier))
			return;
		
		
		energy-=info[1];
		tick+=1;
		if (tick<info[2])
			return;
		
		tick=0;
		
		tank.fluid.amount-=info[0];
		
		
		for (int i=0;i<RecipeManager.getCondensetowerRecipesTier(recipes);i++){
			TileEntity te=worldObj.getBlockTileEntity(xCoord, yCoord+3+i, zCoord);
			if (te instanceof TileEntityLiquidOutput){
				((TileEntityLiquidOutput)te).fill(recipes[i]);
			}
		}
		
	}
	
	
	boolean canwork(FluidStack[] recipes,int tier){		
		for (int i=0;i<tier;i++){
			if (recipes[i]!=null){
				TileEntity te=worldObj.getBlockTileEntity(xCoord, yCoord+3+i, zCoord);
				
				if (te instanceof TileEntityLiquidOutput){
					if (!((TileEntityLiquidOutput)te).canFill(recipes[i]))
						return false;
				}
				else
					return false;		
			}
		}
		
		
		return true;
	}
	
	
    @Override
    public void updateEntity(){  	
        super.updateEntity();
                       
        if (worldObj.isRemote)
            return;
        
        
        if(tank.fluid!=null)
        	if (tank.fluid.amount==0)
        		tank.fluid=null;
        	else
        		dowork(tank.fluid);
        	
        FluidStack l=FluidManager.drainContainer(maxCapacity,tank.fluid,inv,0,1);
        if (l!=null){
        	if (tank.fluid==null)
        		tank.fluid=l;
        	else
        		tank.fluid.amount+=l.amount;
        }
        
        if (tank.fluid!=null){
        	fluidID=tank.fluid.fluidID;
        	amountP=tank.fluid.amount*1000/maxCapacity;
        }
        else
        	fluidID=0;
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

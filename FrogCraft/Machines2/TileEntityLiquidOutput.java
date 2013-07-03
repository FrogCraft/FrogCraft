package FrogCraft.Machines2;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import FrogCraft.*;
import FrogCraft.Common.LiquidIO;
import FrogCraft.Common.LiquidTank;
import FrogCraft.Common.SidedIC2Machine;

public class TileEntityLiquidOutput extends SidedIC2Machine implements ISidedInventory,ITankContainer{
	public int maxCapacity=10000;
	
	public int idOut,damageOut,amountOutP;
	public LiquidTank tank=new LiquidTank(maxCapacity);
	public ItemStack inv[];

	public TileEntityLiquidOutput(){
		inv=new ItemStack[2];
	}
	
	public boolean canFill(int[] liquid){
		return canFill(liquid[0],liquid[1],liquid[2]);
	}
	
	public boolean canFill(int id,int damage,int amount){
		if (tank.liquid==null)
			return true;
		if (tank.getLiquid().itemID==id&tank.getLiquid().itemMeta==damage&tank.getLiquid().amount<=maxCapacity-amount)
			return true;
		return false;
	}
	
	public void fill(int[] liquid){
		if (liquid==null)
			return;
		fill(liquid[0],liquid[1],liquid[2]);
	}
	
	public void fill(int id,int damage,int amount){
		if (id==0)
			return;
		if (tank.getLiquid()==null)
			tank.liquid=(new LiquidStack(id,amount,damage));
		else
			tank.getLiquid().amount+=amount;
	}
	

	
    @Override
    public void updateEntity(){  	
        super.updateEntity();
        
        if (worldObj.isRemote)
            return;
        
        LiquidStack liquid=tank.getLiquid();        
          
        LiquidIO.fillContainer(liquid,inv,0,1);
        
        if (liquid!=null){
        	idOut=liquid.itemID;
        	damageOut=liquid.itemMeta;
        	amountOutP=liquid.amount*1000/maxCapacity;
        }
    }
	    
    //LiquidContainer-----------------------------------------------------------------------------
    
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {return 0;}
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {return 0;}
	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {return tank.drain(maxDrain, doDrain);}
	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {return null;}
	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {return new ILiquidTank[]{tank};}
	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {return tank;}
	
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
                    
            
        	idOut=tagCompound.getInteger("idOut");
        	damageOut=tagCompound.getInteger("damageOut");
        	
        	tank.liquid=(new LiquidStack(idOut,tagCompound.getInteger("amountOut"),damageOut));
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
            
        	int amountOut=0;
        	LiquidStack lin=tank.getLiquid();
        	
        	if (tank.containsValidLiquid()){
            	idOut=lin.itemID;
            	damageOut=lin.itemMeta;
        		amountOut=lin.amount;
        	}

        	
        	tagCompound.setInteger("idOut", idOut);
        	tagCompound.setInteger("damageOut", damageOut);
        	tagCompound.setInteger("amountOut", amountOut);	
    }


	@Override
	public void openChest() {}


	@Override
	public void closeChest() {}


	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {return false;}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		if (var1==0|var1==1)
			return new int[]{0};
		return new int[]{1};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}
}

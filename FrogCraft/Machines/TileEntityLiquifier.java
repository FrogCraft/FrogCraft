package FrogCraft.Machines;

import ic2.api.item.Items;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import FrogCraft.mod_FrogCraft;
import FrogCraft.Common.*;

public class TileEntityLiquifier extends BaseIC2Machine implements ISidedInventory,ITankContainer{
	public static int maxCapacity=10000;
	
	public int idOut,damageOut,amountOutP;
	public LiquidTank tankOut=new LiquidTank(maxCapacity);
	public ItemStack inv[];
	
	public int tick=0;
	
	public TileEntityLiquifier() {
		super(128,10000);
		inv=new ItemStack[4];
	}

    @Override
    public void readFromNBT(NBTTagCompound n) {
    	super.readFromNBT(n);
    	
    	int amountIn,amountOut;
    	
    	idOut=n.getInteger("idOut");
    	damageOut=n.getInteger("damageOut");
    	amountIn=n.getInteger("amountIn");
    	amountOut=n.getInteger("amountOut");
    	
    	tankOut.liquid=(new LiquidStack(idOut,amountOut,damageOut));
    	
        NBTTagList tagList = n.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
                byte slot = tag.getByte("Slot");
                if (slot >= 0 && slot < inv.length) {
                        inv[slot] = ItemStack.loadItemStackFromNBT(tag);
                }
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound n) {
    	super.writeToNBT(n);
	
    	int amountOut=0;
    	LiquidStack lout=tankOut.getLiquid();
    	
    	
    	if (tankOut.containsValidLiquid()){
        	idOut=lout.itemID; 
        	damageOut=lout.itemMeta;
    		amountOut=lout.amount;
    	}
    	
    	n.setInteger("idOut", idOut);
    	n.setInteger("damageOut", damageOut);  
    	n.setInteger("amountOut", amountOut);     
    	
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
        		
        		if (!tankOut.containsValidLiquid()&te.getGas()>=12){
        			if(te.useGas(12)){
        				tankOut.liquid=(new LiquidStack(mod_FrogCraft.Liquids.itemID,10,0));	
        				energy-=32;
        				didSomething=true;
        			}
        		}		
        		if (tankOut.liquid!=null)
        			if(tankOut.liquid.itemID==mod_FrogCraft.Liquids.itemID&tankOut.liquid.itemMeta==0&tankOut.liquid.amount<=maxCapacity-10)
        				if(te.useGas(12)){
        					tankOut.liquid.amount+=10;
        					energy-=32;
        					didSomething=true;
        				}
        	}
        }
        else {
        	if(inv[0]!=null&&Items.getItem("airCell").isItemEqual(inv[0])&&
        			(!tankOut.containsValidLiquid()|(tankOut.containsValidLiquid()&&tankOut.liquid.amount<=maxCapacity-1000))){
        		if (energy<32)
        			tick=0;
        		else{
        			
        			tick++;
   
        			didSomething=true;
        		
        			energy-=32;
        		
        			if(tick>100){
        				tick=0;
        				
        				if (!tankOut.containsValidLiquid()){
        					tankOut.liquid=(new LiquidStack(mod_FrogCraft.Liquids.itemID,1000,0));
        				}
        				else{
        					tankOut.liquid.amount+=1000;
        				}
        				
        				inv[0].stackSize-=1;
        				if(inv[0].stackSize==0)
        				inv[0]=null;
        			}
        		}
        	}
        	else
        		tick=0;
        		
        }
    	
        setWorking(didSomething);
        
        LiquidIO.fillContainer(tankOut.liquid,inv,1,3);
    	
        if (tankOut.liquid!=null){
        	idOut=tankOut.liquid.itemID; 
        	damageOut=tankOut.liquid.itemMeta;
        	amountOutP=tankOut.liquid.amount*1000/maxCapacity;
        }
    }
    
    
    
    //Liquid Options -- Ready for update
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {return 0;}
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {return 0;}
	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {return tankOut.drain(maxDrain, doDrain);}
	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {return tankOut.drain(maxDrain, doDrain);}
	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {return new ILiquidTank[]{tankOut};}
	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {return tankOut;}	
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
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {return false;}

	//SidedInventory
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		if (var1==0)
			return new int[]{0};
		if (var1==1)
			return new int[]{1};		
		return new int[]{2,3};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {return true;}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {return true;}
}

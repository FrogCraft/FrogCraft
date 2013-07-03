package FrogCraft.Machines;

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
	
	public int idIn,idOut,damageIn,damageOut,amountInP,amountOutP;
	public LiquidTank tankIn=new LiquidTank(maxCapacity),tankOut=new LiquidTank(maxCapacity);
	public ItemStack inv[];
	
	public int tick=0;
	
	public TileEntityLiquifier() {
		super(128,10000);
		inv=new ItemStack[4];
		//tankIn.setLiquid(new LiquidStack(gregtechmod.api.GregTech_API.getGregTechItem(14,1,15).itemID,1000,15));
		//tankIn.setLiquid(new LiquidStack(Block.waterStill,10000));
	}

    @Override
    public void readFromNBT(NBTTagCompound n) {
    	super.readFromNBT(n);
    	
    	int amountIn,amountOut;
    	
    	idIn=n.getInteger("idIn");
    	idOut=n.getInteger("idOut");
    	damageIn=n.getInteger("damageIn");
    	damageOut=n.getInteger("damageOut");
    	amountIn=n.getInteger("amountIn");
    	amountOut=n.getInteger("amountOut");
    	
    	tankIn.liquid=(new LiquidStack(idIn,amountIn,damageIn));
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
	
    	int amountIn=0,amountOut=0;
    	LiquidStack lin=tankIn.getLiquid(),lout=tankOut.getLiquid();
    	
    	if (tankIn.containsValidLiquid()){
        	idIn=lin.itemID;
        	damageIn=lin.itemMeta;
    		amountIn=lin.amount;
    	}
    	
    	if (tankOut.containsValidLiquid()){
        	idOut=lout.itemID; 
        	damageOut=lout.itemMeta;
    		amountOut=lout.amount;
    	}
    	
    	n.setInteger("idIn", idIn);
    	n.setInteger("idOut", idOut);
    	n.setInteger("damageIn", damageIn);
    	n.setInteger("damageOut", damageOut);    
    	n.setInteger("amountIn", amountIn);
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
                       
        
        if (worldObj.isRemote)
            return;
        
        boolean didSomething=false;	
        
        if(worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord) instanceof TileEntityAirPump){
        	if (energy>=64){
        		TileEntityAirPump te=(TileEntityAirPump)worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
			
        		if (!tankOut.containsValidLiquid()&te.getGas()>=12){
        			if(te.useGas(12)){
        				tankOut.liquid=(new LiquidStack(mod_FrogCraft.Liquids.itemID,10,0));	
        				energy-=16;
        				didSomething=true;
        			}
        		}		
        		if (tankOut.liquid!=null)
        			if(tankOut.liquid.itemID==mod_FrogCraft.Liquids.itemID&tankOut.liquid.itemMeta==0&tankOut.liquid.amount<=maxCapacity-10)
        				if(te.useGas(12)){
        					tankOut.liquid.amount+=10;
        					energy-=64;
        					didSomething=true;
        				}
        	}
        }
        else if(tankIn.liquid!=null){
        	didSomething=work();
        }
    	
        setWorking(didSomething);
        
        LiquidStack l=LiquidIO.drainContainer(maxCapacity,tankIn.liquid,inv,0,2);
        if (l!=null){
        	if (tankIn.liquid==null)
        		tankIn.liquid=l;
        	else
        		tankIn.liquid.amount+=l.amount;
        	tankIn.liquid=(tankIn.liquid);
        }
        LiquidIO.fillContainer(tankOut.liquid,inv,1,3);
    	
        
        if (tankIn.liquid!=null){
        	idIn=tankIn.liquid.itemID;
        	damageIn=tankIn.liquid.itemMeta;
        	amountInP=tankIn.liquid.amount*1000/maxCapacity;
        }
        if (tankOut.liquid!=null){
        	idOut=tankOut.liquid.itemID; 
        	damageOut=tankOut.liquid.itemMeta;
        	amountOutP=tankOut.liquid.amount*1000/maxCapacity;
        }
    }
    
    boolean work(){
    	int[] recipe=RecipeManager.getLiquifierRecipe(tankIn.liquid.itemID,tankIn.liquid.itemMeta,tankIn.liquid.amount);
    	
    	if (recipe==null)
    		return false;
    	
    	if (energy<recipe[6])
    		return false;
    	
    	if (!tankOut.containsValidLiquid()){
    		tankOut.liquid=(new LiquidStack(recipe[3],recipe[5],recipe[4]));	
    		tankIn.liquid.amount-=recipe[2];
    		energy-=recipe[6];
    	}
    	else{
    		if (tankOut.liquid.itemID!=recipe[3]|tankOut.liquid.itemMeta!=recipe[4]|tankOut.liquid.amount>maxCapacity-recipe[5])
    			return false;
    		
    		tick+=1;
    		if (tick>=recipe[7]){
    			tick=0;
    			tankOut.liquid.amount+=recipe[5];
				tankIn.liquid.amount-=recipe[2];
				energy-=recipe[6];  
    		}
    	}
    	return true;
    }
    
    //boolean canWork(int eu,int v){
    //	if (energy<eu)
    //		return false;
    //	
    //	if (!tankIn.containsValidLiquid())
    //		return false;
    //	
    //	if (tankIn.liquid.amount<v)
    //		return false;
    //	
    //	if (tankOut.liquid!=null)
    //		if(tankOut.liquid.amount>maxCapacity-v)
    //			return false;
    //	
    //	return true;
    //}
    
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		return tankIn.fill(resource, doFill);
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		return tankIn.fill(resource, doFill);
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (from==from.DOWN)
			return tankIn.drain(maxDrain, doDrain);
		else
			return tankOut.drain(maxDrain, doDrain);
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		if (tankIndex==0)
			return tankIn.drain(maxDrain, doDrain);
		else
			return tankOut.drain(maxDrain, doDrain);
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		if (direction==direction.UNKNOWN)
			return new ILiquidTank[]{tankIn,tankOut};
		if (direction==direction.DOWN)
			return new ILiquidTank[]{tankIn};
		else
			return new ILiquidTank[]{tankOut};
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		if (direction==direction.DOWN)
			return tankIn;
		else
			return tankOut;
	}
	
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

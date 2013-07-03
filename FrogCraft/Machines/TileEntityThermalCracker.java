package FrogCraft.Machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import FrogCraft.*;
import FrogCraft.Common.*;

public class TileEntityThermalCracker extends BaseIC2Machine implements ISidedInventory,ITankContainer{
	public int maxCapacity=50000;
	
	public int progress=0,tick=0;
	public ItemStack[] inv;
	public int idOut,damageOut,amountOutP;
	public LiquidTank tank=new LiquidTank(maxCapacity);
	
	public TileEntityThermalCracker() {
		super(128, 10000);
		inv = new ItemStack[4];
	}
	
    @Override
    public void updateEntity(){  	
        super.updateEntity();
    	
        if (worldObj.isRemote)
            return;
       
        
        if (inv[0]!=null)
        	dowork();
        else{
        	tick=0;
        	progress=0;
        	setWorking(false);
        }

        
        fillContainer(tank.liquid,inv,2,3);

        if (tank.liquid!=null)
        	if (tank.liquid.amount==0){
        		tank.liquid=null;
        	}
        
        if (tank.liquid!=null){
        	idOut=tank.liquid.itemID;
        	damageOut=tank.liquid.itemMeta;
        	amountOutP=tank.liquid.amount*1000/maxCapacity;
        }
        else{
        	idOut=0;
        	damageOut=0;
        }
    }
    
    void fillContainer(LiquidStack liquid,ItemStack[] slots,int empty,int target){
    	if (!LiquidContainerRegistry.isEmptyContainer(slots[empty]))
    		return;
    	
    	ItemStack s=LiquidContainerRegistry.fillLiquidContainer(liquid,slots[empty]);
    	if (s==null)
    		return;
    	if (slots[target]!=null)
    		if (slots[target].itemID!=s.itemID|slots[target].getItemDamage()!=s.getItemDamage()|slots[target].stackSize>slots[target].getMaxStackSize()-s.stackSize)
    			return;
    	
    	if (slots[target]==null)
    		slots[target]=s;
    	else
    		slots[target].stackSize+=s.stackSize;
    	
    	if (slots[empty].stackSize==1)
    		slots[empty]=null;
    	else
    		slots[empty].stackSize-=1;
    	
    	liquid.amount-=LiquidContainerRegistry.getLiquidForFilledItem(slots[target]).amount;
    }
	
    void dowork(){
    	int[] recipe=RecipeManager.getThermalCrackerRecipe(inv[0]);
    	boolean canwork=canwork(recipe);
    	
    	setWorking(canwork);
    	
		if (!canwork)
			return;
		
		progress=100*tick/recipe[4];
		
		tick+=1;
		energy-=recipe[3];
		if (tick<recipe[4])
			return;
		
		tick=0;
		progress=0;
		
		if (inv[0].stackSize>recipe[2])
			inv[0].stackSize-=recipe[2];
		else
			inv[0]=null;
		
		if(inv[1]==null)
			inv[1]=new ItemStack(recipe[5],recipe[7],recipe[6]);
		else
			inv[1].stackSize+=recipe[7];
		
		fill(recipe[8],recipe[9],recipe[10]);
    }
    
    boolean canwork(int[] recipe){
    	if (recipe==null) 
    		return false;
    	
		if (energy<recipe[3])
			return false;
    	
    	if (inv[1]!=null)
    		if (inv[1].itemID!=recipe[5]|inv[1].getItemDamage()!=recipe[6]|inv[1].stackSize>inv[1].getMaxStackSize()-recipe[7])
    			return false;
    	
    	if (!canFill(recipe[8],recipe[9],recipe[10]))
    		return false;
    	
    	return true;
    }
    
	public void fill(int id,int damage,int amount){
		if (id==0)
			return;
		if (tank.getLiquid()==null)
			tank.liquid=(new LiquidStack(id,amount,damage));
		else
			tank.getLiquid().amount+=amount;
	}
	
	public boolean canFill(int id,int damage,int amount){
		if (!tank.containsValidLiquid())
			return true;
		if (tank.getLiquid().itemID==id&tank.getLiquid().itemMeta==damage&tank.getLiquid().amount<=maxCapacity-amount)
			return true;
		return false;
	}
    
    //LiquidContainer-----------------------------------------------------------------------------
    
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {return 0;}
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {return 0;}
	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {return tank.drain(maxDrain, doDrain);}
	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {return tank.drain(maxDrain, doDrain);}
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

	
	//SidedInventory
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		if (var1==0|var1==1)
			return new int[]{0};
		return new int[]{1,3};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {return true;}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {return true;}
}

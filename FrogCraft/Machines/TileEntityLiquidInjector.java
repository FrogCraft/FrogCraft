package FrogCraft.Machines;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import FrogCraft.api.*;
import FrogCraft.*;
import FrogCraft.Common.*;
import FrogCraft.Machines2.*;

public class TileEntityLiquidInjector extends BaseIC2Machine implements ISidedInventory,ITankContainer{
	public int maxCapacity=10000;
	
	public int idIn,damageIn,amountInP;
	public LiquidTank tank=new LiquidTank(maxCapacity);
	public int tick=0;
	public ItemStack inv[];
	
	public TileEntityLiquidInjector() {
		super(128, 10000);
		inv=new ItemStack[2];
		//tank.setLiquid(new LiquidStack(Block.waterStill,10000));
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
	
	void dowork(LiquidStack liquid){
		int recipes[]=RecipeManager.getLiquidInjectorRecipes(liquid.itemID, liquid.itemMeta, liquid.amount);
		
		if (recipes==null)
			return ;
		
		if (!fullyset(RecipeManager.getLiquidInjectorRecipesTier(recipes)))
			return ;		
		
		if (energy<recipes[3])
			return ;
		
		if (!canwork(recipes))
			return;
		
		tick+=1;
		if (tick<recipes[4])
			return;
		
		tick=0;
		
		tank.getLiquid().amount-=recipes[2];
		energy-=recipes[3];
		
		for (int i=0;i<5;i++){
			TileEntity te=worldObj.getBlockTileEntity(xCoord, yCoord+3+i, zCoord);
			if (te instanceof TileEntityLiquidOutput){
				((TileEntityLiquidOutput)te).fill(RecipeManager.getLiquidInjectorRecipesX(recipes,i));
			}
		}
		
	}
	
	
	boolean canwork(int[] recipes){		
		for (int i=0;i<5;i++){
			int[] recipe=RecipeManager.getLiquidInjectorRecipesX(recipes, i);

			if (recipe!=null){
				TileEntity te=worldObj.getBlockTileEntity(xCoord, yCoord+3+i, zCoord);
				
				if (te instanceof TileEntityLiquidOutput){
					if (!((TileEntityLiquidOutput)te).canFill(recipe))
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
        
        
        if(tank.liquid!=null)
        	if (tank.liquid.amount==0)
        		tank.liquid=null;
        	else
        		dowork(tank.liquid);
        	
        LiquidStack l=LiquidIO.drainContainer(maxCapacity,tank.liquid,inv,0,1);
        if (l!=null){
        	if (tank.liquid==null)
        		tank.liquid=l;
        	else
        		tank.liquid.amount+=l.amount;
        }
        
        if (tank.liquid!=null){
        	idIn=tank.liquid.itemID;
        	damageIn=tank.liquid.itemMeta;
        	amountInP=tank.liquid.amount*1000/maxCapacity;
        }
        else
        	idIn=0;
    }
	
    @Override
    public void readFromNBT(NBTTagCompound n) {
    	super.readFromNBT(n);
    	    	
    	idIn=n.getInteger("idIn");
    	damageIn=n.getInteger("damageIn");
    	
    	tank.liquid=new LiquidStack(idIn,n.getInteger("amountIn"),damageIn);
    	
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
	
    	int amountIn=0;
    	LiquidStack lin=tank.getLiquid();
    	
    	if (tank.containsValidLiquid()){
        	idIn=lin.itemID;
        	damageIn=lin.itemMeta;
    		amountIn=lin.amount;
    	}

    	
    	n.setInteger("idIn", idIn);
    	n.setInteger("damageIn", damageIn);
    	n.setInteger("amountIn", amountIn);
    	
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
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {return tank.fill(resource, doFill);}
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {return tank.fill(resource, doFill);}
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
		return new int[]{1};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {return true;}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {return true;}
}

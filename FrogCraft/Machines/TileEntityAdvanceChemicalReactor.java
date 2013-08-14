package FrogCraft.Machines;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidContainerRegistry;

import FrogCraft.Common.*;

public class TileEntityAdvanceChemicalReactor extends BaseIC2Machine implements ISidedInventory{
	
	public int tick,tickMax;
	public ItemStack inv[];
	
	public int progress=0;
	public int elevel=0;
	
	int curRecipeIndex=-1;
	
	public TileEntityAdvanceChemicalReactor() {
		super(128, 10000);
		inv=new ItemStack[13];
	}
	
    @Override
	public void onInventoryChanged(){
		curRecipeIndex=RecipeManager.findAdvanceChemicalReactorRecipeIndex(inv); //Find a correct recipe with sufficient amount
    }
	
    @Override
    public void updateEntity(){  	
        super.updateEntity();
        
              
        
        if (worldObj.isRemote)
            return;
        
        elevel=14*energy/maxEnergy;       
        
        
        if (curRecipeIndex>-1){
        	ItemStack[] curRecipe=RecipeManager.advanceChemicalReactorRecipes.get(curRecipeIndex);
        	int[] curRecipeInfo=RecipeManager.advanceChemicalReactorRecipeInfo.get(curRecipeIndex);

        	
        	if(inv[12]!=null&&curRecipe[10]!=null&&inv[12].isItemEqual(curRecipe[10]))
        		tickMax=curRecipeInfo[2]; //Catalyst is needed
        	else if(curRecipe[10]!=null&&inv[12]==null){
        		tickMax=-1;} 			  //Wrong condition  	
        	else if(curRecipe[10]!=null&&inv[12]!=null&&!inv[12].isItemEqual(curRecipe[10]))
        		tickMax=-1; 			  //Wrong condition         	
        	else
        		tickMax=curRecipeInfo[1]; //No catalyst


        	if(tickMax>0&&energy>=curRecipeInfo[0]&&((checkOutput(curRecipe)&&checkCell(curRecipe))|(tick>0))){
        		setWorking(true);
        		
        		//
        		
            	progress=tick*100/tickMax;
        		if (progress>100)
        			progress=100;
        	
        		energy-=curRecipeInfo[0];
        		tick++;
        		
        		if(tick>=tickMax){ 
        			tick=0;
        			
        			if (curRecipe[11]!=null){
        				if(inv[10].stackSize>curRecipe[11].stackSize)
        					inv[10].stackSize-=curRecipe[11].stackSize;
        				else
        					inv[10]=null;
        			}
        			
        			if (curRecipe[12]!=null){
        				if(inv[11]==null)
        					inv[11]=curRecipe[12].copy();
        				else
        					inv[11].stackSize+=curRecipe[12].stackSize;
        			}        			
        			
        			for (int i=0;i<5;i++){  
        				if(curRecipe[i]!=null){ //Consume Input
        					cosumeInput(curRecipe[i]);
        				}
        			
        				
        				if(curRecipe[i+5]!=null){//Set Output
        					if (inv[i+5]==null)
        						inv[i+5]=curRecipe[i+5].copy();
        					else
        						inv[i+5].stackSize+=curRecipe[i+5].stackSize;
        				}
        			}    
        			onInventoryChanged();
        		}
        	}
        	else{
        		setWorking(false);
        		tick=0;
        		tickMax=0;
        		progress=0;        		
        	}
        }
        else{
        	setWorking(false);
        	tick=0;
        	tickMax=0;
        	progress=0;
        }
    }
	
    boolean checkCell(ItemStack[] rec){
    	if (rec[11]!=null){
    		if(inv[10]==null)
    			return false;
    		else if(!inv[10].isItemEqual(rec[11]))
    			return false;
    		else if(inv[10].stackSize<rec[11].stackSize)
    			return false;
    	}
    	
    	if(rec[12]!=null){
    		if(inv[11]==null)
    			return true;
    		else if (!inv[11].isItemEqual(rec[12]))
    			return false;
    		else if (inv[11].stackSize>inv[11].getMaxStackSize()-rec[12].stackSize)
    			return false;
    	}
    	
    	return true;
    }
    
    boolean checkOutput(ItemStack[] curRecipe){    //Check if there are enough space to put the out put	
    	for (int i=5;i<10;i++){                      
    		if(curRecipe[i]!=null){    
    			if(inv[i]!=null&&(!inv[i].isItemEqual(curRecipe[i])))
    				return false;
    			
    			if(inv[i]!=null&&inv[i].stackSize>inv[i].getMaxStackSize()-curRecipe[i].stackSize)
    				return false;
    		}
    	}
    	return true;
    }
    
    
    void cosumeInput(ItemStack consumedItemStack){
    	int amountNeed=consumedItemStack.stackSize;
    	for (int j=0;j<5;j++){ //look up each inv
    		if(amountNeed==0)
    			return;    		
    		
    		if (inv[j]!=null&&inv[j].isItemEqual(consumedItemStack)){
    			if(inv[j].stackSize>amountNeed){
    				inv[j].stackSize-=amountNeed;
    				amountNeed=0;
    			}
    			else{
    				amountNeed-=inv[j].stackSize;
    				inv[j]=null;  
    				     				
    			}
    		}
    	}
    }
    
    //NBT
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {return true;}

	//SidedInventory
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[]{0,1,2,3,4,5,6,7,8,9,10,11};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		if(FluidContainerRegistry.isEmptyContainer(itemstack)){
			if(slot==10)
				return true;
		}
		else if(slot==0|slot==1|slot==2|slot==3|slot==4)
				return true;

		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		if(slot==5|slot==6|slot==7|slot==8|slot==9|slot==11)
			return true;
		return false;
	}
}

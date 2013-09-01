package FrogCraft.Machines2;

import java.util.ArrayList;

import FrogCraft.Common.SidedIC2Machine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAutoWorkBench extends TileEntity implements ISidedInventory{
	public ItemStack inv[];

	public LocalInventoryCrafting craftMatrix = new LocalInventoryCrafting();
	
	private class LocalInventoryCrafting extends InventoryCrafting {
		public ItemStack[] stacks;
		public LocalInventoryCrafting() {
			super(new Container() {
				@Override
				public boolean canInteractWith(EntityPlayer entityplayer) {
					return false;
				}
			}, 3, 3);
			stacks=new ItemStack[9];
		}
		
	    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	    {
	    	super.setInventorySlotContents(par1, par2ItemStack);
	    	stacks[par1] = par2ItemStack;
	    }
	}
	
	
	
	/**Find a matching recipe result*/
    ItemStack getCraftingResult(){
    	for (int i=0;i<9;i++)
    		craftMatrix.setInventorySlotContents(i, inv[27+i]);
    	
    	return CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj);
    }
    
    //Find how many suitable space in the product slots
    int findEmptyProductSpace(){
    	int r=0;
    	for(int i=36;i<40;i++)
    		if(inv[i]==null)
    			r+=inv[40].getMaxStackSize();
    		else if(inv[i].isItemEqual(inv[40]))
    			r+=inv[40].getMaxStackSize()-inv[i].stackSize;
    			
    	return r;
    }
    
    ItemStack[] collectItemStack(ItemStack[] in){
		ItemStack[] items=new ItemStack[9];
		int num=0;
		for (int i=0;i<9;i++){
			if(in[i]!=null){
				int id=-1;
				for(int j=0;j<num;j++){ //contains?
					if(items[j].isItemEqual(in[i]))
						id=j;
				}
				
				if(id==-1) //does't contain
				{
					items[num]=in[i].copy();
					num+=1;
				}
				else{
					items[id].stackSize+=in[i].stackSize;
				}
					
			}
		}
		
		ItemStack[] r=new ItemStack[num];
		for (int i=0;i<num;i++)
			r[i]=items[i];
		
    	return r;
    }
    
    void cosumeInput(ItemStack consumedItemStack){
    	int amountNeed=consumedItemStack.stackSize;
    	for (int j=0;j<27;j++){ //look up each inv
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

	//TileEntity
    @Override
	public void onInventoryChanged(){
    	inv[40]=getCraftingResult();    	
    }
    
    @Override
    public void updateEntity()
    {
    	super.updateEntity();

    	if(worldObj.isRemote)
    		return;
    	
    	if(inv[40]==null)
    		return; 		//When the recipe mismatch
    	
    	if(findEmptyProductSpace()<inv[40].stackSize)
    		return;
    	
    	ItemStack requirements[]=collectItemStack(craftMatrix.stacks);

    	for (ItemStack i:requirements){
    		int amountStillRequired=i.stackSize;
    		
    		for (int j=0;j<27;j++){
    			if (inv[j]!=null&&inv[j].isItemEqual(i))
    				amountStillRequired-=inv[j].stackSize;
    		}
    		
    		if (amountStillRequired>0)
    			return; //Not enough Item
    	}
    	
    	for (ItemStack i:requirements){
    		cosumeInput(i);
    	}
    	
    	int amountStillLeft=inv[40].stackSize;
    	for(int i=36;i<40;i++){  		
    		if(inv[i]==null){
    			inv[i]=inv[40].copy();
    			return;
    		}
    		else if(inv[i].isItemEqual(inv[40])&&inv[i].stackSize<inv[i].getMaxStackSize()){ //There used to be a bug
    			inv[i].stackSize+=amountStillLeft;
    			if(inv[i].stackSize>inv[i].getMaxStackSize())
    				amountStillLeft=inv[i].getMaxStackSize()-inv[i].stackSize;
    			else
    				return;
    		}
    	}
    }
	
	public TileEntityAutoWorkBench(){
		super();
		inv=new ItemStack[41];
	}
    
	//NBT (Saving data)
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

	//ISidedInventory
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
			return new int[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8,
							  9,10,11,12,13,14,15,16,17,
							  18,19,20,21,22,23,24,25,26,
							  36,37,38,39};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		if(slot<27)
			return true;
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		if (slot==36|slot==37|slot==38|slot==39)
			return true;
		return false;
	}
    
	//Inventory
	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
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
	public String getInvName() {return "tileentityAWB";}

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
}

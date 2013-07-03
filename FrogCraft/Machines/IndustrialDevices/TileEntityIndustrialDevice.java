package FrogCraft.Machines.IndustrialDevices;

import ic2.api.tile.IWrenchable;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.network.NetworkHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import java.util.ArrayList;
import java.util.List;

import FrogCraft.Common.*;

public abstract class TileEntityIndustrialDevice extends BaseIC2Machine implements ISidedInventory{
	//Variables
	private ItemStack[] inv;
	
	public int progress;
	public int tick;
	public int heat;

    public static String[] SoundSource = {"Machines/CompressorOp.ogg","Machines/MaceratorOp.ogg","Machines/ExtractorOp.ogg","Machines/Electro Furnace/ElectroFurnaceStart.ogg"};
    
    
    private IC2AudioSource audioSource;
    
	//Class Declaration
	public TileEntityIndustrialDevice(){
		super(128,10000);
		inv = new ItemStack[12];	
		progress=0;
		tick=0;
		heat=0;
		
    	if (networkedFields == null)
    	{
            networkedFields = new ArrayList();
            networkedFields.add("facing");
    	}
	    
	}
	
    @Override
    public void invalidate()
    {
    	if (this.audioSource != null)
    	{
    		IC2AudioSource.removeSource(audioSource);
    		this.audioSource = null;
    	}
    	super.invalidate();
    }
	
    @Override
    public void updateEntity()
    {
    	
    	if (!this.created)
        {
            this.created = true;
            NetworkHelper.requestInitialData(this);
            NetworkHelper.announceBlockUpdate(worldObj, xCoord, yCoord, zCoord);
        }
    	
    	
        super.updateEntity();
                
        
        
        
        if (worldObj.isRemote)        
            return;
        
        
        tick+=1;
                
        boolean canwork=canwork();
        
        setWorking(canwork);
        
        if (canwork&heat<100 & energy-40>=0){
        	if (heat>=98)
        		energy-=5;
        	else 
        		energy-=32;
        	
        	if(tick%10==0)
        		heat=heat+1;
        }
        	        
        if (tick==100){
        	tick=0;
        	
        		if (heat>0)heat=heat-1;
        	
        }
        
        if (isWorking()){
        	NetworkHelper.initiateTileEntityEvent(this, 0, true);
        }
        else{
        	NetworkHelper.initiateTileEntityEvent(this, 1, true);
        }
        
        if (heat<30|!canwork){
        	progress=0;
        	return;
        }
        
        
        

        
        if (cansmeltg()&energy>=300){
        	progress+=1;
        	if (progress==15){
        		progress=0;
        		dowork();
        		
        	}        		
        }
        else
        	progress=0;
    }
    
    void dowork(){
    	for (int i=0;i<6;i++){
    		ItemStack[] results=getResult(inv[i]);
    		ItemStack result=results[1];
    		if (result!=null){ //Can smelt
    			if (inv[i+6]==null){
    				inv[i+6]=result.copy();
    				
    				inv[i]=results[0].copy();
    				if(inv[i].stackSize==0)
    					inv[i]=null;
    				
    				energy-=50;
    			}
    			
    			else if (inv[i+6].getItem()==result.getItem()&inv[i+6].getItemDamage()==result.getItemDamage()){
    				if (inv[i+6].stackSize<=inv[i+6].getMaxStackSize()-result.stackSize){
    					inv[i+6].stackSize+=result.stackSize;
    					
    					
    					inv[i]=results[0].copy();
        				if(inv[i].stackSize==0)
        					inv[i]=null;
    					
        				energy-=50;	
    				}
    			}
    		}
    	}
    }
    
    public boolean canwork(){
    	if (energy<300)
    		return false;
    	
    	if (redPowerd())
    		return true;
    	else if (cansmeltg())
    		return true;
    	else				
    		return false;
    }
    
    boolean redPowerd(){
    		return (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord));
    }
    
    boolean cansmeltg(){
    	for (int i=0;i<6;i++){
    		ItemStack result=getResultF(inv[i]);
    		if (result!=null){
    			if(inv[i+6]==null){
    				return true;
    			}
    			else if (result.getItem()==inv[i+6].getItem()&result.getItemDamage()==inv[i+6].getItemDamage()&inv[i+6].stackSize<=inv[i+6].getMaxStackSize()-result.stackSize){
    				return true;
    			}
    		}
    	}
    	return false;
    }
	
    public abstract ItemStack[] getResult(ItemStack i);
    public abstract ItemStack getResultF(ItemStack i);    
    
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
	public String getInvName() {
		// TODO Auto-generated method stub
		return "tileentityIndustrialDevice";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

    @Override
    public int getInventoryStackLimit() {
            return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
            return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
            player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
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
            
            heat = tagCompound.getInteger("heat");        
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
            tagCompound.setInteger("heat", heat);
    }


	public int[] getAccessibleSlotsFromSide(int var1){
    	if (var1==1|var1==0)
    		return new int[]{0,1,2,3,4,5};
    	return new int[]{6,7,8,9,10,11};
    }
	
    public boolean canInsertItem(int i, ItemStack itemstack, int j){return true;}

    public boolean canExtractItem(int i, ItemStack itemstack, int j){return true;}
    
	@Override
	public void onNetworkEvent(int event){	
	    	if (worldObj.isRemote)
	    	{
	            if ((this.audioSource == null))
	            {
	                this.audioSource = new IC2AudioSource(this, SoundSource[getBlockMetadata()-2]);
	            }

	            switch (event)
	            {
	                case 0:
	                    if (this.audioSource == null) break;
	                    audioSource.play();
	                    break;
	                case 1:
	                    if (audioSource == null) break;
	                    audioSource.stop();
	                    audioSource.removeSource(audioSource);
	            }
	    	}
	    	
	    	NetworkHelper.announceBlockUpdate(worldObj, xCoord, yCoord, zCoord);
	}
	
	
    
    
	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {return false;}

}

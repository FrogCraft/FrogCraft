package FrogCraft.Items.MobilePS;

import java.util.ArrayList;
import java.util.List;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileSourceEvent;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.network.NetworkHelper;
import FrogCraft.mod_FrogCraft;
import FrogCraft.Common.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;


public class TileEntityMobilePS extends TileEntity implements ic2.api.energy.tile.IEnergySource,ISidedInventory, INetworkDataProvider,INetworkTileEntityEventListener{
	public int maxEnergy=Integer.MAX_VALUE;
	public int energyMK=0,energyMM=0,energyMB=0;
	public int vOut=32;
	public int energy=0;
	public int energyK=0,energyM=0,energyB=0;
	public int progress=0;
	public ItemStack[] inv;
	private EnergyTileSourceEvent sourceEvent;
	private int fuelslot=3,chargeslot=2,tier=1,chargingRate=1;
	private int tick=0,tickMax=150,smeltingCost=390;
	public int topID=0,topDamage=0;
	public static List networkedFields;
	
	@Override
	public void onNetworkEvent(int event) {}

	@Override
	public List<String> getNetworkedFields() {return networkedFields;}
	
	public TileEntityMobilePS(){
		super();
		inv=new ItemStack[8];
    	if (networkedFields == null)
    	{
            networkedFields = new ArrayList();
            networkedFields.add("topID");
            networkedFields.add("topDamage");
    	}
	}
	
    @Override
    public void updateEntity(){  	
        super.updateEntity();
                       
        
        if (worldObj.isRemote)
            return;
        
        maxEnergy=ItemBlock_MobilePS.maxCharge+maxEnergy();
        
        if      (maxEnergy>=100000000)
        	tier=5;
        else if (maxEnergy>=10000000)
        	tier=4;
        else if (maxEnergy>=1000000)
        	tier=3;  
        else if (maxEnergy>=100000)
        	tier=2;          
        else 
        	tier=1;
        
        energy+=solar();
        if (energy>maxEnergy)
        	energy=maxEnergy;
        	
		NetworkHelper.updateTileEntityField(this,"topID");
		NetworkHelper.updateTileEntityField(this,"topDamage");
        
        energy=IC2Charger.discharge(inv[fuelslot], chargingRate, tier, energy, maxEnergy);
        
        energy=IC2Charger.charge(inv[chargeslot], chargingRate, tier, energy, maxEnergy);       

        setvOut();
        chargingRate=vOut*2;
        int o=getOverClock();
        if (o>7)
        	o=7;
        
        tickMax=150-(o*20);
        smeltingCost=(int) (390*(o/3.0)+390);
        
        if (smelt()){
        	progress=tick*100/tickMax;
        }
        else
        {
        	tick=0;
        	progress=0;
        }
        
        energyB=energy/1000000;
        energyM=(energy-(energyB*1000000))/1000;
        energyK=energy-(energyB*1000000)-(energyM*1000); 
        energyMB=maxEnergy/1000000;
        energyMM=(maxEnergy-(energyMB*1000000))/1000;
        energyMK=maxEnergy-(energyMB*1000000)-(energyMM*1000);        
        out(vOut);
    }
    
    int getOverClock(){
    	if (inv[7]==null)
    		return 0;
    	
		if (ic2.api.item.Items.getItem("overclockerUpgrade").itemID!=inv[7].itemID |
			ic2.api.item.Items.getItem("overclockerUpgrade").getItemDamage()!=inv[7].getItemDamage())
			return 0;    	
		
		
		
    	return inv[7].stackSize;
    }
    
    boolean smelt(){
    	if (energy<smeltingCost)
    		return false;
    	
    	ItemStack result=FurnaceRecipes.smelting().getSmeltingResult(inv[0]);
    	if (result==null)
    		return false;
    	
    	if (inv[1]!=null){
    		if (result.itemID!=inv[1].itemID|result.getItemDamage()!=inv[1].getItemDamage())
    			return false;
    	
    		if (inv[1].stackSize>inv[1].getMaxStackSize()-result.stackSize)
    			return false;
    	}
    	
    	tick++;
    	if (tick>tickMax){
    		tick=0;
    		
    		if (inv[1]==null)
    			inv[1]=result.copy();
    		else
    			inv[1].stackSize+=result.stackSize;
    	
    		if (inv[0].stackSize==1)
    			inv[0]=null;
    		else
    			inv[0].stackSize-=1;
    		
    		energy-=smeltingCost;
    	}
    	
    	return true;
    }
	
    void setvOut(){
        if (inv[5]!=null){
        	if (inv[5].itemID==ic2.api.item.Items.getItem("transformerUpgrade").itemID&&
        		inv[5].getItemDamage()==ic2.api.item.Items.getItem("transformerUpgrade").getItemDamage()){
        		switch(inv[5].stackSize){
        		case 1:
        			vOut=128;
        			break;
        		case 2:
        			vOut=512;
        			break;        			
        		default:
        			vOut=2048;
        		}
        	}
        	else if (inv[5].itemID==mod_FrogCraft.GTVUpdate&&
        			inv[5].getItemDamage()==27){
        				if (inv[5].stackSize==1)
        					vOut=512;
        				else
        					vOut=2048;
        	}
           	else
        		vOut=32;
        }
        else
        	vOut=32;
    }
    
    int solar(){    	   	
    	if (inv[4]==null){
    		topID=0;
    		topDamage=0;    		
    		return 0;
    	}
    	
    
    		
    	int e[]=RecipeManager.getMobilePSSolarValue(inv[4], !worldObj.isDaytime());   	
    	
    	if(e[1]==1){
    		topID=inv[4].itemID;
    		topDamage=inv[4].getItemDamage();
        	if(worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord))
        		return e[0]*inv[4].stackSize;
        	else
        		return 0;
    	}
    	else if (e[1]==2){
    		topID=0;
    		topDamage=0;    	    		
        	if(worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord))
        		return e[0]*inv[4].stackSize;    		
        	else
        		return 0;
    	}
    	else
    	{
    		topID=0;
    		topDamage=0;    		
    		return 0;
    	}
    }
        
    int maxEnergy(){
    	if (inv[6]==null)
    		return 0;
    	
  		return inv[6].stackSize*RecipeManager.getMobilePSEnergyUpdatesValue(inv[6]); //IC2 energy storage upgrade item
    }
    
	void out(int amount){
		if (amount>energy)
			amount=energy;
		
		if (amount<0)
			return;
		
		sourceEvent=new EnergyTileSourceEvent(this,amount);
		MinecraftForge.EVENT_BUS.post(sourceEvent);		
		energy-=amount-sourceEvent.amount;			
	}
    
    //IEnergySource -------------------------------------------------------------------------
	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) {return true;}

	@Override
	public boolean isAddedToEnergyNet() {return true;}

	@Override
	public int getMaxEnergyOutput() {return 32;}
	
	//NBT -----------------------------------------------------------------------------------
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
            maxEnergy=tagCompound.getInteger("maxEnergy");  
            energy=tagCompound.getInteger("energy");  
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
            tagCompound.setInteger("maxEnergy", maxEnergy);
            tagCompound.setInteger("energy", energy);            
    }
	
    //Inventory ------------------------------------------------------------------------------
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
	public String getInvName() {return "tileentityMobilePS";}

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

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		// TODO Auto-generated method stub
		return false;
	}
}

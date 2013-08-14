package FrogCraft.Machines;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import FrogCraft.Common.BaseIC2Machine;

public class TileEntityPneumaticCompressor extends BaseIC2Machine implements ISidedInventory{
	//Variables
	private ItemStack[] inv;
	
	public int progress;
	public int tick;
	public int gas_total=0;


	//Class Declaration
	public TileEntityPneumaticCompressor(){
		super(32,10000);
		inv = new ItemStack[2];	
		progress=0;
		tick=0;	    
	}
	
	
    @Override
    public void updateEntity(){  	
        super.updateEntity();
    	
        if (worldObj.isRemote)
            return;
        
        //////////////////
        if (inv[0]!=null&energy-8>=0&fully()){

        	Object[] rec=FrogCraft.Intergration.GregTech.findImplosionRecipe(inv[0]);
        	if (rec!=null){  //able to work
        		ItemStack out=(ItemStack) rec[0];
        		gas_total=(Integer) rec[1];
        		
        		
        		gas_total=gas_total*FrogCraft.mod_FrogCraft.rate_PneumaticCompressor;
        		

        		tick+=cosume_gas(20);
        		energy-=8;
        		progress=tick*100/gas_total;
        			
        		rndboom();
        			
        		if(tick>gas_total){
        			//Make product
        			if (inv[1]==null)
        				inv[1]=out.copy();
        			else
        				inv[1].stackSize+=out.stackSize;
        				
        				
        			//Consume Input
        			inv[0].stackSize-=(Integer)rec[2];
        			if (inv[0].stackSize==0)
        				inv[0]=null;
        			
        			
        			tick=0;	
        				
        			boom();
        		}         		
        	}
        }
        else{
        	tick=0;
        	progress=0;
        	gas_total=0;
        }
    }
    
    int cosume_gas(int amount){
    	int r=0;
    	
    	TileEntity te[]={
    	worldObj.getBlockTileEntity(xCoord+2, yCoord-3, zCoord),
    	worldObj.getBlockTileEntity(xCoord-2, yCoord-3, zCoord),
    	worldObj.getBlockTileEntity(xCoord, yCoord-3, zCoord+2),
    	worldObj.getBlockTileEntity(xCoord, yCoord-3, zCoord-2)};
    	
    	for (int i=0;i<4;i++){
        	if (te[i] != null&te[i] instanceof TileEntityAirPump){
        		if (((TileEntityAirPump)te[i]).useGas(amount))
        			r+=amount;
        	}
    	}
    	
    	return r;
    }
    
	void boom(){if(FrogCraft.mod_FrogCraft.boom_PneumaticCompressor)worldObj.createExplosion(null, xCoord, yCoord+1, zCoord, 0F, false);}
    void rndboom(){if(FrogCraft.mod_FrogCraft.rndboom_PneumaticCompressor){
    	if (worldObj.rand.nextInt(20)==5)boom();
    }}
	
    public boolean fully(){
    	if(worldObj.getBlockMetadata(xCoord+1, yCoord-1, zCoord)!=14)
    		return false;
    	if(worldObj.getBlockMetadata(xCoord, yCoord-1, zCoord)!=14)
    		return false;    	
    	if(worldObj.getBlockMetadata(xCoord-1, yCoord-1, zCoord)!=14)
    		return false;
    	if(worldObj.getBlockMetadata(xCoord, yCoord-1, zCoord+1)!=14)
    		return false;    	    	
    	if(worldObj.getBlockMetadata(xCoord, yCoord-1, zCoord-1)!=14)
    		return false;
    	if(worldObj.getBlockMetadata(xCoord+1, yCoord-1, zCoord+1)!=15)    		
    		return false;   
    	if(worldObj.getBlockMetadata(xCoord+1, yCoord-1, zCoord-1)!=15)
    		return false;
    	if(worldObj.getBlockMetadata(xCoord-1, yCoord-1, zCoord+1)!=15)
    		return false;    	
    	if(worldObj.getBlockMetadata(xCoord-1, yCoord-1, zCoord-1)!=15)
    		return false;
    	
    	if(worldObj.getBlockMetadata(xCoord+1, yCoord-2, zCoord)!=14)
    		return false;
    	if(worldObj.getBlockId(xCoord, yCoord-2, zCoord)!=0)
    		return false;    	
    	if(worldObj.getBlockMetadata(xCoord-1, yCoord-2, zCoord)!=14)
    		return false;
    	if(worldObj.getBlockMetadata(xCoord, yCoord-2, zCoord+1)!=14)
    		return false;    	    	
    	if(worldObj.getBlockMetadata(xCoord, yCoord-2, zCoord-1)!=14)
    		return false;
    	if(worldObj.getBlockMetadata(xCoord+1, yCoord-2, zCoord+1)!=14)    		
    		return false;   
    	if(worldObj.getBlockMetadata(xCoord+1, yCoord-2, zCoord-1)!=14)
    		return false;
    	if(worldObj.getBlockMetadata(xCoord-1, yCoord-2, zCoord+1)!=14)
    		return false;    	
    	if(worldObj.getBlockMetadata(xCoord-1, yCoord-2, zCoord-1)!=14)
    		return false;    	
    	
    	if(worldObj.getBlockMetadata(xCoord+1, yCoord-3, zCoord)!=14)
    		return false;
    	if(worldObj.getBlockMetadata(xCoord, yCoord-3, zCoord)!=14)
    		return false;    	
    	if(worldObj.getBlockMetadata(xCoord-1, yCoord-3, zCoord)!=14)
    		return false;
    	if(worldObj.getBlockMetadata(xCoord, yCoord-3, zCoord+1)!=14)
    		return false;    	    	
    	if(worldObj.getBlockMetadata(xCoord, yCoord-3, zCoord-1)!=14)
    		return false;
    	if(worldObj.getBlockMetadata(xCoord+1, yCoord-3, zCoord+1)!=15)    		
    		return false;   
    	if(worldObj.getBlockMetadata(xCoord+1, yCoord-3, zCoord-1)!=15)
    		return false;
    	if(worldObj.getBlockMetadata(xCoord-1, yCoord-3, zCoord+1)!=15)
    		return false;    	
    	if(worldObj.getBlockMetadata(xCoord-1, yCoord-3, zCoord-1)!=15)
    		return false;
    	
    	
    	return true;
    }
    
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
	public String getInvName() {return "tileentityPneumaticCompressor";}

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

	@Override
	public void openChest() {}


	@Override
	public void closeChest() {}


	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {return false;}
	
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


//    	ItemStack a=new ItemStack(ic2.api.item.Items.getItem("industrialTnt").itemID,64,0);
//if (!world.isRemote)entityPlayer.sendChatToPlayer(String.valueOf(gregtechmod.api.util.GT_Recipe.findEqualImplosionRecipeIndex(gregtechmod.api.GregTech_API.getGregTechItem(1,4,36),a)));

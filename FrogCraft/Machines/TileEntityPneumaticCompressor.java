package FrogCraft.Machines;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

import ic2.api.tile.IWrenchable;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.network.NetworkHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;

import FrogCraft.Common.BaseIC2Machine;
import FrogCraft.Common.SidedIC2Machine;

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
        	
        	int recid = gregtechmod.api.util.GT_Recipe.findEqualImplosionRecipeIndex(inv[0],FrogCraft.mod_FrogCraft.iTNT);
        	
        	if (recid>-1){  //able to work
        		gregtechmod.api.util.GT_Recipe g=gregtechmod.api.util.GT_Recipe.sImplosionRecipes.get(recid);
        		ItemStack res=g.mOutput1;
        		gas_total=g.mOutput2.stackSize;
        		
        		
        		gas_total=gas_total*FrogCraft.mod_FrogCraft.rate_PneumaticCompressor;
        		if (inv[1]==null){
        			tick+=cosume_gas(20);
        			energy-=8;
        			progress=tick*100/gas_total;
        			
        			rndboom();
        			
        			if(tick>gas_total){
        				inv[1]=res.copy();
        				
        				if (inv[0].stackSize==g.mInput1.stackSize)
        					inv[0]=null;        				
        				else
        					inv[0].stackSize-=g.mInput1.stackSize;
        				
        				tick=0;
        				
        				boom();
        			}
        		}
        		else if(inv[1].getItem()==res.getItem()&inv[1].getItemDamage()==res.getItemDamage()&inv[1].stackSize<=res.getMaxStackSize()-res.stackSize){
        			tick+=cosume_gas(20);
        			energy-=8;
        			progress=tick*100/gas_total;
        			
        			rndboom();
        			
        			if(tick>gas_total){
        				inv[1]=new ItemStack(res.getItem(),inv[1].stackSize+res.stackSize,inv[1].getItemDamage());
        				
        				if (inv[0].stackSize==g.mInput1.stackSize)
        					inv[0]=null;
        				else
        					inv[0].stackSize-=g.mInput1.stackSize;
        				
        				tick=0;	
        				
        				boom();
        			} 
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
    	if ((new Random()).nextInt(20)==5)boom();
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


//    	ItemStack a=new ItemStack(ic2.api.item.Items.getItem("industrialTnt").itemID,64,0);
//if (!world.isRemote)entityPlayer.sendChatToPlayer(String.valueOf(gregtechmod.api.util.GT_Recipe.findEqualImplosionRecipeIndex(gregtechmod.api.GregTech_API.getGregTechItem(1,4,36),a)));

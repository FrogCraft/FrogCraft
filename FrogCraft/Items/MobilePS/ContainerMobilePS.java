package FrogCraft.Items.MobilePS;

import java.util.Iterator;

import FrogCraft.Common.ProductSlot;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMobilePS extends Container {

	protected TileEntityMobilePS tileEntity;
	
	public int vOut,progress;
	public int energyK=0,energyM=0,energyB=0;
	public int energyMK=0,energyMM=0,energyMB=0;
	
	public ContainerMobilePS (InventoryPlayer inventoryPlayer, TileEntityMobilePS te){
        tileEntity = te;

        
        addSlotToContainer(new Slot(tileEntity, 0, 16, 46)); //Furnace In
        addSlotToContainer(new ProductSlot(tileEntity, 1, 76, 46)); //Furnace Out
        addSlotToContainer(new Slot(tileEntity, 2, 113, 24));//Charge Slot
        addSlotToContainer(new Slot(tileEntity, 3, 113, 42));//Fuel Slot
        
        addSlotToContainer(new Slot(tileEntity, 4, 20, 20)); //Solar Update
        addSlotToContainer(new Slot(tileEntity, 5, 38, 20)); //Voltage Update
        addSlotToContainer(new Slot(tileEntity, 6, 56, 20));//Storage Update
        addSlotToContainer(new Slot(tileEntity, 7, 74, 20));//Overclock
       
        bindPlayerInventory(inventoryPlayer);
	}
	

	
    @Override
    public void addCraftingToCrafters(ICrafting par1iCrafting) {
        super.addCraftingToCrafters(par1iCrafting);
      	par1iCrafting.sendProgressBarUpdate(this, 0, tileEntity.energyK);
       	par1iCrafting.sendProgressBarUpdate(this, 1, tileEntity.energyM);        
       	par1iCrafting.sendProgressBarUpdate(this, 2, tileEntity.energyB);  
      	par1iCrafting.sendProgressBarUpdate(this, 3, tileEntity.vOut);
      	par1iCrafting.sendProgressBarUpdate(this, 4, tileEntity.energyMK);
       	par1iCrafting.sendProgressBarUpdate(this, 5, tileEntity.energyMM);        
       	par1iCrafting.sendProgressBarUpdate(this, 6, tileEntity.energyMB); 
       	par1iCrafting.sendProgressBarUpdate(this, 7, tileEntity.progress);       	
    }
    
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
    	if (par1 == 0)	tileEntity.energyK = par2;
    	if (par1 == 1)	tileEntity.energyM = par2;
    	if (par1 == 2)	tileEntity.energyB = par2;
    	if (par1 == 3)	tileEntity.vOut = par2;    
    	if (par1 == 4)	tileEntity.energyMK = par2;
    	if (par1 == 5)	tileEntity.energyMM = par2;
    	if (par1 == 6)	tileEntity.energyMB = par2;
    	if (par1 == 7)	tileEntity.progress = par2;   	
   	}
    
    @Override
    public void detectAndSendChanges()
    {
    	// TODO Auto-generated method stub
    	super.detectAndSendChanges();
    	Iterator var1 = this.crafters.iterator();
    	while (var1.hasNext())
    	{
    		ICrafting var2 = (ICrafting)var1.next();
    		var2.sendProgressBarUpdate(this, 0, energyK);
    		var2.sendProgressBarUpdate(this, 1, energyM);
    		var2.sendProgressBarUpdate(this, 2, energyB);  
            var2.sendProgressBarUpdate(this, 3, vOut);  	
    		var2.sendProgressBarUpdate(this, 4, energyMK);
    		var2.sendProgressBarUpdate(this, 5, energyMM);
    		var2.sendProgressBarUpdate(this, 6, energyMB); 
    		var2.sendProgressBarUpdate(this, 7, progress);     		
    	}
    	
    	energyK=tileEntity.energyK;
    	energyM=tileEntity.energyM;
    	energyB=tileEntity.energyB;
    	vOut=tileEntity.vOut;
    	energyMK=tileEntity.energyMK;
    	energyMM=tileEntity.energyMM;
    	energyMB=tileEntity.energyMB;
    	progress=tileEntity.progress;
    }
	
	//Common Stuff
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                        addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 18, 84 + i * 18));
                }
        }

        for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
}
	
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
            ItemStack stack = null;
            Slot slotObject = (Slot) inventorySlots.get(slot);         
            
            
            //null checks and checks if the item can be stacked (maxStackSize > 1)
            if (slotObject != null && slotObject.getHasStack() ) {
                    ItemStack stackInSlot = slotObject.getStack();
                    stack = stackInSlot.copy();

                    //merges the item into player inventory since its in the tileEntity
                    if (slot < 8) {
                            if (!this.mergeItemStack(stackInSlot, 8, 44, true)) {
                                    return null;
                            }
                    }
                    //places it into the tileEntity is possible since its in the player inventory
                    else {
                    	if(stackInSlot.getItem() instanceof ic2.api.item.IElectricItem){
                    		if (!this.mergeItemStack(stackInSlot, 2,3, false)) 
                    			return null;                  
                    	}
                    	else if (!this.mergeItemStack(stackInSlot, 0,1, false)) 
                           return null;
                    }

                    if (stackInSlot.stackSize == 0) {
                            slotObject.putStack(null);
                    } else {
                            slotObject.onSlotChanged();
                    }

                    if (stackInSlot.stackSize == stack.stackSize) {
                            return null;
                    }
                    slotObject.onPickupFromSlot(player, stackInSlot);
            }
            return stack;
    }
}

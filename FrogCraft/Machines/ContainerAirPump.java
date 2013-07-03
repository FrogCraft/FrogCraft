package FrogCraft.Machines;

import java.util.Iterator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAirPump extends Container {

	protected TileEntityAirPump tileEntity;
	
	public int GasPercentage=0;
	public int energy=0;
	
	public ContainerAirPump (InventoryPlayer inventoryPlayer, TileEntityAirPump te){
        tileEntity = te;

        bindPlayerInventory(inventoryPlayer);
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
            if (slotObject != null && slotObject.getHasStack()) {
                    ItemStack stackInSlot = slotObject.getStack();
                    stack = stackInSlot.copy();

                    //merges the item into player inventory since its in the tileEntity
                    if (slot < 0) {
                            if (!this.mergeItemStack(stackInSlot, 0, 36, true)) {
                                    return null;
                            }
                    }
                    //places it into the tileEntity is possible since its in the player inventory
                    else if (!this.mergeItemStack(stackInSlot, 0,0, false)) {
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
    
    
    
    
    
    @Override
    public void addCraftingToCrafters(ICrafting par1iCrafting) {
            // TODO Auto-generated method stub
           super.addCraftingToCrafters(par1iCrafting);
            par1iCrafting.sendProgressBarUpdate(this, 0, tileEntity.energy);
            par1iCrafting.sendProgressBarUpdate(this, 1, tileEntity.GasPercentage); 
    }
    
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
    	if (par1 == 0)	tileEntity.energy = par2;
    	if (par1 == 1)	tileEntity.GasPercentage = par2;
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
            //if (energy!=tileEntity.energy)
            	var2.sendProgressBarUpdate(this, 0, energy);
            //if (progress!=tileEntity.progress)
            	var2.sendProgressBarUpdate(this, 1, GasPercentage);
    	}
    	
    	energy=tileEntity.energy;
    	GasPercentage=tileEntity.GasPercentage;
    }





}

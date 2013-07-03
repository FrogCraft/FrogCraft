package FrogCraft.Machines;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import FrogCraft.Common.ProductSlot;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.liquids.LiquidStack;

public class ContainerAdvanceChemicalReactor extends Container {

	protected TileEntityAdvanceChemicalReactor tileEntity;
	
	public int elevel=0,progress=0;
	
	public ContainerAdvanceChemicalReactor (InventoryPlayer inventoryPlayer, TileEntityAdvanceChemicalReactor te){
        tileEntity = te;

        addSlotToContainer(new Slot(tileEntity, 0, 40, 22));
        addSlotToContainer(new Slot(tileEntity, 1, 60, 22));
        addSlotToContainer(new Slot(tileEntity, 2, 80, 22));
        addSlotToContainer(new Slot(tileEntity, 3, 100, 22));
        addSlotToContainer(new Slot(tileEntity, 4, 120, 22));
        
        addSlotToContainer(new ProductSlot(tileEntity, 5, 40, 52));
        addSlotToContainer(new ProductSlot(tileEntity, 6, 60, 52));
        addSlotToContainer(new ProductSlot(tileEntity, 7, 80, 52));
        addSlotToContainer(new ProductSlot(tileEntity, 8, 100, 52));
        addSlotToContainer(new ProductSlot(tileEntity, 9, 120, 52));
        
        addSlotToContainer(new Slot(tileEntity, 10, 12, 22));	//Cell In
        addSlotToContainer(new ProductSlot(tileEntity, 11, 12, 52));	//Cell Out
        addSlotToContainer(new Slot(tileEntity, 12, 147, 52)); 	//Catalyst       
        
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
                    if (slot < 13) {
                            if (!this.mergeItemStack(stackInSlot, 13, 49, true)) {
                                    return null;
                            }
                    }
                    //places it into the tileEntity is possible since its in the player inventory
                    else if (!this.mergeItemStack(stackInSlot, 0,5, false)) {
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
    	super.addCraftingToCrafters(par1iCrafting);
    	par1iCrafting.sendProgressBarUpdate(this, 0, tileEntity.elevel);
    	par1iCrafting.sendProgressBarUpdate(this, 1, progress);
    }
    
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
    	switch(par1){
    		case 0:{tileEntity.elevel = par2;return;}
    		case 1:{tileEntity.progress = par2;return;}    		  		
    	}
    	
   	}
    
    @Override
    public void detectAndSendChanges()
    {
    	super.detectAndSendChanges();
    	Iterator var1 = this.crafters.iterator();
    	while (var1.hasNext())
    	{
    		ICrafting var2 = (ICrafting)var1.next();
    		var2.sendProgressBarUpdate(this, 0, elevel);
    		var2.sendProgressBarUpdate(this, 1, progress);        		
    	}
    	
    	elevel=tileEntity.elevel;
    	progress=tileEntity.progress; 	
    }





}
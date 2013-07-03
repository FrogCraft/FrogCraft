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

public class ContainerLiquidInjector extends Container {

	protected TileEntityLiquidInjector tileEntity;
	
	public int idIn,damageIn,amountInP;
	public int energy=0;
	
	public ContainerLiquidInjector (InventoryPlayer inventoryPlayer, TileEntityLiquidInjector te){
        tileEntity = te;

        addSlotToContainer(new Slot(tileEntity, 0, 113, 21));
        addSlotToContainer(new ProductSlot(tileEntity, 1, 113, 56));
        
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
                    if (slot < 2) {
                            if (!this.mergeItemStack(stackInSlot, 2, 38, true)) {
                                    return null;
                            }
                    }
                    //places it into the tileEntity is possible since its in the player inventory
                    else if (!this.mergeItemStack(stackInSlot, 0,1, false)) {
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
    	par1iCrafting.sendProgressBarUpdate(this, 0, tileEntity.energy);
    	par1iCrafting.sendProgressBarUpdate(this, 1, tileEntity.idIn);
    	par1iCrafting.sendProgressBarUpdate(this, 3, tileEntity.damageIn);    	
    	par1iCrafting.sendProgressBarUpdate(this, 5, tileEntity.amountInP);    	
    }
    
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
    	switch(par1){
    		case 0:{tileEntity.energy = par2;return;}
    		case 1:{tileEntity.idIn = par2;return;}    		
    		case 3:{tileEntity.damageIn = par2;return;}     
    		case 5:{tileEntity.amountInP = par2;return;}       		
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
    		var2.sendProgressBarUpdate(this, 0, energy);
    		var2.sendProgressBarUpdate(this, 1, idIn);     
    		var2.sendProgressBarUpdate(this, 3, damageIn);        
    		var2.sendProgressBarUpdate(this, 5, amountInP);     		
    	}
    	
    	energy=tileEntity.energy;
    	idIn=tileEntity.idIn;
    	damageIn=tileEntity.damageIn;
    	amountInP=tileEntity.amountInP;
    }





}
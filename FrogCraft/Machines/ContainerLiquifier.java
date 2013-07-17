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

public class ContainerLiquifier extends Container {

	protected TileEntityLiquifier tileEntity;
	
	public int idOut,damageOut,amountOutP;
	public int energy=0,tick=0;
	
	public ContainerLiquifier (InventoryPlayer inventoryPlayer, TileEntityLiquifier te){
        tileEntity = te;


        addSlotToContainer(new Slot(tileEntity, 0, 47, 21));           //LU
        addSlotToContainer(new Slot(tileEntity, 1, 113, 21));		   //RU
        addSlotToContainer(new ProductSlot(tileEntity, 2, 47, 56));    //LD    
        addSlotToContainer(new ProductSlot(tileEntity, 3, 113, 56));   //RD   
        
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
                    if (slot < 4) {
                            if (!this.mergeItemStack(stackInSlot, 4, 40, true)) {
                                    return null;
                            }
                    }
                    //places it into the tileEntity is possible since its in the player inventory
                    else if (!this.mergeItemStack(stackInSlot, 0, 2, false)) {
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
    	par1iCrafting.sendProgressBarUpdate(this, 1, tileEntity.tick);
    	par1iCrafting.sendProgressBarUpdate(this, 2, tileEntity.idOut);  	
    	par1iCrafting.sendProgressBarUpdate(this, 4, tileEntity.damageOut);
    	par1iCrafting.sendProgressBarUpdate(this, 6, tileEntity.amountOutP);       	
    }
    
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
    	switch(par1){
    		case 0:{tileEntity.energy = par2;return;}	
    		case 1:{tileEntity.tick = par2;return;}
    		case 2:{tileEntity.idOut = par2;return;}    		
    		case 4:{tileEntity.damageOut = par2;return;}       
    		case 6:{tileEntity.amountOutP = par2;return;}     		
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
    		var2.sendProgressBarUpdate(this, 1, tick);
    		var2.sendProgressBarUpdate(this, 2, idOut);
    		var2.sendProgressBarUpdate(this, 4, damageOut);    		
     		var2.sendProgressBarUpdate(this, 6, amountOutP);    		
    	}
    	
    	energy=tileEntity.energy;
    	tick=tileEntity.tick;
    	idOut=tileEntity.idOut;	
    	damageOut=tileEntity.damageOut;		
    	amountOutP=tileEntity.amountOutP;
    }





}

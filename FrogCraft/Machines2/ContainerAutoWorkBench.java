package FrogCraft.Machines2;

import java.util.Iterator;

import FrogCraft.Common.*;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerAutoWorkBench extends Container {

	private class CraftSlot extends ProductSlot{

		public CraftSlot(IInventory par1iInventory, int par2, int par3, int par4) {
			super(par1iInventory, par2, par3, par4);
			// TODO Auto-generated constructor stub
		}
		
		public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack){
			super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
			for (int i=27;i<36;i++)
				this.inventory.decrStackSize(i, 1);
		}
	}
	
	protected TileEntityAutoWorkBench tileEntity;
	
	public ContainerAutoWorkBench (InventoryPlayer inventoryPlayer, TileEntityAutoWorkBench te){
        tileEntity = te;

        //Raw Material Slots(0-26)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                    addSlotToContainer(new Slot(tileEntity, j + i * 9,8 + j * 18, 17 + i * 18));
            }
        }
        
        //Crafting Matrix(27-35)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                    addSlotToContainer(new Slot(tileEntity, j + i * 3+27,j * 18-58, 17 + i * 18));
            }
        }        
        
        //Product Slot
        addSlotToContainer(new ProductSlot(tileEntity, 36, -68, 142));
        addSlotToContainer(new ProductSlot(tileEntity, 37, -50, 142));
        addSlotToContainer(new ProductSlot(tileEntity, 38, -32, 142));
        addSlotToContainer(new ProductSlot(tileEntity, 39, -14, 142));
        //Product Demo Slot
        addSlotToContainer(new CraftSlot(tileEntity, 40, -40, 102));
        
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
                    if (slot < 41) {
                            if (!this.mergeItemStack(stackInSlot, 41, 41+36, true)) {
                                    return null;
                            }
                    }
                    //places it into the tileEntity is possible since its in the player inventory
                    else if (!this.mergeItemStack(stackInSlot, 0,27, false)) {
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
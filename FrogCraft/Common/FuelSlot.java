package FrogCraft.Common;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class FuelSlot extends Slot{
	public FuelSlot(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		// TODO Auto-generated constructor stub
	}
	
	 public boolean isItemValid(ItemStack items){return TileEntityFurnace.isItemFuel(items);}
}

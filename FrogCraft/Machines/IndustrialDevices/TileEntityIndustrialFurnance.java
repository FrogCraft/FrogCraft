package FrogCraft.Machines.IndustrialDevices;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileEntityIndustrialFurnance extends TileEntityIndustrialDevice {

	@Override
	public ItemStack getResult(ItemStack inv[],int i) {
		if (inv[i]==null)
			return null;
		ItemStack r= FurnaceRecipes.smelting().getSmeltingResult(inv[i]);
		if(r!=null)
			inv[i].stackSize-=1;
		return r;
	}

	@Override
	public ItemStack getResult(ItemStack i) {
		if (i==null)
			return null;
		return FurnaceRecipes.smelting().getSmeltingResult(i.copy());
	}
}

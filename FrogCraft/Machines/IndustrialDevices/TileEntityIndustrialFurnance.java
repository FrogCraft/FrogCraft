package FrogCraft.Machines.IndustrialDevices;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileEntityIndustrialFurnance extends TileEntityIndustrialDevice {

	@Override
	public ItemStack[] getResult(ItemStack i) {
		if (i==null)
			return new ItemStack[]{i,null};
		ItemStack r= FurnaceRecipes.smelting().getSmeltingResult(i.copy());
		i.stackSize-=1;
		return new ItemStack[]{i,r};
	}

	@Override
	public ItemStack getResultF(ItemStack i) {
		if (i==null)
			return null;
		return FurnaceRecipes.smelting().getSmeltingResult(i.copy());
	}
}

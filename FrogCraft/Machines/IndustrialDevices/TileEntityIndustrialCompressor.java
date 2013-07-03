package FrogCraft.Machines.IndustrialDevices;

import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;

public class TileEntityIndustrialCompressor extends TileEntityIndustrialDevice {


	@Override
	public ItemStack[] getResult(ItemStack i) {
		if (i==null)
			return new ItemStack[]{i,null};
		ItemStack r=Recipes.compressor.getOutputFor(i, true);
		return new ItemStack[]{i,r};
	}
	
	@Override
	public ItemStack getResultF(ItemStack i) {
		if (i==null)
			return null;
		return Recipes.compressor.getOutputFor(i.copy(), false);
	}


}

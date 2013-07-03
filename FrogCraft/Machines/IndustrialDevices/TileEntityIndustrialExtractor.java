package FrogCraft.Machines.IndustrialDevices;

import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;

public class TileEntityIndustrialExtractor extends TileEntityIndustrialDevice {

	@Override
	public ItemStack[] getResult(ItemStack i) {
		if (i==null)
			return new ItemStack[]{i,null};
		ItemStack r=Recipes.extractor.getOutputFor(i, true);
		return new ItemStack[]{i,r};
	}

	@Override
	public ItemStack getResultF(ItemStack i) {
		if (i==null)
			return null;
		return Recipes.extractor.getOutputFor(i.copy(), false);
	}
}

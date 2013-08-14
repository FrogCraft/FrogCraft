package FrogCraft.Machines.IndustrialDevices;

import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;

public class TileEntityIndustrialExtractor extends TileEntityIndustrialDevice {

	
	@Override
	public ItemStack getResult(ItemStack[] inv,int i) {
		if (inv[i]==null)
			return null;
		ItemStack r=Recipes.extractor.getOutputFor(inv[i], true);
		return r;
	}
	
	@Override
	public ItemStack getResult(ItemStack i) {
		if (i==null)
			return null;
		return Recipes.extractor.getOutputFor(i.copy(), false);
	}
}

package FrogCraft.Machines.IndustrialDevices;



import net.minecraft.item.ItemStack;
import ic2.api.recipe.Recipes;

public class TileEntityIndustrialMacerator extends TileEntityIndustrialDevice {

	@Override
	public ItemStack[] getResult(ItemStack i) {
		if (i==null)
			return new ItemStack[]{i,null};
		ItemStack r= Recipes.macerator.getOutputFor(i, true);
		return new ItemStack[]{i,r};
	}

	@Override
	public ItemStack getResultF(ItemStack i) {
		if (i==null)
			return null;
		return Recipes.macerator.getOutputFor(i.copy(), false);
	}
}

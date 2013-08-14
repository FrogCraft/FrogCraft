package FrogCraft.Machines.IndustrialDevices;



import net.minecraft.item.ItemStack;
import ic2.api.recipe.Recipes;

public class TileEntityIndustrialMacerator extends TileEntityIndustrialDevice {
	@Override
	public ItemStack getResult(ItemStack[] inv,int i) {
		if (inv[i]==null)
			return null;
		ItemStack r=Recipes.macerator.getOutputFor(inv[i], true);
		return r;
	}
	
	@Override
	public ItemStack getResult(ItemStack i) {
		if (i==null)
			return null;
		return Recipes.macerator.getOutputFor(i.copy(), false);
	}
}

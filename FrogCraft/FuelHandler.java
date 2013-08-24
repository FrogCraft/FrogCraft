package FrogCraft;

import FrogCraft.api.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel==null)
			return 0;		

		for (ItemStack item:OreDictionary.getOres("dustCoal"))
			if (item.isItemEqual(fuel))
				return 1800;		
		
		for (ItemStack item:OreDictionary.getOres("dustCharcoal"))
			if (item.isItemEqual(fuel))
				return 1800;		
		
		for (ItemStack item:OreDictionary.getOres("dustSulfur"))
			if (item.isItemEqual(fuel))
				return 1600;
			
		
		if(fuel.itemID==fcItems.miscsID){
			switch (fuel.getItemDamage()){
			case 0:
				return 1800;  //Piece of coking coal
			case 1:
				return 16000; //Briquette (45600eu needed to make a briquette)
			}
		}
			
		
		return 0;
	}

}

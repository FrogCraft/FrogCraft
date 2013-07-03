package FrogCraft;

import FrogCraft.api.*;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel==null)
			return 0;		
		
		if(fuel.isItemEqual(ic2.api.item.Items.getItem("coalDust")))   //IC2 CoalDust
			return 1800;
		
		if(fuel.itemID==fcItems.miscsID){
			switch (fuel.getItemDamage()){
			case 0:
				return 2500;  //Piece of coking coal
			case 1:
				return 25000; //Briquette (45600eu needed to make a briquette)
			}
		}
			
		
		return 0;
	}

}

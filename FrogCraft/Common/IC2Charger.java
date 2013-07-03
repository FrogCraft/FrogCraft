package FrogCraft.Common;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class IC2Charger {
	public static int discharge(ItemStack item,int chargingRate,int tier,int energy,int maxEnergy){
        if (item!=null){
        	int fuelID=item.itemID;
        	if (Item.itemsList[fuelID] instanceof IElectricItem){

        		int chargeRate;
        		
        		if ((chargingRate>((IElectricItem)Item.itemsList[fuelID]).getTransferLimit(item)))
        			chargeRate=chargingRate;
        		else
        			chargeRate=((IElectricItem)Item.itemsList[fuelID]).getTransferLimit(item);
        		
        		if(maxEnergy-energy<chargeRate)
        			chargeRate=maxEnergy-energy;
        		

        		energy +=ElectricItem.discharge(item, chargeRate, tier, true, false);
        	}
        }
        return energy;
	}
	
	public static int charge(ItemStack item,int chargingRate,int tier,int energy,int maxEnergy){
        if (item!=null&energy>0){
        	int chargeID=item.itemID;
        	if (Item.itemsList[chargeID] instanceof IElectricItem){
        		int charge=ElectricItem.charge(item,chargingRate, tier, true, true);
        		
        		int chargeRate;
        		
        		if ((chargingRate>((IElectricItem)Item.itemsList[chargeID]).getTransferLimit(item)))
        			chargeRate=chargingRate;
        		else
        			chargeRate=((IElectricItem)Item.itemsList[chargeID]).getTransferLimit(item);
        		
        		if (chargeRate>energy)
        			chargeRate=energy;
        		
        		if (charge<energy)
        			energy -=ElectricItem.charge(item,chargeRate, tier, true, false);	
        		else
        			energy -=ElectricItem.charge(item,energy, tier, true, false);    
        		
        	}	
        }
        
        return energy;
	}
}

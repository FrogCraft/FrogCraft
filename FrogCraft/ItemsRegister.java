package FrogCraft;

import net.minecraft.item.ItemStack;
import FrogCraft.Items.Item_Cells;
import FrogCraft.Items.Item_Dusts;
import FrogCraft.Items.Item_Gases;
import FrogCraft.Items.Item_Ingots;
import FrogCraft.Items.Item_Liquids;
import FrogCraft.Items.Item_Miscs;
import FrogCraft.api.fcItems;

public class ItemsRegister {
	public static void loadItemsData(){
		//Cells
		Item_Cells.add(0,"cell_Ammonia");
		Item_Cells.add(1,"cell_CoalTar");
/*2*/	Item_Cells.add(2,"cell_Oxygen");
		Item_Cells.add(3,"cell_LiquifiedAir");
		Item_Cells.add(4,"cell_CO2");
/*5*/	Item_Cells.add(5,"cell_Argon");		
  		Item_Cells.add(6,"cell_HNO3");
		Item_Cells.add(7,"cell_NO");
/*8*/	Item_Cells.add(8,"cell_CO");
		Item_Cells.add(9,"cell_Benzene");
		Item_Cells.add(10,"cell_Bromine");
/*11*/	Item_Cells.add(11,"cell_Fluorine");
		
		//Miscs
		Item_Miscs.add(0,"pCokingCoal");
		Item_Miscs.add(1,"Briquette");
/*2*/	Item_Miscs.add(2,"Railgun_Core");
		Item_Miscs.add(3,"GoldClod");
		Item_Miscs.add(4,"ElectrolizeModule");
/*5*/	Item_Miscs.add(5,"HeatingModule");
		Item_Miscs.add(6,"AmmoniaModule");
		
		//Dusts
		Item_Dusts.add(0,"CaO");
		Item_Dusts.add(1,"Urea");
/*2*/	Item_Dusts.add(2,"NH4NO3");
		Item_Dusts.add(3,"CaSiO3");
		Item_Dusts.add(4,"CaOH2");
/*5*/	Item_Dusts.add(5,"Magnalium");	
		Item_Dusts.add(6,"Carnallite");	
		Item_Dusts.add(7,"KCl");	
/*8*/	Item_Dusts.add(8,"MgBr2");
		Item_Dusts.add(9,"Fluorapatite");
		Item_Dusts.add(10,"CaF2");
/*11*/
		
		
		//Ingots
		Item_Ingots.add(0,"K");
		Item_Ingots.add(1,"P");
		
		
		
		//Liquids
		Item_Liquids.add(0,"LiquifiedAir");
		Item_Liquids.add(1,"CoalTar");
/*2*/	Item_Liquids.add(2,"HNO3");
		Item_Liquids.add(3,"Benzene");
		Item_Liquids.add(4,"Bromine");
/*5*/	
		
		//Gases
		Item_Gases.add(0,"Oxygen");
		Item_Gases.add(1,"CO2");
/*2*/	Item_Gases.add(2,"Argon");
		Item_Gases.add(3,"Ammonia");
		Item_Gases.add(4,"NO");
/*5*/	Item_Gases.add(5,"CO");	
		Item_Gases.add(6,"Fluorine");
	}
	
	public static void loadContainerSettings(){
		
		Item_Liquids.filledContainer.put(0, new ItemStack(fcItems.cellsID,1,3));
		Item_Liquids.filledContainer.put(1, new ItemStack(fcItems.cellsID,1,1));
		Item_Liquids.filledContainer.put(2, new ItemStack(fcItems.cellsID,1,6));
		Item_Liquids.filledContainer.put(3, new ItemStack(fcItems.cellsID,1,9));
		Item_Liquids.filledContainer.put(4, new ItemStack(fcItems.cellsID,1,10));
		
		Item_Gases.filledContainer.put(0, new ItemStack(fcItems.cellsID,1,2));
		Item_Gases.filledContainer.put(1, new ItemStack(fcItems.cellsID,1,4));	
		Item_Gases.filledContainer.put(2, new ItemStack(fcItems.cellsID,1,5));
		Item_Gases.filledContainer.put(3, new ItemStack(fcItems.cellsID,1,0));			
		Item_Gases.filledContainer.put(4, new ItemStack(fcItems.cellsID,1,7));	
		Item_Gases.filledContainer.put(5, new ItemStack(fcItems.cellsID,1,8));
		Item_Gases.filledContainer.put(6, new ItemStack(fcItems.cellsID,1,11));
	}
}

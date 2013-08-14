package FrogCraft;

import net.minecraft.item.ItemStack;

import FrogCraft.Common.FluidManager;
import FrogCraft.Items.*;
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
}
	

	public static void registerFluids(){
		FluidManager.RegisterFluid("LiquifiedAir", new ItemStack(fcItems.cellsID,1,3));
		FluidManager.RegisterFluid("CoalTar", new ItemStack(fcItems.cellsID,1,1));
		FluidManager.RegisterFluid("HNO3", new ItemStack(fcItems.cellsID,1,6));
		FluidManager.RegisterFluid("Benzene", new ItemStack(fcItems.cellsID,1,9));
		FluidManager.RegisterFluid("Bromine", new ItemStack(fcItems.cellsID,1,10));
		
		FluidManager.RegisterFluid("Oxygen", new ItemStack(fcItems.cellsID,1,2));
		FluidManager.RegisterFluid("CO2", new ItemStack(fcItems.cellsID,1,4));
		FluidManager.RegisterFluid("Argon", new ItemStack(fcItems.cellsID,1,5));
		FluidManager.RegisterFluid("Ammonia", new ItemStack(fcItems.cellsID,1,0));
		FluidManager.RegisterFluid("NO", new ItemStack(fcItems.cellsID,1,7));
		FluidManager.RegisterFluid("CO", new ItemStack(fcItems.cellsID,1,8));
		FluidManager.RegisterFluid("Fluorine", new ItemStack(fcItems.cellsID,1,11));
	}
}

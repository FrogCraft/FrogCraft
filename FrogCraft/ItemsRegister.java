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
		Item_Cells.add("cell_Ammonia", "Ammonia Cell","NH3");
		Item_Cells.add("cell_CoalTar","Coal Tar Cell");
 /*2*/	Item_Cells.add("cell_Oxygen","Oxygen Cell","O2");
		Item_Cells.add("cell_LiquifiedAir","Liquefied Air Cell");
		Item_Cells.add("cell_CO2","CO2 Cell","CO2");
 /*5*/	Item_Cells.add("cell_Argon","Argon Cell","Ar");		
  		Item_Cells.add("cell_HNO3","Nitric Acid Cell","HNO3");
		Item_Cells.add("cell_NO", "Nitrogen Oxide", "NO");
		Item_Cells.add("cell_CO", "Carbon MonoOxide","CO");
		Item_Cells.add("cell_Benzyl", "Benzyl Cell","C6H6");
		
		//Miscs
		Item_Miscs.add("pCokingCoal", "Piece of Coking Coal");
		Item_Miscs.add("Briquette", "Briquette");
		Item_Miscs.add("Railgun_Core", "Railgun Core");
		Item_Miscs.add("GoldClod", "GoldClod");
		
		//Dusts
		Item_Dusts.add("CaO","Calcium Oxide","CaO");
		Item_Dusts.add("Urea","Urea","CO(NH2)2");
		Item_Dusts.add("NH4NO3","Ammonium Nitrate","NH4NO3");
		Item_Dusts.add("CaSiO3", "Calcium Silicate","CaSiO3");
		Item_Dusts.add("CaOH2", "Calcium Hydroxide","Ca(OH)2");
		Item_Dusts.add("Magnalium", "Magnalium dust","MgAl2");		
		
		//Ingots
		Item_Ingots.add("K", "Potassium","K");
		Item_Ingots.add("P", "Phosphor","P");
		
		//Liquids
		Item_Liquids.add("LiquifiedAir", "Liquified Air", "Warning: Super Cold!");
		Item_Liquids.add("CoalTar", "Coal Tar" ,"Very complex compound QAQ");
		Item_Liquids.add("HNO3", "Nitric Acid" ,"Warning: Crossive!");
		Item_Liquids.add("Benzyl", "Benzyl" , "Warning: Toxic!");
		
		//Gases
		Item_Gases.add("Oxygen", "Oxygen","O2");
		Item_Gases.add("CO2", "Carbon Dioxide","CO2");
		Item_Gases.add("Argon", "Argon","Ar");
		Item_Gases.add("Ammonia", "Ammonia","NH3");
		Item_Gases.add("NO", "Nitrogen Monoxide","NO");
/*5*/	Item_Gases.add("CO", "Carbon Monoxide","CO");		
	}
	
	public static void loadContainerSettings(){
		Item_Liquids.filledContainer=new ItemStack[]{
				new ItemStack(fcItems.cellsID,1,3),
				new ItemStack(fcItems.cellsID,1,1),
				new ItemStack(fcItems.cellsID,1,6),
				new ItemStack(fcItems.cellsID,1,9)};
		
		Item_Gases.filledContainer=new ItemStack[]{
				new ItemStack(fcItems.cellsID,1,2),
				new ItemStack(fcItems.cellsID,1,4),
				new ItemStack(fcItems.cellsID,1,5),
				new ItemStack(fcItems.cellsID,1,0),
				new ItemStack(fcItems.cellsID,1,7),
				new ItemStack(fcItems.cellsID,1,8)};		
	}
}

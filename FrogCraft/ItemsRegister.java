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
/*8*/	Item_Cells.add("cell_CO", "Carbon MonoOxide","CO");
		Item_Cells.add("cell_Benzyl", "Benzyl Cell","C6H6");
		Item_Cells.add("cell_Bromine", "Bromine Cell","Br2");
/*11*/	Item_Cells.add("cell_Fluorine", "Fluorine Cell","F2");
		
		//Miscs
		Item_Miscs.add("pCokingCoal", "Piece of Coking Coal");
		Item_Miscs.add("Briquette", "Briquette" ,"Chinese style!");
/*2*/	Item_Miscs.add("Railgun_Core", "Railgun Core");
		Item_Miscs.add("GoldClod", "GoldClod" ,"妈妈的0.0");
		Item_Miscs.add("ElectrolizeModule", "Electrolize Module");
/*5*/	Item_Miscs.add("HeatingModule", "Heating Module");
		Item_Miscs.add("AmmoniaModule", "Ammonia Catalytic Module");
		
		//Dusts
		Item_Dusts.add("CaO","Calcium Oxide","CaO");
		Item_Dusts.add("Urea","Urea","CO(NH2)2");
/*2*/	Item_Dusts.add("NH4NO3","Ammonium Nitrate","NH4NO3");
		Item_Dusts.add("CaSiO3", "Calcium Silicate","CaSiO3");
		Item_Dusts.add("CaOH2", "Calcium Hydroxide","Ca(OH)2");
/*5*/	Item_Dusts.add("Magnalium", "Magnalium dust","MgAl2");	
		Item_Dusts.add("Carnallite", "Carnallite dust","4KCl.MgBr2.8H2O");	
		Item_Dusts.add("KCl", "Potassium chloride","KCl");	
/*8*/	Item_Dusts.add("MgBr2", "Magnesium bromide","MgBr2");
		Item_Dusts.add("Fluorapatite", "Fluorapatite dust","Ca5F(PO4)3");
		Item_Dusts.add("CaF2", "Calcium Fluoride","CaF2");
/*11*/
		
		
		//Ingots
		Item_Ingots.add("K", "Potassium","K");
		Item_Ingots.add("P", "Phosphor","P");
		
		//Liquids
		Item_Liquids.add("LiquifiedAir", "Liquified Air", "Warning: Super Cold!");
		Item_Liquids.add("CoalTar", "Coal Tar" ,"Very complex compound QAQ");
/*2*/	Item_Liquids.add("HNO3", "Nitric Acid" ,"Warning: Crossive!");
		Item_Liquids.add("Benzyl", "Benzyl" , "Warning: Toxic!");
		Item_Liquids.add("Bromine", "Bromine","Smells terrible!");
/*5*/	
		
		//Gases
		Item_Gases.add("Oxygen", "Oxygen","O2");
		Item_Gases.add("CO2", "Carbon Dioxide","CO2");
/*2*/	Item_Gases.add("Argon", "Argon","Ar");
		Item_Gases.add("Ammonia", "Ammonia","Never breath it!");
		Item_Gases.add("NO", "Nitrogen Monoxide","NO");
/*5*/	Item_Gases.add("CO", "Carbon Monoxide","CO");	
		Item_Gases.add("Fluorine", "Fluorine", "Warning: React with everything!");
		//Item_Gases.add("HF", "Hydrogen Fluoride", "Warning: Toxic!");
/*8*/	//Item_Gases.add("HCl", "Hydrogen Chloride", "Warning: Corrosive!");
		//Item_Gases.add("HBr", "Hydrogen Bromied", "Warning: Corrosive!");
		//Item_Gases.add("HI", "Hydrogen Idoine", "Warning: Super Acidic!");	
/*11*/	//Item_Gases.add("HCN", "Hydrogen Cyanide", "Warning: Extremely deadly!");
	}
	
	public static void loadContainerSettings(){
		Item_Liquids.filledContainer=new ItemStack[]{
				new ItemStack(fcItems.cellsID,1,3),
				new ItemStack(fcItems.cellsID,1,1),
				new ItemStack(fcItems.cellsID,1,6),
				new ItemStack(fcItems.cellsID,1,9),
				new ItemStack(fcItems.cellsID,1,10)};
		
		Item_Gases.filledContainer=new ItemStack[]{
				new ItemStack(fcItems.cellsID,1,2),
				new ItemStack(fcItems.cellsID,1,4),
		/*2*/	new ItemStack(fcItems.cellsID,1,5),
				new ItemStack(fcItems.cellsID,1,0),
				new ItemStack(fcItems.cellsID,1,7),
		/*5*/	new ItemStack(fcItems.cellsID,1,8),
				new ItemStack(fcItems.cellsID,1,11)};		
	}
}

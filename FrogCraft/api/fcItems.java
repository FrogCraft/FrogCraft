package FrogCraft.api;

import java.util.List;

import FrogCraft.api.fcItems.cls;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


/** This is a part of FrogCraft API allow you to access FrogCraft items, you can include this as apart of your mod,
 *  do not modify any thing, otherwise it will cause unknown compatibility error or any unexpected result!
 *  These variables are initialized in pre init */
public class fcItems {
	/**Refer to FrogCraft.ItemRegister,meta sensitive*/
	public static int machineID,machine2ID,oreID;
	/**Refer to FrogCraft.ItemRegister*/
	public static int ingotsID,cellsID,miscsID,dustsID,gasesID,liquidsID;
	
	/**Not meta sensitive items and blocks*/
	public static Block acwindmillcylinder;
	public static Block mobileps;
	public static Item fan,railgun;
	public static Item IC2Coolant_NH3_60K;
	public static Item IC2Coolant_NH3_180K;
	public static Item IC2Coolant_NH3_360K;
	
	public static ItemStack getItem(cls type,String name){
		return getItem(type,name,1);
	}
	
	public static ItemStack getItem(cls type, String name, int amount) {
		return getItem(type.t,name,amount);
	}
	
	
	public static ItemStack getItem(int type,String name,int amount){
		List<String[]> itemsData=null;
		int id=-1;
		try{
			if (type==0){
				id=ingotsID;
				itemsData=(List<String[]>) Class.forName("FrogCraft.Items.Item_Ingots").getField("itemsData").get(null);
			}
			if (type==1){
				id=cellsID;
				itemsData=(List<String[]>) Class.forName("FrogCraft.Items.Item_Cells").getField("itemsData").get(null);
			}
			if (type==2){
				id=miscsID;
				itemsData=(List<String[]>) Class.forName("FrogCraft.Items.Item_Miscs").getField("itemsData").get(null);
			}
			if (type==3){
					id=dustsID;
				itemsData=(List<String[]>) Class.forName("FrogCraft.Items.Item_Dusts").getField("itemsData").get(null);			
			}
		}
		catch(Exception e){}
		
		
		if(id==-1|itemsData==null)
			return null;
		
		for(int i=0;i<itemsData.size();i++){
			if (itemsData.get(i)[0]==name)
				return new ItemStack(id,amount,i);
		}
		
		return null;
	}
	
	/***/
	public enum cls {
		ingot(0),cell(1),misc(2),dust(3),gas(4),liquid(5);
		
		int t;
		cls(int t) {
			this.t = t;
		}
	}
}

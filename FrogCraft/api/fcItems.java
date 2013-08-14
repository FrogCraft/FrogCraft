package FrogCraft.api;

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
	public static int ingotsID,cellsID,miscsID,dustsID;
	
	/**Not meta sensitive items and blocks*/
	public static Block acwindmillcylinder;
	public static Block mobileps;
	public static Item fan,railgun;
	public static Item IC2Coolant_NH3_60K;
	public static Item IC2Coolant_NH3_180K;
	public static Item IC2Coolant_NH3_360K;
	public static Item UBattery,ThBattery,PuBattery;
	
	public static ItemStack getItem(cls type,String name){return getItem(type,name,1);}
	
	public static ItemStack getItem(cls type, String name, int amount) {return getItem(type.t,name,amount);}

	public static ItemStack getItem(int type,String name){return getItem(type,name,1);}
	
	public static ItemStack getItem(int type,String name,int amount){
		try {
		return (ItemStack) Class.forName("FrogCraft.Common.ItemManager").getMethod("getItem", int.class,String.class,int.class)
		.invoke(null, type,name,amount);
		}catch (Exception e) {return null;}	
	}
	
	/***/
	public enum cls {
		ingot(0),cell(1),misc(2),dust(3);
		
		public int t;
		cls(int t) {
			this.t = t;
		}
	}
}

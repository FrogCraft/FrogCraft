package FrogCraft.api;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


/** This is a part of FrogCraft API allow you to access FrogCraft items, you can include this as apart of your mod,
 *  do not modify any thing, otherwise it will cause unknown compatibility error or any unexpected result!
 *  These variables are initialized in pre init */
public class fcItems {
	//This are also used by FrogCraft internally!!!DO NOT MODIFY!!! 
    /** Creative Page*/
    public static CreativeTabs tabFrogCraft;
	
	/**Meta sensitive items and blocks*/
	public static Block Machines;	
	public static Block Machines2;
	public static Block Ore;	
	public static Item Ingots;
	public static Item Cells;
	public static Item Miscs;
	public static Item Dusts;
	
	/**Not meta sensitive items and blocks*/
	public static Block BlockHNO3;
	public static Block ACWindMillCylinder;
	public static Block MobilePS;
	
	/**Not meta sensitive items*/
	public static Item NitricAcidBucket;
	public static Item IC2Coolant_NH3_60K;
	public static Item IC2Coolant_NH3_180K;
	public static Item IC2Coolant_NH3_360K;	
	public static Item Railgun;
	public static Item Fan;
	public static Item UBattery,ThBattery,PuBattery;
	
	/** Type of Items */
	public enum cls {
		ingot(0),cell(1),misc(2),dust(3);
		
		public int t;
		cls(int t) {this.t = t;}
	}
	
	
	//API functions - FrogCraft does not uses these functions internally, you can copy them in to your mod
	/** Get a item from frogcraft, Just for ingot,cells,miscs and dusts*/
	public static ItemStack getItem(cls type, String name, int amount) {return getItem(type.t,name,amount);}
	
	public static ItemStack getItem(cls type,String name){return getItem(type,name,1);}

	public static ItemStack getItem(int type,String name){return getItem(type,name,1);}
	
	public static ItemStack getItem(int type,String name,int amount){
		try {
		return (ItemStack) Class.forName("FrogCraft.Common.ItemManager").getMethod("getItem", int.class,String.class,int.class)
		.invoke(null, type,name,amount);
		}catch (Exception e) {return null;}	
	}
}

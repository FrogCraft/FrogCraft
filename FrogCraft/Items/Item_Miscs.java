package FrogCraft.Items;

import java.util.*;

import FrogCraft.api.fcItems;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class Item_Miscs extends Item{	
	public static String iconDir="Miscs",unLocalizedName="Item_Miscs";	
	
	public static Map<Integer,Icon> Icons;
	public static BiMap<Integer,String> subNames= HashBiMap.create();
	
	public static void add(int id,String name){
		subNames.put(id,name);
	}
	
	//Common stuffs
	public Item_Miscs(int id) {
		super(id);
		setHasSubtypes(true);
		setUnlocalizedName(unLocalizedName);
		setMaxDamage(0); 
		setCreativeTab(fcItems.tabFrogCraft);	
	}

	@SideOnly(Side.CLIENT)
    @Override	
	public void getSubItems(int par1, CreativeTabs tab, List subItems) {
		for (int i:subNames.keySet().toArray(new Integer[]{}))
			subItems.add(new ItemStack(this, 1,i));
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	Icons=new HashMap();
    	for (int i:subNames.keySet().toArray(new Integer[]{}))
    		Icons.put(i,par1IconRegister.registerIcon("FrogCraft:"+iconDir+"/"+subNames.get(i)));
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage(int damage)
    {
    	if (!Icons.containsKey(damage))
    		return null;
        return Icons.get(damage);
    }
    
    @SideOnly(Side.CLIENT)    
    @Override
    public String getItemDisplayName(ItemStack par1ItemStack)
    {
    	if (!subNames.containsKey(par1ItemStack.getItemDamage()))
    		return null;    	
        return StatCollector.translateToLocal(getLocalizedName(par1ItemStack)+"."+subNames.get(par1ItemStack.getItemDamage()));
    }
    
	public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		int dmg=itemStack.getItemDamage();
		String info=StatCollector.translateToLocal(getLocalizedName(itemStack)+"."+subNames.get(itemStack.getItemDamage())+".info");
		if (info!="")
			list.add(info);
	}
}

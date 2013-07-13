package FrogCraft.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import FrogCraft.*;
import FrogCraft.api.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.Icon;
import net.minecraftforge.liquids.*;

public class Item_Liquids extends Item{
	public static Icon Icons[];	
	public static List<String[]> itemsData= new ArrayList<String[]>();
	public static Map<String,Integer> nameMap=new HashMap();
	
	public static void add(String name,String displayName){add(name,displayName,"");}
	public static void add(String name,String displayName,String info){
		itemsData.add(new String[]{name,displayName,info});
		nameMap.put(name, itemsData.size()-1);
	}
	public static ItemStack[] filledContainer;
	
	ItemStack getCell(int meta){
		if(filledContainer.length>meta)
			return filledContainer[meta];
		
		return new ItemStack(this,1,meta);
	}
	
	public Item_Liquids(int id) {
		super(id);
		setHasSubtypes(true);
		setUnlocalizedName("fcItem_Liquids");
		setMaxDamage(0); 
		setCreativeTab(mod_FrogCraft.tabFrogCraft);
		
		for (int ix = 0; ix < itemsData.size(); ix++) {
			LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid(itemsData.get(ix)[0],new LiquidStack(itemID,1000,ix)), getCell(ix), ic2.api.item.Items.getItem("cell")));
		}
		
	}

	@SideOnly(Side.CLIENT)
    @Override	
	public void getSubItems(int par1, CreativeTabs tab, List subItems) {
		for (int ix = 0; ix < itemsData.size(); ix++) {
			subItems.add(new ItemStack(this, 1, ix));
		}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	Icons=new Icon[itemsData.size()];
    	for (int i=0;i<itemsData.size();i++){
    		Icons[i]=par1IconRegister.registerIcon("FrogCraft:Liquids/"+itemsData.get(i)[0]);

    		LiquidDictionary.getCanonicalLiquid(new LiquidStack(itemID, 1000, i)).setRenderingIcon(Icons[i]).setTextureSheet("/gui/items.png");
    	}
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage(int damage)
    {
    	if (damage>=itemsData.size())
    		return null;
        return Icons[damage];
    }
    
    @SideOnly(Side.CLIENT)    
    @Override
    public String getItemDisplayName(ItemStack par1ItemStack)
    {
    	if (par1ItemStack.getItemDamage()>=itemsData.size())
    		return null;    	
        return itemsData.get(par1ItemStack.getItemDamage())[1];
    }
    
	public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		int dmg=itemStack.getItemDamage();
		String info=itemsData.get(dmg)[2];
		if (info!="")
			list.add(info);
	}
}

package FrogCraft.Items;

import java.util.*;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import FrogCraft.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class Item_Ingots extends Item{
	//Explosive Ingot(K)
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
    	if (entityItem.getEntityItem()!=null&&entityItem.getEntityItem().getItemDamage()==0){
        	int block=entityItem.worldObj.getBlockId((int)entityItem.posX,(int)(entityItem.posY+0.4),(int) entityItem.posZ);
        	if (block==Block.waterStill.blockID|block==Block.waterMoving.blockID|block==Block.waterlily.blockID){
        		mod_FrogCraft.fcAchievements.achieved(entityItem.worldObj.getClosestPlayer(entityItem.posX, entityItem.posY, entityItem.posZ, -1),"killed_by_Potassium");
        		entityItem.hurtResistantTime=40;
        		if(!entityItem.worldObj.isRemote){
        			entityItem.worldObj.createExplosion(null, entityItem.posX, entityItem.posY, entityItem.posZ, 0.9f*(float)Math.sqrt(entityItem.getEntityItem().stackSize), false);
        			entityItem.setDead();
        		}
        	}
    	}
        return false;
    }
    
    
	public static String iconDir="Ingots",unLocalizedName="Item_Ingots";	
	
	public static Map<Integer,Icon> Icons;
	public static BiMap<Integer,String> subNames= HashBiMap.create();
	
	public static void add(int id,String name){
		subNames.put(id,name);
	}
	
	//Common stuffs
	public Item_Ingots(int id) {
		super(id);
		setHasSubtypes(true);
		setUnlocalizedName(unLocalizedName);
		setMaxDamage(0); 
		setCreativeTab(mod_FrogCraft.tabFrogCraft);	
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

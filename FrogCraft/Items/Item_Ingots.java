package FrogCraft.Items;

import java.util.ArrayList;
import java.util.List;
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
import net.minecraft.world.World;
import net.minecraftforge.liquids.*;

public class Item_Ingots extends Item{
	public static Icon Icons[];
	
	public static List<String[]> itemsData= new ArrayList<String[]>();
	
	public static void add(String name,String displayName){add(name,displayName,"");}
	public static void add(String name,String displayName,String info){itemsData.add(new String[]{name,displayName,info});}
	
   
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
    
	//Common stuffs
	public Item_Ingots(int id) {
		super(id);
		setHasSubtypes(true);
		setUnlocalizedName("Item_Ingots");
		setMaxDamage(0); 
		setCreativeTab(mod_FrogCraft.tabFrogCraft);		
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
    		Icons[i]=par1IconRegister.registerIcon("FrogCraft:Ingots/"+itemsData.get(i)[0]);
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

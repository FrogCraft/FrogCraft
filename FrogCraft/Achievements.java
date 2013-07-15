package FrogCraft;

import java.util.HashMap;

import FrogCraft.api.fcItems;

import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.*;
import net.minecraft.util.StringTranslate;
import net.minecraftforge.common.AchievementPage;

public class Achievements implements ICraftingHandler {

	public Achievements(){
		this.achievementList = new HashMap();
		addAchievement(0,null,new ItemStack(mod_FrogCraft.Machines,1,8),0,0,"get_EVT",false);		
		addAchievement(1,achievementList.get("get_EVT"),mod_FrogCraft.Railgun,0,-2,"get_railgun",true);		
		addAchievement(2,null,new ItemStack(fcItems.ingotsID,1,0),-2,0,"killed_by_Potassium",true);		
		addAchievement(3,null,new ItemStack(mod_FrogCraft.Machines,1,1),2,0,"gaspump",false);	
		addAchievement(4,achievementList.get("gaspump"),new ItemStack(mod_FrogCraft.Machines,1,0),2,2,"pneumaticCompressor",false);
		addAchievement(5,achievementList.get("gaspump"),new ItemStack(mod_FrogCraft.Machines,1,9),2,-2,"liquifier",false);
		addAchievement(6,achievementList.get("get_EVT"),new ItemStack(mod_FrogCraft.Machines,1,6),0,2,"hsu",false);		
		addAchievement(7,achievementList.get("hsu"),new ItemStack(mod_FrogCraft.Machines,1,7),0,4,"uhsu",false);					
		addAchievement(8,achievementList.get("gaspump"),new ItemStack(mod_FrogCraft.Machines,1,12),4,0,"get_ACR",false);	
		addAchievement(9,achievementList.get("get_ACR"),new ItemStack(fcItems.miscsID,1,3),4,-2,"goldclod",false);	
		addAchievement(10,achievementList.get("liquifier"),new ItemStack(mod_FrogCraft.Machines,1,10),2,-4,"condensetower",false);	
		addAchievement(11,achievementList.get("condensetower"),new ItemStack(mod_FrogCraft.Machines2,1,0),4,-4,"condensetower2",false);		
		
		AchievementPage.registerAchievementPage(new AchievementPage("FrogCraft", (Achievement[])achievementList.values().toArray(new Achievement[achievementList.size()])));
		GameRegistry.registerCraftingHandler(this);
	}
	
	//Crafting Handler
    @Override
    public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) 
    {
    	if(item.itemID==fcItems.machineID){
    		switch(item.getItemDamage()){
    		case 0:
    			achieved(player, "pneumaticCompressor");
    			return;        			
    		case 1:
    			achieved(player, "gaspump");
    			return;         			
    		case 6:
    			achieved(player, "hsu");
    			return;            		
    		case 7:
    			achieved(player, "uhsu");
    			return;          
    		case 8:
    			achieved(player, "get_EVT");
    			return;
    		case 9:
    			achieved(player, "liquifier");
    			return;        			
    		case 10:
    			achieved(player, "condensetower");
    			return;             			
    		case 12:
    			achieved(player, "get_ACR");
    			return;        			
    		}
    	}
    	
    	if(item.itemID==fcItems.machine2ID){     
    		switch(item.getItemDamage()){
    		case 0:
    			achieved(player, "condensetower2");
    			return;            			
    		}
    	}
    	if(item.itemID==fcItems.railgun.itemID)
    		achieved(player, "get_railgun");
    	if(item.itemID==fcItems.miscsID&&item.getItemDamage()==3)
    		achieved(player, "goldclod");
    }

    @Override
    public void onSmelting(EntityPlayer player, ItemStack item) {}
	
	//---------------------------------Common Fields and Functions ------------------------------------------------------------------
	public  HashMap<String, Achievement> achievementList;
	
	/** Add a new Achievement */
	public  Achievement addAchievement(int id,Achievement dependedAchievement,Object IconContainingObj,int x,int y,String name,boolean isSpecial){
		Achievement r=null;
		if (IconContainingObj instanceof Item)
			r=new Achievement(746750+id, name, x, y, (Item) IconContainingObj, dependedAchievement);
		if (IconContainingObj instanceof Block)
			r=new Achievement(746750+id, name, x, y, (Block) IconContainingObj, dependedAchievement);
		if (IconContainingObj instanceof ItemStack)
			r=new Achievement(746750+id, name, x, y, (ItemStack) IconContainingObj, dependedAchievement);
		
		if (r!=null){
			if (isSpecial)
				r.setSpecial();
			r.registerAchievement();			
			achievementList.put(name,r);
		}
		
		return r;
	}
	
	/** Get a specific achievement in FrogCraft by name */
	public  Achievement getAchievement(String name){
		return achievementList.get(name);
	}
	
	/** Add a new achievement to a player */
	public  void achieved(EntityPlayer player,String achievement){
		if (!achievementList.containsKey(achievement))
			return;
		if	(player==null)
			return;
		player.addStat(getAchievement(achievement), 1);
	}
}

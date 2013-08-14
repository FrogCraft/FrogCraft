package FrogCraft.Items;

import FrogCraft.mod_FrogCraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Item_Camera extends Item{

	public Item_Camera(int par1) {
		super(par1);
		setUnlocalizedName("test233");
		setMaxDamage(0); 
		setCreativeTab(mod_FrogCraft.tabFrogCraft);	
	}

    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
    	return true;
    }
    
    public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase entity)
    {
    	EntityPlayer player=(EntityPlayer) entity;
    	ItemStack skull = new ItemStack(Item.skull);
    	if (target instanceof EntitySkeleton)
    		skull.setItemDamage(0);    	    	
    	else if (target instanceof EntityZombie)
    		skull.setItemDamage(2);    	
    	else if (target instanceof EntityCreeper)
    		skull.setItemDamage(4);
    	else {
    		skull.setItemDamage(3);
    	}
    		
    	player.inventory.addItemStackToInventory(skull);
    	return false;
    }
}

package Spray;

import java.util.List;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class Item_Spray extends Item {
	public static int maxSpray;
	public static String[] names;
	
	
	public Item_Spray(int par1) {
		super(par1);
		setMaxDamage(0);
		setCreativeTab(CreativeTabs.tabMisc);
	}

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	itemIcon=par1IconRegister.registerIcon("FrogCraft:Spray");
    }
	
    public String getItemDisplayName(ItemStack i)
    {
    	if (i.getItemDamage()>=names.length)
    		return "";
        return names[i.getItemDamage()];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for (int i=0;i<maxSpray;i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }
	
	@Override
	public boolean onItemUse(ItemStack item_stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float x_off,
			float y_off, float z_off) {
		if (side == 0)
			return false;
		if (side == 1)
			return false;

		int direction = Direction.facingToDirection[side];

		EntitySpray entity = new EntitySpray(world, x, y, z, direction,
				item_stack.getItemDamage());

		if (entity.onValidSurface(player)) {
			if (!player.capabilities.isCreativeMode)
				player.inventory.consumeInventoryItem(itemID);
			
			if (!world.isRemote) {
				world.spawnEntityInWorld(entity);
			}
		}
		return true;
	}
}
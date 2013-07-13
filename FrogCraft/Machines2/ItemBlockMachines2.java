package FrogCraft.Machines2;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMachines2 extends ItemBlock {

	public final static String[] subNames = {"LiquidOutput","CondenseTowerCylinder","ACWindMillTop","ACWindMillBase","AutoWorkBench"};
	public static String[] Machines2_Names={"Condense Tower Output","Condense Tower Cylinder","ACWindMill Top","ACWindMill Base","Auto WorkBench"};
	
	public ItemBlockMachines2(int id) {
		super(id);
		setHasSubtypes(true);
		setUnlocalizedName("Item_Machines2");
	}

	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean par4) {
		int dmg=item.getItemDamage();
		if (dmg==3){list.add("Out: 32");}
	}
	
	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
	}


}
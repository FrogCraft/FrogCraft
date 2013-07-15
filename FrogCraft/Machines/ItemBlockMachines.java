package FrogCraft.Machines;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;

public class ItemBlockMachines extends ItemBlock {

	public final static String[] subNames = {"PneumaticCompressor","AirPump","IndustrialCompressor","IndustrialMacerator",
											 "IndustrialExtractor","IndustrialFurnance","HSU","UHSU",
											 "EVT","Liquifier","LiquidInjector","ThermalCracker","AdvanceChemicalReactor"};
	
	public final static int[] maxIn={32,128,128,128,
									128,128,2048,8192,
									8192,128,128,128,128};
	
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean par4) {
		int dmg=item.getItemDamage();
		list.add("Max In: "+String.valueOf(maxIn[dmg]));
		if (dmg==6){list.add("Out: 2048");}
		if (dmg==7){list.add("Out: 8192");}
		if (dmg==8){list.add("Out: 2048");}
	}
	
	
	public ItemBlockMachines(int id) {
		super(id);
		setHasSubtypes(true);
		setUnlocalizedName("Item_Machines");
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
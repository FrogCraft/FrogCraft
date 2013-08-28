package FrogCraft.Blocks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOre extends ItemBlock {

	public final static String[] subNames = {"oreCarnallite","oreFluorapatite","oreDewalquite","oreRuby"
		,"oreSapphire","oreGreenSapphire","stoneMarble","stoneBasalt"};
	
	public ItemBlockOre(int id) {
		super(id);
		setHasSubtypes(true);
		setUnlocalizedName("Block_fcOre");
	}

	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean par4) {
		int dmg=item.getItemDamage();
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
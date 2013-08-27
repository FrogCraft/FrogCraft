package FrogCraft.Items;

import java.util.List;

import FrogCraft.Common.FluidManager;
import FrogCraft.api.fcItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class Item_NitricAcidBucket extends ItemBucket{

	public Item_NitricAcidBucket(int id) {
		super(id, fcItems.BlockHNO3.blockID);
		setCreativeTab(fcItems.tabFrogCraft);
		setUnlocalizedName("Item_NitricAcidBucket");
		FluidContainerRegistry.registerFluidContainer(FluidManager.getFluid("HNO3"), new ItemStack(this), new ItemStack(Item.bucketEmpty));
	}

	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		String info=StatCollector.translateToLocal(getLocalizedName(itemStack)+".info");
		list.add(info);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister IconRegister)
    {
    	itemIcon=IconRegister.registerIcon("frogcraft:Bucket_HNO3");
    }
}

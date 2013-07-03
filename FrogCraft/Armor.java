package FrogCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.src.ModLoader;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;

public class Armor extends net.minecraft.item.ItemArmor{
	public static EnumArmorMaterial fcArmor = EnumHelper.addArmorMaterial("Modium_Armor", 5, new int[]{1, 3, 2, 1}, 15);
	static Icon ico;
	public Armor() {
		super(2000, fcArmor, mod_FrogCraft.proxy.addArmor(fcArmor.name()), 1);
	}

    public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
    {
    	player.addPotionEffect(new PotionEffect(Potion.confusion.id,200,0));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister r)
    {
    	ico=r.registerIcon("FrogCraft:Modium_Armor");
    }
	
    @SideOnly(Side.CLIENT)
    public static Icon func_94602_b(int par0)
    {
    	return ico;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
    	return ("/mods/FrogCraft/textures/armor/Modium_Armor.png");
    }

}

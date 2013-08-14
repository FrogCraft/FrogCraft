package FrogCraft.Items.Railgun;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class DamageSource_Coin extends EntityDamageSourceIndirect{

	public DamageSource_Coin(String par1Str, Entity par2Entity,
			Entity par3Entity) {
		super(par1Str, par2Entity, par3Entity);
	}

	
    public static DamageSource causeCoinDamage(EntityCoin Coin, Entity Player)
    {
        return (new DamageSource_Coin("coin", Coin, Player)).setProjectile();
    }
    
    @Override
    public ChatMessageComponent getDeathMessage(EntityLivingBase player)
    {
    	return ChatMessageComponent.func_111082_b(((EntityPlayer)getEntity()).username+" Railguned "+((EntityPlayer)player).username);
    }
}

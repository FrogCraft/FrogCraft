package FrogCraft.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import FrogCraft.Common.FluidManager;
import FrogCraft.api.fcItems;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.FluidContainerRegistry;


public class BlockHNO3 extends BlockFluidClassic{
	@ForgeSubscribe 
	public void fill(FillBucketEvent event){
		int blockID=event.world.getBlockId(event.target.blockX, event.target.blockY, event.target.blockZ);
		if (blockID==fcItems.BlockHNO3.blockID){
			event.result=new ItemStack(fcItems.NitricAcidBucket);
			event.setResult(Result.ALLOW);
			event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
		}
	}
	
	public Icon flowIcon;
	public BlockHNO3(int id) {
		super(id, FluidManager.getFluid("hno3").getFluid(), Material.water);
		//setCreativeTab(mod_FrogCraft.tabFrogCraft );
		
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityLivingBase){
			EntityLivingBase player=(EntityLivingBase)entity;
			player.addPotionEffect(new PotionEffect(Potion.poison.id,100,0));
		}
	}
    
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister IconRegister)
    {
    	flowIcon=IconRegister.registerIcon("frogcraft:Fluids/HNO3_flow");
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
    	return par1 != 0 && par1 != 1 ? flowIcon : this.getFluid().getStillIcon();
    }
}

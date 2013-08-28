package FrogCraft.Blocks;

import java.util.List;
import java.util.Random;

import FrogCraft.api.fcItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public class BlockOre extends Block{
	
	private Icon[] iconBuffer;
	
    public BlockOre(int id) {
		super(id, Material.rock);
        setHardness(2.0F);
        setResistance(5.0F);
        //setStepSound(Block.dirt.stepSound);
        setUnlocalizedName("fcOre");
        setCreativeTab(fcItems.tabFrogCraft);
	}
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int meta, Random random, int fortune)
    {
    	switch (meta){
    	case 3:
    		return fcItems.Ingots.itemID;
    	case 4:
    		return fcItems.Ingots.itemID;  
    	case 5:
    		return fcItems.Ingots.itemID;    		
    	default:
    		return blockID;
    	}
    }
    
    public int damageDropped(int meta){
    	switch(meta){
    	case 3:
    		return 2;
    	case 4:
    		return 3;
    	case 5:
    		return 4;
    	default:
    		return meta; 
    	} 
    }
    
    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return this.quantityDropped(random) + random.nextInt(fortune + 1);
    }
    
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		for (int ix = 0; ix < ItemBlockOre.subNames.length; ix++) {
			subItems.add(new ItemStack(this, 1, ix));
		}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int blockSide)
    {
    	return iconBuffer[world.getBlockMetadata(x, y, z)];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int blockSide, int blockMeta)
    {
    	return iconBuffer[blockMeta];
    }
    
    //Regist Icons
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister r)    {
    	iconBuffer=new Icon[ItemBlockOre.subNames.length];
    	for (int i=0;i<ItemBlockOre.subNames.length;i++)
    		iconBuffer[i]=r.registerIcon("FrogCraft:Ores/"+ItemBlockOre.subNames[i]);
    }
}

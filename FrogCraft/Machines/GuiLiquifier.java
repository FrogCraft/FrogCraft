package FrogCraft.Machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import FrogCraft.mod_FrogCraft;
import FrogCraft.Common.GuiLiquids;

public class GuiLiquifier extends GuiLiquids {
	protected TileEntityLiquifier te;

	
    public GuiLiquifier (InventoryPlayer inventoryPlayer,TileEntityLiquifier tileEntity) {
    	super(new ContainerLiquifier(inventoryPlayer,tileEntity));
    	te=tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	fontRenderer.drawString(StatCollector.translateToLocal("tile.Machines."+ItemBlockMachines.subNames[te.getBlockMetadata()]+".name"), 8, 6, 4210752);
    	//fontRenderer.drawString("Eu Stored: "+String.valueOf(te.energy), 46, 50, 4210752); 
    	
    	drawLiquidBar(143, 23, 16, 47, te.fluidID, te.amountP/10);

    	mc.renderEngine.func_110577_a(new ResourceLocation("frogcraf:textures/gui/Gui_Liquifier.png"));      
    	this.drawTexturedModalRect(143, 23, 176, 0, 16, 47);
      	

    	int x=par1-((width-xSize)/2),y=par2-((height-ySize)/2);
    	if (x>=142&y>=22&x<=160&y<=70){
    		List<String> l = new ArrayList<String>();
    		if (te.fluidID>0){
				//l.add(getFluidDisplayName(tileentity.fluidID));
				l.add(String.valueOf(te.amountP/10)+"."+String.valueOf(te.amountP-(te.amountP/10)*10)+"%");
				l.add("About "+String.valueOf(te.amountP*te.maxCapacity/1000)+"mB");
				func_102021_a(l, x, y);    
            }
    	} 	
    }
        
        
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.func_110577_a(new ResourceLocation("frogcraft:textures/gui/Gui_Liquifier.png"));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
                
                
		if (te.energy>0)
			this.drawTexturedModalRect(x + 81, y + 27 +14 - gete(), 176, 52+14- gete(), 13, gete());
		
		this.drawTexturedModalRect(x + 77, y +56, 176, 70, 24*te.tick/100, 16);
	}

	int gete(){
		return 14*te.energy/te.maxEnergy;
	}
}
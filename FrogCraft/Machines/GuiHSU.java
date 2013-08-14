package FrogCraft.Machines;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import FrogCraft.mod_FrogCraft;

public class GuiHSU extends GuiContainer {
		
	protected TileEntityHSU tileentity;
	int l;
		
    public GuiHSU (InventoryPlayer inventoryPlayer,TileEntityHSU tileEntity) {
    	super(new ContainerHSU(inventoryPlayer,tileEntity));
    	tileentity=tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {     	
     	//draw text and stuff here
       	//the parameters for drawString are: string, x, y, color
    	
       	fontRenderer.drawString(StatCollector.translateToLocal("tile.Machines."+ItemBlockMachines.subNames[tileentity.getBlockMetadata()]+".name"), 8, 6, 4210752);
       	fontRenderer.drawString("Percentage: "+getpS()+"%", 8, 24, 4210752); 
       	fontRenderer.drawString("Eu: "+getE(), 8, 34, 4210752); 
       	fontRenderer.drawString("Max: "+String.valueOf(tileentity.maxEnergy), 8, 44, 4210752);
       	fontRenderer.drawString("Output: "+String.valueOf(tileentity.maxInput)+"Eu/t", 8, 54, 4210752);
       	//draws "Inventory" or your regional equivalent
       	fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,int par3) {
       	//draw your Gui here, only thing you need to change is the path
       	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
       	mc.renderEngine.func_110577_a(new ResourceLocation("frogcraft","/textures/gui/GUI_HSU.png"));
       	int x = (width - xSize) / 2;
       	int y = (height - ySize) / 2;
       	this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
                 
       	l=40*getp()/10000;
       	this.drawTexturedModalRect(x + 145, y + 18, 176, 41-l, 12,5+l);
       	       
    }

    String getpS(){
    	int p=getp();
    	String a=String.valueOf(p-((p/100)*100));
    	if (a.length()==1)
    		a="0"+a;
    	return String.valueOf(p/100)+"."+a;
    }
    
    int getp(){
      	return getE()/(tileentity.maxEnergy/10000);
    }
        
    int gete(){
      	return getp()*14/10000;
    }
       
    int getE(){
       	return (tileentity.energyB*1000000)+(tileentity.energyM*1000)+tileentity.energyK;
    }
}
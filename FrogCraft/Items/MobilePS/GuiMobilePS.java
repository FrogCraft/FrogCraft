package FrogCraft.Items.MobilePS;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;


public class GuiMobilePS extends GuiContainer {
		
	protected TileEntityMobilePS tileentity;
	int l;
		
    public GuiMobilePS (InventoryPlayer inventoryPlayer,TileEntityMobilePS tileEntity) {
    	super(new ContainerMobilePS(inventoryPlayer,tileEntity));
    	tileentity=tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {     	
     	//draw text and stuff here
       	//the parameters for drawString are: string, x, y, color
    	//fontRenderer.drawString(String.valueOf(tileentity.progress), 8, 6, 4210752);
       	fontRenderer.drawString(ItemBlock_MobilePS.name, 8, 6, 4210752);
       	fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96, 4210752);
       	fontRenderer.drawString("Out:"+String.valueOf(tileentity.vOut), 124, ySize - 96, 4210752);       	
       	
    	int x=par1-((width-xSize)/2),y=par2-((height-ySize)/2);
    	if (x>=145&y>=18&x<=156&y<=62){
    		List<String> l = new ArrayList<String>();
    		l.add("Stored:"+String.valueOf(getE())+"Eu");
    		l.add("Max:"+String.valueOf(getEM())+"Eu");    		
    		func_102021_a(l, x, y);            
    	}   
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,int par3) {
       	//draw your Gui here, only thing you need to change is the path
       	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
       	this.mc.renderEngine.bindTexture("/mods/FrogCraft/textures/gui/GUI_MobilePS.png");
       	int x = (width - xSize) / 2;
       	int y = (height - ySize) / 2;
       	this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
                 
       	l=40*getp()/10000;
       	this.drawTexturedModalRect(x + 145, y + 18, 176, 41-l, 12,5+l);

       	if (tileentity.progress>0)
       		this.drawTexturedModalRect(x + 39, y + 47, 176, 51, tileentity.progress*24/100,16);       		
    }

    String getpS(){
    	int p=getp();
    	String a=String.valueOf(p-((p/100)*100));
    	if (a.length()==1)
    		a="0"+a;
    	return String.valueOf(p/100)+"."+a;
    }
    
    int getp(){
    	int em=getEM();
    	if (em==0)
    		return 0;
      	return getE()/(em/10000);
    }
        
    int gete(){
      	return getp()*14/10000;
    }
       
    int getEM(){
       	return (tileentity.energyMB*1000000)+(tileentity.energyMM*1000)+tileentity.energyMK;    	
    }
    
    int getE(){
       	return (tileentity.energyB*1000000)+(tileentity.energyM*1000)+tileentity.energyK;
    }
}
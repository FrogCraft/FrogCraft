package FrogCraft.Machines;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import FrogCraft.mod_FrogCraft;

public class GuiPneumaticCompressor extends GuiContainer {
		
		protected TileEntityPneumaticCompressor tileentity;
	
        public GuiPneumaticCompressor (InventoryPlayer inventoryPlayer,TileEntityPneumaticCompressor tileEntity) {
                super(new ContainerPneumaticCompressor(inventoryPlayer, tileEntity));
                tileentity=tileEntity;
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int param1, int param2) {
                //draw text and stuff here
                //the parameters for drawString are: string, x, y, color
                fontRenderer.drawString(ItemBlockMachines.Machines_Names[tileentity.getBlockMetadata()], 8, 6, 4210752);
                //draws "Inventory" or your regional equivalent
                fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96, 4210752);
                if(tileentity.fully()){
                	fontRenderer.drawString(String.valueOf(tileentity.tick)+"MPa",110 ,	ySize - 116,  4210752);
                	fontRenderer.drawString("Of "+String.valueOf(tileentity.gas_total)+"MPa",110 ,	ySize - 106,  4210752);                	
                }
                else
                	fontRenderer.drawString("Fail to run!", 110, ySize - 96, 4210752);                	
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                        int par3) {
                //draw your Gui here, only thing you need to change is the path
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.mc.renderEngine.bindTexture("/mods/FrogCraft/textures/gui/GUI_PneumaticCompressor.png");
                int x = (width - xSize) / 2;
                int y = (height - ySize) / 2;
                this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
                
                
                if (tileentity.energy>0)
                	this.drawTexturedModalRect(x + 81, y + 52+14-gete(), 176, 31 - gete(), 14, gete());
                
                
                if (tileentity.progress>0)
                	this.drawTexturedModalRect(x + 79, y + 28, 176, 0, getp(), 10);
                
        }

        int gete(){
        	return 14*tileentity.energy/tileentity.maxEnergy;
        }
        
        int getp(){
        	return tileentity.progress*19/100;
        }
}
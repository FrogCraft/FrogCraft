package FrogCraft.Items.Railgun;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import FrogCraft.mod_FrogCraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderRailgun implements IItemRenderer {
	private static RenderItem renderItem = new RenderItem();
	private static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
	private static Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {return type==ItemRenderType.EQUIPPED_FIRST_PERSON
	|type==ItemRenderType.EQUIPPED;}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type,ItemStack item,ItemRendererHelper helper) {return false;}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {   
		if(type==ItemRenderType.EQUIPPED_FIRST_PERSON)
			renderFirstPerson(item);
		else
			renderThirdPerson((EntityPlayer)data[1],item);	
	}
		
	public void renderIcon(double par1, double par2, Icon par3Icon, double par4, double par5)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par5), 0d, (double)par3Icon.getMinU(), (double)par3Icon.getMaxV());
        tessellator.addVertexWithUV((double)(par1 + par4), (double)(par2 + par5), 0d, (double)par3Icon.getMaxU(), (double)par3Icon.getMaxV());
        tessellator.addVertexWithUV((double)(par1 + par4), (double)(par2 + 0), 0d, (double)par3Icon.getMaxU(), (double)par3Icon.getMinV());
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), 0d, (double)par3Icon.getMinU(), (double)par3Icon.getMinV());
        tessellator.draw();
    }
    
    void renderFirstPerson(ItemStack item){ 	
    	TextureManager texturemanager = this.mc.func_110434_K();

    	//First Person View
    	Icon icon;
    	if (mc.thePlayer.isUsingItem())
    		icon= Item_Railgun.using;
    	else
    		icon = Item_Railgun.icon;	
    	
        //mc.renderEngine.func_110577_a(new ResourceLocation("/gui/items.png"));
        
    	texturemanager.func_110577_a(texturemanager.func_130087_a(1));
    	ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getOriginX(), icon.getOriginY(), 0.0625F);
        
        if (mc.thePlayer.isUsingItem()){       	
        	if (mc.thePlayer.inventory.hasItem(Item_Railgun.AmmoID)|mc.thePlayer.capabilities.isCreativeMode){
        		renderCoin();
        		renderShadow();
        	}
        }

    }
    
	void renderThirdPerson(EntityPlayer player,ItemStack item) {
        //Third Person View
		if (!player.inventory.hasItem(Item_Railgun.AmmoID)){
			if (!mc.thePlayer.capabilities.isCreativeMode)
				return;
		}
		
        renderIcon(0, 0, Item_Railgun.coin, 0.5, 0.5);
        if (player.isUsingItem())
        	renderShadow();
	}
	
	void renderShadow(){
    	GL11.glDepthFunc(GL11.GL_EQUAL);
    	GL11.glDisable(GL11.GL_LIGHTING);
    	mc.renderEngine.func_110577_a(new ResourceLocation(("textures/entity/creeper/creeper_armor.png")));
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
    	float f7 = 0.76F;
    	GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
    	GL11.glMatrixMode(GL11.GL_TEXTURE);
    	GL11.glPushMatrix();
        float f8 = 0.125F;
        GL11.glScalef(f8, f8, f8);
        float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
        GL11.glTranslatef(f9, 0.0F, 0.0F);
        GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(f8, f8, f8);
        f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
        GL11.glTranslatef(-f9, 0.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
	}
	
	void renderCoin(){
		Tessellator tessellator = Tessellator.instance;
		mc.renderEngine.func_110577_a(new ResourceLocation("frogcraft:textures/render/Coin.png"));
        GL11.glPushMatrix();
        //GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(1.9F, 1.9F, 0.5F);
        GL11.glScalef(0.04f, 0.04f, 0.04f);
 
    	GL11.glRotatef(25.0F, 0.0F, 0.0F, 1.0F);
    	
        float f2 = (float)(0) / 32.0F;
        float f3 = (float)(16) / 32.0F;
        float f4 = (float)(0) / 32.0F;
        float f5 = (float)(16) / 32.0F;
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        int f=1;
        for (int i = 0; i < 2; ++i)
        {
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, 1f);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-8.0D, -8.0D, f*1D, (double)f2, (double)f4);
            tessellator.addVertexWithUV(8.0D, -8.0D, f*1D, (double)f3, (double)f4);
            tessellator.addVertexWithUV(8.0D, 8.0D, f*1D, (double)f3, (double)f5);
            tessellator.addVertexWithUV(-8.0D, 8.0D, f*1D, (double)f2, (double)f5);
            tessellator.draw();       
        }
        for (int i=0 ;i <2 ;i++){
        	GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        	for (int k=0;k<8;k++){
        		GL11.glNormal3f(0.0F, 0.0F, 1f);
        		tessellator.startDrawingQuads();
        		tessellator.addVertexWithUV(-1*(double)(8-k), -(8D), -1D, (double)(16+2*k)/32f, (double)0);
        		tessellator.addVertexWithUV(-1*(double)(8-k), -(8D), 1D, (double)(18+2*k)/32f, (double)0);
        		tessellator.addVertexWithUV(-1*(double)(8-k), (8D), 1D, (double)(18+2*k)/32f, (double)16/32f);
        		tessellator.addVertexWithUV(-1*(double)(8-k), (8D), -1D, (double)(16+2*k)/32f, (double)16/32f);
        		tessellator.draw();             		
        	}
        }
        for (int i=0 ;i <2 ;i++){
        	GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);        		
        	for (int k=0;k<8;k++){
        		GL11.glNormal3f(0.0F, 0.0F, 1f);
        		tessellator.startDrawingQuads();
        		tessellator.addVertexWithUV(-8D, -1*(double)(8-k), -1.0D, (double)0, (double)(16+2*k)/32D);
        		tessellator.addVertexWithUV(8D, -1*(double)(8-k), -1.0D, (double)16/32D, (double)(16+2*k)/32D);
        		tessellator.addVertexWithUV(8D, -1*(double)(8-k), 1.0D, (double)16/32D, (double)(18+2*k)/32D);
        		tessellator.addVertexWithUV(-8D, -1*(double)(8-k), 1.0D, (double)0, (double)(18+2*k)/32D);
        		tessellator.draw();             		
        	}		
    	}  
        GL11.glRotatef(270.0F, 0.0F, 0.5F, 0.0F);
        //GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
	}
}

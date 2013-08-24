package FrogCraft.Items.Railgun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderCoin extends Render
{
	
	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
    public void render(EntityCoin entity, double par2, double par4, double par6, float par8, float par9)
    {
    	for (int r=0;r<2;r++){
    		if (r==0)
    			func_110776_a(new ResourceLocation("frogcraft:textures/render/Coin.png"));
    		else
    			func_110776_a(new ResourceLocation("textures/entity/creeper/creeper_armor.png"));
    		
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        float f6 = 0.0F;
        float f7 = 0.15625F;
        float f8 = (float)(0) / 32.0F;
        float f9 = (float)(5) / 32.0F;
        float f10 = 0.01F;//0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float f11 = (float)7 - par9;

        if (f11 > 0.0F)
        {
            float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
            GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
        }

        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
        
        float f2 = (float)(0) / 32.0F;
        float f3 = (float)(16) / 32.0F;
        float f4 = (float)(0) / 32.0F;
        float f5 = (float)(16) / 32.0F;
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        int f=1;
        for (int i = 0; i < 2; ++i)
        {
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, 16f);
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
        		GL11.glNormal3f(0.0F, 0.0F, 16f);
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
        		GL11.glNormal3f(0.0F, 0.0F, 16f);
        		tessellator.startDrawingQuads();
        		tessellator.addVertexWithUV(-8D, -1*(double)(8-k), -1.0D, (double)0, (double)(16+2*k)/32D);
        		tessellator.addVertexWithUV(8D, -1*(double)(8-k), -1.0D, (double)16/32D, (double)(16+2*k)/32D);
        		tessellator.addVertexWithUV(8D, -1*(double)(8-k), 1.0D, (double)16/32D, (double)(18+2*k)/32D);
        		tessellator.addVertexWithUV(-8D, -1*(double)(8-k), 1.0D, (double)0, (double)(18+2*k)/32D);
        		tessellator.draw();             		
        	}		
    	}  
        
       

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    	}
        

    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.render((EntityCoin)par1Entity, par2, par4, par6, par8, par9);
    }
}

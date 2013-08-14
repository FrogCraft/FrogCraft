package FrogCraft.NEI;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import FrogCraft.Common.FluidManager;
import FrogCraft.Common.GuiLiquids;
import FrogCraft.Machines.ItemBlockMachines;

import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;
import static codechicken.core.gui.GuiDraw.*;


public class CondenseTowerRecipeHandler extends TemplateRecipeHandler{
	public CondenseTowerRecipeHandler(){
		codechicken.nei.api.API.registerRecipeHandler(this);
		codechicken.nei.api.API.registerUsageHandler(this);
	}
	
	@Override
	public void loadTransferRects(){
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(35, 23,24, 16), getRecipeId(), new Object[0]));
		
		//Add Machine Gui support
		ArrayList guis = new ArrayList();
		ArrayList transferRects2 = new ArrayList();
		guis.add(FrogCraft.Machines.GuiLiquidInjector.class);
		transferRects2.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(109, 30,127-114, 52-40), getRecipeId(), new Object[0]));
		TemplateRecipeHandler.RecipeTransferRectHandler.registerRectsToGuis(guis, transferRects2); 
	}
	
	//The string displayed on the top of the recipe window
	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("tile.Machines."+ItemBlockMachines.subNames[10]+".name");
	}

	//Recipe window texture
	@Override
	public String getGuiTexture() {
		return "frogcraft:textures/gui/Gui_CondenseTower.png";
	}
	
	@Override
	public int recipiesPerPage(){
		return 1;
	}
	
	//Return a custom ID for the Recipe GUI
	public String getRecipeId(){
		return "FrogCraft.CT";
	}
	
	@Override
    public void drawBackground(int recipe)
    {
    	super.drawBackground(recipe);
    	Cached_Recipe rec=(Cached_Recipe)arecipes.get(recipe);
    	
    	GuiLiquids.drawLiquidBar(9, 23, 16, 16, rec.id, 100);
    	if(rec.rec.length>0&&rec.rec[0]!=null)
    		GuiLiquids.drawLiquidBar(71, 23, 16, 16, rec.rec[0].fluidID, 100);
		if(rec.rec.length>1&&rec.rec[1]!=null)
			GuiLiquids.drawLiquidBar(89, 23, 16, 16,rec.rec[1].fluidID, 100);
		if(rec.rec.length>2&&rec.rec[2]!=null)
			GuiLiquids.drawLiquidBar(107, 23, 16, 16,rec.rec[2].fluidID, 100);	
		if(rec.rec.length>3&&rec.rec[3]!=null)
			GuiLiquids.drawLiquidBar(125, 23, 16, 16,rec.rec[3].fluidID, 100);	
		if(rec.rec.length>4&&rec.rec[4]!=null)
			GuiLiquids.drawLiquidBar(143, 23, 16, 16,rec.rec[4].fluidID, 100);	
    }
	
	@Override
	public void drawExtras(int recipe){
		Cached_Recipe rec=(Cached_Recipe)arecipes.get(recipe);
		drawString(StatCollector.translateToLocal("nei.euTotal")+":"+String.valueOf(rec.eu),28,80, 4210752,false);
		drawString("Per "+String.valueOf(rec.unit)+"mB",28,90, 4210752,false);	
		drawString(StatCollector.translateToLocal("nei.euTick")+":"+String.valueOf(rec.tick),28,100, 4210752,false);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		changeTexture(getGuiTexture());
		drawProgressBar(35, 23, 176, 0, 24, 47,30,0);
	}
	
	@Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
		if (outputId.equals(getRecipeId())) {
			for(int i:FrogCraft.Common.RecipeManager.condenseTowerRecipes.keySet())
				arecipes.add(new Cached_Recipe(i));
		}
		else 
			super.loadCraftingRecipes(outputId, results);
    }
	
	@Override
	public void loadCraftingRecipes(ItemStack result){
		for(int i:FrogCraft.Common.RecipeManager.condenseTowerRecipes.keySet()){
			Cached_Recipe rec=new Cached_Recipe(i);
			if (rec.contains(rec.products, result))
				this.arecipes.add(rec);
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient){
		for(int i:FrogCraft.Common.RecipeManager.condenseTowerRecipes.keySet()){
			Cached_Recipe rec=new Cached_Recipe(i);
			if (rec.contains(rec.resources, ingredient))
				this.arecipes.add(rec);
		}
	}
	
	
	//Custom class represents a cached recipe
	public class Cached_Recipe extends TemplateRecipeHandler.CachedRecipe{
		public ArrayList products= new ArrayList();
		public ArrayList resources= new ArrayList();

		public int eu,tick,unit,id;
		public FluidStack[] rec;
		public Integer[] info;
		
		public Cached_Recipe(int i){			
			id=i;
			rec=FrogCraft.Common.RecipeManager.condenseTowerRecipes.get(i);
			info=FrogCraft.Common.RecipeManager.condenseTowerRecipeInfo.get(i);
			this.resources.add(new PositionedStack(FluidManager.getFilledContainers(i), 9, 23));
			eu=info[2];
			tick=info[1];
			unit=info[0];
			if(rec.length>0&&rec[0]!=null)
				this.products.add(new PositionedStack(FluidManager.getFilledContainers(rec[0]), 71, 23));
			if(rec.length>1&&rec[1]!=null)
				this.products.add(new PositionedStack(FluidManager.getFilledContainers(rec[1]), 89, 23));
			if(rec.length>2&&rec[2]!=null)
				this.products.add(new PositionedStack(FluidManager.getFilledContainers(rec[2]), 107, 23));	
			if(rec.length>3&&rec[3]!=null)
				this.products.add(new PositionedStack(FluidManager.getFilledContainers(rec[3]), 125, 23));	
			if(rec.length>4&&rec[4]!=null)
				this.products.add(new PositionedStack(FluidManager.getFilledContainers(rec[4]), 143, 23));				
		}
		
		public ArrayList getIngredients()
		{
			return resources;
		}

		public PositionedStack getResult()
		{
			return null;
		}
		
		public ArrayList getOtherStacks() {
			return this.products;
		}
	}
}

package FrogCraft.NEI;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import FrogCraft.Common.GuiLiquids;
import FrogCraft.Common.LiquidIO;

import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CondenseTowerRecipeHandler extends TemplateRecipeHandler{
	public CondenseTowerRecipeHandler(){
		codechicken.nei.api.API.registerRecipeHandler(this);
		codechicken.nei.api.API.registerUsageHandler(this);
	}
	
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
		return FrogCraft.Machines.ItemBlockMachines.Machines_Names[10];
	}

	//Recipe window texture
	@Override
	public String getGuiTexture() {
		return "/mods/FrogCraft/textures/gui/Gui_CondenseTower.png";
	}
	
	@Override
	public int recipiesPerPage(){
		return 1;
	}
	
	//Return a custom ID for the Recipe GUI
	public String getRecipeId(){
		return "FrogCraft.CT";
	}
	
    public void drawBackground(GuiContainerManager gui, int recipe)
    {
    	super.drawBackground(gui, recipe);
    	Cached_Recipe rec=(Cached_Recipe)arecipes.get(recipe);
    	GuiLiquids.drawLiquidBar(9, 23, 16, 16, rec.rec[0], rec.rec[1], 100);
    	if (rec.rec[5]>0)
    		GuiLiquids.drawLiquidBar(71, 23, 16, 16, rec.rec[5], rec.rec[6], 100);
		if(rec.rec[8]>0)
			GuiLiquids.drawLiquidBar(89, 23, 16, 16,rec.rec[8],rec.rec[9], 100);
		if(rec.rec[11]>0)
			GuiLiquids.drawLiquidBar(107, 23, 16, 16,rec.rec[11],rec.rec[12], 100);	
		if(rec.rec[14]>0)
			GuiLiquids.drawLiquidBar(125, 23, 16, 16,rec.rec[14],rec.rec[15], 100);	
		if(rec.rec[17]>0)
			GuiLiquids.drawLiquidBar(143, 23, 16, 16,rec.rec[17],rec.rec[18], 100);	
    }
	
	public void drawExtras(GuiContainerManager gui, int recipe){
		Cached_Recipe rec=(Cached_Recipe)arecipes.get(recipe);
		gui.drawText(28,80,NEI_Config.euTotal+String.valueOf(rec.eu), 4210752,false);
		gui.drawText(28,90,"Per "+String.valueOf(rec.unit)+"mB", 4210752,false);	
		gui.drawText(28,100,NEI_Config.tick+String.valueOf(rec.tick), 4210752,false);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		gui.bindTexture(getGuiTexture());
		drawProgressBar(gui,35, 23, 176, 0, 24, 47,30,0);
	}
	
	@Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
		if (outputId.equals(getRecipeId())) {
			for(int i=0;i<FrogCraft.Common.RecipeManager.liquidInjectorRecipes.size();i++)
				arecipes.add(new Cached_Recipe(i));
		}
		else 
			super.loadCraftingRecipes(outputId, results);
    }
	
	@Override
	public void loadCraftingRecipes(ItemStack result){
		for(int i=0;i<FrogCraft.Common.RecipeManager.liquidInjectorRecipes.size();i++){
			Cached_Recipe rec=new Cached_Recipe(i);
			if (rec.contains(rec.products, result))
				this.arecipes.add(rec);
		}
	}
	
	public void loadUsageRecipes(ItemStack ingredient){
		for(int i=0;i<FrogCraft.Common.RecipeManager.liquidInjectorRecipes.size();i++){
			Cached_Recipe rec=new Cached_Recipe(i);
			if (rec.contains(rec.resources, ingredient))
				this.arecipes.add(rec);
		}
	}
	
	
	//Custom class represents a cached recipe
	public class Cached_Recipe extends TemplateRecipeHandler.CachedRecipe{
		public ArrayList products= new ArrayList();
		public ArrayList resources= new ArrayList();

		public int eu,tick,unit;
		public int[] rec;
		
		public Cached_Recipe(int i){			
			rec=FrogCraft.Common.RecipeManager.liquidInjectorRecipes.get(i);
			this.resources.add(new PositionedStack(LiquidIO.getFilledContainers(rec[0],rec[1]), 9, 23));
			eu=rec[3];
			tick=rec[4];
			unit=rec[2];
			if(rec[5]>0)
				this.products.add(new PositionedStack(LiquidIO.getFilledContainers(rec[5],rec[6]), 71, 23));
			if(rec[8]>0)
				this.products.add(new PositionedStack(LiquidIO.getFilledContainers(rec[8],rec[9]), 89, 23));
			if(rec[11]>0)
				this.products.add(new PositionedStack(LiquidIO.getFilledContainers(rec[11],rec[12]), 107, 23));	
			if(rec[14]>0)
				this.products.add(new PositionedStack(LiquidIO.getFilledContainers(rec[14],rec[15]), 125, 23));	
			if(rec[17]>0)
				this.products.add(new PositionedStack(LiquidIO.getFilledContainers(rec[17],rec[18]), 143, 23));				
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

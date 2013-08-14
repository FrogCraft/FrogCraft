package FrogCraft;

import java.util.HashSet;
import java.util.Set;

import FrogCraft.Machines.*;
import FrogCraft.Machines.IndustrialDevices.*;
import FrogCraft.Machines2.*;
import FrogCraft.Items.MobilePS.*;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CommonProxy implements IGuiHandler {

	@Override
    	public Object getServerGuiElement(int id, EntityPlayer player, World world,int x, int y, int z) {
        	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        	if (tileEntity instanceof TileEntityMobilePS)
        		return new ContainerMobilePS(player.inventory, (TileEntityMobilePS) tileEntity);        	
        	
        	if (tileEntity instanceof TileEntityIndustrialDevice)
        		return new ContainerIndustrialDevice(player.inventory, (TileEntityIndustrialDevice) tileEntity);
        	if (tileEntity instanceof TileEntityPneumaticCompressor)
        		return new ContainerPneumaticCompressor(player.inventory, (TileEntityPneumaticCompressor) tileEntity);
        	if (tileEntity instanceof TileEntityAirPump)
        		return new ContainerAirPump(player.inventory, (TileEntityAirPump) tileEntity);
        	if (tileEntity instanceof TileEntityHSU)
        		return new ContainerHSU(player.inventory, (TileEntityHSU) tileEntity);
        	if (tileEntity instanceof TileEntityLiquifier)
        		return new ContainerLiquifier(player.inventory, (TileEntityLiquifier) tileEntity);
        	if (tileEntity instanceof TileEntityLiquidInjector)
        		return new ContainerLiquidInjector(player.inventory, (TileEntityLiquidInjector) tileEntity);
        	if (tileEntity instanceof TileEntityThermalCracker)
        		return new ContainerThermalCracker(player.inventory, (TileEntityThermalCracker) tileEntity);
        	if (tileEntity instanceof TileEntityAdvanceChemicalReactor)
        		return new ContainerAdvanceChemicalReactor(player.inventory, (TileEntityAdvanceChemicalReactor) tileEntity);
        	
        	if (tileEntity instanceof TileEntityLiquidOutput)
        		return new ContainerLiquidOutput(player.inventory, (TileEntityLiquidOutput) tileEntity);
        	if (tileEntity instanceof TileEntityAutoWorkBench)
        		return new ContainerAutoWorkBench(player.inventory, (TileEntityAutoWorkBench) tileEntity);
        	
        	return null;
        }

        //returns an instance of the Gui you made earlier
        @Override
        public Object getClientGuiElement(int id, EntityPlayer player, World world,int x, int y, int z) {
        	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        	if (tileEntity instanceof TileEntityMobilePS)
        		return new GuiMobilePS(player.inventory, (TileEntityMobilePS) tileEntity);     
        	
        	if (tileEntity instanceof TileEntityIndustrialDevice)
        		return new GuiIndustrialDevice(player.inventory, (TileEntityIndustrialDevice) tileEntity);
        	if (tileEntity instanceof TileEntityPneumaticCompressor)
        		return new GuiPneumaticCompressor(player.inventory, (TileEntityPneumaticCompressor) tileEntity);
        	if (tileEntity instanceof TileEntityAirPump)
        		return new GuiAirPump(player.inventory, (TileEntityAirPump) tileEntity);
        	if (tileEntity instanceof TileEntityHSU)
        		return new GuiHSU(player.inventory, (TileEntityHSU) tileEntity);
        	if (tileEntity instanceof TileEntityLiquifier)
        		return new GuiLiquifier(player.inventory, (TileEntityLiquifier) tileEntity);
        	if (tileEntity instanceof TileEntityLiquidInjector)
        		return new GuiLiquidInjector(player.inventory, (TileEntityLiquidInjector) tileEntity);
        	if (tileEntity instanceof TileEntityThermalCracker)
        		return new GuiThermalCracker(player.inventory, (TileEntityThermalCracker) tileEntity);  
        	if (tileEntity instanceof TileEntityAdvanceChemicalReactor)
        		return new GuiAdvanceChemicalReactor(player.inventory, (TileEntityAdvanceChemicalReactor) tileEntity);
        	
        	if (tileEntity instanceof TileEntityLiquidOutput)
        		return new GuiLiquidOutput(player.inventory, (TileEntityLiquidOutput) tileEntity);
        	if (tileEntity instanceof TileEntityAutoWorkBench)
        		return new GuiAutoWorkBench(player.inventory, (TileEntityAutoWorkBench) tileEntity);
      
        	return null;
        }

    	public static Set<String> languages = new HashSet();

    	static {
    		languages.add("zh_CN");
    		languages.add("en_US");
    	}
    	
    	public void init() {
    		for (String lang : languages) {
    			LanguageRegistry.instance().loadLocalization(
    					"/assets/frogcraft/lang/" + lang + ".properties", lang, false);
    		}
    	}
        
        
    	public int addArmor(String armor) {return 0;}
        
        public void registerRenderInformation(){}     
        public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/){}
        public World getClientWorld(){return null;}
}
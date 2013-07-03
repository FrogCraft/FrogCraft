package FrogCraft.NEI;

import net.minecraftforge.common.Configuration;
import codechicken.nei.api.IConfigureNEI;

public class NEI_Config implements IConfigureNEI{
	public static String euTotal="Total Cosumed Eu:",
						 euTick="Required Eu/Tick:",
						 tick="Time:",
						 tickWithCatalyst="Time with catalyst:",
						 catalystRequird="Catalyst is Required!",
						 liquid="Liquid:";
	
	@Override
	public void loadConfig() {
		new AdvanceChemicalReactorRecipeHandler();
		new ThermalCrackerRecipeHandler();
		new CondenseTowerRecipeHandler();
	}

	@Override
	public String getName() {
		return "FrogCraft NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "0.99";
	}

}

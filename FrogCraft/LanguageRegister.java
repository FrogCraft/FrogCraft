package FrogCraft;

import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.util.StringTranslate;
import net.minecraftforge.common.Configuration;
import FrogCraft.Items.*;
import FrogCraft.Items.MobilePS.ItemBlock_MobilePS;
import FrogCraft.Items.Railgun.Item_Railgun;
import FrogCraft.Machines.ItemBlockMachines;
import FrogCraft.Machines2.ItemBlockMachines2;
import FrogCraft.Machines2.ACWindMill.BlockACWindMillCylinder;
import FrogCraft.Machines2.ACWindMill.Item_Fan;
import FrogCraft.NEI.NEI_Config;
import FrogCraft.Ore.ItemBlockOre;

public class LanguageRegister {
	public static void loadLanguage(Configuration lang,Boolean isClient){
		//NEI        
		if(isClient){
			NEI_Config.euTotal=LanguageRegister.getl(lang, "euTotal", "总耗电量:");
			NEI_Config.euTick=LanguageRegister.getl(lang, "euTick", "每Tick耗电量:");
			NEI_Config.tick=LanguageRegister.getl(lang, "tick", "所需时间:");
			NEI_Config.tickWithCatalyst=LanguageRegister.getl(lang, "tickWithCatalyst", "催化下的反应时间:");
			NEI_Config.catalystRequird=LanguageRegister.getl(lang, "catalystRequird", "这个反应需要催化剂!");
			NEI_Config.liquid=LanguageRegister.getl(lang, "Liquid", "输出液体:");
		}
		
		ItemBlockMachines.Machines_Names=new String[]{
				getl(lang,"PneumaticCompressor","气动压缩机"),
				getl(lang,"AirPump","工业气泵"),
				getl(lang,"IndustrialCompressor","工业压缩机"),
				getl(lang,"IndustrialMacerator","工业打粉机"),	
				getl(lang,"IndustrialExtractor","工业提取机"),
				getl(lang,"IndustrialFurnance","工业炉子"),
				getl(lang,"HSU","混合存电单元(HSU)"),
				getl(lang,"UHSU","终极混合存电单元(UHSU)"),	
				getl(lang,"EVT","超高压变压器"),
				getl(lang,"Liquifier","液化机"),
				getl(lang,"LiquidInjector","冷凝塔"),
				getl(lang,"ThermalCracker","热解裂机"),
				getl(lang,"AdvanceChemicalReactor","高级化学反应器")
		};
		ItemBlockMachines2.Machines2_Names=new String[]{
				getl(lang,"LiquidOutput","冷凝塔液体出口"),
				getl(lang,"CondenseTowerCylinder","冷凝塔壁"),
				getl(lang,"ACWindMillTop","学园都市风力发电机主体"),
				getl(lang,"ACWindMillBase","学园都市风力发电机底座"),
				getl(lang,"AutoWorkBench","自动工作台")
		};		
		ItemBlockOre.Ore_Names=new String[]{
				getl(lang,"oreCarnallite","光卤石"),
				getl(lang,"oreFluorapatite","氟磷灰石")
		};
		Item_Railgun.name=getl(lang,"Railgun_Name","超电磁炮");
		Item_Railgun.tooTired=getl(lang,"Railgun_TooTired","亲 你太累了 休息会儿再射吧");
		Item_Railgun.info=getl(lang,"Railgun_Info","吡哩吡哩~~~");
		Item_Fan.name=getl(lang,"Fan_Name", "学园都市风力发电机叶片");
		Item_IC2Coolant.Item_IC2CoolantNH3_60K.displayName=getl(lang,"IC2CoolantNH3_60K_Name", "60K氨冷却槽");
		Item_IC2Coolant.Item_IC2CoolantNH3_180K.displayName=getl(lang,"IC2CoolantNH3_180K_Name", "180K氨冷却槽");	
		Item_IC2Coolant.Item_IC2CoolantNH3_360K.displayName=getl(lang,"IC2CoolantNH3_360K_Name", "360K氨冷却槽");		
		ItemBlock_MobilePS.name=getl(lang,"MobilePS_Name", "移动电源");
		BlockACWindMillCylinder.BlockACWindMillCylinder_Name=getl(lang,"BlockACWindMillCylinder_Name", "学园都市风力发电机塔壁");


		
		//Gases
		applyToItem(lang,Item_Gases.itemsData,new String[]{"氧气","二氧化碳","氩气","氨气","一氧化氮","一氧化碳","氟气"});//,"氢氟酸","盐酸","氢溴酸","氢碘酸","巨毒的氢氰酸"});
		
		//Liquids
		applyToItem(lang,Item_Liquids.itemsData,new String[]{"液化空气","煤焦油","硝酸","苯","液态溴"});
		
		//Ingots
		applyToItem(lang,Item_Ingots.itemsData,new String[]{"钾单质","磷单质"});	
		
		//Dusts
		applyToItem(lang,Item_Dusts.itemsData,new String[]{"氧化钙","尿素","硝酸铵","硅酸钙","氢氧化钙","铝镁合金粉","光卤石粉","氯化钾","溴化镁","氟磷灰石粉","氟化钙"});
		
		//Miscs
		applyToItem(lang,Item_Miscs.itemsData,new String[]{"焦煤碳碎片","蜂窝煤","超电磁炮组件","金坷垃","电解器插件","高温加热器插件","氨合成专用催化器件"});
		
		//Cells
		applyToItem(lang,Item_Cells.itemsData,new String[]{"氨槽","煤焦油槽","氧气槽","液化空气槽","二氧化碳槽","氩槽","硝酸槽","一氧化氮槽","一氧化碳槽","苯槽","溴槽","氟槽"});
		
		HashMap<String,String[]> achInfo=new HashMap<String,String[]>();
		achInfo.put("get_railgun", new String[]{"Only my railgun!","来一炮?"});
		achInfo.put("get_EVT", new String[]{"超高压变压器","降压到2048"});
		achInfo.put("killed_by_Potassium", new String[]{"被钾炸死","不作死就不会死,永远不要把碱金属放到水里!"});
		achInfo.put("gaspump", new String[]{"空气的力量","一股无形的力量"});
		achInfo.put("pneumaticCompressor", new String[]{"工业TNT 886~","制作衣板再也不需要工业TNT啦"});	
		achInfo.put("liquifier", new String[]{"无尽的原材料","乃学会了从空气中获得原材料"});		
		achInfo.put("hsu", new String[]{"更大的MFSU","可以存储100000000eu!"});	
		achInfo.put("uhsu", new String[]{"变态的MFSU","可以存储1000000000eu!"});	
		achInfo.put("goldclod", new String[]{"妈妈的","金坷拉是我的!"});	
		achInfo.put("get_ACR", new String[]{"化学之路","能出现嘛有趣的反应呢?"});	
		achInfo.put("condensetower", new String[]{"分立他们?","似乎还缺点什么"});
		achInfo.put("condensetower2", new String[]{"分立他们.","乃学会了如何分离液体和固体的混合物"});		
		for (String name:achInfo.keySet()){
			setAchInfo(name,getl(lang,name+"_Name",achInfo.get(name)[0]),getl(lang,name+"_Desc",achInfo.get(name)[1]));
		}
	}
	
	
	static void applyToItem(Configuration lang,List<String[]> list,String[] langStr){
		for (int i=0;i<list.size();i++){
			list.set(i,new String[]{list.get(i)[0],getl(lang,list.get(i)[0],langStr[i]),list.get(i)[2]});
		}		
	}
	
	public static void setAchInfo(String name,String title,String info){
		String l=StringTranslate.getInstance().currentLanguage;
		LanguageRegistry.instance().addStringLocalization("achievement." + name, StringTranslate.getInstance().currentLanguage, title);
		LanguageRegistry.instance().addStringLocalization("achievement." + name +".desc", StringTranslate.getInstance().currentLanguage, info);		
	}
	
	public static String getl(Configuration lang,String key,String _default){
		return lang.get("Languages", key, _default).getString();
	}
}

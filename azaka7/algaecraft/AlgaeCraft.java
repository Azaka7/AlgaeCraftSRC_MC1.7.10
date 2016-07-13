package azaka7.algaecraft;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;
import azaka7.algaecraft.client.ACClientEventHandler;
import azaka7.algaecraft.client.ACKeyBindingHandler;
import azaka7.algaecraft.client.ClientProxy;
import azaka7.algaecraft.common.ACConfiguration;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.CommonProxy;
import azaka7.algaecraft.common.TabAlgaeCraft;
import azaka7.algaecraft.common.blocks.ACBlocks;
import azaka7.algaecraft.common.commands.CommandExportStructure;
import azaka7.algaecraft.common.commands.CommandImportStructure;
import azaka7.algaecraft.common.entity.ACEntities;
import azaka7.algaecraft.common.handlers.ACCraftingHandler;
import azaka7.algaecraft.common.handlers.ACInterModHandler;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler;
import azaka7.algaecraft.common.handlers.ACTickHandler;
import azaka7.algaecraft.common.handlers.ACWorldGenHandler;
import azaka7.algaecraft.common.items.ACItems;
import azaka7.algaecraft.common.structures.ACStructures;
import azaka7.algaecraft.common.structures.StructureHandler;
import azaka7.algaecraft.common.world.ACBiomes;
import azaka7.algaecraft.network.ACPacketDispatcher;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = AlgaeCraft.MODID, name = "AlgaeCraft", version = AlgaeCraft.VERSION)
public class AlgaeCraft {
	public static final String MODID = "algaecraft";
	public static final String VERSION = "1.5.8";
	public static final AlgaeCraft INSTANCE = new AlgaeCraft();
	
	public static final CreativeTabs modTab = new TabAlgaeCraft();
	
	private static boolean thermal_expansion;
	private static boolean thaumcraft;
	
	private static CommonProxy proxy;
	
	@EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
		
		thermal_expansion = Loader.isModLoaded("ThermalExpansion");
		if(thermal_expansion){
			System.out.println("[AlgaeCraft] Thermal Expansion mod has been detected by AlgaeCraft.");
		}
		thaumcraft = Loader.isModLoaded("Thaumcraft");
		if(thaumcraft){
			System.out.println("[AlgaeCraft] Thaumcraft mod has been detected by AlgaeCraft.");
		}
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		ACConfiguration.setup(config);
		
		ACGameData.INSTANCE.configure();
		
		ACBiomes.initBiomes();
		ACWorldGenHandler.register();
		ACBlocks.registerPreItemBlocks();
		ACItems.registerItems();
		ACBlocks.registerBlocks();
		
		proxy = event.getSide()==Side.CLIENT ? new ClientProxy() : new CommonProxy();
		if(event.getSide()==Side.CLIENT){
			ACKeyBindingHandler.INSTANCE.initialize(event);
			FMLCommonHandler.instance().bus().register(ACKeyBindingHandler.INSTANCE);
			ACClientEventHandler.INSTANCE.initialize();
		}
		ACTickHandler.INSTANCE.initialize();
		proxy.registerCommon();
		proxy.registerRenders();
		
		ACSwimmingHandler.registerKnownFactors();
		
    }
	
	@EventHandler
    public void init(FMLInitializationEvent event)
    {
		if(thaumcraft()){
			ACInterModHandler.registerThumcraftCompatability();
		}
		
		ACPacketDispatcher.registerPackets();
		//NETWORK.initialise();
		//NETWORK.registerPacket(KeyBindingPacket.class);
    }
	
	@EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {
		
		ACEntities.registerEntities();
		
		//NETWORK.postInitialise();
		
		ACCraftingHandler.INSTANCE.registerRecipes();
		ACCraftingHandler.INSTANCE.registerSmelting();
		
		ACStructures.init();
		ACConfiguration.save();
    }
	
	public static boolean thermalExpansion(){
		thermal_expansion = Loader.isModLoaded("ThermalExpansion") && ACGameData.detectThermalExpansion;
		return thermal_expansion;
	}
	
	public static boolean thaumcraft(){
		thaumcraft = Loader.isModLoaded("Thaumcraft") && ACGameData.detectThaumcraft;
		return thaumcraft;
	}
	
	public static CommonProxy getProxy(){
		return proxy;
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandExportStructure());
		event.registerServerCommand(new CommandImportStructure());
	}
}

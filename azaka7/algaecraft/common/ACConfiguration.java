package azaka7.algaecraft.common;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ACConfiguration {

	private static Configuration config;
	private static String section;
	
	public static void setup(Configuration cfg){
		config = cfg;
	}
	
	public static void startSection(String s){
		section = s;
	}
	
	public static void addComment(String s){
		config.addCustomCategoryComment(section, s);
	}
	
	public static int getInt(String s, int def, String... c){
		Property ret = config.get(section, s, def);
		if(c != null && c.length > 0){
		ret.comment = c[0];}
		return ret.getInt();
	}
	
	public static double getDouble(String s, double def, String... c){
		Property ret = config.get(section, s, def);
		if(c != null && c.length > 0){
		ret.comment = c[0];}
		return ret.getDouble();
	}
	
	public static boolean getBool(String s, boolean def, String... c){
		Property ret = config.get(section, s, def);
		if(c != null && c.length > 0){
		ret.comment = c[0];}
		return ret.getBoolean();
	}
	
	public static String getString(String s, String def, String... c){
		Property ret = config.get(section, s, def);
		if(c != null && c.length > 0){
		ret.comment = c[0];}
		return ret.getString();
	}
	
	public static int[] getIntArray(String s, int[] def, String... c){
		Property ret = config.get(section, s, def);
		if(c != null && c.length > 0){
		ret.comment = c[0];}
		return ret.getIntList();
	}
	
	public static double[] getDoubleArray(String s, double[] def, String... c){
		Property ret = config.get(section, s, def);
		if(c != null && c.length > 0){
		ret.comment = c[0];}
		return ret.getDoubleList();
	}
	
	public static boolean[] getBoolArray(String s, boolean[] def, String... c){
		Property ret = config.get(section, s, def);
		if(c != null && c.length > 0){
		ret.comment = c[0];}
		return ret.getBooleanList();
	}
	
	public static String[] getStringArray(String s, String[] def, String... c){
		Property ret = config.get(section, s, def);
		if(c != null && c.length > 0){
		ret.comment = c[0];}
		return ret.getStringList();
	}
	
	public static void endSection(){
		section = "General";
	}

	public static void save() {
		config.save();
	}

}

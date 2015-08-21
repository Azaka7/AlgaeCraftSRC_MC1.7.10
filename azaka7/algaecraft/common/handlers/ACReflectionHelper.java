package azaka7.algaecraft.common.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ACReflectionHelper {
	
	public static void setFinalStatic(Field field, Object newValue) throws Exception {
	      field.setAccessible(true);
	      
	      Field modifiersField = Field.class.getDeclaredField("modifiers");
	      modifiersField.setAccessible(true);
	      modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	      
	      field.set(null, newValue);
	}
	
	public static String translate(String s){
		if(s == "maxNumberOfCreature"){
			return "field_75606_e";
		}
		return s;
	}
}

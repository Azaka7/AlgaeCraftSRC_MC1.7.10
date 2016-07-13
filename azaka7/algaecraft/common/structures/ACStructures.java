package azaka7.algaecraft.common.structures;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class ACStructures {
	
	public static final Structure tarKiln = StructureLoader.INSTANCE.loadStructure("kiln");// = StructureTarKiln.createStructure();
	public static final Structure smallShip = StructureLoader.INSTANCE.loadStructure("smallship");
	public static final Structure smallShipWreck = StructureLoader.INSTANCE.loadStructure("smallship_wreck");
	public static final Structure largeShip = StructureLoader.INSTANCE.loadStructure("largeship");
	public static final Structure largeShipWreck = StructureLoader.INSTANCE.loadStructure("largeship_wreck");
	public static final Structure egyptShip = StructureLoader.INSTANCE.loadStructure("egyptship");
	public static final Structure egyptShipWreck = StructureLoader.INSTANCE.loadStructure("egyptship_wreck");
	public static final Structure laboratoryBasic = StructureLoader.INSTANCE.loadStructure("basiclab");
	
	private static HashMap<String,Structure> structureMap = new HashMap<String,Structure>();
	
	public static boolean isStructureLoaded(String file){
		if(structureMap == null){
			structureMap = new HashMap<String,Structure>();
		}
		if(file == null){file = "";}
		boolean b = structureMap.containsKey(file);
		////System.out.println("Is Structure "+file+" Loaded: "+b);
		return b;
	}
	
	public static Structure loadStructure(String file){
		return structureMap.get(file);
	}

	public static void saveStructure(String file, Structure structure) {
		structureMap.put(file, structure);
	}
	
	public static void init(){}
	
}

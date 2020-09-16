package com.test.thread.Social91Thread;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.thread.model.Component;
import com.test.thread.model.CycleComponentLibrary;
import com.test.thread.model.ProductDetail;

public class CycleCostLibrary {
	
	private static Map<String, List<ProductDetail>> mapLib;
	
	/**
	 * Method to load Cost of each component to a library which is used later to calculate cost of a cycle
	 * @param fileName
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void loadCycleLibrary(String fileName) throws JsonParseException, JsonMappingException, IOException {
		if(mapLib == null) {
			synchronized(CycleCostLibrary.class) {
				if(mapLib == null) {
					ObjectMapper mapper = new ObjectMapper();
					mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					mapper.registerModule(new Jdk8Module());
					mapper.registerModule(new JavaTimeModule());
					/**
					 * Read object from file
					 */
					CycleComponentLibrary value = null;
					value = mapper.readValue(new File(fileName), CycleComponentLibrary.class);
					Map<String, List<ProductDetail>> cycleMap = new ConcurrentHashMap<>();
					for (Component m : value.getCompList()) {
						cycleMap.put(m.getName() + ":" + m.getType(), m.getProductDetails());
					}
					mapLib = cycleMap;
				}
			}
		}
	}
	
	/**
	 * Method provides the costing list for each component which is date range specific
	 * @param componentName
	 * @return
	 */
	public static List<ProductDetail> getProductList(String componentName) {
		if(mapLib == null) {
			throw new RuntimeException("Cycle Library cost is not loaded before calling this utilization");
		}
		return mapLib.get(componentName);
	}

}

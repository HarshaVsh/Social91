package com.test.thread.Social91Thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.thread.model.Cycle;
import com.test.thread.model.CycleInStock;
import com.test.thread.model.ProductDetail;

public class App {

	private Map<String, List<ProductDetail>> cycleMap;
	private final static int threadSize = 10;
	private static Logger logger = Logger.getLogger(App.class.getName());
	private static BufferedWriter writer;

	public Map<String, List<ProductDetail>> getCycleMap() {
		return cycleMap;
	}

	/**
	 * @param args
	 * arg[0]: file name of the cycle cost library, which contains date specific cost of each cycle component
	 * arg[1]: file name of JSON file whicch contains the cycles for which the COST has to be calculated
	 * arg[2]: file name for output result file to where the cost calculated for each file has to be written
	 */
	public static void main(String[] args) {
		App app = new App();
		try {
			String cycleCostLibfile = args[0];//"cycle_component_library.json";
			String cycleInStockfile =  args[1];//"cycle_in_stock.json";
			String outputFileName =  args[2];//"cycle_output_stock.txt";
			writer = new BufferedWriter(new FileWriter(outputFileName), 600);
			CycleCostLibrary.loadCycleLibrary(cycleCostLibfile);

			List<Cycle> cycleStockList = app.readCycleInStock(cycleInStockfile);

			ExecutorService exService = new ThreadPoolExecutor(threadSize, threadSize, 1000, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<>(threadSize));

			int startIndex = 0;
			int endIndex = 0;
			// Full this with tasks
			for (int i = 0; i < 10; i++) {
				startIndex = i * 100;
				endIndex = startIndex + 100;
				List<Cycle> subList = cycleStockList.subList(startIndex, endIndex);
				CycleCostCalculator t = new CycleCostCalculator(subList);
				exService.submit(t);
			}
			exService.shutdown();
			exService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			writer.close();
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.warning("Exception: Pleaase verify the inputs provided" + e);
		} catch (Exception e) {
			logger.warning("Exception: " + e);
		}
	}

	/**
	 * Method reads the data of each cycle from a JSON file to calculate it's cost
	 * Each cycle is added to list and then split across threads
	 * @param cycleInStockfile
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private List<Cycle> readCycleInStock(String cycleInStockfile)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		/**
		 * Read object from file
		 */
		CycleInStock stock = null;
		stock = mapper.readValue(new File(cycleInStockfile), CycleInStock.class);
		return stock.getCycleList();
	}

	/**
	 * Method writes the result of cycle cost to output file
	 * @param str
	 * @throws IOException
	 */
	public static void addCyccleCostResult(String str) throws IOException {
		synchronized (writer) {
			writer.write(str);
			writer.write("\n");
		}
	}

	/**
	 * This method is called by each thread before its terminated to flush all the data
	 * @throws IOException
	 */
	public static void flush() throws IOException {
		writer.flush();
	}
}

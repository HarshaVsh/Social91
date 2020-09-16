package com.test.thread.Social91Thread;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.thread.model.Cycle;
import com.test.thread.model.CycleComponent;
import com.test.thread.model.CycleInStock;

public class GenerateData {

	public static void main(String[] args) throws JsonProcessingException {
		Cycle cycle = new Cycle();
		List<CycleComponent> frameLst = new ArrayList<CycleComponent>();
		List<CycleComponent> handleLst = new ArrayList<CycleComponent>();
		List<CycleComponent> seatLst = new ArrayList<CycleComponent>();
		List<CycleComponent> wheelLst = new ArrayList<CycleComponent>();
		List<CycleComponent> chainLst = new ArrayList<CycleComponent>();

		CycleComponent monoFrame2017 = new CycleComponent("Monocoque", "frame", LocalDate.of(2017, 10, 7));
		CycleComponent recubframe2017 = new CycleComponent("Recumbent", "frame", LocalDate.of(2017, 10, 7));
		CycleComponent trussframe2017 = new CycleComponent("Truss", "frame", LocalDate.of(2017, 10, 7));
		CycleComponent monoFrame2020 = new CycleComponent("Monocoque", "frame", LocalDate.of(2020, 10, 7));
		CycleComponent recubframe2020 = new CycleComponent("Recumbent", "frame", LocalDate.of(2020, 10, 7));
		CycleComponent trussframe2020 = new CycleComponent("Truss", "frame", LocalDate.of(2020, 10, 7));
		frameLst.add(monoFrame2017);
		frameLst.add(recubframe2017);
		frameLst.add(trussframe2017);
		frameLst.add(monoFrame2020);
		frameLst.add(recubframe2020);
		frameLst.add(trussframe2020);


		CycleComponent riserHandle2016 = new CycleComponent("Riser", "handle", LocalDate.of(2016, 11, 7));
		CycleComponent cruiserHandle2017 = new CycleComponent("Cruiser", "handle", LocalDate.of(2017, 12, 7));
		CycleComponent bullhornHandle2016 = new CycleComponent("Bullhorn", "handle", LocalDate.of(2016, 10, 7));
		CycleComponent riserHandle201611 = new CycleComponent("Riser", "handle", LocalDate.of(2016, 6, 7));
		CycleComponent cruiserHandle2020 = new CycleComponent("Cruiser", "handle", LocalDate.of(2020, 7, 7));
		CycleComponent bullhornHandle2017 = new CycleComponent("Bullhorn", "handle", LocalDate.of(2017, 1, 7));
		handleLst.add(riserHandle2016);
		handleLst.add(cruiserHandle2017);
		handleLst.add(bullhornHandle2016);
		handleLst.add(cruiserHandle2020);
		handleLst.add(riserHandle201611);
		handleLst.add(bullhornHandle2017);


		CycleComponent tubelessWheel201611 = new CycleComponent("Tubeless", "wheel", LocalDate.of(2016, 11, 7));
		CycleComponent bearingswheel2020 = new CycleComponent("Bearings", "wheel", LocalDate.of(2020, 7, 7));
		CycleComponent lacingWheel2017 = new CycleComponent("Lacing", "wheel", LocalDate.of(2017, 1, 7));
		wheelLst.add(tubelessWheel201611);
		wheelLst.add(bearingswheel2020);
		wheelLst.add(lacingWheel2017);

		CycleComponent seating201611 = new CycleComponent("Suspension", "seating", LocalDate.of(2016, 11, 7));
		CycleComponent seating2020 = new CycleComponent("Cover", "seating", LocalDate.of(2020, 7, 7));
		CycleComponent seating2017 = new CycleComponent("Rails", "seating", LocalDate.of(2017, 1, 7));
		CycleComponent seating201711 = new CycleComponent("Suspension", "seating", LocalDate.of(2017, 11, 7));
		CycleComponent seating2016 = new CycleComponent("Cover", "seating", LocalDate.of(2016, 7, 7));
		CycleComponent seating20201 = new CycleComponent("Rails", "seating", LocalDate.of(2020, 1, 7));
		seatLst.add(seating201611);
		seatLst.add(seating2020);
		seatLst.add(seating2017);
		seatLst.add(seating2016);
		seatLst.add(seating201711);
		seatLst.add(seating20201);

		CycleComponent chain201611 = new CycleComponent("SRAM", "chain", LocalDate.of(2016, 11, 7));
		CycleComponent chain2020 = new CycleComponent("Campagnolo", "chain", LocalDate.of(2020, 7, 7));
		CycleComponent chain2017 = new CycleComponent("Shimano", "chain", LocalDate.of(2017, 1, 7));
		chainLst.add(chain201611);
		chainLst.add(chain2020);
		chainLst.add(chain2017);

		Map<String, List<CycleComponent>> map = new HashMap<>();
		map.put("frame", frameLst);
		map.put("handle", handleLst);
		map.put("wheel", wheelLst);
		map.put("seating", seatLst);
		map.put("chain", chainLst);

		List<Cycle> cycleLst = new ArrayList<>();
		for(int i = 1; i <= 1000; i++) {
			cycle = new Cycle();
			cycle.setCycleUniqueId(i);
			for(int j = 0; j < map.size(); j++) {
				if(j == 0) {
					cycle.setFrame(map.get("frame").get(i % map.get("frame").size()));
				} else if (j == 1) {
					cycle.setHandle(map.get("handle").get(i % map.get("handle").size()));
				} else if (j == 2) {
					cycle.setWheel(map.get("wheel").get(i % map.get("wheel").size()));
				} else if (j == 3) {
					cycle.setSeat(map.get("seating").get(i % map.get("seating").size()));
				} else if (j == 4) {
					cycle.setChain(map.get("chain").get(i % map.get("chain").size()));
				}
			}

			cycleLst.add(cycle);
		}
		CycleInStock cylcleStock = new CycleInStock();
		cylcleStock.setCycleList(cycleLst);
		ObjectMapper objectMap = new ObjectMapper();
		objectMap.registerModule(new Jdk8Module());
		objectMap.registerModule(new JavaTimeModule());
		String str = objectMap.writeValueAsString(cylcleStock);
		System.out.println("List: "+str);

	}

}

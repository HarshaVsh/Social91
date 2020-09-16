package com.test.thread.Social91Thread;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.test.thread.model.Cycle;
import com.test.thread.model.CycleComponent;
import com.test.thread.model.ProductDetail;

public class CycleCostCalculator implements Runnable {
	
	private static Logger logger = Logger.getLogger(App.class.getName());
	private List<Cycle> cycleStockList;
 
    public CycleCostCalculator( List<Cycle> cycleStockList) {
        this.cycleStockList = cycleStockList;
    }


	@Override
	public void run() {
		try {
			for(Cycle cycle: cycleStockList) {
				int chainCost = getIndividualCompCost(cycle.getChain());
				int frameCost = getIndividualCompCost(cycle.getFrame());
				int seatCost = getIndividualCompCost(cycle.getSeat());
				int handleCost = getIndividualCompCost(cycle.getHandle());
				int wheelCost = getIndividualCompCost(cycle.getWheel());
				String str = "Cycle Unique Id: "+cycle.getCycleUniqueId();
				str = str + "	" + "Cycle Total Cost: "+ (chainCost + frameCost + seatCost + handleCost + wheelCost);
				str = str + "	" + "Frame Cost: "+ frameCost;
				str = str + "	" + "Handle Cost: "+ handleCost;
				str = str + "	" + "Wheel Cost: "+ wheelCost;
				str = str + "	" + "Seat Cost: "+ seatCost;
				str = str + "	" + "Chain Cost: "+ chainCost;
				App.addCyccleCostResult(str);
			}
			App.flush();
		} catch (Exception e) {
			logger.warning("Exception; while executing thread "+Thread.currentThread().getId()+ ", Exception Messgae: "+e);
		}
		
	}

	public int getIndividualCompCost(CycleComponent cycleComponent) throws JsonParseException, JsonMappingException, IOException {
		String chainNameType = cycleComponent.getName() + ":" + cycleComponent.getType();
		for(ProductDetail product: CycleCostLibrary.getProductList(chainNameType)) {
			if(product.getStartDate().isBefore(cycleComponent.getManufactureDate()) && product.getEndDate().isAfter(cycleComponent.getManufactureDate())) {
				return product.getPrice();
			}
		}
		return 100;
	}

}

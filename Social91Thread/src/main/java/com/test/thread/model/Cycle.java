package com.test.thread.model;

public class Cycle {
	
	private int cycleUniqueId;
	private CycleComponent frame;
	private CycleComponent handle;
	private CycleComponent seat;
	private CycleComponent wheel;
	private CycleComponent chain;
	
	public CycleComponent getFrame() {
		return frame;
	}
	public void setFrame(CycleComponent frame) {
		this.frame = frame;
	}
	public CycleComponent getHandle() {
		return handle;
	}
	public void setHandle(CycleComponent handle) {
		this.handle = handle;
	}
	public CycleComponent getSeat() {
		return seat;
	}
	public void setSeat(CycleComponent seat) {
		this.seat = seat;
	}
	public CycleComponent getWheel() {
		return wheel;
	}
	public void setWheel(CycleComponent wheel) {
		this.wheel = wheel;
	}
	public CycleComponent getChain() {
		return chain;
	}
	public void setChain(CycleComponent chain) {
		this.chain = chain;
	}
	
	@Override
	public String toString() {
		return "Cycle Identification: "+cycleUniqueId+" \n [frame=" + frame + ", handle=" + handle + ", seat=" + seat + ", wheel=" + wheel + ", chain="
				+ chain + "]";
	}
	public int getCycleUniqueId() {
		return cycleUniqueId;
	}
	public void setCycleUniqueId(int cycleUniqueId) {
		this.cycleUniqueId = cycleUniqueId;
	}

}

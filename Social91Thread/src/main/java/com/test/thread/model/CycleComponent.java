package com.test.thread.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class CycleComponent {
	
	private String name;
	private String type;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate manufactureDate;
	
	public CycleComponent() {
		
	}
	
	
	public CycleComponent(String name, String type, LocalDate manufactureDate) {
		super();
		this.name = name;
		this.type = type;
		this.manufactureDate = manufactureDate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LocalDate getManufactureDate() {
		return manufactureDate;
	}
	public void setManufactureDate(LocalDate manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	
	@Override
	public String toString() {
		return "CycleComponent [name=" + name + ", type=" + type + ", manufactureDate=" + manufactureDate + "]";
	}

}

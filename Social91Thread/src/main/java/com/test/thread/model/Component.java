package com.test.thread.model;

import java.util.List;

public class Component {
	
	private String name;
	private String type;
	private List<ProductDetail> productDetails;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ProductDetail> getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(List<ProductDetail> productDetails) {
		this.productDetails = productDetails;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}

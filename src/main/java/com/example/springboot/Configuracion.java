
package com.example.springboot;

import java.util.List;

public class Configuracion {
	
	private String id, name, address;
	private Address tagged_addresses;
	private List<String> tags;
	private HealthCheck check;
	private Integer port;
		
	public HealthCheck getCheck() {
		return check;
	}

	public void setCheck(HealthCheck check) {
		this.check = check;
	}

	public List<String> getTags() {
		return tags;
	}
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getPort() {
		return port;
	}
	
	public void setPort(Integer port) {
		this.port = port;
	}
}

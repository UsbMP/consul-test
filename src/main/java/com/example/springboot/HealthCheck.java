package com.example.springboot;

import java.util.List;

public class HealthCheck {

	private String name, interval, status;
	private List<String> args;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInterval() {
		return interval;
	}
	
	public void setInterval(String interval) {
		this.interval = interval;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<String> getArgs() {
		return args;
	}
	
	public void setArgs(List<String> args) {
		this.args = args;
	}
}

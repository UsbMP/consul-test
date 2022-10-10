package com.example.springboot;

public class Address {
	
	private NetType LAN, WAN;

	public NetType getLAN() {
		return LAN;
	}

	public void setLAN(NetType lAN) {
		LAN = lAN;
	}

	public NetType getWAN() {
		return WAN;
	}

	public void setWAN(NetType wAN) {
		WAN = wAN;
	}
}

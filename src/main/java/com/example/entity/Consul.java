package com.example.entity;

import java.util.List;

public class Consul {

	private String Path, id, token, search, description, expirationttl, expirationtime;
	private List<Policies> policies;
	private Boolean local;

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getLocal() {
		return local;
	}

	public void setLocal(Boolean local) {
		this.local = local;
	}

	public List<Policies> getPolicies() {
		return policies;
	}

	public void setPolicies(List<Policies> policies) {
		this.policies = policies;
	}

	public String getexpirationTTL() {
		return expirationttl;
	}

	public void setexpirationTTL(String expirationTTL) {
		this.expirationttl = expirationTTL;
	}

	public String getexpirationTime() {
		return expirationtime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationtime = expirationTime;
	}
	
	public String toString() {
		return "-------------------------------------------\nPath: " + Path + "\nid: " + id + "\ntoken: " + token + "\nsearch: " +  search + "\ndescription: " +  description + "\nttl: " +  expirationttl + "\ntimeTTL: " +  expirationtime;
	}
}

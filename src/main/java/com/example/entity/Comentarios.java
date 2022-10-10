package com.example.entity;

public class Comentarios {

	private Integer PostID, ID;
	private String name, email, body;
	
	public Integer getPostID() {
		return PostID;
	}
	
	public void setPostID(Integer postID) {
		PostID = postID;
	}
	
	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer iD) {
		ID = iD;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
}

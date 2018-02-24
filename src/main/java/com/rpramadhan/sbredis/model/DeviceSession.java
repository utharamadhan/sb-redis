package com.rpramadhan.sbredis.model;

import java.io.Serializable;

public class DeviceSession implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7347626381301529558L;

	private Long id;
	
	private String token;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
}

package com.rpramadhan.sbredis.service;

import java.util.List;

import com.rpramadhan.sbredis.model.DeviceSession;

public interface IDeviceSessionRepository {
	
	public List<DeviceSession> findAll();
	
	public void add(DeviceSession deviceSession);
	
	public void delete(Long id);
	
	public DeviceSession findById(Long id);

}

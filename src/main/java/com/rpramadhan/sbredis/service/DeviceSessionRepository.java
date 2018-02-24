package com.rpramadhan.sbredis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpramadhan.sbredis.model.DeviceSession;

@Repository
public class DeviceSessionRepository implements IDeviceSessionRepository {
	
	private final static String KEY = "device-session";
	
	private RedisTemplate<String, Object> redisTemplate;
	
	private HashOperations hashOperations;
	
	@Autowired
    public DeviceSessionRepository(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }
	
	@PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

	@Override
	public List<DeviceSession> findAll() {
		return convertToList(hashOperations.entries(KEY));
	}
	
	private List<DeviceSession> convertToList(Map<Object, Object> resultMap) {
		List<DeviceSession> list = new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			for (Object result : resultMap.values()) {
				list.add(mapper.readValue(result.toString(), DeviceSession.class));
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	@Override
	public void add(DeviceSession deviceSession) {
		try {
			hashOperations.put(KEY, deviceSession.getId(), new ObjectMapper().writeValueAsString(deviceSession));	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		hashOperations.delete(KEY, id);
	}

	@Override
	public DeviceSession findById(Long id) {
		try {
			String deviceSessionStr = hashOperations.get(KEY, id).toString();
			return new ObjectMapper().readValue(deviceSessionStr, DeviceSession.class);	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}

package com.rpramadhan.sbredis.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rpramadhan.sbredis.model.DeviceSession;
import com.rpramadhan.sbredis.model.Response;
import com.rpramadhan.sbredis.service.IDeviceSessionRepository;

@RestController
public class RedisController {
	
	@Autowired
	private IDeviceSessionRepository repository;
	
	@RequestMapping(value = "/get/{id}", method=RequestMethod.GET)
	public ResponseEntity<Response> findById(@PathVariable("id") Long id) {
		DeviceSession session = repository.findById(id);
		Response response = new Response();
		if (session != null) {
			response.setResponseCode("00");
			response.setResponseDesc("success");
			response.setResult(session);
			return ResponseEntity.ok().body(response);
		} else {
			response.setResponseCode("04");
			response.setResponseDesc("not found");
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@RequestMapping(value = "/getAll", method=RequestMethod.GET)
	public ResponseEntity<Response> post() {
		List<DeviceSession> sessions = repository.findAll();
		Response response = new Response();
		if (!sessions.isEmpty()) {
			response.setResponseCode("00");
			response.setResponseDesc("success");
			response.setResult(sessions);
			return ResponseEntity.ok().body(response);
		} else {
			response.setResponseCode("04");
			response.setResponseDesc("not found");
			return ResponseEntity.badRequest().body(response);	
		}
	}
	
	@RequestMapping(value = "/registerSession", method=RequestMethod.POST)
	public ResponseEntity<Response> put(@RequestBody DeviceSession session) {
		repository.add(generateToken(session));
		Response response = new Response();
		response.setResponseCode("00");
		response.setResponseDesc("success");
		response.setResult(session);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@RequestMapping(value = "/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Response> delete(@PathVariable(value="id") Long id) {
		repository.delete(id);
		Response response = new Response();
		response.setResponseCode("00");
		response.setResponseDesc("success");
		return ResponseEntity.ok().body(response);
	}
	
	private DeviceSession generateToken(DeviceSession session) {
		session.setToken(UUID.randomUUID().toString());
		return session;
	}
	
}

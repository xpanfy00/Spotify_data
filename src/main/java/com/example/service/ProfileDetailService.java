package com.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class ProfileDetailService {

	private final RestTemplate restTemplate;
	private static final String URL = "https://api.spotify.com/v1/me";

	public LinkedHashMap getUser(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<String> entity = new HttpEntity<>("paramters", headers);

		ResponseEntity<Object> response = restTemplate.exchange(URL, HttpMethod.GET, entity, Object.class);
		LinkedHashMap result = (LinkedHashMap) response.getBody();
		return result;
	}

	public String getUsername(String token) {
		LinkedHashMap user = getUser(token);
		return (String) user.get("display_name");
	}

}

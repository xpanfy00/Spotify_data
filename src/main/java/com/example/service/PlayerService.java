package com.example.service;

import com.example.constant.UrlPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final RestTemplate restTemplate;
    private final ProfileDevicesService device;

    public LinkedHashMap getPlay(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        var deviceId = (ArrayList) device.getDevices(token).get("devices");
        var idMap = (LinkedHashMap) deviceId.get(0);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<Object> response = restTemplate.exchange(UrlPath.PLAYER+ "/volume" + "?device_id=" + idMap.get("id") + "&volume_percent=15", HttpMethod.PUT, entity, Object.class);
        LinkedHashMap result = (LinkedHashMap) response.getBody();
        return result;
    }
}

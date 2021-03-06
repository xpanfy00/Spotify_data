package com.example.service;

import com.example.constant.UrlPath;
import com.example.exception.NoTrackPlayingException;
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
public class CurrentPlayingService {

    private final RestTemplate restTemplate;

    public LinkedHashMap getCurrentPlaying(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<Object> response = restTemplate.exchange(UrlPath.CURRENTLY_PLAYING, HttpMethod.GET, entity, Object.class);
        if (response.getStatusCodeValue() == 204) {
            throw new NoTrackPlayingException();
        }
        LinkedHashMap result = (LinkedHashMap) response.getBody();
        return result;
    }
}

package com.example.service;

import com.example.constant.UrlPath;
import com.example.dto.AccessTokenDto;
import com.example.properties.SpotifyAppConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(value = SpotifyAppConfigurationProperties.class)
public class AccessTokenService {
    private final SpotifyUrlService spotifyUrlService;
    private final RestTemplate restTemplate;
    private final SpotifyAppConfigurationProperties spotifyAppConfigurationProperties;

    public String getToken(String code){
        final var properties = spotifyAppConfigurationProperties.getApp();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", properties.getClientId());
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", properties.getRedirectUrl());
        map.add("code_verifier", spotifyUrlService.getCodeVerifier());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<AccessTokenDto> response = restTemplate.postForEntity(UrlPath.TOKEN, request, AccessTokenDto.class);
        return response.getBody().getAccess_token();
    }
}

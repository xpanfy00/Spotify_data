package com.example.service;

import com.example.properties.SpotifyAppConfigurationProperties;
import com.example.utility.CodeChallengeUtility;
import com.example.utility.CodeVerifierUtility;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import java.util.UUID;

import java.util.Random;

@Data
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(SpotifyAppConfigurationProperties.class)
public class SpotifyUrlService {

    private final SpotifyAppConfigurationProperties spotifyAppConfigurationProperties;
    private String codeVerifier;

    public String getAuthorizationURL() {
        final var properties = spotifyAppConfigurationProperties.getApp();
        final var codeVerifier = CodeVerifierUtility.generate();
        setCodeVerifier(codeVerifier);
        final var state = UUID.randomUUID().toString();

        return "https://accounts.spotify.com/en/authorize?client_id=" + properties.getClientId()
                + "&response_type=code&redirect_uri=" + properties.getRedirectUrl()
                + "&code_challenge_method=S256&code_challenge=" + CodeChallengeUtility.generate(codeVerifier)
                + properties.getScope()
                + "&state=" + state;
    }

}

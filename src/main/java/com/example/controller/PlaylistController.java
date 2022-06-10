package com.example.controller;

import com.example.constant.ApiPath;
import com.example.constant.Template;
import com.example.exception.NoTrackPlayingException;
import com.example.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class PlaylistController {

    private final SpotifyUrlService spotifyUrlService;
    private final AccessTokenService accessToken;
    private final ProfileDetailService userDetails;

    private final PlaylistService playlist;





    @GetMapping(value = ApiPath.PLAYLIST, produces = MediaType.TEXT_HTML_VALUE)
    public String handleCallback(final HttpSession session, final Model model) {
        model.addAttribute("playlist", playlist.getPlaylist((String) session.getAttribute("accessToken")));
        var item = playlist.getPlaylist((String) session.getAttribute("accessToken"));
        System.out.println(item.keySet());
        return Template.PLAYLIST;
    }
}

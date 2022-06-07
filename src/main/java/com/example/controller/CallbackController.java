package com.example.controller;

import com.example.constant.ApiPath;
import com.example.constant.Template;
import com.example.exception.NoTrackPlayingException;
import com.example.service.AccessTokenService;
import com.example.service.CurrentPlayingService;
import com.example.service.ProfileDetailService;
import com.example.service.SpotifyUrlService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class CallbackController {

	private final SpotifyUrlService spotifyUrlService;
	private final AccessTokenService accessToken;
	private final ProfileDetailService userDetails;
	private final CurrentPlayingService currentPlaying;

	@GetMapping(value = ApiPath.CALLBACK, produces = MediaType.TEXT_HTML_VALUE)
	public String handleCallback(@RequestParam(value = "code", required = false) final String code,
			@RequestParam(value = "error", required = false) final String error, final Model model,
			final HttpSession session) {

		if (error != null) {
			model.addAttribute("url", spotifyUrlService.getAuthorizationURL());
			return Template.CALLBACK_FAILURE;
		}
		session.setAttribute("code", code);
		String token = accessToken.getToken(code);

		session.setAttribute("accessToken", token);
		model.addAttribute("accessToken", token);
		model.addAttribute("userName", userDetails.getUsername(token));

		try {
			model.addAttribute("currentPlaying", currentPlaying.getCurrentPlaying(token));
			model.addAttribute("display", 1);
		} catch (NoTrackPlayingException exception) {
			model.addAttribute("display", 0);
		}

		return Template.CALLBACK_SUCCESS;
	}
}

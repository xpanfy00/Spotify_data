package com.example.controller;

import com.example.constant.ApiPath;
import com.example.constant.Template;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorNotFoundController implements ErrorController {
    @RequestMapping(value = ApiPath.ERROR, produces = MediaType.TEXT_HTML_VALUE)
    public String handleError() {
        return Template.ERROR;
    }
}

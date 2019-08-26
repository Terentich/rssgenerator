package com.github.terentich.rssgenerator.web;

import com.github.terentich.rssgenerator.service.RssGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController(RssController.ROOT_PATH)
public class RssController {
    public static final String ROOT_PATH = "/rss";

    @Autowired
    private RssGenerator rssGenerator;

    @RequestMapping(path = "**", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public String createRss(HttpServletRequest request) throws Exception {
        String url = "http://" + request.getRequestURI().replace(ROOT_PATH + "/", "");
        return rssGenerator.createRss(url);
    }
}
package com.github.terentich.rssgenerator.web;

import com.github.terentich.rssgenerator.service.RssGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RssController {
    @Autowired
    private RssGenerator rssGenerator;

    @RequestMapping("/{url}")
    @ResponseBody
    public String createRss(@PathVariable("url") String url) throws Exception {
        return rssGenerator.createRss(url);
    }
}
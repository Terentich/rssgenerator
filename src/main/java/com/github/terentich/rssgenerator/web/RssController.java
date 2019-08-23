package com.github.terentich.rssgenerator.web;

import com.github.terentich.rssgenerator.service.RssGenerator;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RssController {
    @Autowired
    private RssGenerator rssGenerator;

//    @GetMapping("/{url}")
    @RequestMapping("/{url}")
    @ResponseBody
    public String getAppName(@PathVariable("url") String url) throws FeedException {
        return rssGenerator.createRss(url);
    }
}
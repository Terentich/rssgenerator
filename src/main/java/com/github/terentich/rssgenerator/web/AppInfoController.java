package com.github.terentich.rssgenerator.web;

import com.github.terentich.rssgenerator.model.AppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppInfoController {
    @Autowired
    private AppInfo appInfo;

    @GetMapping("/info")
    public AppInfo getAppName() {
        return appInfo;
    }
}
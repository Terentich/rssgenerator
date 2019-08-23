package com.github.terentich.rssgenerator.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppInfo {
    @Value("${spring.application.name}")
    String appName;
}

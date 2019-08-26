package com.github.terentich.rssgenerator;

import com.github.terentich.rssgenerator.service.DefaultRssGenerator;
import com.github.terentich.rssgenerator.service.MosFMConverter;
import com.github.terentich.rssgenerator.service.RssConverter;
import com.github.terentich.rssgenerator.service.RssGenerator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;

@TestConfiguration
public class TestConfig {
    @Bean
    public RssGenerator createRssGenerator() {
        return new DefaultRssGenerator();
    }

    @Bean
    public List<RssConverter> createConverters() {
        return Collections.singletonList(new MosFMConverter());
    }
}
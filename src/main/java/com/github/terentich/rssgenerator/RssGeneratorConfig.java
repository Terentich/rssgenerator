package com.github.terentich.rssgenerator;

import com.github.terentich.rssgenerator.service.MosFMConverter;
import com.github.terentich.rssgenerator.service.RssConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class RssGeneratorConfig {
    @Bean
    public List<RssConverter> createConverters() {
        return Collections.singletonList(new MosFMConverter());
    }
}
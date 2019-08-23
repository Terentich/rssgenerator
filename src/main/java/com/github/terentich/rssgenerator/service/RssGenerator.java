package com.github.terentich.rssgenerator.service;

import com.rometools.rome.io.FeedException;
import org.springframework.stereotype.Service;

public interface RssGenerator {
    String createRss(String url) throws FeedException;
}

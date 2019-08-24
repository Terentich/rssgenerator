package com.github.terentich.rssgenerator.service;

import com.rometools.rome.io.FeedException;

import java.io.IOException;

public interface RssGenerator {
    String createRss(String url) throws FeedException, IOException;
}

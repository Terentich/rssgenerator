package com.github.terentich.rssgenerator.service;

import com.github.terentich.rssgenerator.model.FeedItem;
import org.jsoup.nodes.Document;

import java.util.List;

public interface RssConverter {
    String getSiteUrl();

    List<FeedItem> getFeedItems(Document doc);
}
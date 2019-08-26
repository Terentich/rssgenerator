package com.github.terentich.rssgenerator.service;

import com.github.terentich.rssgenerator.model.FeedItem;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DefaultRssGenerator implements RssGenerator {
    private static final String ATOM_FEED_TYPE = "atom_1.0";

    @Autowired
    private List<RssConverter> converters;

    @Override
    public String createRss(String url) throws FeedException, IOException {
        log.info("Create RSS for URL: {}", url);

        String host = new URL(url).getHost();
        Document doc = Jsoup.connect(url).get();
        String title = doc.title();

        List<FeedItem> entries = converters.stream()
                .filter(converter -> converter.getSiteUrl().equals(host))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported url: %s\n " +
                        "Available converters urls: %s", url, converters.stream().map(RssConverter::getSiteUrl))))
                .getFeedItems(doc);
        return createRss(url, title, entries);
    }

    private String createRss(String url, String title, List<FeedItem> feedItems) throws FeedException {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType(ATOM_FEED_TYPE);
        feed.setTitle(title);
        feed.setLink(url);
//        feed.setDescription(title);

        List<SyndEntry> rssEntries = feedItems.stream()
                .map(this::createRssEntry)
                .collect(Collectors.toList());

        feed.setEntries(rssEntries);
        return new SyndFeedOutput().outputString(feed);
    }

    private SyndEntry createRssEntry(FeedItem feedItem) {
        SyndEntry entry = new SyndEntryImpl();
        entry.setTitle(feedItem.getTitle());
        entry.setUri(feedItem.getLink());
        entry.setPublishedDate(Date.from(feedItem.getDate().atZone(ZoneId.systemDefault()).toInstant()));

        SyndContent content = new SyndContentImpl();
        content.setValue(feedItem.getContent());
        entry.setContents(Collections.singletonList(content));
        return entry;
    }
}

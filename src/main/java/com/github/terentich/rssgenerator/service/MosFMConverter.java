package com.github.terentich.rssgenerator.service;

import com.github.terentich.rssgenerator.model.FeedItem;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MosFMConverter implements RssConverter {
    private DateTimeFormatter MOSFM_PROGRAM_FORMATTER = DateTimeFormatter.ofPattern("yyyy dd.MM HH:mm");

    @Override
    public String getSiteUrl() {
        return "mosfm.com";
    }

    @Override
    public List<FeedItem> getFeedItems(Document doc) {
        String episodesSelector = "div.RubricsProgramsList li";

        return doc.select(episodesSelector)
                .stream()
                .map(this::createFeedItem)
                .collect(Collectors.toList());
    }

    private FeedItem createFeedItem(Element e) {
        String entryTitle = e.select("p.Title a").text();
        String entryUrl = e.select("p.Title a").attr("href");
        String entryImage = e.select("img").attr("src");
        LocalDateTime entryDate = LocalDateTime.parse("2019 " + e.select("p.Date").text(), MOSFM_PROGRAM_FORMATTER);

        return FeedItem.builder()
                .title(entryTitle)
                .link(entryUrl)
                .date(entryDate)
                .content(entryImage)
                .build();
    }
}
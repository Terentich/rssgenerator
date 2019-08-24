package com.github.terentich.rssgenerator.service;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import lombok.Builder;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Try ASCII doctor:
//  https://www.baeldung.com/asciidoctor,
//  https://asciidoctor.org/docs/asciidoctor-maven-plugin/
//  https://asciidoctor.org/docs/asciidoctor-diagram/
//  https://github.com/asciidoctor/asciidoctor-maven-examples/blob/master/asciidoctor-diagram-example/README.adoc
//  https://asciidoctor.org/news/2014/02/18/plain-text-diagrams-in-asciidoctor/


@Service
public class DefaultRssGenerator implements RssGenerator {
    public static void main(String[] args) throws Exception {
        String result = new DefaultRssGenerator().createRss("https://www.mosfm.com/programs/7");
        System.out.println("RSS: \n" + result);
    }

    @Override
    public String createRss(String url) throws FeedException, IOException {
        Document doc = Jsoup.connect(url).get();
        String title = doc.title();

        Elements episods = doc.select("div.RubricsProgramsList");
        List<Entry> entries = episods.select("li")
                .stream()
                .map(e -> {
                    Elements entry = e.select("p.Title");
                    String entryTitle = entry.select("a").text();
                    String entryUrl = entry.select("a").attr("href");
                    String entryImage = e.select("img").attr("src");

                    String entryDate = e.select("p.Date").text();
                    System.out.println("===================");
                    System.out.println("Entry: " + entryTitle);
                    System.out.println("Url: " + entryUrl);
                    System.out.println("Date: " + entryDate);
                    System.out.println("Image: " + entryImage);
                    return Entry.builder()
                            .title(entryTitle)
                            .link(entryUrl)
                            .date(entryDate)
                            .content(entryImage)
                            .build();
                })
                .collect(Collectors.toList());

        return createRss(url, title, entries);
    }

    @Data
    @Builder
    static class Entry {
        String title;
        String link;
        String date;
        String content;
    }

    private String createRss(String url, String title, List<Entry> entries) throws FeedException {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("atom_1.0");
        feed.setTitle(title);
        feed.setLink(url);
//        feed.setDescription(title);

        List<SyndEntry> syndEntries = entries.stream()
                .map(e -> {
                    SyndEntry entry = new SyndEntryImpl();
                    entry.setTitle(e.title);
                    entry.setUri(e.link);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy dd.MM HH:mm");
                    entry.setPublishedDate(Date.from(
                            LocalDateTime.parse("2019 " + e.date, formatter)
                                    .atZone(ZoneId.systemDefault()).toInstant()));

                    SyndContent content = new SyndContentImpl();
                    content.setValue(e.content);
                    entry.setContents(Collections.singletonList(content));
                    return entry;
                }).collect(Collectors.toList());

        feed.setEntries(syndEntries);
        return new SyndFeedOutput().outputString(feed);
    }
}

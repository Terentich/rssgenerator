package com.github.terentich.rssgenerator.service;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DefaultRssGenerator implements RssGenerator {
    @Override
    public String createRss(String url) throws FeedException {
//        SyndFeed feed = new SyndFeedImpl();
//        feed.setFeedType("rss_1.0");
//        feed.setTitle("Test title");
//        feed.setLink("http://www.somelink.com");
//        feed.setDescription("Basic description");
//        return url + " blalbalb";

        // TODO: Try ASCII doctor:
        //  https://www.baeldung.com/asciidoctor,
        //  https://asciidoctor.org/docs/asciidoctor-maven-plugin/
        //  https://asciidoctor.org/docs/asciidoctor-diagram/
        //  https://github.com/asciidoctor/asciidoctor-maven-examples/blob/master/asciidoctor-diagram-example/README.adoc
        //  https://asciidoctor.org/news/2014/02/18/plain-text-diagrams-in-asciidoctor/

        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("atom_1.0");

        SyndEntry entry = new SyndEntryImpl();
        entry.setTitle("Sample feed title");

        SyndContent content = new SyndContentImpl();
        content.setValue("Sample feed content");

        entry.setContents(Collections.singletonList(content));
        feed.setEntries(Collections.singletonList(entry));

        return new SyndFeedOutput().outputString(feed);
    }
}

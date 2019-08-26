package com.github.terentich.rssgenerator.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class FeedItem {
    String title;
    String link;
    LocalDateTime date;
    String content;
}

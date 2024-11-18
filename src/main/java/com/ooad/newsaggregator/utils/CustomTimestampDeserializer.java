package com.ooad.newsaggregator.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomTimestampDeserializer extends JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        String dateString = parser.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        return localDateTime.toInstant(java.time.ZoneOffset.UTC);
    }
}

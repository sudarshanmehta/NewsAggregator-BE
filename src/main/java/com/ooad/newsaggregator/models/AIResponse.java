package com.ooad.newsaggregator.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIResponse {
    private String sentiment;
    private String summary;
}

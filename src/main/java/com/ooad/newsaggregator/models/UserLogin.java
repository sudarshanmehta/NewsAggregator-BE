package com.ooad.newsaggregator.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
    private String userId;
    private String email;
    private String password;
}


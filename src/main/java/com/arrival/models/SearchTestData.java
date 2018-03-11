package com.arrival.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchTestData {
    private String description;
    private String url;
    private String searchPattern;
    private int expectedStatusCode;
    private String expectedTitle;
    private int expectedResultSize;
}

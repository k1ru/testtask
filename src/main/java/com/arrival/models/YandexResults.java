package com.arrival.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jsoup.select.Elements;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class YandexResults {
    private int statusCode;
    private Elements title;
    private Elements results;
    private Elements href;

}
